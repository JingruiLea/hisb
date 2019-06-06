package edu.neu.his.controller.registeredTollCollectorController;

import edu.neu.his.bean.*;
import edu.neu.his.config.BillRecordStatus;
import edu.neu.his.config.OperateStatus;
import edu.neu.his.config.RegistrationConfig;
import edu.neu.his.config.Response;
import edu.neu.his.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//4.1 挂号
@RestController
@RequestMapping("/outpatientRegistration")
public class OutpatientRegistrationController {

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private RegistrationLevelService registrationLevelService;

    @Autowired
    private SettlementCategoryService settlementCategoryService;

    @Autowired
    private OutpatientRegistrationService outpatientRegistrationService;

    @Autowired
    private ExpenseClassificationService expenseClassificationService;

    @Autowired
    private BillRecordService billRecordService;

    @Autowired
    private OperateLogService operateLogService;

    @GetMapping("/init")
    @ResponseBody
    public Map init(){
        Map data = new HashMap();
        data.put("departments", departmentService.findAll());
        data.put("defaultRegistrationLevel",registrationLevelService.findDefault());
        data.put("registrationLevel",registrationLevelService.findAll());
        data.put("settlementCategory",settlementCategoryService.findAll());
        return Response.Ok(data);
    }

    @PostMapping("/syncDoctorList")
    @ResponseBody
    public Map syncDoctorList(@RequestBody Map req){
        int department_id = (int)req.get("department_id");
        int registration_level_id = (int)req.get("registration_level_id");
        String title = hash2Title(registration_level_id);
        if(title==null)
            return Response.Error("错误 挂号等级不存在");
        else {
            List<User> users = outpatientRegistrationService.findByDepartmentAndRegistrationLevel(department_id,title);
            for(int i=0; i<users.size(); i++)
                users.get(i).setPassword(null);
            return Response.Ok(users);
        }
    }

    @PostMapping("/calculateFee")
    @ResponseBody
    public Map calulateFee(@RequestBody Map req){
        Map data = new HashMap();
        int registration_level_id = (int)req.get("registration_level_id");
        RegistrationLevel registration_level = registrationLevelService.findById(registration_level_id);
        if(registration_level!=null) {
            float fee = registration_level.getFee();
            int has_record_book = (int) req.get("has_record_book");
            if (has_record_book == 1)
                fee++;

            data.put("fee", fee);
            return Response.Ok(data);
        }else
            return Response.Error("错误，挂号类别不存在");
    }

    @PostMapping("/confirm")
    @ResponseBody
    public Map confirm(@RequestBody Map req){
        Map data = new HashMap();
        Registration registration = req2Registration(req);
        int registration_level_id = (int)req.get("registration_level_id");
        RegistrationLevel registration_level = registrationLevelService.findById(registration_level_id);
        if(registration_level==null)
            return Response.Error("错误，挂号类别不存在");

        String registration_category = registration_level.getName();
        float fee = registration_level.getFee();
        int has_record_book = (int)req.get("has_record_book");
        if(has_record_book==1)
            fee++;
        registration.setCost(fee);
        registration.setRegistration_category(registration_category);
        registration.setStatus(RegistrationConfig.registrationAvailable);

        //挂号记录
        int medical_record_number = outpatientRegistrationService.insertRegistration(registration);
        data.put("medical_record_number", medical_record_number);//病历号

        //票据记录
        String billType = BillRecordStatus.Charge;
        BillRecord billRecord = reqToBillRecord(req,medical_record_number,billType,fee);
        if(billRecord==null)
            return Response.Error("错误，费用类型不存在");
        int bill_record_id = billRecordService.insertBillRecord(billRecord);
        data.put("bill",billRecord);

        //操作记录
        String operateType = OperateStatus.Register;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String create_time = df.format(new Date());
        OperateLog operateLog = new OperateLog((int)req.get("_uid"),medical_record_number,operateType,bill_record_id,fee,create_time);
        operateLogService.insertOperateLog(operateLog);

        return  Response.Ok(data);
    }

