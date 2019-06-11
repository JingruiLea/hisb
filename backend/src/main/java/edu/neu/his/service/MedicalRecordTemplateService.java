package edu.neu.his.service;

import edu.neu.his.bean.MedicalRecordTemplate;
import edu.neu.his.config.MedicalRecordStatus;
import edu.neu.his.mapper.MedicalRecordTemplateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MedicalRecordTemplateService {
    @Autowired
    private MedicalRecordTemplateMapper medicalRecordTemplateMapper;

    @Transactional
    public List<MedicalRecordTemplate> SelectByUserId(int id){
        return medicalRecordTemplateMapper.selectByUserId(id);
    }

    @Transactional
    public List<MedicalRecordTemplate> SelectByDepartmentId(int id){
        return medicalRecordTemplateMapper.selectByDepartmentId(id);
    }

    @Transactional
    public List<MedicalRecordTemplate> SelectAll(){
        return medicalRecordTemplateMapper.selectAll();
    }

    @Transactional
    public MedicalRecordTemplate SelectById(int id){
        return medicalRecordTemplateMapper.selectByPrimaryKey(id);
    }

    @Transactional
    public List<MedicalRecordTemplate> SelectByName(String name){
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
}
