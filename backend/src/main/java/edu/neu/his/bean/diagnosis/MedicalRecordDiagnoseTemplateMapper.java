package edu.neu.his.bean.diagnosis;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

/**
 * 该类对数据库中的medical_record_diagnose_template表进行数据持久化操作
 */
@Mapper
public interface MedicalRecordDiagnoseTemplateMapper {
    /**
     * 根据用户id和诊断类型查找对应的诊断模版
     * @param user_id 用户id
     * @param type 诊断类型
     * @return 返回对应的诊断模版
     */
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
    List<MedicalRecordDiagnoseTemplate> selectByUserId(Integer user_id, Integer type);

    /**
     * 根据科室id和诊断类型查找对应的诊断模版
     * @param department_id 科室id
     * @param type 诊断类型
     * @return 返回对应的诊断模版
     */
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
    List<MedicalRecordDiagnoseTemplate> selectByDepartmentId(Integer department_id, Integer type);

    /**
     * 根据诊断类型查找对应的诊断模版
     * @param type 诊断类型
     * @return 返回对应的诊断模版
     */
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
    List<MedicalRecordDiagnoseTemplate> selectByType(Integer type);

    /**
     * 根据诊断名称查找对应的诊断模版
     * @param title 诊断名称
     * @return 返回对应的诊断模版
     */
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
    List<MedicalRecordDiagnoseTemplate> selectByTitle(String title);
}
