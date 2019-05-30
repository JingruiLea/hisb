package edu.neu.his.mapper;

import edu.neu.his.bean.Registration_level;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component(value = "RegistrationLevelMapper")
public interface RegistrationLevelMapper {
    @Select("SELECT * FROM registration_level WHERE name = #{name}")
    Registration_level findByName(@Param("name") String name);

    @Insert("INSERT INTO registration_level (id, name, is_default, seq_num, fee) VALUES(#{id},#{name}, #{is_default}, #{seq_num}, #{fee})")
    void insert(Registration_level registration_level);

    @Update("UPDATE registration_level SET name = #{name}, is_default = #{is_default}, seq_num = #{seq_num}, fee = #{fee} WHERE id = #{id}")
    void update(Registration_level registration_level);

    @Select("SELECT * FROM registration_level")
    List<Registration_level> findAll();

    @Delete("DELETE FROM registration_level WHERE id=#{id}")
    void delete(int id);

    @Select("select * from REGISTRATION_LEVEL where id=#{id}")
    Registration_level find(int id);

    @Select("SELECT * FROM registration_level WHERE id = #{id}")
    Registration_level findById(@Param("id") int id);

    @Select("SElECT * From userinfo where ")
    void doctorList(@Param("departmentId") int departmentId, @Param("registrationLevelId") int registrationLevelId);
}
