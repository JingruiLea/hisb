package edu.neu.his.bean.registration;

import edu.neu.his.bean.billRecord.BillRecord;
import edu.neu.his.bean.billRecord.BillRecordService;
import edu.neu.his.bean.billRecord.BillRecordStatus;
import edu.neu.his.bean.department.DepartmentService;
import edu.neu.his.bean.expenseClassification.ExpenseClassificationService;
import edu.neu.his.bean.operateLog.OperateLog;
import edu.neu.his.bean.operateLog.OperateLogService;
import edu.neu.his.bean.operateLog.OperateStatus;
import edu.neu.his.bean.settlementCategory.SettlementCategoryService;
import edu.neu.his.bean.user.User;
import edu.neu.his.config.*;
import edu.neu.his.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping("/init")
    @ResponseBody
    public Map init(){
        Map data = new HashMap();
        data.put("departments", departmentService.findAll());
        data.put("defaultRegistrationLevel",registrationLevelService.findDefault());
        data.put("registrationLevel",registrationLevelService.findAll());
        data.put("settlementCategory",settlementCategoryService.findAll());
        return Response.ok(data);
    }

    @PostMapping("/syncDoctorList")
    @ResponseBody
    public Map syncDoctorList(@RequestBody Map req){
        int department_id = (int)req.get("department_id");
        int registration_level_id = (int)req.get("registration_level_id");
        String title = RegistrationConfig.hashToTitle(registration_level_id);
        if(title==null)
            return Response.error("错误 挂号等级不存在");
        else {
            List<User> users = outpatientRegistrationService.findByDepartmentAndRegistrationLevel(department_id,title);
            for(int i=0; i<users.size(); i++)
                users.get(i).setPassword(null);
            return Response.ok(users);
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
            return Response.ok(data);
        }else
            return Response.error("错误，挂号类别不存在");
    }

    @PostMapping("/confirm")
    @ResponseBody
    public Map confirm(@RequestBody Map req) {
        Map data = new HashMap();
        int uid = Auth.uid(req);
        Registration registration = Utils.fromMap(req,Registration.class);
        String medical_certificate_number_type = (String)req.get("medical_certificate_number_type");

        if(medical_certificate_number_type.equals("id")){
            registration.setId_number((String)req.get("medical_certificate_number"));
            registration.setMedical_certificate_number("");
        }
        if(registration.getAddress()==null)
            registration.setAddress("");
        if(registration.getRegistration_source()==null)
            registration.setRegistration_source((String)req.get("registration_source"));

        int registration_level_id = (int)req.get("registration_level_id");
        RegistrationLevel registration_level = registrationLevelService.findById(registration_level_id);
        if(registration_level==null)
            return Response.error("错误，挂号类别不存在");

        String registration_category = registration_level.getName();
        float fee = registration_level.getFee();
        int has_record_book = (int)req.get("has_record_book");
        if(has_record_book==1)
            fee++;
        registration.setCost(fee);
        registration.setRegistration_category(registration_category);
        registration.setStatus(RegistrationConfig.registrationAvailable);

        String create_time = Utils.getSystemTime();

        //挂号记录
        int medical_record_number = outpatientRegistrationService.insertRegistration(registration);
        data.put("medical_record_number", medical_record_number);//病历号

        //票据记录
        BillRecord billRecord = Utils.fromMap(req,BillRecord.class);

        if(billRecord==null)
            return Response.error("错误，票据记录创建失败");

        billRecord.setType(BillRecordStatus.Charge);
        billRecord.setCost(fee);
        billRecord.setMedical_record_id(medical_record_number);
        billRecord.setCreate_time(create_time);
        billRecord.setUser_id(uid);

        int bill_record_id = billRecordService.insertBillRecord(billRecord);
        data.put("bill",billRecord);

        //操作记录
        String operateType = OperateStatus.Register;
        OperateLog operateLog = new OperateLog(uid,medical_record_number,operateType,bill_record_id,fee,create_time);
        operateLogService.insertOperateLog(operateLog);

        return  Response.ok(data);
    }

    @PostMapping("/withdrawNumber")
    @ResponseBody
    public Map withdrawNumber(@RequestBody Map req){
        int medical_record_id = (int)req.get("medical_record_id");
        int uid = Auth.uid(req);

        Registration registration = outpatientRegistrationService.findRegistrationById(medical_record_id);
        if(registration==null){
            return Response.error("该病历号不存在");
        }else if(registration.getStatus().equals(RegistrationConfig.registrationAvailable)) {
            //退号
            registration.setStatus(RegistrationConfig.registrationCanceled);
            outpatientRegistrationService.updateStatus(registration);

            //票据冲正
            String billType = BillRecordStatus.Refund;
            BillRecord billRecord = registrationToWithdrawBill(registration,billType,uid);
            if(billRecord==null)
                return Response.error("错误，费用类型不存在");
            int bill_record_id = billRecordService.insertBillRecord(billRecord);

            //操作冲正
            String operateType = OperateStatus.Cancel;
            OperateLog operateLog = registrationToWithdrawOperateLog(registration,operateType,bill_record_id,uid);
            operateLogService.insertOperateLog(operateLog);

            return Response.ok();
        }else {
            return Response.error("不可退号");
        }
    }

    private BillRecord registrationToWithdrawBill(Registration registration, String type,int uid){
        BillRecord billRecord = new BillRecord();
        billRecord.setCost(0-registration.getCost());
        billRecord.setMedical_record_id(registration.getMedical_record_id());
        billRecord.setUser_id(uid);
        billRecord.setType(type);
        billRecord.setCreate_time(Utils.getSystemTime());

        return billRecord;
    }

    private OperateLog registrationToWithdrawOperateLog(Registration registration, String type,int bill_record_id, int uid){
        String create_time = Utils.getSystemTime();
        int medical_record_number = registration.getMedical_record_id();
        float fee = 0 - registration.getCost();

        OperateLog operateLog = new OperateLog(uid,medical_record_number,type,bill_record_id,fee,create_time);

        return operateLog;
    }
}