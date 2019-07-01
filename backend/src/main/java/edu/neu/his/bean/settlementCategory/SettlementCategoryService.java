package edu.neu.his.bean.settlementCategory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 实现处理数据库中settlement_category表的相关操作
 */
@Service
public class SettlementCategoryService {
    @Autowired
    SettlementCategoryMapper settlementCategoryMapper;

    /**
     * 向数据库中插入一条结算类型记录
     * @param settlementCategory 要插入数据库中的SettlementCategory对象
     */
    @Transactional
    public void addSettlementCategory(SettlementCategory settlementCategory) {
        settlementCategoryMapper.addSettlementCategory(settlementCategory);
    }

    /**
     * 根据名称从数据库中删除对应结算类型
     * @param name 要删除的科室的名称
     */
    @Transactional
    public void deleteSettlementCategoryByName(String name) {
        settlementCategoryMapper.deleteSettlementCategoryByName(name);
    }

    /**
     * 根据id从数据库中删除对应结算类型
     * @param id 要删除的科室id
     */
    @Transactional
    public void deleteSettlementCategoryById(int id) {
        settlementCategoryMapper.deleteSettlementCategoryById(id);
    }

    /**
     * 更新数据库中的一条相应的结算类型记录
     * @param settlementCategory 要进行更新的SettlementCategory对象
     */
    @Transactional
    public void update(SettlementCategory settlementCategory) {
        settlementCategoryMapper.updateSettlementCategory(settlementCategory);
    }

    /**
     * 查找数据库中所有结算类型的列表
     * @return 返回查找到的所有结算类型列表
     */
    @Transactional
    public List<SettlementCategory> findAll() {
        return settlementCategoryMapper.findAll();
    }

    /**
     * 判断该结算类型的信息能否进行更新
     * @param settlementCategory 要更新的SettlementCategory对象
     * @return 返回是否能更新，true代表能，false代表不能
     */
    @Transactional
    public boolean canUpdate(SettlementCategory settlementCategory) {
        int id_num = settlementCategoryMapper.checkIdExists(settlementCategory.getId());
        int name_num = settlementCategoryMapper.checkNameExists(settlementCategory.getName());
        if(id_num==0 || name_num>1 || id_num>1)
            return false;
        else if(name_num==1){
            SettlementCategory d = settlementCategoryMapper.findByName(settlementCategory.getName());
            if(d.getId() != settlementCategory.getId())
                return false;
            else
                return true;
        } else
            return true;
    }

    /**
     * 判断该结算类型能否插入数据库中
     * @param settlementCategory 要插入数据库的SettlementCategory对象
     * @return 返回是否能插入，true代表能，false代表不能
     */
    @Transactional
    public boolean canInsert(SettlementCategory settlementCategory) {
        int id_num = settlementCategoryMapper.checkIdExists(settlementCategory.getId());
        int name_num = settlementCategoryMapper.checkNameExists(settlementCategory.getName());
        if(id_num>=1 || name_num>=1)
            return false;
        else
            return true;
    }

    /**
     * 判断该结算类型的信息能否进行删除
     * @param id 要更新的SettlementCategory对象的id
     * @return 返回是否能更新，true代表能，false代表不能
     */
    @Transactional
    public int canDelete(int id) {
        return settlementCategoryMapper.checkIdExists(id);//0,不能删
    }
}
