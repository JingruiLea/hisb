package edu.neu.his.mapper;

import edu.neu.his.bean.CommonDiagnosis;
import java.util.List;

public interface CommonDiagnosisMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CommonDiagnosis record);

    CommonDiagnosis selectByPrimaryKey(Integer id);

    List<CommonDiagnosis> selectAll();

    int updateByPrimaryKey(CommonDiagnosis record);
}