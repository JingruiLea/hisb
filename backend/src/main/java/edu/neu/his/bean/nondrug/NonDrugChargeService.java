
package edu.neu.his.bean.nondrug;

import edu.neu.his.bean.expenseClassification.ExpenseClassification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class NonDrugChargeService {
    @Autowired
    private NonDrugChargeItemMapper nonDrugChargeItemMapper;

    @Autowired
    private edu.neu.his.auto.NonDrugChargeItemMapper autoNonDrugChargeItemMapper;

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
    public void deleteNonDrugCharge(int id) {
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
    public boolean canDelete(int id) {
        return nonDrugChargeItemMapper.checkId(id)==1;
    }

    @Transactional
    public NonDrugChargeItem selectById(int id){
        return autoNonDrugChargeItemMapper.selectByPrimaryKey(id);
    }
}