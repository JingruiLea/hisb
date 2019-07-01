package edu.neu.his.bean.outpatientCharges;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 该类对数据库中的outpatient_charges_record表进行数据持久化操作
 */
@Mapper
@Component(value = "ChargeAndRefundMapper")
public interface ChargeAndRefundMapper {
    /**
     * 根据病历号查找对应的缴费记录列表
     * @param medical_record_id 病历号
     * @return 对应的缴费记录列表
     */
    @Select("SELECT * from outpatient_charges_record where medical_record_id = #{medical_record_id}")
    List<OutpatientChargesRecord> findByMedicalRecordId(@Param("medical_record_id") int medical_record_id);

    /**
     * 根据病历号和id查找对应的缴费记录
     * @param medical_record_id 病历号
     * @param id id
     * @return 对应的缴费记录
     */
    @Select("SELECT * from outpatient_charges_record where medical_record_id = #{medical_record_id} and id = #{id}")
    OutpatientChargesRecord findByMedicalRecordIdAndId(@Param("medical_record_id") int medical_record_id, @Param("id") int id);

    /**
     * 根据病历号和起止时间查找对应的缴费记录
     * @param medical_record_id 病历号
     * @param start_time 起始时间
     * @param end_time 截止时间
     * @return 对应的缴费记录
     */
    @Select("SELECT * from outpatient_charges_record where medical_record_id = #{medical_record_id} and create_time > #{start_time} and create_time < #{end_time}")
    List<OutpatientChargesRecord> findByMedicalRecordIdAndTime(@Param("medical_record_id") int medical_record_id,
                                                               @Param("start_time") String start_time, @Param("end_time") String end_time);

    /**
     * 根据类型id查找对应的缴费记录
     * @param item_id 类型id
     * @return 对应的缴费记录
     */
    @Select("SELECT * from outpatient_charges_record where item_id = #{item_id}")
    OutpatientChargesRecord findByItemId(@Param("item_id") int item_id);
}
