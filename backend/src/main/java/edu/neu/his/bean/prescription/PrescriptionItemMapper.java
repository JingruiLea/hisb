package edu.neu.his.bean.prescription;

import edu.neu.his.bean.prescription.PrescriptionItem;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component(value = "PrescriptionItemMapper")
public interface PrescriptionItemMapper {
    @Select("SELECT prescription_item.id, amount, prescription_id, drug_id, prescription_item.status, note " +
            "FROM  prescription_item, outpatient_charges_record " +
            "WHERE prescription_id = #{prescription_id} " +
            "and prescription_item.status = #{prescriptionStatus} " +
            "and prescription_item.id = outpatient_charges_record.item_id " +
            "and outpatient_charges_record.status = #{recordStatus}")
    List<PrescriptionItem> selectByPrescriptionAndStatus(@Param("prescription_id") Integer prescription_id,
                                                         @Param("prescriptionStatus") String prescriptionStatus,
                                                         @Param("recordStatus") String recordStatus);

    @Select("SELECT * FROM  prescription_item" +
            "where status = #{status}")
    List<PrescriptionItem> selectByStatus(@Param("status") String status);
}
