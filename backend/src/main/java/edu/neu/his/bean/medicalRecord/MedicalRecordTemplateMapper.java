package edu.neu.his.bean.medicalRecord;

import java.util.List;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Component;

/**
 * 该类对数据库中的medical_record_template表进行数据持久化操作
 */
@Mapper
@Component(value = "MedicalRecordTemplateMapper")
public interface MedicalRecordTemplateMapper {
    /**
     * 根据用户id和类型查找对应的病历模版
     * @param user_id 用户id
     * @param type 类型
     * @return 根据用户id和类型查找的对应病历模版
     */
    @Select({
            "select",
            "id, `title`, `type`, user_id, department_id, create_time, chief_complaint, ",
            "current_medical_history, current_treatment_situation, past_history, allergy_history, ",
            "physical_examination",
            "from medical_record_template",
            "where user_id = #{user_id,jdbcType=INTEGER} and type = #{type,jdbcType=INTEGER}"
    })
    @Results({
            @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
            @Result(column="title", property="title", jdbcType=JdbcType.VARCHAR),
            @Result(column="type", property="type", jdbcType=JdbcType.INTEGER),
            @Result(column="user_id", property="user_id", jdbcType=JdbcType.INTEGER),
            @Result(column="department_id", property="department_id", jdbcType=JdbcType.INTEGER),
            @Result(column="create_time", property="create_time", jdbcType=JdbcType.VARCHAR),
            @Result(column="chief_complaint", property="chief_complaint", jdbcType=JdbcType.LONGVARCHAR),
            @Result(column="current_medical_history", property="current_medical_history", jdbcType=JdbcType.LONGVARCHAR),
            @Result(column="current_treatment_situation", property="current_treatment_situation", jdbcType=JdbcType.LONGVARCHAR),
            @Result(column="past_history", property="past_history", jdbcType=JdbcType.LONGVARCHAR),
            @Result(column="allergy_history", property="allergy_history", jdbcType=JdbcType.LONGVARCHAR),
            @Result(column="physical_examination", property="physical_examination", jdbcType=JdbcType.LONGVARCHAR)
    })
    List<MedicalRecordTemplate> selectByUserId(int user_id, int type);

    /**
     * 根据科室id和类型查找对应的病历模版
     * @param department_id 科室id
     * @param type 类型
     * @return 根据科室id和类型查找的对应病历模版
     */
    @Select({
            "select",
            "id, `title`, `type`, user_id, department_id, create_time, chief_complaint, ",
            "current_medical_history, current_treatment_situation, past_history, allergy_history, ",
            "physical_examination",
            "from medical_record_template",
            "where department_id = #{department_id,jdbcType=INTEGER} and type = #{type,jdbcType=INTEGER}"
    })
    @Results({
            @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
            @Result(column="title", property="title", jdbcType=JdbcType.VARCHAR),
            @Result(column="type", property="type", jdbcType=JdbcType.INTEGER),
            @Result(column="user_id", property="user_id", jdbcType=JdbcType.INTEGER),
            @Result(column="department_id", property="department_id", jdbcType=JdbcType.INTEGER),
            @Result(column="create_time", property="create_time", jdbcType=JdbcType.VARCHAR),
            @Result(column="chief_complaint", property="chief_complaint", jdbcType=JdbcType.LONGVARCHAR),
            @Result(column="current_medical_history", property="current_medical_history", jdbcType=JdbcType.LONGVARCHAR),
            @Result(column="current_treatment_situation", property="current_treatment_situation", jdbcType=JdbcType.LONGVARCHAR),
            @Result(column="past_history", property="past_history", jdbcType=JdbcType.LONGVARCHAR),
            @Result(column="allergy_history", property="allergy_history", jdbcType=JdbcType.LONGVARCHAR),
            @Result(column="physical_examination", property="physical_examination", jdbcType=JdbcType.LONGVARCHAR)
    })
    List<MedicalRecordTemplate> selectByDepartmentId(int department_id, int type);

