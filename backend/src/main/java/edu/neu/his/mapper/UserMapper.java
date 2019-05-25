package edu.neu.his.mapper;

import edu.neu.his.bean.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("SELECT * FROM USER WHERE username = #{name}")
    List<User> findByName(@Param("username") String username);

    @Insert("INSERT INTO user(username, password) VALUES(#{username}, #{password})")
    void insert(User user);

    @Update("UPDATE user SET username = #{username}, password = #{password} WHERE id = #{id}")
    void update(User user);
}
