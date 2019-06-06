package edu.neu.his.mapper;

import edu.neu.his.bean.OutpatientChargesRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component(value = "ChargeAndRefundMapper")
public interface ChargeAndRefundMapper {
    @Select("SELECT * from outpatient_charges_record where medical_record_id = #{medical_record_id}")
    List<OutpatientChargesRecord> findByMedicalRecordId(@Param("medical_record_id") int medical_record_id);

    @Select("SELECT * from outpatient_charges_record where medical_record_id = #{medical_record_id} and id = #{id}")
    OutpatientChargesRecord findByMedicalRecordIdAndId(@Param("medical_record_id") int medical_record_id, @Param("id") int id);
}
