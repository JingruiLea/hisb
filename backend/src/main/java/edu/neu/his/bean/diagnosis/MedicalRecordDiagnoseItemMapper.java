package edu.neu.his.bean.diagnosis;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

/**
 * 该类对数据库中的medical_record_diagnose_item表进行数据持久化操作
 */
@Mapper
public interface MedicalRecordDiagnoseItemMapper {
    /**
     * 根据诊断id查找对应的诊断子目
     * @param medical_record_diagnose_id 诊断id
     * @return 返回对应的诊断子目
     */
    @Select({
            "select",
            "id, medical_record_diagnose_id, disease_id, disease_name, disease_code, diagnose_type, ",
            "main_symptom, suspect, syndrome_differentiation",
            "from medical_record_diagnose_item",
            "where medical_record_diagnose_id = #{medical_record_diagnose_id,jdbcType=INTEGER}"
    })
    @Results({
            @Result(column="id", property="id", jdbcType= JdbcType.INTEGER, id=true),
            @Result(column="medical_record_diagnose_id", property="medical_record_diagnose_id", jdbcType=JdbcType.INTEGER),
            @Result(column="disease_id", property="disease_id", jdbcType=JdbcType.INTEGER),
            @Result(column="disease_name", property="disease_name", jdbcType=JdbcType.VARCHAR),
            @Result(column="disease_code", property="disease_code", jdbcType=JdbcType.VARCHAR),
            @Result(column="diagnose_type", property="diagnose_type", jdbcType=JdbcType.VARCHAR),
            @Result(column="main_symptom", property="main_symptom", jdbcType=JdbcType.BIT),
            @Result(column="suspect", property="suspect", jdbcType=JdbcType.BIT),
            @Result(column="syndrome_differentiation", property="syndrome_differentiation", jdbcType=JdbcType.LONGVARCHAR)
    })
    List<MedicalRecordDiagnoseItem> selectByDiagnoseId(Integer medical_record_diagnose_id);

    /**
     * 根据诊断id和类型查找对应的诊断子目
     * @param medical_record_diagnose_id 诊断id
     * @param diagnose_type 诊断类型
     * @return 返回对应的诊断子目
     */
    @Select({
            "select",
            "id, medical_record_diagnose_id, disease_id, disease_name, disease_code, diagnose_type, ",
            "main_symptom, suspect, syndrome_differentiation",
            "from medical_record_diagnose_item",
            "where medical_record_diagnose_id = #{medical_record_diagnose_id,jdbcType=INTEGER}" +
                    " and diagnose_type = #{diagnose_type,jdbcType=VARCHAR}"
    })
    @Results({
            @Result(column="id", property="id", jdbcType= JdbcType.INTEGER, id=true),
            @Result(column="medical_record_diagnose_id", property="medical_record_diagnose_id", jdbcType=JdbcType.INTEGER),
            @Result(column="disease_id", property="disease_id", jdbcType=JdbcType.INTEGER),
            @Result(column="disease_name", property="disease_name", jdbcType=JdbcType.VARCHAR),
            @Result(column="disease_code", property="disease_code", jdbcType=JdbcType.VARCHAR),
            @Result(column="diagnose_type", property="diagnose_type", jdbcType=JdbcType.VARCHAR),
            @Result(column="main_symptom", property="main_symptom", jdbcType=JdbcType.BIT),
            @Result(column="suspect", property="suspect", jdbcType=JdbcType.BIT),
            @Result(column="syndrome_differentiation", property="syndrome_differentiation", jdbcType=JdbcType.LONGVARCHAR)
    })
    List<MedicalRecordDiagnoseItem> selectByDiagnoseIdAndType(Integer medical_record_diagnose_id, String diagnose_type);
}
