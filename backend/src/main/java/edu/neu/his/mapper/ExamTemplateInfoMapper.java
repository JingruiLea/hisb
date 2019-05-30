package edu.neu.his.mapper;

import edu.neu.his.bean.ExamTemplateInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ExamTemplateInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ExamTemplateInfo record);

    ExamTemplateInfo selectByPrimaryKey(Integer id);

    List<ExamTemplateInfo> selectAll();

    int updateByPrimaryKey(ExamTemplateInfo record);
}