    @PostMapping("/withdrawNumber")
    @ResponseBody
    public Map withdrawNumber(@RequestBody Map req){
        int medical_record_id = (int)req.get("medical_record_id");
        int uid = (int)req.get("_uid");

        Registration registration = outpatientRegistrationService.findRegistrationById(medical_record_id);
        if(registration==null){
            return Response.Error("该病历号不存在");
        }else if(registration.getStatus().equals(RegistrationConfig.registrationAvailable)) {
            //退号
            registration.setStatus(RegistrationConfig.registrationCanceled);
            outpatientRegistrationService.updateStatus(registration);

            //票据冲正
            String billType = BillRecordStatus.Refund;
            BillRecord billRecord = registrationToWithdrawBill(registration,billType,uid);
            if(billRecord==null)
                return Response.Error("错误，费用类型不存在");
            int bill_record_id = billRecordService.insertBillRecord(billRecord);

            //操作冲正
            String operateType = OperateStatus.Cancel;
            OperateLog operateLog = registrationToWithdrawOperateLog(registration,operateType,bill_record_id,uid);
            operateLogService.insertOperateLog(operateLog);

            return Response.Ok();
        }else {
            return Response.Error("不可退号");
        }
    }

    private String hash2Title(int registration_level_id){
        RegistrationConfig.initTitleMap();
        String title = null;
        if(RegistrationConfig.titleMap.containsKey(registration_level_id))
            title = RegistrationConfig.titleMap.get(registration_level_id);
        return title;
    }

    private Registration req2Registration(Map req){
        String address;
        if(!req.containsKey("address"))
            address = "";
        else
            address = (String)req.get("address");

        int age = (int)req.get("age");
        String birthday =(String)req.get("birthday");
        String consultation_date = (String)req.get("consultation_date");
        String medicial_category = (String)req.get("medical_category");
        String patient_name = (String)req.get("name");
        int outpatient_doctor_id = (int)req.get("outpatent_doctor_id");
        int registration_department_id = (int)req.get("department_id");
        int settlement_category_id = (int)req.get("settlement_category_id");
        String registraton_source = (String)req.get("registration_source");
        String gender = (String)req.get("gender");
        String medical_insurance_diagnosis = (String)req.get("medical_insurance_diagnosis");
        String medical_certificate_number_type = (String)req.get("medical_certificate_number_type");
        String id_number,medicial_certificate_number;
        if(medical_certificate_number_type.equals("id")){
            id_number = (String)req.get("medical_certificate_number");
            medicial_certificate_number = "";
        }
        else {
            medicial_certificate_number = (String)req.get("medical_certificate_number");
            id_number = "";
        }

        Registration registration = new Registration(address,age,birthday,consultation_date,medicial_category,patient_name,
                outpatient_doctor_id,registration_department_id,settlement_category_id,registraton_source,gender,
                medical_insurance_diagnosis,id_number,medicial_certificate_number);

        return registration;
    }

    private BillRecord reqToBillRecord(Map req,int medical_record_number,String type,float fee){
        BillRecord billRecord = new BillRecord();
        billRecord.setTruely_pay((float)req.get("truely_pay"));
        billRecord.setShould_pay((float)req.get("should_pay"));
        billRecord.setRetail_fee((float)req.get("retail_fee"));
        billRecord.setUser_id((int)req.get("_uid"));
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        billRecord.setCreat_time(df.format(new Date()));
        billRecord.setMedical_record_id(medical_record_number);
        billRecord.setType(type);
        billRecord.setCost(fee);


        return billRecord;
    }

    private BillRecord registrationToWithdrawBill(Registration registration, String type,int uid){
        BillRecord billRecord = new BillRecord();
        billRecord.setCost(0-registration.getCost());
        billRecord.setMedical_record_id(registration.getMedical_record_id());
        billRecord.setUser_id(uid);
        billRecord.setType(type);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        billRecord.setCreat_time(df.format(new Date()));

        return billRecord;
    }

    private OperateLog registrationToWithdrawOperateLog(Registration registration, String type,int bill_record_id, int uid){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String create_time = df.format(new Date());
        int medical_record_number = registration.getMedical_record_id();
        float fee = 0 - registration.getCost();

        OperateLog operateLog = new OperateLog(uid,medical_record_number,type,bill_record_id,fee,create_time);

        return operateLog;
    }
}

