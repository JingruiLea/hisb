package edu.neu.his.service;

import edu.neu.his.bean.Disease;
import edu.neu.his.bean.DiseaseClassification;
import edu.neu.his.mapper.DiseaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DiagnoseDirectoryService {
    @Autowired
    private DiseaseMapper diseaseMapper;

    @Transactional
    public void updateDisease(String rawId,Disease disease) {
        diseaseMapper.update(rawId, disease.getCode(),disease.getName(),disease.getClassification_id(),disease.getPinyin(),disease.getCustom_name(),disease.getCustom_pinyin());
    }

    @Transactional
    public Disease findDiseaseByName(String name) {
        return diseaseMapper.findByName(name);
    }

    @Transactional
    public void insertDisease(Disease disease) {
        diseaseMapper.insertDisease(disease);
    }

    @Transactional
    public List<Disease> findAll(int classification_id) {
        return diseaseMapper.findAll(classification_id);
    }

    @Transactional
    public void deleteDisease(String id) {
        diseaseMapper.deleteDisease(id);
    }

    @Transactional
    public void insertDiseaseClassification(DiseaseClassification diseaseClassification) {
        diseaseMapper.insertDiseaseClassification(diseaseClassification);
    }

    @Transactional
    public boolean checkDiseaseExist(String id) {
        return diseaseMapper.checkIdExist(id)==1;
    }

    @Transactional
    public List<DiseaseClassification> findAllDiseaseClassification() {
        return diseaseMapper.findAllDiseaseClassification();
    }
}
