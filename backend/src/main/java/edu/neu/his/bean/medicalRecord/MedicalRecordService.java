package edu.neu.his.bean.medicalRecord;

import edu.neu.his.bean.registration.Registration;
import edu.neu.his.bean.registration.RegistrationConfig;
import edu.neu.his.bean.registration.OutpatientRegistrationMapper;
import edu.neu.his.util.Common;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 实现处理数据库中medical_record表的相关操作
 */
@Service
public class MedicalRecordService {
    @Autowired
    private OutpatientRegistrationMapper outpatientRegistrationMapper;

    @Autowired
    private MedicalRecordMapper medicalRecordMapper;

    /**
     * 从数据库中根据证件类型和信息找到对应的病历列表
     * @param type 证件类型
     * @param number 信息
     * @return 根据证件类型和信息找到的对应病历列表
     */
    @Transactional
    public List<Registration> findHistory(String type, String number){
        if(type.equals("id"))
            return outpatientRegistrationMapper.findRegistrationByIdNumber(number);
        else
            return outpatientRegistrationMapper.findRegistrationByMedicalCertificateNumber(number);
    }

    /**
     * 根据病历状态id查找病历是否已提交
     * @param id 病历状态id
     * @return 是否已提交
     */
    @Transactional
    public boolean medicalRecordHasSubmit(int id){
        String medicalRecordStatus = this.getStatusById(id);
        if(Common.YITIJIAO.equals(medicalRecordStatus)){
            return true;
        }
        return false;
    }

    /**
     * 从数据库中根据证件类型、信息和病历状态找到对应的病历列表
     * @param type 证件类型
     * @param number 信息
     * @param status 病历状态
     * @return 根据证件类型、信息和病历状态找到的对应病历列表
     */
    @Transactional
    public List<Registration> find(String type, String number, String status){
        if(type.equals("id"))
            return outpatientRegistrationMapper.findRegistrationByIdNumberAndStatus(number, status);
        else
            return outpatientRegistrationMapper.findRegistrationByMedicalCertificateNumberAndStatus(number, status);
    }

    /**
     * 从数据库中根据病历号找到对应的病历
     * @param medical_record_id 病历号
     * @return 根据病历号找到的对应病历
     */
    @Transactional
    public MedicalRecord findMedicalRecordById(int medical_record_id){
        return medicalRecordMapper.selectByPrimaryKey(medical_record_id);
    }

    /**
     * 向数据库中插入一条病历记录
     * @param medicalRecord 要插入数据库中的MedicalRecord对象
     * @return 插入的病历id
     */
    @Transactional
    public int insertMedicalRecord(MedicalRecord medicalRecord){
        medicalRecordMapper.insert(medicalRecord);
        return medicalRecord.getId();
    }

    /**
     * 更新数据库中的一条相应的病历记录
     * @param medicalRecord 要进行更新的MedicalRecord对象
     * @return 更新的病历id
     */
    @Transactional
    public int updateMedicalRecord(MedicalRecord medicalRecord){
        return medicalRecordMapper.updateByPrimaryKey(medicalRecord);
    }

    /**
     * 从数据库中根据病历状态id找到对应的病历状态
     * @param id 病历状态id
     * @return 根据病历状态id找到的对应病历状态
     */
    @Transactional
    public String getStatusById(Integer id){
        MedicalRecord medicalRecord = medicalRecordMapper.selectByPrimaryKey(id);
        if(medicalRecord == null){
            return null;
        }
        return medicalRecord.getStatus();
    }

    /**
     * 判断是否可以打开病历
     * @param medical_record_id 病历号
     * @return 是否可以打开病历
     */
    @Transactional
    public boolean canOperateMedicalRecord(int medical_record_id){
        Registration registration = outpatientRegistrationMapper.findRegistrationById(medical_record_id);
        if(registration==null)
            return false;

        if(!registration.getStatus().equals(RegistrationConfig.registrationCanceled))
            return true;
        else return false;
    }

    /**
     * 判断病历是否已提交
     * @param id 病历号
     * @return 病历是否已提交
     */
    @Transactional
    public boolean hasSubmit(Integer id){
        MedicalRecord medicalRecord = medicalRecordMapper.selectByPrimaryKey(id);
        if(medicalRecord==null){
            return false;
        }
        if(!Common.YITIJIAO.equals(medicalRecord.getStatus())){
            return false;
        }
        return true;
    }
}
