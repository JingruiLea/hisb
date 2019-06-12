package edu.neu.his.mapper.auto;

import edu.neu.his.bean.CommonDiagnosis;
import java.util.List;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

@Mapper
public interface CommonDiagnosisMapper {
    @Delete({
        "delete from common_diagnosis",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into common_diagnosis (`name`, user_id, ",
        "department_id, content)",
        "values (#{name,jdbcType=VARCHAR}, #{user_id,jdbcType=INTEGER}, ",
        "#{department_id,jdbcType=INTEGER}, #{content,jdbcType=LONGVARCHAR})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Integer.class)
    int insert(CommonDiagnosis record);

    @Select({
        "select",
        "id, `name`, user_id, department_id, content",
        "from common_diagnosis",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="user_id", property="user_id", jdbcType=JdbcType.INTEGER),
        @Result(column="department_id", property="department_id", jdbcType=JdbcType.INTEGER),
        @Result(column="content", property="content", jdbcType=JdbcType.LONGVARCHAR)
    })
    CommonDiagnosis selectByPrimaryKey(Integer id);

    @Select({
        "select",
        "id, `name`, user_id, department_id, content",
        "from common_diagnosis"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="user_id", property="user_id", jdbcType=JdbcType.INTEGER),
        @Result(column="department_id", property="department_id", jdbcType=JdbcType.INTEGER),
        @Result(column="content", property="content", jdbcType=JdbcType.LONGVARCHAR)
    })
    List<CommonDiagnosis> selectAll();

    @Update({
        "update common_diagnosis",
        "set `name` = #{name,jdbcType=VARCHAR},",
          "user_id = #{user_id,jdbcType=INTEGER},",
          "department_id = #{department_id,jdbcType=INTEGER},",
          "content = #{content,jdbcType=LONGVARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(CommonDiagnosis record);
}