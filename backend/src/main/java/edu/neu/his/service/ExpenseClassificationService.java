package edu.neu.his.service;

import edu.neu.his.mapper.ExpenseClassificationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ExpenseClassificationService {
    @Autowired
    private ExpenseClassificationMapper expenseClassificationMapper;

    @Transactional
    public int findClassificationIdByName(String fee_name){
        return expenseClassificationMapper.findClassificationIdByName(fee_name);
    }

    @Transactional
    public String findClassificationById(int id){
        return expenseClassificationMapper.findClassificationById(id);
    }
}
