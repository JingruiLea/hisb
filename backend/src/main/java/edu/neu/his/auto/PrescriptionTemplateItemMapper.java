package edu.neu.his.auto;

import edu.neu.his.bean.prescriptionTemplate.PrescriptionTemplateItem;
import java.util.List;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

@Mapper
public interface PrescriptionTemplateItemMapper {
    @Delete({
        "delete from prescription_template_item",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into prescription_template_item (prescription_template_id, drug_id, ",
        "amount, note)",
        "values (#{prescription_template_id,jdbcType=INTEGER}, #{drug_id,jdbcType=INTEGER}, ",
        "#{amount,jdbcType=INTEGER}, #{note,jdbcType=LONGVARCHAR})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Integer.class)
    int insert(PrescriptionTemplateItem record);

    @Select({
        "select",
        "id, prescription_template_id, drug_id, amount, note",
        "from prescription_template_item",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="prescription_template_id", property="prescription_template_id", jdbcType=JdbcType.INTEGER),
        @Result(column="drug_id", property="drug_id", jdbcType=JdbcType.INTEGER),
        @Result(column="amount", property="amount", jdbcType=JdbcType.INTEGER),
        @Result(column="note", property="note", jdbcType=JdbcType.LONGVARCHAR)
    })
    PrescriptionTemplateItem selectByPrimaryKey(Integer id);

    @Select({
        "select",
        "id, prescription_template_id, drug_id, amount, note",
        "from prescription_template_item"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="prescription_template_id", property="prescription_template_id", jdbcType=JdbcType.INTEGER),
        @Result(column="drug_id", property="drug_id", jdbcType=JdbcType.INTEGER),
        @Result(column="amount", property="amount", jdbcType=JdbcType.INTEGER),
        @Result(column="note", property="note", jdbcType=JdbcType.LONGVARCHAR)
    })
    List<PrescriptionTemplateItem> selectAll();

    @Update({
        "update prescription_template_item",
        "set prescription_template_id = #{prescription_template_id,jdbcType=INTEGER},",
          "drug_id = #{drug_id,jdbcType=INTEGER},",
          "amount = #{amount,jdbcType=INTEGER},",
          "note = #{note,jdbcType=LONGVARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(PrescriptionTemplateItem record);

    @Select({
            "select",
            "id, prescription_template_id, drug_id, amount, note",
            "from prescription_template_item",
            "where prescription_template_id = #{prescriptionId}",
            "and drug_id = #{drugId}"
    })
    @Results({
            @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
            @Result(column="prescription_template_id", property="prescription_template_id", jdbcType=JdbcType.INTEGER),
            @Result(column="drug_id", property="drug_id", jdbcType=JdbcType.INTEGER),
            @Result(column="amount", property="amount", jdbcType=JdbcType.INTEGER),
            @Result(column="note", property="note", jdbcType=JdbcType.LONGVARCHAR)
    })
    PrescriptionTemplateItem selectByDetail(int prescriptionId, Integer drugId);


    @Select({
            "select",
            "id, prescription_template_id, drug_id, amount, note",
            "from prescription_template_item",
            "where prescription_template_id = #{prescriptionId}"
    })
    @Results({
            @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
            @Result(column="prescription_template_id", property="prescription_template_id", jdbcType=JdbcType.INTEGER),
            @Result(column="drug_id", property="drug_id", jdbcType=JdbcType.INTEGER),
            @Result(column="amount", property="amount", jdbcType=JdbcType.INTEGER),
            @Result(column="note", property="note", jdbcType=JdbcType.LONGVARCHAR)
    })
    List<PrescriptionTemplateItem> selectByPrescriptionId(int prescriptionId);


    @Delete({
            "delete from prescription_template_item",
            "where prescription_template_id = #{id,jdbcType=INTEGER}"
    })
    void deleteAllByPrescriptionId(int id);
}