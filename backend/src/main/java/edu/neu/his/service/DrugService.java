package edu.neu.his.service;

import edu.neu.his.bean.Drug;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DrugService {

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
}
