package edu.neu.his.bean.dignosis;

import edu.neu.his.mapper.auto.CommonDiagnosisMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DiagnosisTemplateService {
    @Autowired
    private CommonDiagnosisMapper commonDiagnosisMapper;

    @Transactional
    public DiagnosisTemplate SelectById(int id){
        return commonDiagnosisMapper.selectByPrimaryKey(id);
    }

    @Transactional
    public int insert(DiagnosisTemplate diagnosisTemplate){
        return commonDiagnosisMapper.insert(diagnosisTemplate);
    }

    @Transactional
    public List<DiagnosisTemplate> selectAll(){
        return commonDiagnosisMapper.selectAll();
    }

    @Transactional
    public int update(DiagnosisTemplate diagnosisTemplate){
        return commonDiagnosisMapper.updateByPrimaryKey(diagnosisTemplate);
    }

    @Transactional
    public int delete(int id){
        return commonDiagnosisMapper.deleteByPrimaryKey(id);
    }
}
