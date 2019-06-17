package edu.neu.his.bean.department;

import edu.neu.his.bean.department.Department;
import edu.neu.his.bean.department.DepartmentClassification;
import edu.neu.his.util.Importable;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component(value = "DepartmentMapper")
public interface DepartmentMapper extends Importable<Department> {

    @Select("SELECT department.id, pinyin, department.name, type,department_classification.name as classification_name, department_classification.id as classification_id " +
            "FROM department, department_classification " +
            "WHERE department.classification_id = department_classification.id and department.name = #{name}")
    Department findByName(@Param("name") String name);

    @Select("SELECT * from department_classification")
    List<DepartmentClassification> findAllClassification();

    @Select("SELECT id FROM department_classification where name = #{name}")
    int findClassificationIdByName(@Param("name") String name);

    @Insert("INSERT INTO department(id,pinyin, name , type, classification_id ) VALUES(#{id}, #{pinyin}, #{name} ,#{type},#{classification_id})")
    int insert(Department department);

    @Update("UPDATE department SET pinyin = #{pinyin} ,type = #{type} ,name = #{name}, classification_id = #{classification_id} WHERE id = #{id}")
    void update(Department department);

    @Select("SELECT department.id, pinyin, department.name, type,department_classification.name as classification_name, department_classification.id as classification_id " +
            "FROM department, department_classification " +
            "WHERE department.classification_id = department_classification.id;\n")
    List<Department> findAll();

    @Delete("DELETE FROM department WHERE id=#{id}")
    void deleteDepartment(int id);

    @Select("SELECT count(*) FROM department WHERE id = #{id}")
    int checkIdExists(@Param("id") int id);

    @Select("SELECT count(*) FROM department WHERE name = #{name}")
    int checkNameExists(@Param("name") String name);

    @Select("SELECT count(*) FROM department_classification WHERE id = #{id}")
    int checkClassificationExists(@Param("id") int id);
}
