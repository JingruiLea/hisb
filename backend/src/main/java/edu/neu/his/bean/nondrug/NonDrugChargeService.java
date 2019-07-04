
package edu.neu.his.bean.nondrug;

import edu.neu.his.bean.exam.Exam;
import edu.neu.his.bean.expenseClassification.ExpenseClassification;
import edu.neu.his.util.Common;
import edu.neu.his.util.ExcelImportation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * 实现处理数据库中non_drug_charge_item表的相关操作
 */
@Service
public class NonDrugChargeService {
    @Autowired
    private NonDrugChargeItemMapper nonDrugChargeItemMapper;

    @Autowired
    private edu.neu.his.auto.NonDrugChargeItemMapper autoNonDrugChargeItemMapper;

    /**
     * 更新数据库中的一条相应的非药品收费项目记录
     * @param nonDrugCharge 要进行更新的NonDrugChargeItem对象
     */
    @Transactional
    public void updateNonDrugCharge(NonDrugChargeItem nonDrugCharge) {
        nonDrugChargeItemMapper.update(nonDrugCharge);
    }

    /**
     * 从数据库中根据名称找到对应的非药品收费项目
     * @param name 非药品收费项目名称
     * @return 返回根据名称找到的对应非药品收费项目
     */
    @Transactional
    public NonDrugChargeItem findNonDrugChargeByName(String name) {
        return nonDrugChargeItemMapper.findByName(name);
    }

    /**
     * 向数据库中插入一条非药品收费项目记录
     * @param nonDrugCharge 向数据库中插入记录的次数
     */
    @Transactional
    public void insertNonDrugCharge(NonDrugChargeItem nonDrugCharge) {
        nonDrugChargeItemMapper.insert(nonDrugCharge);
    }

    /**
     * 查找数据库中所有非药品收费项目的列表
     * @return 返回查找到的所有非药品收费项目列表
     */
    @Transactional
    public List<NonDrugChargeItem> findAll() {
        return nonDrugChargeItemMapper.findAll();
    }

    /**
     * 查找数据库中所有费用类别的列表
     * @return 返回查找到的所有费用类别的列表
     */
    @Transactional
    public List<ExpenseClassification> findAllExpenseClassificationNames() {return nonDrugChargeItemMapper.findAllExpenseClassificationNames();}

    /**
     * 根据id从数据库中删除对应非药品收费项目
     * @param id 要删除的非药品收费项目的id
     */
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

    /**
     * 检查非药品收费项目是否存在
     * @param nonDrugChargeItem 要进行检查的NonDrugChargeItem对象
     * @return 返回该非药品收费项目是否存在，true代表存在，false代表不存在
     */
    @Transactional
    public boolean exist(NonDrugChargeItem nonDrugChargeItem) {
        if(nonDrugChargeItem==null) return false;
        return nonDrugChargeItemMapper.checkIdExistNums(nonDrugChargeItem)==1;
    }

    /**
     * 判断该非药品收费项目的信息能否进行删除
     * @param id 要删除的非药品收费项目的id
     * @return 返回是否能删除，true代表能，false代表不能
     */
    @Transactional
    public boolean canDelete(int id) {
        return nonDrugChargeItemMapper.checkId(id)==1;
    }

    /**
     * 从数据库中根据id找到对应的非药品收费项目
     * @param id 非药品收费项目id
     * @return  根据id找到的对应非药品收费项目
     */
    @Transactional
    public NonDrugChargeItem selectById(int id){
        return autoNonDrugChargeItemMapper.selectByPrimaryKey(id);
    }

    /**
     * 从文件中批量导入非药品收费项目
     * @param inputStream 输入流
     * @return 返回是否导入成功，true代表成功，false代表失败
     */
    @Transactional
    public boolean importFromFile(InputStream inputStream) {
        try {
            ExcelImportation excel = new ExcelImportation(inputStream, NonDrugChargeItem.class, nonDrugChargeItemMapper);
            excel.setColumnFields(null, "id", "name", "format", "fee", "expense_classification_id", "department_id", "pinyin");
            //((Map<String, Function<String, ?>>)excel.getPreFunctionMap()).put("classification_id", departmentMapper::findClassificationIdByName);
            excel.exec();
            return true;
        } catch (Exception e)  {
            return false;
        }
    }
}