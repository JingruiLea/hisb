package edu.neu.his.service;

import edu.neu.his.bean.Drug;
import edu.neu.his.mapper.auto.DrugMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class DrugService {

    @Autowired
    DrugMapper drugMapper;

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
            Drug drug = drugMapper.selectByPrimaryKey((Integer) map.get("id"));
            if(drug == null) return false;
        }
        return true;
    }
}
