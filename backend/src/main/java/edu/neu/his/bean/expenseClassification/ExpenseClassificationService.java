package edu.neu.his.bean.expenseClassification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * 实现处理数据库中expense_classification表的相关操作
 */
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

    /**
     * 更新数据库中的一条相应的费用类别记录
     * @param expenseClass 要进行更新的ExpenseClassification对象
     */
    @Transactional
    public void updateExpenseClassification(ExpenseClassification expenseClass) {
        expenseClassificationMapper.update(expenseClass);
    }

    /**
     * 向数据库中插入一条费用类别记录
     * @param expenseClass 要插入数据库中的ExpenseClassification对象
     */
    @Transactional
    public void insertExpenseClass(ExpenseClassification expenseClass) {
        expenseClassificationMapper.insert(expenseClass);
    }

    /**
     * 查找数据库中所有费用类别的列表
     * @return 返回查找到的所有费用类别列表
     */
    @Transactional
    public List<ExpenseClassification> findAll() {
        return expenseClassificationMapper.findAll();
    }

    /**
     * 根据id从数据库中删除对应费用类别
     * @param id 要删除的费用类别的id
     */
    @Transactional
    public void deleteExpenseClassification(int id) {
        expenseClassificationMapper.delete(id);
    }

    /**
     * 检查科室费用类别是否存在
     * @param expenseClassification 要进行检查的ExpenseClassification对象
     * @return 返回该科室的费用类别是否存在，true代表存在，false代表不存在
     */
    @Transactional
    public boolean exist(ExpenseClassification expenseClassification) {
        return expenseClassificationMapper.checkIdExistNums(expenseClassification)==1 || expenseClassificationMapper.checkNameExistNums(expenseClassification)==1;
    }

    /**
     * 判断该科室的信息能否进行删除
     * @param id 要更新的费用科目id
     * @return 返回是否能删除，true代表能，false代表不能
     */
    @Transactional
    public boolean canDelete(int id) {
        return expenseClassificationMapper.checkId(id)==1;
    }

    /**
     * 从数据库中根据id找到对应的费用科目
     * @param id 费用科目id
     * @return 找到的对应费用科目
     */
    @Transactional
    public String selectById(int id){
        return expenseClassificationMapper.findClassificationById(id);
    }

}
