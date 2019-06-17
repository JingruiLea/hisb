package edu.neu.his.auto;

import edu.neu.his.bean.MedicalRecordDiagnosisTemplate;
import java.util.List;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

@Mapper
public interface MedicalRecordDiagnosisTemplateMapper {
    @Delete({
        "delete from medical_record_diagnosis_template",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into medical_record_diagnosis_template (`name`, user_id, ",
        "department_id, `type`, ",
        "medical_record_template_id, disease_id, ",
        "disease_name, disease_code, ",
        "diagnose_type, is_final, ",
        "main_symptom, suspect, syndrome_differentiation)",
        "values (#{name,jdbcType=VARCHAR}, #{user_id,jdbcType=INTEGER}, ",
        "#{department_id,jdbcType=INTEGER}, #{type,jdbcType=INTEGER}, ",
        "#{medical_record_template_id,jdbcType=INTEGER}, #{disease_id,jdbcType=INTEGER}, ",
        "#{disease_name,jdbcType=VARCHAR}, #{disease_code,jdbcType=VARCHAR}, ",
        "#{diagnose_type,jdbcType=VARCHAR}, #{is_final,jdbcType=BIT}, ",
        "#{main_symptom,jdbcType=BIT}, #{suspect,jdbcType=BIT}, #{syndrome_differentiation,jdbcType=LONGVARCHAR})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Integer.class)
    int insert(MedicalRecordDiagnosisTemplate record);

    @Select({
        "select",
        "id, `name`, user_id, department_id, `type`, medical_record_template_id, disease_id, ",
        "disease_name, disease_code, diagnose_type, is_final, main_symptom, suspect, ",
        "syndrome_differentiation",
        "from medical_record_diagnosis_template",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="user_id", property="user_id", jdbcType=JdbcType.INTEGER),
        @Result(column="department_id", property="department_id", jdbcType=JdbcType.INTEGER),
        @Result(column="type", property="type", jdbcType=JdbcType.INTEGER),
        @Result(column="medical_record_template_id", property="medical_record_template_id", jdbcType=JdbcType.INTEGER),
        @Result(column="disease_id", property="disease_id", jdbcType=JdbcType.INTEGER),
        @Result(column="disease_name", property="disease_name", jdbcType=JdbcType.VARCHAR),
        @Result(column="disease_code", property="disease_code", jdbcType=JdbcType.VARCHAR),
        @Result(column="diagnose_type", property="diagnose_type", jdbcType=JdbcType.VARCHAR),
        @Result(column="is_final", property="is_final", jdbcType=JdbcType.BIT),
        @Result(column="main_symptom", property="main_symptom", jdbcType=JdbcType.BIT),
        @Result(column="suspect", property="suspect", jdbcType=JdbcType.BIT),
        @Result(column="syndrome_differentiation", property="syndrome_differentiation", jdbcType=JdbcType.LONGVARCHAR)
    })
    MedicalRecordDiagnosisTemplate selectByPrimaryKey(Integer id);

    @Select({
        "select",
        "id, `name`, user_id, department_id, `type`, medical_record_template_id, disease_id, ",
        "disease_name, disease_code, diagnose_type, is_final, main_symptom, suspect, ",
        "syndrome_differentiation",
        "from medical_record_diagnosis_template"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="user_id", property="user_id", jdbcType=JdbcType.INTEGER),
        @Result(column="department_id", property="department_id", jdbcType=JdbcType.INTEGER),
        @Result(column="type", property="type", jdbcType=JdbcType.INTEGER),
        @Result(column="medical_record_template_id", property="medical_record_template_id", jdbcType=JdbcType.INTEGER),
        @Result(column="disease_id", property="disease_id", jdbcType=JdbcType.INTEGER),
        @Result(column="disease_name", property="disease_name", jdbcType=JdbcType.VARCHAR),
        @Result(column="disease_code", property="disease_code", jdbcType=JdbcType.VARCHAR),
        @Result(column="diagnose_type", property="diagnose_type", jdbcType=JdbcType.VARCHAR),
        @Result(column="is_final", property="is_final", jdbcType=JdbcType.BIT),
        @Result(column="main_symptom", property="main_symptom", jdbcType=JdbcType.BIT),
        @Result(column="suspect", property="suspect", jdbcType=JdbcType.BIT),
        @Result(column="syndrome_differentiation", property="syndrome_differentiation", jdbcType=JdbcType.LONGVARCHAR)
    })
    List<MedicalRecordDiagnosisTemplate> selectAll();

    @Update({
        "update medical_record_diagnosis_template",
        "set `name` = #{name,jdbcType=VARCHAR},",
          "user_id = #{user_id,jdbcType=INTEGER},",
          "department_id = #{department_id,jdbcType=INTEGER},",
          "`type` = #{type,jdbcType=INTEGER},",
          "medical_record_template_id = #{medical_record_template_id,jdbcType=INTEGER},",
          "disease_id = #{disease_id,jdbcType=INTEGER},",
          "disease_name = #{disease_name,jdbcType=VARCHAR},",
          "disease_code = #{disease_code,jdbcType=VARCHAR},",
          "diagnose_type = #{diagnose_type,jdbcType=VARCHAR},",
          "is_final = #{is_final,jdbcType=BIT},",
          "main_symptom = #{main_symptom,jdbcType=BIT},",
          "suspect = #{suspect,jdbcType=BIT},",
          "syndrome_differentiation = #{syndrome_differentiation,jdbcType=LONGVARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(MedicalRecordDiagnosisTemplate record);
}