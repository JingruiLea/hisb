package edu.neu.his.mapper;

import edu.neu.his.bean.Department;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component(value = "DepartmentMapper")
public interface DepartmentMapper {
    @Select("SELECT * FROM department WHERE name = #{name}")
    List<Department> findByName(@Param("name") String name);

    @Select("SELECT name FROM department_classification")
    List<String> findAllClassification();

    @Insert("INSERT INTO department(code, name, classification,is_clinical, classification_id ) VALUES(#{code}, #{name}, #{classification},#{is_clinical},#{classification_id})")
    void insert(Department department);

    @Update("UPDATE department SET code = #{code}, name = #{name}, classification = #{classification}, classification_id = #{classification_id} WHERE id = #{id}")
    void update(Department department);

    @Select("SELECT * FROM department")
    List<Department> findAll();

    @Delete("DELETE FROM department WHERE id=#{id}")
    void deleteDepartment(int id);
}
