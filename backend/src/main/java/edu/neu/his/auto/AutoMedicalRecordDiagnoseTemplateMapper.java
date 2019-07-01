package edu.neu.his.auto;

import edu.neu.his.bean.diagnosis.MedicalRecordDiagnoseTemplate;
import java.util.List;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

/**
 * 该类对数据库中的medical_record_diagnose_template表进行数据持久化操作
 */
@Mapper
public interface AutoMedicalRecordDiagnoseTemplateMapper {
    /**
     * 根据id从数据库中删除对应的诊断模版
     * @param id 诊断模版id
     * @return 返回删除的诊断模版id
     */
    @Delete({
        "delete from medical_record_diagnose_template",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    /**
     *  向数据库的medical_record_diagnose_template表中插入一条记录
     * @param record  要插入数据库中的medical_record_diagnose_template对象
     * @return 返回插入对象的id
     */
    @Insert({
        "insert into medical_record_diagnose_template (title, user_id, ",
        "department_id, `type`, ",
        "create_time)",
        "values (#{title,jdbcType=VARCHAR}, #{user_id,jdbcType=INTEGER}, ",
        "#{department_id,jdbcType=INTEGER}, #{type,jdbcType=INTEGER}, ",
        "#{create_time,jdbcType=VARCHAR})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Integer.class)
    int insert(MedicalRecordDiagnoseTemplate record);

    /**
     * 根据id查找对应的诊断模版
     * @param id 诊断模版id
     * @return 返回对应的诊断模版
     */
    @Select({
        "select",
        "id, title, user_id, department_id, `type`, create_time",
        "from medical_record_diagnose_template",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="title", property="title", jdbcType=JdbcType.VARCHAR),
        @Result(column="user_id", property="user_id", jdbcType=JdbcType.INTEGER),
        @Result(column="department_id", property="department_id", jdbcType=JdbcType.INTEGER),
        @Result(column="type", property="type", jdbcType=JdbcType.INTEGER),
        @Result(column="create_time", property="create_time", jdbcType=JdbcType.VARCHAR)
    })
    MedicalRecordDiagnoseTemplate selectByPrimaryKey(Integer id);

    /**
     * 查找所有诊断模版
     * @return 返回所有诊断模版的列表
     */
    @Select({
        "select",
        "id, title, user_id, department_id, `type`, create_time",
        "from medical_record_diagnose_template"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="title", property="title", jdbcType=JdbcType.VARCHAR),
        @Result(column="user_id", property="user_id", jdbcType=JdbcType.INTEGER),
        @Result(column="department_id", property="department_id", jdbcType=JdbcType.INTEGER),
        @Result(column="type", property="type", jdbcType=JdbcType.INTEGER),
        @Result(column="create_time", property="create_time", jdbcType=JdbcType.VARCHAR)
    })
    List<MedicalRecordDiagnoseTemplate> selectAll();

    /**
     * 根据id更新数据库的medical_record_diagnose_template表中相应的记录
     * @param record  要在数据库中更新的MedicalRecordDiagnoseTemplate对象
     * @return 返回插入的诊断模版id
     */
    @Update({
        "update medical_record_diagnose_template",
        "set title = #{title,jdbcType=VARCHAR},",
          "user_id = #{user_id,jdbcType=INTEGER},",
          "department_id = #{department_id,jdbcType=INTEGER},",
          "`type` = #{type,jdbcType=INTEGER}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(MedicalRecordDiagnoseTemplate record);
}