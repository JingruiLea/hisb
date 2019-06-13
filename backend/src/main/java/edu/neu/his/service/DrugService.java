package edu.neu.his.service;

import edu.neu.his.bean.Drug;
import edu.neu.his.mapper.auto.AutoDrugMapper;
import edu.neu.his.mapper.DrugMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DrugService {
    @Autowired
    AutoDrugMapper autoDrugMapper;

    @Autowired
    DrugMapper drugMapper;

    @Transactional
    public int insertDrug(Drug drug){
        return drugMapper.insert(drug);
    }

    @Transactional
    public boolean existDrug(int id){
        if(autoDrugMapper.selectByPrimaryKey(id)==null)
            return false;
        else return true;
    }

    @Transactional
    public int deleteDrug(int id){
        return autoDrugMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public int updateDrug(Drug drug){
        return autoDrugMapper.updateByPrimaryKey(drug);
    }

    @Transactional
    public List<Drug> selectAllDrug(){
        return autoDrugMapper.selectAll();
    }

    @Transactional
    public List<Drug> selectDrugByName(String name){
        return drugMapper.selectByName(name);
    }
}
