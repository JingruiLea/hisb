package edu.neu.his.bean.diagnosis;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

@Mapper
public interface MedicalRecordDiagnoseTemplateMapper {
    @Select({
            "select",
            "id, title, user_id, department_id, `type`, create_time",
            "from medical_record_diagnose_template",
            "where user_id = #{user_id,jdbcType=INTEGER} " +
                    "and type = #{type,jdbcType=INTEGER}"
    })
    @Results({
            @Result(column="id", property="id", jdbcType= JdbcType.INTEGER, id=true),
            @Result(column="title", property="title", jdbcType=JdbcType.VARCHAR),
            @Result(column="user_id", property="user_id", jdbcType=JdbcType.INTEGER),
            @Result(column="department_id", property="department_id", jdbcType=JdbcType.INTEGER),
            @Result(column="type", property="type", jdbcType=JdbcType.INTEGER),
            @Result(column="create_time", property="create_time", jdbcType=JdbcType.VARCHAR)
    })
    List<MedicalRecordDiagnoseTemplate> selectByUserId(@Param("user_id") Integer user_id, @Param("type") Integer type);

    @Select({
            "select",
            "id, title, user_id, department_id, `type`, create_time",
            "from medical_record_diagnose_template",
            "where department_id = #{department_id,jdbcType=INTEGER} " +
                    "and type = #{type,jdbcType=INTEGER}"
    })
    @Results({
            @Result(column="id", property="id", jdbcType= JdbcType.INTEGER, id=true),
            @Result(column="title", property="title", jdbcType=JdbcType.VARCHAR),
            @Result(column="user_id", property="user_id", jdbcType=JdbcType.INTEGER),
            @Result(column="department_id", property="department_id", jdbcType=JdbcType.INTEGER),
            @Result(column="type", property="type", jdbcType=JdbcType.INTEGER),
            @Result(column="create_time", property="create_time", jdbcType=JdbcType.VARCHAR)
    })
    List<MedicalRecordDiagnoseTemplate> selectByDepartmentId(@Param("department_id")Integer department_id, @Param("type")Integer type);

    @Select({
            "select",
            "id, title, user_id, department_id, `type`, create_time",
            "from medical_record_diagnose_template",
            "where type = #{type,jdbcType=INTEGER}"
    })
    @Results({
            @Result(column="id", property="id", jdbcType= JdbcType.INTEGER, id=true),
            @Result(column="title", property="title", jdbcType=JdbcType.VARCHAR),
            @Result(column="user_id", property="user_id", jdbcType=JdbcType.INTEGER),
            @Result(column="department_id", property="department_id", jdbcType=JdbcType.INTEGER),
            @Result(column="type", property="type", jdbcType=JdbcType.INTEGER),
            @Result(column="create_time", property="create_time", jdbcType=JdbcType.VARCHAR)
    })
    List<MedicalRecordDiagnoseTemplate> selectByType(@Param("type")Integer type);

    @Select({
            "select",
            "id, title, user_id, department_id, `type`, create_time",
            "from medical_record_diagnose_template",
            "where title = #{title,jdbcType=VARCHAR}"
    })
    @Results({
            @Result(column="id", property="id", jdbcType= JdbcType.INTEGER, id=true),
            @Result(column="title", property="title", jdbcType=JdbcType.VARCHAR),
            @Result(column="user_id", property="user_id", jdbcType=JdbcType.INTEGER),
            @Result(column="department_id", property="department_id", jdbcType=JdbcType.INTEGER),
            @Result(column="type", property="type", jdbcType=JdbcType.INTEGER),
            @Result(column="create_time", property="create_time", jdbcType=JdbcType.VARCHAR)
    })
    List<MedicalRecordDiagnoseTemplate> selectByTitle(@Param("title")String title);
}
