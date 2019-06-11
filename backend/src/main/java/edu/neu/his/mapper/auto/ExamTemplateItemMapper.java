package edu.neu.his.mapper.auto;

import edu.neu.his.bean.ExamTemplateItem;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.type.JdbcType;

public interface ExamTemplateItemMapper {
    @Delete({
        "delete from exam_template_item",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into exam_template_item (exam_template_id, non_drug_item_id)",
        "values (#{exam_template_id,jdbcType=INTEGER}, #{non_drug_item_id,jdbcType=INTEGER})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Integer.class)
    int insert(ExamTemplateItem record);

    @Select({
        "select",
        "id, exam_template_id, non_drug_item_id",
        "from exam_template_item",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="exam_template_id", property="exam_template_id", jdbcType=JdbcType.INTEGER),
        @Result(column="non_drug_item_id", property="non_drug_item_id", jdbcType=JdbcType.INTEGER)
    })
    ExamTemplateItem selectByPrimaryKey(Integer id);

    @Select({
        "select",
        "id, exam_template_id, non_drug_item_id",
        "from exam_template_item"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="exam_template_id", property="exam_template_id", jdbcType=JdbcType.INTEGER),
        @Result(column="non_drug_item_id", property="non_drug_item_id", jdbcType=JdbcType.INTEGER)
    })
    List<ExamTemplateItem> selectAll();

    @Update({
        "update exam_template_item",
        "set exam_template_id = #{exam_template_id,jdbcType=INTEGER},",
          "non_drug_item_id = #{non_drug_item_id,jdbcType=INTEGER}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(ExamTemplateItem record);
}