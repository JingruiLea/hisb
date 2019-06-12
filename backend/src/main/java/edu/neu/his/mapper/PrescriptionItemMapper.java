package edu.neu.his.mapper;

import edu.neu.his.bean.PrescriptionItem;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PrescriptionItemMapper {
    @Select("SELECT id, amount, prescription_id, drug_id, status, note, drug.name as drug_item" +
            "FROM  prescription_item, drug " +
            "WHERE prescription_item.drug_id = drug.id and prescription_id = #{prescription_id} and status = #{status}")
    List<PrescriptionItem> selectByPrescriptionAndStatus(@Param("prescription_id") Integer prescription_id, @Param("status") String status);
}
