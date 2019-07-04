
package edu.neu.his.bean.nondrug;

import edu.neu.his.bean.expenseClassification.ExpenseClassification;
import edu.neu.his.util.Importable;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 该类对数据库中的non_drug_charge_item表进行数据持久化操作
 */
@Mapper
@Component(value = "NonDrugChargeItemMapper")
public interface NonDrugChargeItemMapper extends Importable<NonDrugChargeItem> {
    /**
     * 根据非药品收费项目名称查找对应的非药品收费项目
     * @param name 非药品收费项目名称
     * @return 非药品收费项目
     */
    @Select("SELECT non_drug_charge_item.id, non_drug_charge_item.code, non_drug_charge_item.pinyin, format, non_drug_charge_item.pinyin, non_drug_charge_item.name, fee, " +
            "expense_classification_id, department_id, expense_classification.fee_name as expense_classification_name, department.name as department_name " +
            "FROM  non_drug_charge_item, department, expense_classification " +
            "WHERE non_drug_charge_item.expense_classification_id = expense_classification.id " +
            "and non_drug_charge_item.department_id=department.id and non_drug_charge_item.name = #{name}")
    NonDrugChargeItem findByName(@Param("name") String name);

    /**
     * 向数据库的non_drug_charge_item表中插入一条记录
     * @param nonDrugCharge 要插入数据库中的NonDrugChargeItem对象
     * @return 向数据库中插入记录的次数
     */
    @Insert("INSERT INTO non_drug_charge_item(id, code, format, pinyin, name , fee, expense_classification_id, department_id) " +
            "VALUES(#{id}, #{code}, #{format}, #{pinyin}, #{name}, #{fee}, #{expense_classification_id}, #{department_id})")
    int insert(NonDrugChargeItem nonDrugCharge);

    /**
     * 根据id更新数据库的non_drug_charge_item表中相应的记录
     * @param nonDrugCharge 要在数据库中更新的NonDrugChargeItem对象
     */
    @Update("UPDATE non_drug_charge_item SET pinyin = #{pinyin}, code = #{code}, format=#{format}, pinyin=#{pinyin}, name = #{name}," +
            "fee=#{fee}, expense_classification_id = #{expense_classification_id}, department_id = #{department_id} WHERE id = #{id}")
    void update(NonDrugChargeItem nonDrugCharge);

    /**
     * 查找所有非药品收费项目记录
     * @return 返回所有非药品收费项目记录的列表
     */
    @Select("SELECT non_drug_charge_item.id, non_drug_charge_item.code, non_drug_charge_item.pinyin, format, non_drug_charge_item.name, fee, " +
            "expense_classification_id, department_id, expense_classification.fee_name as expense_classification_name, department.name as department_name " +
            "FROM  non_drug_charge_item, department, expense_classification " +
            "WHERE  non_drug_charge_item.expense_classification_id = expense_classification.id " +
            "and non_drug_charge_item.department_id=department.id;\n")
    List<NonDrugChargeItem> findAll();

    /**
     * 查找所有费用类别记录
     * @return 返回所有费用类别记录的列表
     */
    @Select("select * from expense_classification")
    List<ExpenseClassification> findAllExpenseClassificationNames();

    /**
     * 根据id从数据库中删除对应的非药品收费项目
     * @param id 要删除的非药品收费项目的id
     */
    @Delete("DELETE FROM non_drug_charge_item WHERE id=#{id}")
    void deleteNonDrugCharge(@Param("id") int id);

    /**
     * 根据费用类别名称查找对应的费用类别
     * @param fee_name 费用类别id
     * @return 根据费用类别名称查找的对应费用类别
     */
    @Select("SELECT id FROM expense_classification where fee_name = #{fee_name}")
    int findExpenseClassificationIdByName(@Param("fee_name") String fee_name);

    /**
     * 检查数据库中是否已存在该非药品收费项目
     * @param nonDrugChargeItem 要检查是否存在的非药品收费项目
     * @return 返回该非药品收费项目在数据库non_drug_charge_item表中存在的数量
     */
    @Select("SELECT count(*) FROM non_drug_charge_item where id = #{id}")
    int checkIdExistNums(NonDrugChargeItem nonDrugChargeItem);

    /**
     * 检查数据库中是否已存在该非药品收费项目id
     * @param id 要检查是否存在的非药品收费项目id
     * @return 返回该非药品收费项目id在数据库non_drug_charge_item表中存在的数量
     */
    @Select("SELECT count(*) FROM non_drug_charge_item where id = #{id}")
    int checkId(@Param("id") int id);
}
 