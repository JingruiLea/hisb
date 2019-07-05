package edu.neu.his.bean.outpatientCharges;

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

    @Select("SELECT * from outpatient_charges_record where medical_record_id = #{medical_record_id} and CAST(collect_time AS DATETIME) > CAST(#{start_time} AS DATETIME) " +
            "and  CAST(collect_time AS DATETIME) < CAST(#{end_time} AS DATETIME)")
    List<OutpatientChargesRecord> findByMedicalRecordIdAndTime(@Param("medical_record_id") int medical_record_id,
                                                               @Param("start_time") String start_time, @Param("end_time") String end_time);

    @Select("SELECT * from outpatient_charges_record where item_id = #{item_id}")
    OutpatientChargesRecord findByItemId(@Param("item_id") int item_id);
}
