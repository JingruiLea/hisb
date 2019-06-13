package edu.neu.his.bean.medicalRecord;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MedicalRecordTemplateService {
    @Autowired
    private MedicalRecordTemplateMapper medicalRecordTemplateMapper;

    @Transactional
    public List<MedicalRecordTemplate> selectByUserId(int id){
        return medicalRecordTemplateMapper.selectByUserId(id,MedicalRecordStatus.SelectByUserId);
    }

    @Transactional
    public List<MedicalRecordTemplate> selectByDepartmentId(int id){
        return medicalRecordTemplateMapper.selectByDepartmentId(id,MedicalRecordStatus.SelectByDepartmentId);
    }

    @Transactional
    public List<MedicalRecordTemplate> SelectAll(){
        return medicalRecordTemplateMapper.selectAll();
    }

    @Transactional
    public MedicalRecordTemplate selectById(int id){
        return medicalRecordTemplateMapper.selectByPrimaryKey(id);
    }

    @Transactional
    public List<MedicalRecordTemplate> selectByName(String name){
        return medicalRecordTemplateMapper.selectByName(name);
    }

    @Transactional
    public int insert(MedicalRecordTemplate medicalRecordTemplate){
        return medicalRecordTemplateMapper.insert(medicalRecordTemplate);
    }

    @Transactional
    public int update(MedicalRecordTemplate medicalRecordTemplate){
        return medicalRecordTemplateMapper.updateByPrimaryKey(medicalRecordTemplate);
    }

    @Transactional
    public int delete(int id){
        return medicalRecordTemplateMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public List<MedicalRecordTemplate> selectByUser(int uid, int department_id){
        List<MedicalRecordTemplate> list = medicalRecordTemplateMapper.selectByDepartmentId(department_id,MedicalRecordStatus.SelectByDepartmentId);
        List<MedicalRecordTemplate> listByUserId = medicalRecordTemplateMapper.selectByUserId(uid,MedicalRecordStatus.SelectByUserId);
        List<MedicalRecordTemplate> listByType = medicalRecordTemplateMapper.selectByType(MedicalRecordStatus.SelectAll);
        list.addAll(listByUserId);
        list.addAll(listByType);
        return list;
    }

    @Transactional
    public List<MedicalRecordTemplate> SelectByType(int type){
        return medicalRecordTemplateMapper.selectByType(type);
    }
}
