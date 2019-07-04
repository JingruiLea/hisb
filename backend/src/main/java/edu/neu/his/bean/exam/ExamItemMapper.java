package edu.neu.his.bean.exam;

import java.util.List;

import edu.neu.his.util.Importable;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Component;

/**
 * 该类对数据库中的exam_item表进行数据持久化操作
 */
@Mapper
@Component(value = "ExamItemMapper")
public interface ExamItemMapper  {
    /**
     * 根据id从数据库中删除对应的检查/检验/处置子目
     * @param id 要删除的检查/检验/处置子目的id
     * @return 删除的检查/检验/处置子目的id
     */
    @Delete({
        "delete from exam_item",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    /**
     * 向数据库的exam_item表中插入一条记录
     * @param record 要插入数据库中的ExamItem对象
     * @return 向数据库中插入记录的次数
     */
    @Insert({
        "insert into exam_item (exam_id, non_drug_item_id, ",
        "`status`)",
        "values (#{exam_id,jdbcType=INTEGER}, #{non_drug_item_id,jdbcType=INTEGER}, ",
        "#{status,jdbcType=VARCHAR})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Integer.class)
    int insert(ExamItem record);

    /**
     * 根据id查找对应的检查/检验/处置子目
     * @param id id
     * @return 对应的检查/检验/处置子目
     */
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

    /**
     * 根据检查/检验/处置id查找对应的检查/检验/处置子目
     * @param examId 检查/检验/处置子目id
     * @return 对应的检查/检验/处置子目
     */
     @Select({
            "select",
            "id, exam_id, non_drug_item_id, `status`",
            "from exam_item",
            "where exam_id = #{examId,jdbcType=INTEGER}"
    })
    @Results({
            @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
            @Result(column="exam_id", property="exam_id", jdbcType=JdbcType.INTEGER),
            @Result(column="non_drug_item_id", property="non_drug_item_id", jdbcType=JdbcType.INTEGER),
            @Result(column="status", property="status", jdbcType=JdbcType.VARCHAR)
    })
    List<ExamItem> selectByExamId(Integer examId);

    /**
     * 根据非药品项目id和检查/检验/处置id查找对应的检查/检验/处置子目
     * @param nonDrugId 非药品项目id
     * @param examId 检查/检验/处置id
     * @return 对应的检查/检验/处置子目
     */
    @Select({
            "select",
            "id, exam_id, non_drug_item_id, `status`",
            "from exam_item",
            "where non_drug_item_id = #{nonDrugId, jdbcType=INTEGER}",
            "and exam_id = #{examId, jdbcType=INTEGER}"
    })
    @Results({
            @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
            @Result(column="exam_id", property="exam_id", jdbcType=JdbcType.INTEGER),
            @Result(column="non_drug_item_id", property="non_drug_item_id", jdbcType=JdbcType.INTEGER),
            @Result(column="status", property="status", jdbcType=JdbcType.VARCHAR)
    })
    ExamItem selectOneByDetail(Integer nonDrugId, Integer examId);

    /**
     * 查找所有检查/检验/处置子目记录
     * @return 所有检查/检验/处置子目记录列表
     */
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

    /**
     * 根据id更新数据库的exam_item表中相应的记录
     * @param record 要在数据库中更新的ExamItem对象
     * @return 更新的ExamItem对象id
     */
    @Update({
        "update exam_item",
        "set exam_id = #{exam_id,jdbcType=INTEGER},",
          "non_drug_item_id = #{non_drug_item_id,jdbcType=INTEGER},",
          "`status` = #{status,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(ExamItem record);
}