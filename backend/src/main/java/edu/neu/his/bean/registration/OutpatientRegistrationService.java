package edu.neu.his.bean.registration;

import edu.neu.his.bean.billRecord.BillRecord;
import edu.neu.his.bean.billRecord.BillRecordService;
import edu.neu.his.bean.billRecord.BillRecordStatus;
import edu.neu.his.bean.operateLog.OperateLog;
import edu.neu.his.bean.operateLog.OperateLogService;
import edu.neu.his.bean.operateLog.OperateStatus;
import edu.neu.his.bean.user.User;
import edu.neu.his.bean.user.UserService;
import edu.neu.his.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 实现处理数据库中registration、bill_record、operate_log表的相关操作
 */
@Service
public class OutpatientRegistrationService {
    @Autowired
    private OutpatientRegistrationMapper outpatientRegistrationMapper;

    @Autowired
    private BillRecordService billRecordService;

    @Autowired
    private OperateLogService operateLogService;

    @Autowired
    private UserService userService;

    /**
     * 从数据库中根据科室id和挂号等级id找到对应的挂号记录
     * @param department_id 科室id
     * @param registration_level_id 挂号等级id
     * @return 根据科室id和挂号等级id找到的对应挂号记录
     */
    @Transactional
    public List<User> findByDepartmentAndRegistrationLevel(int department_id, int registration_level_id){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        //System.out.println(df.format(new Date()));
        String curr_date = df.format(new Date());
        return outpatientRegistrationMapper.findByDepartmentAndTitle(department_id,registration_level_id,curr_date);
    }

    /**
     * 向数据库中插入一条挂号记录
     * @param registration 挂号信息
     * @return 病历号id
     */
    @Transactional
    public int insertRegistration(Registration registration){
        outpatientRegistrationMapper.insert(registration);
        return  registration.getMedical_record_id();
    }

    /**
     * 从数据库中根据病历号找到对应的挂号记录
     * @param medical_record_id 病历号
     * @return 根据病历号找到的对应挂号记录
     */
    @Transactional
    public Registration findRegistrationById(int medical_record_id){
        return outpatientRegistrationMapper.findRegistrationById(medical_record_id);
    }

    /**
     * 更新挂号状态
     * @param registration 挂号信息
     */
    @Transactional
    public void updateStatus(Registration registration){
       outpatientRegistrationMapper.update(registration);
    }

    /**
     * 从数据库中根据医生找到对应的挂号记录
     * @param id 医生id
     * @return 根据医生找到的对应挂号记录
     */
    @Transactional
    public List<Registration> findByDoctor(int id){
        return outpatientRegistrationMapper.findRegistrationByDoctor(id);
    }

    /**
     * 从数据库中根据患者姓名找到对应的病历号
     * @param name 患者姓名
     * @return 根据患者姓名找到的对应病历号
     */
    @Transactional
    public List<Integer> findMedicalRecordIdByName(String name){
        return outpatientRegistrationMapper.findMedicalRecordIdByName(name);
    }

    /**
     * 退号
     * @param registration 挂号记录
     * @param uid 用户id
     */
    @Transactional
    public void cancelRegistration(Registration registration, int uid){
        //退号
        registration.setStatus(RegistrationConfig.registrationCanceled);
        updateStatus(registration);

        //冲正票据记录
        String billType = BillRecordStatus.Refund;
        BillRecord billRecord = registrationToWithdrawBill(registration,billType,uid);
        int bill_record_id = billRecordService.insertBillRecord(billRecord);

        //添加操作记录
        String operateType = OperateStatus.Cancel;
        OperateLog operateLog = registrationToWithdrawOperateLog(registration,operateType,bill_record_id,uid);
        operateLogService.insertOperateLog(operateLog);
    }

    /**
     * 挂号
     * @param registration 挂号信息
     * @param req 包含票据记录和科室id
     * @param fee 费用
     * @param uid 用户id
     * @return 挂号信息
     */
    @Transactional
    public Map createRegistration(Registration registration, Map req, float fee, int uid){
        Map data = new HashMap();
        String create_time = Utils.getSystemTime();

        //创建票据记录
        BillRecord billRecord = Utils.fromMap(req,BillRecord.class);
        if(billRecord==null)
            return null;

        //挂号记录
        registration.setRegistration_department_id((int)req.get("department_id"));
        int medical_record_number = insertRegistration(registration);
        data.put("medical_record_number", medical_record_number);//病历号

        //设置票据记录的字段
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

        return data;
    }

    /**
     * 退号时添加操作记录
     * @param registration 挂号信息
     * @param type 操作类型
     * @param bill_record_id 票据记录id
     * @param uid 用户id
     * @return 新增的操作记录
     */
    private OperateLog registrationToWithdrawOperateLog(Registration registration, String type,int bill_record_id, int uid){
        String create_time = Utils.getSystemTime();
        int medical_record_number = registration.getMedical_record_id();
        float fee = 0 - registration.getCost();

        OperateLog operateLog = new OperateLog(uid,medical_record_number,type,bill_record_id,fee,create_time);

        return operateLog;
    }

    /**
     * 退号时添加票据记录
     * @param registration 挂号信息
     * @param type 票据类型
     * @param uid 用户id
     * @return 新增的票据记录
     */
    private BillRecord registrationToWithdrawBill(Registration registration, String type,int uid){
        BillRecord billRecord = new BillRecord();
        billRecord.setCost(0-registration.getCost());
        billRecord.setMedical_record_id(registration.getMedical_record_id());
        billRecord.setUser_id(uid);
        billRecord.setType(type);
        billRecord.setCreate_time(Utils.getSystemTime());

        return billRecord;
    }
}
