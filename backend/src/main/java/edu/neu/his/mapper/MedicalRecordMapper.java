package edu.neu.his.mapper;

import edu.neu.his.bean.MedicalRecord;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface MedicalRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MedicalRecord record);

    MedicalRecord selectByPrimaryKey(Integer id);

    List<MedicalRecord> selectAll();

    int updateByPrimaryKey(MedicalRecord record);
}