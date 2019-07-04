package edu.neu.his.bean.diagnosis;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

@Mapper
public interface MedicalRecordDiagnoseMapper {
    @Select({
            "select",
            "id, medical_record_id ",
            "from medical_record_diagnose ",
            "where medical_record_id = #{medical_record_id,jdbcType=INTEGER} ",
            "and is_end = #{is_end,jdbcType=BIT} "
    })
    @Results({
            @Result(column="id", property="id", jdbcType= JdbcType.INTEGER, id=true),
            @Result(column="medical_record_id", property="medical_record_id", jdbcType=JdbcType.INTEGER)
    })
    MedicalRecordDiagnose selectByMedicalRecordId2(@Param("medical_record_id") Integer medical_record_id,@Param("is_end") boolean is_end);


    @Select({
            "select",
            "id, medical_record_id ",
            "from medical_record_diagnose ",
            "where medical_record_id = #{medical_record_id,jdbcType=INTEGER} ",
            "and is_end = null"
    })
    @Results({
            @Result(column="id", property="id", jdbcType= JdbcType.INTEGER, id=true),
            @Result(column="medical_record_id", property="medical_record_id", jdbcType=JdbcType.INTEGER)
    })
    MedicalRecordDiagnose selectByMedicalRecordId(@Param("medical_record_id") Integer medical_record_id);
}
