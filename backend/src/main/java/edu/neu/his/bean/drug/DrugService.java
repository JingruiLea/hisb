package edu.neu.his.bean.drug;

import edu.neu.his.auto.AutoDrugMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class DrugService {

    @Autowired
    AutoDrugMapper autoDrugMapper;

    @Autowired
    DrugMapper drugMapper;

    @Transactional
    public int insertDrug(Drug drug){
        drugMapper.insert(drug);
        return drug.getId();
    }

    @Transactional
    public int getExpenseClassificationId(Drug drug){
        switch (drug.getType()){
            case "103":
                return 15;
            case "101":
                return 13;
            case "102":
                return 14;
        }
        return 0;
    }

    @Transactional
    public boolean allItemValid(List<Map> drugList) {
        for(Map map:drugList){
            Drug drug = autoDrugMapper.selectByPrimaryKey((Integer) map.get("id"));
            if(drug == null) return false;
        }
        return true;
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

    @Transactional
    public Drug selectDrugById(int id){
        return autoDrugMapper.selectByPrimaryKey(id);
    }
}
