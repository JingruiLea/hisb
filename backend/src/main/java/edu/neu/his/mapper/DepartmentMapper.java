package edu.neu.his.mapper;

import edu.neu.his.bean.Department;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component(value = "DepartmentMapper")
public interface DepartmentMapper {

    @Select("SELECT department.id, code, department.name, is_clinical,department_classification.name as classification, department_classification.id as classification_id " +
            "FROM department, department_classification " +
            "WHERE department.classification_id = department_classification.id and department.name = #{name}")
    Department findByName(@Param("name") String name);

    @Select("SELECT name from department_classification")
    List<String> findAllClassification();

    @Select("SELECT id FROM department_classification where name = #{name}")
    int findClassificationIdByName(@Param("name") String name);

    @Insert("INSERT INTO department(code, name , is_clinical, classification_id ) VALUES(#{code}, #{name} ,#{is_clinical},#{classification_id})")
    void insert(Department department);

    @Update("UPDATE department SET code = #{code}, name = #{name},is_clinical = #{is_clinical}, classification_id = #{classification_id} WHERE id = #{id}")
    void update(Department department);

    @Select("SELECT department.id, code, department.name, is_clinical,department_classification.name as classification, department_classification.id as classification_id " +
            "FROM department, department_classification " +
            "WHERE department.classification_id = department_classification.id;\n")
    List<Department> findAll();

    @Select("select name from department")
    List<String> findAllNames();

    @Delete("DELETE FROM department WHERE id=#{id}")
    void deleteDepartment(int id);
}
