
package edu.neu.his.mapper;

import edu.neu.his.bean.ExpenseClassification;
import edu.neu.his.bean.NonDrugChargeItem;
import edu.neu.his.util.Importable;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component(value = "NonDrugChargeItemMapper")
public interface NonDrugChargeItemMapper extends Importable<NonDrugChargeItem> {
    @Select("SELECT non_drug_charge_item.id, non_drug_charge_item.pinyin, format, non_drug_charge_item.pinyin, non_drug_charge_item.name, fee, " +
            "expense_classification_id, department_id, expense_classification.fee_name as expense_classification_name, department.name as department_name " +
            "FROM  non_drug_charge_item, department, expense_classification " +
            "WHERE non_drug_charge_item.expense_classification_id = expense_classification.id " +
            "and non_drug_charge_item.department_id=department.id and non_drug_charge_item.name = #{name}")
    NonDrugChargeItem findByName(@Param("name") String name);

    @Insert("INSERT INTO non_drug_charge_item(id, format, pinyin, name , fee, expense_classification_id, department_id) " +
            "VALUES(#{id}, #{format}, #{pinyin}, #{name}, #{fee}, #{expense_classification_id}, #{department_id})")
    void insert(NonDrugChargeItem nonDrugCharge);

    @Update("UPDATE non_drug_charge_item SET pinyin = #{pinyin}, format=#{format}, pinyin=#{pinyin}, name = #{name}," +
            "fee=#{fee}, expense_classification_id = #{expense_classification_id}, department_id = #{department_id} WHERE id = #{id}")
    void update(NonDrugChargeItem nonDrugCharge);

    @Select("SELECT non_drug_charge_item.id, non_drug_charge_item.pinyin, format, non_drug_charge_item.name, fee, " +
            "expense_classification_id, department_id, expense_classification.fee_name as expense_classification_name, department.name as department_name " +
            "FROM  non_drug_charge_item, department, expense_classification " +
            "WHERE  non_drug_charge_item.expense_classification_id = expense_classification.id " +
            "and non_drug_charge_item.department_id=department.id;\n")
    List<NonDrugChargeItem> findAll();

    @Select("select * from expense_classification")
    List<ExpenseClassification> findAllExpenseClassificationNames();

    @Delete("DELETE FROM non_drug_charge_item WHERE id=#{id}")
    void deleteNonDrugCharge(@Param("id") String id);

    @Select("SELECT id FROM expense_classification where fee_name = #{fee_name}")
    int findExpenseClassificationIdByName(@Param("fee_name") String fee_name);

    @Select("SELECT count(*) FROM non_drug_charge_item where id = #{id}")
    int checkIdExistNums(NonDrugChargeItem nonDrugChargeItem);
}
 