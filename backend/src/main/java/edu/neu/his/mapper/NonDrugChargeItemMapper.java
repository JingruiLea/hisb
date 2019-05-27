
package edu.neu.his.mapper;

import edu.neu.his.bean.NonDrugChargeItem;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component(value = "NonDrugChargeItemMapper")
public interface NonDrugChargeItemMapper {
    @Select("SELECT non_drug_charge_item.id, non_drug_charge_item.code, format, pinyin, non_drug_charge_item.name, fee, " +
            "expense_classification_id, department_id, expense_classification.fee_name as fee_name, department.name as department " +
            "FROM  non_drug_charge_item, department, expense_classification " +
            "WHERE non_drug_charge_item.expense_classification_id = expense_classification.id " +
            "and non_drug_charge_item.department_id=department.id and non_drug_charge_item.name = #{name}")
    NonDrugChargeItem findByName(@Param("name") String name);

    @Insert("INSERT INTO non_drug_charge_item(code, format, pinyin, name , fee, expense_classification_id, department_id) " +
            "VALUES(#{code}, #{format}, #{pinyin}, #{name}, #{fee}, #{expense_classification_id}, #{department_id})")
    void insert(NonDrugChargeItem nonDrugCharge);

    @Update("UPDATE department SET code = #{code}, format=#{format}, pinyin=#{pinyin}, name = #{name}," +
            "fee=#{fee}, expense_classification_id = #{expense_classification_id}, department_id = #{department_id} WHERE id = #{id}")
    void update(NonDrugChargeItem nonDrugCharge);

    @Select("SELECT non_drug_charge_item.id, non_drug_charge_item.code, format, pinyin, non_drug_charge_item.name, fee, " +
            "expense_classification_id, department_id, expense_classification.fee_name as fee_name, department.name as department " +
            "FROM  non_drug_charge_item, department, expense_classification " +
            "WHERE  non_drug_charge_item.expense_classification_id = expense_classification.id " +
            "and non_drug_charge_item.department_id=department.id;\n")
    List<NonDrugChargeItem> findAll();

    @Select("select fee_name from expense_classification")
    List<String> findAllExpenseClassificationNames();

    @Delete("DELETE FROM non_drug_charge_item WHERE id=#{id}")
    void deleteNonDrugCharge(int id);

    @Select("SELECT id FROM expense_classification where fee_name = #{fee_name}")
    int findExpenseClassificationIdByName(@Param("fee_name") String fee_name);
}
 