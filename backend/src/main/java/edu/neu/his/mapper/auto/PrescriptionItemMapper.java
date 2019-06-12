package edu.neu.his.mapper.auto;

import edu.neu.his.bean.PrescriptionItem;
import java.util.List;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

@Mapper
public interface PrescriptionItemMapper {
    @Delete({
        "delete from prescription_item",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into prescription_item (amount, prescription_id, ",
        "drug_id, `status`, ",
        "note)",
        "values (#{amount,jdbcType=INTEGER}, #{prescription_id,jdbcType=INTEGER}, ",
        "#{drug_id,jdbcType=INTEGER}, #{status,jdbcType=VARCHAR}, ",
        "#{note,jdbcType=LONGVARCHAR})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Integer.class)
    int insert(PrescriptionItem record);

    @Select({
        "select",
        "id, amount, prescription_id, drug_id, `status`, note",
        "from prescription_item",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="amount", property="amount", jdbcType=JdbcType.INTEGER),
        @Result(column="prescription_id", property="prescription_id", jdbcType=JdbcType.INTEGER),
        @Result(column="drug_id", property="drug_id", jdbcType=JdbcType.INTEGER),
        @Result(column="status", property="status", jdbcType=JdbcType.VARCHAR),
        @Result(column="note", property="note", jdbcType=JdbcType.LONGVARCHAR)
    })
    PrescriptionItem selectByPrimaryKey(Integer id);

    @Select({
        "select",
        "id, amount, prescription_id, drug_id, `status`, note",
        "from prescription_item"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="amount", property="amount", jdbcType=JdbcType.INTEGER),
        @Result(column="prescription_id", property="prescription_id", jdbcType=JdbcType.INTEGER),
        @Result(column="drug_id", property="drug_id", jdbcType=JdbcType.INTEGER),
        @Result(column="status", property="status", jdbcType=JdbcType.VARCHAR),
        @Result(column="note", property="note", jdbcType=JdbcType.LONGVARCHAR)
    })
    List<PrescriptionItem> selectAll();

    @Update({
        "update prescription_item",
        "set amount = #{amount,jdbcType=INTEGER},",
          "prescription_id = #{prescription_id,jdbcType=INTEGER},",
          "drug_id = #{drug_id,jdbcType=INTEGER},",
          "`status` = #{status,jdbcType=VARCHAR},",
          "note = #{note,jdbcType=LONGVARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(PrescriptionItem record);
}