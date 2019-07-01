package edu.neu.his.bean.exam;

import java.util.List;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Component;

/**
 * 该类对数据库中的exam表进行数据持久化操作
 */
@Mapper
@Component(value = "ExamMapper")
public interface ExamMapper {
    /**
     * 根据id从数据库中删除对应的检查/检验/处置
     * @param id 要删除的检查/检验/处置的id
     * @return 删除的检查/检验/处置的id
     */
    @Delete({
        "delete from exam",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    /**
     * 向数据库的exam表中插入一条记录
     * @param record 要插入数据库中的Exam对象
     * @return 插入数据库中的Exam对象的id
     */
    @Insert({
        "insert into exam (medical_record_id, `type`, ",
        "create_time, user_id, ",
        "`status`)",
        "values (#{medical_record_id,jdbcType=INTEGER}, #{type,jdbcType=INTEGER}, ",
        "#{create_time,jdbcType=VARCHAR}, #{user_id,jdbcType=INTEGER}, ",
        "#{status,jdbcType=VARCHAR})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Integer.class)
    int insert(Exam record);

    /**
     * 根据id查找对应的检查/检验/处置
     * @param id id
     * @return 对应的检查/检验/处置
     */
    @Select({
        "select",
        "id, medical_record_id, `type`, create_time, user_id, `status`",
        "from exam",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="medical_record_id", property="medical_record_id", jdbcType=JdbcType.INTEGER),
        @Result(column="type", property="type", jdbcType=JdbcType.INTEGER),
        @Result(column="create_time", property="create_time", jdbcType=JdbcType.VARCHAR),
        @Result(column="user_id", property="user_id", jdbcType=JdbcType.INTEGER),
        @Result(column="status", property="status", jdbcType=JdbcType.VARCHAR)
    })
    Exam selectByPrimaryKey(Integer id);

    /**
     * 查找所有检查/检验/处置记录
     * @return 所有检查/检验/处置记录列表
     */
    @Select({
        "select",
        "id, medical_record_id, `type`, create_time, user_id, `status`",
        "from exam"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="medical_record_id", property="medical_record_id", jdbcType=JdbcType.INTEGER),
        @Result(column="type", property="type", jdbcType=JdbcType.INTEGER),
        @Result(column="create_time", property="create_time", jdbcType=JdbcType.VARCHAR),
        @Result(column="user_id", property="user_id", jdbcType=JdbcType.INTEGER),
        @Result(column="status", property="status", jdbcType=JdbcType.VARCHAR)
    })
    List<Exam> selectAll();

    /**
     * 根据id更新数据库的exam表中相应的记录
     * @param record 要在数据库中更新的Exam对象
     * @return 更新的Exam对象id
     */
    @Update({
        "update exam",
        "set medical_record_id = #{medical_record_id,jdbcType=INTEGER},",
          "`type` = #{type,jdbcType=INTEGER},",
          "create_time = #{create_time,jdbcType=VARCHAR},",
          "user_id = #{user_id,jdbcType=INTEGER},",
          "`status` = #{status,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(Exam record);

    /**
     * 根据病历号查找对应的检查/检验/处置
     * @param medicalRecordId 病历号
     * @return 对应的检查/检验/处置列表
     */
    @Select({
            "select",
            "id, medical_record_id, `type`, create_time, user_id, `status`",
            "from exam",
            "where medical_record_id = #{medicalRecordId,jdbcType=INTEGER}"
    })
    @Results({
            @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
            @Result(column="medical_record_id", property="medical_record_id", jdbcType=JdbcType.INTEGER),
            @Result(column="type", property="type", jdbcType=JdbcType.INTEGER),
            @Result(column="create_time", property="create_time", jdbcType=JdbcType.VARCHAR),
            @Result(column="user_id", property="user_id", jdbcType=JdbcType.INTEGER),
            @Result(column="status", property="status", jdbcType=JdbcType.VARCHAR)
    })
    List<Exam> selectByMedicalRecordId(int medicalRecordId);
}