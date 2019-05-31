package edu.neu.his.mapper;

import edu.neu.his.bean.ExamTemplate;
import java.util.List;

public interface ExamTemplateMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ExamTemplate record);

    ExamTemplate selectByPrimaryKey(Integer id);

    List<ExamTemplate> selectAll();

    int updateByPrimaryKey(ExamTemplate record);
}