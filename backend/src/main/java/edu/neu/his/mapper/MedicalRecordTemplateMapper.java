package edu.neu.his.mapper;

import edu.neu.his.bean.MedicalRecordTemplate;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface MedicalRecordTemplateMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MedicalRecordTemplate record);

    MedicalRecordTemplate selectByPrimaryKey(Integer id);

    List<MedicalRecordTemplate> selectAll();

    int updateByPrimaryKey(MedicalRecordTemplate record);
}