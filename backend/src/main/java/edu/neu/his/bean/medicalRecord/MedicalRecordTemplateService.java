package edu.neu.his.bean.medicalRecord;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 实现处理数据库中medical_record_template表的相关操作
 */
@Service
public class MedicalRecordTemplateService {
    @Autowired
    private MedicalRecordTemplateMapper medicalRecordTemplateMapper;

    /**
     * 查找数据库中所有病历模版的列表
     * @return 查找到的所有病历模版列表
     */
    @Transactional
    public List<MedicalRecordTemplate> selectAll(){
        return medicalRecordTemplateMapper.selectAll();
    }

    /**
     * 从数据库中根据id找到对应的病历模版
     * @param id 病历模版id
     * @return 根据id找到的对应病历模版
     */
    @Transactional
    public MedicalRecordTemplate selectById(int id){
        return medicalRecordTemplateMapper.selectByPrimaryKey(id);
    }

    /**
     * 从数据库中根据名称找到对应的病历模版
     * @param title 病历模版名称
     * @return 根据名称找到的对应病历模版
     */
    @Transactional
    public List<MedicalRecordTemplate> selectByTitle(String title){
        return medicalRecordTemplateMapper.selectByTitle(title);
    }

    /**
     * 向数据库中插入一条病历模版记录
     * @param medicalRecordTemplate 要插入数据库中的MedicalRecordTemplate对象
     * @return 病历模版id
     */
    @Transactional
    public int insert(MedicalRecordTemplate medicalRecordTemplate){
        medicalRecordTemplateMapper.insert(medicalRecordTemplate);
        return medicalRecordTemplate.getId();
    }

    /**
     * 更新数据库中的一条相应的病历模版记录
     * @param medicalRecordTemplate 要进行更新的MedicalRecordTemplate对象
     * @return 病历模版id
     */
    @Transactional
    public int update(MedicalRecordTemplate medicalRecordTemplate){
        return medicalRecordTemplateMapper.updateByPrimaryKey(medicalRecordTemplate);
    }

    /**
     * 根据id从数据库中删除对应病历模版
     * @param id 要删除的病历模版id
     * @return 删除的病历模版id
     */
    @Transactional
    public int delete(int id){
        return medicalRecordTemplateMapper.deleteByPrimaryKey(id);
    }

    /**
     * 从数据库中根据用户id找到对应的病历模版列表
     * @param uid 用户id
     * @return 根据用户id找到的对应病历模版列表
     */
    @Transactional
    public List<MedicalRecordTemplate> selectByUser(int uid){
        List<MedicalRecordTemplate> listByUserId = medicalRecordTemplateMapper.selectByUserId(uid,MedicalRecordStatus.Personal);
        return listByUserId;
    }

    /**
     * 从数据库中根据科室id找到对应的病历模版列表
     * @param department_id 科室id
     * @return 根据科室id找到的对应病历模版列表
     */
    @Transactional
    public List<MedicalRecordTemplate> selectByDepartment(int department_id){
        List<MedicalRecordTemplate> list = medicalRecordTemplateMapper.selectByDepartmentId(department_id,MedicalRecordStatus.Department);
        return list;
    }

    /**
     * 从数据库中根据类型找到对应的病历模版列表
     * @param type 类型
     * @return 根据类型找到的对应病历模版列表
     */
    @Transactional
    public List<MedicalRecordTemplate> selectByType(int type){
        return medicalRecordTemplateMapper.selectByType(type);
    }
}
