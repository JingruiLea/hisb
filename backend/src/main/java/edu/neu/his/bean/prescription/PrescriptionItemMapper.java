package edu.neu.his.bean.prescription;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 该类对数据库中的prescription_item表进行数据持久化操作
 */
@Mapper
@Component(value = "PrescriptionItemMapper")
public interface PrescriptionItemMapper {
    /**
     * 根据处方id和状态查找对应的处方详情
     * @param prescription_id 处方id
     * @param prescriptionStatus 处方状态
     * @param recordStatus 病历状态
     * @return 对应的处方详情列表
     */
    @Select("SELECT prescription_item.id, amount, prescription_id, drug_id, prescription_item.status, note " +
            "FROM  prescription_item, outpatient_charges_record " +
            "WHERE prescription_id = #{prescription_id} " +
            "and prescription_item.status = #{prescriptionStatus} " +
            "and prescription_item.id = outpatient_charges_record.item_id " +
            "and outpatient_charges_record.status = #{recordStatus}")
    List<PrescriptionItem> selectByPrescriptionAndStatus(@Param("prescription_id") Integer prescription_id,
                                                         @Param("prescriptionStatus") String prescriptionStatus,
                                                         @Param("recordStatus") String recordStatus);

    /**
     * 根据状态查找对应的处方详情
     * @param status 处方状态
     * @return 处方详情列表
     */
    @Select("SELECT * FROM  prescription_item " +
            "where status = #{status,jdbcType=VARCHAR}")
    List<PrescriptionItem> selectByStatus(@Param("status") String status);
}
