package edu.neu.his.service;

import edu.neu.his.bean.SettlementCategory;
import edu.neu.his.mapper.SettlementCategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SettlementCategoryService {
    @Autowired
    SettlementCategoryMapper settlementCategoryMapper;

    @Transactional
    public void addSettlementCategory(String name) {
        settlementCategoryMapper.addSettlementCategory(name);
    }

    @Transactional
    public void deleteSettlementCategoryByName(String name) {
        settlementCategoryMapper.deleteSettlementCategoryByName(name);
    }

    @Transactional
    public void update(SettlementCategory settlementCategory) {
        settlementCategoryMapper.updateSettlementCategory(settlementCategory);
    }

    @Transactional
    public List<SettlementCategory> findAll() {
        return settlementCategoryMapper.findAll();
    }
}