    /**
     * 根据名称查找对应的病历模版
     * @param title 病历模版名称
     * @return 根据名称查找的对应病历模版
     */
    @Select({
            "select",
            "id, `title`, `type`, user_id, department_id, create_time, chief_complaint, ",
            "current_medical_history, current_treatment_situation, past_history, allergy_history, ",
            "physical_examination",
            "from medical_record_template",
            "where `title` = #{title,jdbcType=VARCHAR}"
    })
    @Results({
            @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
            @Result(column="title", property="title", jdbcType=JdbcType.VARCHAR),
            @Result(column="type", property="type", jdbcType=JdbcType.INTEGER),
            @Result(column="user_id", property="user_id", jdbcType=JdbcType.INTEGER),
            @Result(column="department_id", property="department_id", jdbcType=JdbcType.INTEGER),
            @Result(column="create_time", property="create_time", jdbcType=JdbcType.VARCHAR),
            @Result(column="chief_complaint", property="chief_complaint", jdbcType=JdbcType.LONGVARCHAR),
            @Result(column="current_medical_history", property="current_medical_history", jdbcType=JdbcType.LONGVARCHAR),
            @Result(column="current_treatment_situation", property="current_treatment_situation", jdbcType=JdbcType.LONGVARCHAR),
            @Result(column="past_history", property="past_history", jdbcType=JdbcType.LONGVARCHAR),
            @Result(column="allergy_history", property="allergy_history", jdbcType=JdbcType.LONGVARCHAR),
            @Result(column="physical_examination", property="physical_examination", jdbcType=JdbcType.LONGVARCHAR)
    })
    List<MedicalRecordTemplate> selectByTitle(String title);

    /**
     * 根据类型查找对应的病历模版
     * @param type 模版类型
     * @return 根据类型查找的对应病历模版
     */
    @Select({
            "select",
            "id, `title`, `type`, user_id, department_id, create_time, chief_complaint, ",
            "current_medical_history, current_treatment_situation, past_history, allergy_history, ",
            "physical_examination",
            "from medical_record_template",
            "where type = #{type,jdbcType=INTEGER}"
    })
    @Results({
            @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
            @Result(column="title", property="title", jdbcType=JdbcType.VARCHAR),
            @Result(column="type", property="type", jdbcType=JdbcType.INTEGER),
            @Result(column="user_id", property="user_id", jdbcType=JdbcType.INTEGER),
            @Result(column="department_id", property="department_id", jdbcType=JdbcType.INTEGER),
            @Result(column="create_time", property="create_time", jdbcType=JdbcType.VARCHAR),
            @Result(column="chief_complaint", property="chief_complaint", jdbcType=JdbcType.LONGVARCHAR),
            @Result(column="current_medical_history", property="current_medical_history", jdbcType=JdbcType.LONGVARCHAR),
            @Result(column="current_treatment_situation", property="current_treatment_situation", jdbcType=JdbcType.LONGVARCHAR),
            @Result(column="past_history", property="past_history", jdbcType=JdbcType.LONGVARCHAR),
            @Result(column="allergy_history", property="allergy_history", jdbcType=JdbcType.LONGVARCHAR),
            @Result(column="physical_examination", property="physical_examination", jdbcType=JdbcType.LONGVARCHAR)
    })
    List<MedicalRecordTemplate> selectByType(int type);

