package edu.neu.his.service;

import edu.neu.his.bean.CommonDiagnosis;
import edu.neu.his.mapper.auto.CommonDiagnosisMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommonDiagnosisService {
    @Autowired
    private CommonDiagnosisMapper commonDiagnosisMapper;

    @Transactional
    public CommonDiagnosis SelectById(int id){
        return commonDiagnosisMapper.selectByPrimaryKey(id);
    }

    @Transactional
    public int insert(CommonDiagnosis commonDiagnosis){
        return commonDiagnosisMapper.insert(commonDiagnosis);
    }

    @Transactional
    public List<CommonDiagnosis> selectAll(){
        return commonDiagnosisMapper.selectAll();
    }

    @Transactional
    public int update(CommonDiagnosis commonDiagnosis){
        return commonDiagnosisMapper.updateByPrimaryKey(commonDiagnosis);
    }

    @Transactional
    public int delete(int id){
        return commonDiagnosisMapper.deleteByPrimaryKey(id);
    }
}
