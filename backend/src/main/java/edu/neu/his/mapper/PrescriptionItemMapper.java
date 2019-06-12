package edu.neu.his.mapper;

import edu.neu.his.bean.PrescriptionItem;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PrescriptionItemMapper {
    @Select("SELECT prescription_item.id, amount, prescription_id, drug_id, prescription_item.status, note, drug.name as drug_item" +
            "FROM  prescription_item, drug, outpatient_charges_record " +
            "WHERE prescription_item.drug_id = drug.id and prescription_id = #{prescription_id} " +
            "and prescription_item.status = #{prescriptionStatus} " +
            "and prescription_item.id = outpatient_charges_record.item_id " +
            "and outpatient_charges_record.status = #{recordStatus}")
    List<PrescriptionItem> selectByPrescriptionAndStatus(@Param("prescription_id") Integer prescription_id,
                                                         @Param("prescriptionStatus") String prescriptionStatus,
                                                         @Param("recordStatus") String recordStatus);
}
