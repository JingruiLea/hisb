package edu.neu.his.mapper;

import edu.neu.his.bean.CommonDiagnosis;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface CommonDiagnosisMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CommonDiagnosis record);

    CommonDiagnosis selectByPrimaryKey(Integer id);

    List<CommonDiagnosis> selectAll();

    int updateByPrimaryKey(CommonDiagnosis record);
}