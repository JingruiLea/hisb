package edu.neu.his.mapper.auto;

import edu.neu.his.bean.PrescriptionTemplateItem;
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
        "insert into prescription_template_item (prescription_template_id, drug_id)",
        "values (#{prescription_template_id,jdbcType=INTEGER}, #{drug_id,jdbcType=INTEGER})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Integer.class)
    int insert(PrescriptionTemplateItem record);

    @Select({
        "select",
        "id, prescription_template_id, drug_id",
        "from prescription_template_item",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="prescription_template_id", property="prescription_template_id", jdbcType=JdbcType.INTEGER),
        @Result(column="drug_id", property="drug_id", jdbcType=JdbcType.INTEGER)
    })
    PrescriptionTemplateItem selectByPrimaryKey(Integer id);

    @Select({
        "select",
        "id, prescription_template_id, drug_id",
        "from prescription_template_item"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="prescription_template_id", property="prescription_template_id", jdbcType=JdbcType.INTEGER),
        @Result(column="drug_id", property="drug_id", jdbcType=JdbcType.INTEGER)
    })
    List<PrescriptionTemplateItem> selectAll();

    @Update({
        "update prescription_template_item",
        "set prescription_template_id = #{prescription_template_id,jdbcType=INTEGER},",
          "drug_id = #{drug_id,jdbcType=INTEGER}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(PrescriptionTemplateItem record);
}