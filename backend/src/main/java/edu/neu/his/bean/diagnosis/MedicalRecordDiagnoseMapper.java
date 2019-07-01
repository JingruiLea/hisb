package edu.neu.his.bean.diagnosis;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;

/**
 * 该类对数据库中的medical_record_diagnose表进行数据持久化操作
 */
@Mapper
public interface MedicalRecordDiagnoseMapper {
    /**
     * 根据病历号查找对应的诊断
     * @param medical_record_id 病历号
     * @return 返回对应的诊断
     */
    @Select({
            "select",
            "id, medical_record_id",
            "from medical_record_diagnose",
            "where medical_record_id = #{medical_record_id,jdbcType=INTEGER}"
    })
    @Results({
            @Result(column="id", property="id", jdbcType= JdbcType.INTEGER, id=true),
            @Result(column="medical_record_id", property="medical_record_id", jdbcType=JdbcType.INTEGER)
    })
    MedicalRecordDiagnose selectByMedicalRecordId(Integer medical_record_id);
}
