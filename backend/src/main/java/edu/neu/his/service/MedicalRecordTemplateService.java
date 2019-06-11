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
}
