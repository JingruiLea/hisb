
package edu.neu.his.service;

import edu.neu.his.bean.ExpenseClassification;
import edu.neu.his.bean.NonDrugChargeItem;
import edu.neu.his.mapper.NonDrugChargeItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class NonDrugChargeService {
    @Autowired
    private NonDrugChargeItemMapper nonDrugChargeItemMapper;

    @Transactional
    public void updateNonDrugCharge(NonDrugChargeItem nonDrugCharge) {
        nonDrugChargeItemMapper.update(nonDrugCharge);
    }

    @Transactional
    public NonDrugChargeItem findNonDrugChargeByName(String name) {
        return nonDrugChargeItemMapper.findByName(name);
    }

    @Transactional
    public void insertNonDrugCharge(NonDrugChargeItem nonDrugCharge) {
        nonDrugChargeItemMapper.insert(nonDrugCharge);
    }

    @Transactional
    public List<NonDrugChargeItem> findAll() {
        return nonDrugChargeItemMapper.findAll();
    }

    @Transactional
    public List<ExpenseClassification> findAllExpenseClassificationNames() {return nonDrugChargeItemMapper.findAllExpenseClassificationNames();}

    @Transactional
    public void deleteNonDrugCharge(String id) {
        nonDrugChargeItemMapper.deleteNonDrugCharge(id);
    }

    @Transactional
    public int findExpenseClassificationIdByName(String fee_name) {
        int id = 0;
        try {
            id = nonDrugChargeItemMapper.findExpenseClassificationIdByName(fee_name);
        } catch (Exception e) {
            id = -1;
        }
        return id;
    }

    @Transactional
    public boolean exist(NonDrugChargeItem nonDrugChargeItem) {
        return nonDrugChargeItemMapper.checkIdExistNums(nonDrugChargeItem)==1;
    }

    @Transactional
    public boolean canDelete(String id) {
        return nonDrugChargeItemMapper.checkId(id)==1;
    }
}