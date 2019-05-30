package edu.neu.his.mapper;

import edu.neu.his.bean.Exam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface ExamMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Exam record);

    Exam selectByPrimaryKey(Integer id);

    List<Exam> selectAll();

    int updateByPrimaryKey(Exam record);
}