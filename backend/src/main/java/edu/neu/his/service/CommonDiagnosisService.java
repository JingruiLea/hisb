package edu.neu.his.service;

import edu.neu.his.bean.MedicalRecordDiagnosisTemplate;
import edu.neu.his.mapper.auto.MedicalRecordDiagnosisTemplateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommonDiagnosisService {
    @Autowired
    private MedicalRecordDiagnosisTemplateMapper medicalRecordDiagnosisTemplateMapper;

    @Transactional
    public MedicalRecordDiagnosisTemplate SelectById(int id){
        return medicalRecordDiagnosisTemplateMapper.selectByPrimaryKey(id);
    }

    @Transactional
    public int insert(MedicalRecordDiagnosisTemplate commonDiagnosis){
        return medicalRecordDiagnosisTemplateMapper.insert(commonDiagnosis);
    }

    @Transactional
    public List<MedicalRecordDiagnosisTemplate> selectAll(){
        return medicalRecordDiagnosisTemplateMapper.selectAll();
    }

    @Transactional
    public int update(MedicalRecordDiagnosisTemplate commonDiagnosis){
        return medicalRecordDiagnosisTemplateMapper.updateByPrimaryKey(commonDiagnosis);
    }

    @Transactional
    public int delete(int id){
        return medicalRecordDiagnosisTemplateMapper.deleteByPrimaryKey(id);
    }
}