    /**
     * 根据id从数据库中删除对应的病历模版
     * @param id 模版id
     * @return 删除的模版id
     */
    @Delete({
            "delete from medical_record_template",
            "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    /**
     * 向数据库的medical_record_template表中插入一条记录
     * @param record 要插入数据库中的MedicalRecordTemplate对象
     * @return 插入数据库中的MedicalRecordTemplate的id
     */
    @Insert({
            "insert into medical_record_template (`title`, `type`, ",
            "user_id, department_id, ",
            "create_time, chief_complaint, current_medical_history, ",
            "current_treatment_situation, past_history, ",
            "allergy_history, physical_examination)",
            "values (#{title,jdbcType=VARCHAR}, #{type,jdbcType=INTEGER}, ",
            "#{user_id,jdbcType=INTEGER}, #{department_id,jdbcType=INTEGER}, ",
            "#{create_time,jdbcType=VARCHAR}, ",
            "#{chief_complaint,jdbcType=LONGVARCHAR}, #{current_medical_history,jdbcType=LONGVARCHAR}, ",
            "#{current_treatment_situation,jdbcType=LONGVARCHAR}, #{past_history,jdbcType=LONGVARCHAR}, ",
            "#{allergy_history,jdbcType=LONGVARCHAR}, #{physical_examination,jdbcType=LONGVARCHAR})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Integer.class)
    int insert(MedicalRecordTemplate record);

    /**
     * 根据id查找对应的病历模版
     * @param id 病历模版id
     * @return 根据id查找的对应病历模版
     */
    @Select({
            "select",
            "id, `title`, `type`, user_id, department_id, create_time, chief_complaint, ",
            "current_medical_history, current_treatment_situation, past_history, allergy_history, ",
            "physical_examination",
            "from medical_record_template",
            "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
            @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
            @Result(column="title", property="title", jdbcType=JdbcType.VARCHAR),
            @Result(column="type", property="type", jdbcType=JdbcType.INTEGER),
            @Result(column="user_id", property="user_id", jdbcType=JdbcType.INTEGER),
            @Result(column="department_id", property="department_id", jdbcType=JdbcType.INTEGER),
            @Result(column="create_time", property="create_time", jdbcType=JdbcType.VARCHAR),
            @Result(column="chief_complaint", property="chief_complaint", jdbcType=JdbcType.LONGVARCHAR),
            @Result(column="current_medical_history", property="current_medical_history", jdbcType=JdbcType.LONGVARCHAR),
            @Result(column="current_treatment_situation", property="current_treatment_situation", jdbcType=JdbcType.LONGVARCHAR),
            @Result(column="past_history", property="past_history", jdbcType=JdbcType.LONGVARCHAR),
            @Result(column="allergy_history", property="allergy_history", jdbcType=JdbcType.LONGVARCHAR),
            @Result(column="physical_examination", property="physical_examination", jdbcType=JdbcType.LONGVARCHAR)
    })
    MedicalRecordTemplate selectByPrimaryKey(Integer id);

    /**
     * 查找所有病历模版
     * @return 查找到的所有病历模版
     */
    @Select({
            "select",
            "id, `title`, `type`, user_id, department_id, create_time, chief_complaint, ",
            "current_medical_history, current_treatment_situation, past_history, allergy_history, ",
            "physical_examination",
            "from medical_record_template"
    })
    @Results({
            @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
            @Result(column="title", property="title", jdbcType=JdbcType.VARCHAR),
            @Result(column="type", property="type", jdbcType=JdbcType.INTEGER),
            @Result(column="user_id", property="user_id", jdbcType=JdbcType.INTEGER),
            @Result(column="department_id", property="department_id", jdbcType=JdbcType.INTEGER),
            @Result(column="create_time", property="create_time", jdbcType=JdbcType.VARCHAR),
            @Result(column="chief_complaint", property="chief_complaint", jdbcType=JdbcType.LONGVARCHAR),
            @Result(column="current_medical_history", property="current_medical_history", jdbcType=JdbcType.LONGVARCHAR),
            @Result(column="current_treatment_situation", property="current_treatment_situation", jdbcType=JdbcType.LONGVARCHAR),
            @Result(column="past_history", property="past_history", jdbcType=JdbcType.LONGVARCHAR),
            @Result(column="allergy_history", property="allergy_history", jdbcType=JdbcType.LONGVARCHAR),
            @Result(column="physical_examination", property="physical_examination", jdbcType=JdbcType.LONGVARCHAR)
    })
    List<MedicalRecordTemplate> selectAll();

    /**
     * 根据id更新数据库的medical_record_template表中相应的记录
     * @param record 要在数据库中更新的MedicalRecordTemplate对象
     * @return 更新的MedicalRecordTemplate对象id
     */
    @Update({
            "update medical_record_template",
            "set `title` = #{title,jdbcType=VARCHAR},",
            "`type` = #{type,jdbcType=INTEGER},",
            "user_id = #{user_id,jdbcType=INTEGER},",
            "department_id = #{department_id,jdbcType=INTEGER},",
            "create_time = #{create_time,jdbcType=VARCHAR},",
            "chief_complaint = #{chief_complaint,jdbcType=LONGVARCHAR},",
            "current_medical_history = #{current_medical_history,jdbcType=LONGVARCHAR},",
            "current_treatment_situation = #{current_treatment_situation,jdbcType=LONGVARCHAR},",
            "past_history = #{past_history,jdbcType=LONGVARCHAR},",
            "allergy_history = #{allergy_history,jdbcType=LONGVARCHAR},",
            "physical_examination = #{physical_examination,jdbcType=LONGVARCHAR}",
            "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(MedicalRecordTemplate record);

}