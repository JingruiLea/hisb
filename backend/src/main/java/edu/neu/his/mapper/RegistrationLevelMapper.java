package edu.neu.his.mapper;

import edu.neu.his.bean.Registration_level;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component(value = "RegistrationLevelMapper")
public interface RegistrationLevelMapper {
    @Select("SELECT * FROM REGISTRATION_LEVEL WHERE name = #{name}")
    List<Registration_level> findByName(@Param("name") String name);

    @Insert("INSERT INTO registration_level(id, name, is_default, seq_num, fee) VALUES(#{id}, #{name}, #{is_default}, #{seq_num}, #{fee})")
    void insert(Registration_level registration_level);

    @Update("UPDATE registration_level SET name = #{name}, is_default = #{is_default}, seq_num = #{seq_num}, fee = #{fee} WHERE id = #{id}")
    void update(Registration_level registration_level);

    @Select("SELECT * FROM REGISTRATION_LEVEL")
    List<Registration_level> findAll();

    @Delete("DELETE FROM REGISTRATION_LEVEL WHERE id=#{id}")
    void delete(int id);
}
