package edu.neu.his.mapper;

import edu.neu.his.bean.Department;
import edu.neu.his.bean.DepartmentClassification;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component(value = "DepartmentClassificationMapper")
public interface DepartmentClassificationMapper {
    @Select("SELECT * FROM department_classification")
    List<DepartmentClassification> selectAll();

    @Select("SELECT id FROM department_classification where name = #{name}")
    int selectIdByName(@Param("name") String name);
}

