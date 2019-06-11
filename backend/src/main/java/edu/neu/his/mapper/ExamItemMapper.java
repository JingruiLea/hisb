package edu.neu.his.mapper;

import edu.neu.his.bean.ExamItem;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.type.JdbcType;

public interface ExamItemMapper {
    @Delete({
        "delete from exam_item",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into exam_item (exam_id, non_drug_item_id, ",
        "`status`)",
        "values (#{exam_id,jdbcType=INTEGER}, #{non_drug_item_id,jdbcType=INTEGER}, ",
        "#{status,jdbcType=VARCHAR})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Integer.class)
    int insert(ExamItem record);

    @Select({
        "select",
        "id, exam_id, non_drug_item_id, `status`",
        "from exam_item",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="exam_id", property="exam_id", jdbcType=JdbcType.INTEGER),
        @Result(column="non_drug_item_id", property="non_drug_item_id", jdbcType=JdbcType.INTEGER),
        @Result(column="status", property="status", jdbcType=JdbcType.VARCHAR)
    })
    ExamItem selectByPrimaryKey(Integer id);

    @Select({
        "select",
        "id, exam_id, non_drug_item_id, `status`",
        "from exam_item"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="exam_id", property="exam_id", jdbcType=JdbcType.INTEGER),
        @Result(column="non_drug_item_id", property="non_drug_item_id", jdbcType=JdbcType.INTEGER),
        @Result(column="status", property="status", jdbcType=JdbcType.VARCHAR)
    })
    List<ExamItem> selectAll();

    @Update({
        "update exam_item",
        "set exam_id = #{exam_id,jdbcType=INTEGER},",
          "non_drug_item_id = #{non_drug_item_id,jdbcType=INTEGER},",
          "`status` = #{status,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(ExamItem record);
}