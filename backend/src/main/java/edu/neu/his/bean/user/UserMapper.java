package edu.neu.his.bean.user;

import edu.neu.his.bean.user.Role;
import edu.neu.his.bean.user.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 该类对数据库中的user表进行数据持久化操作
 */
@Mapper
@Component(value = "UserMapper")
public interface UserMapper {

    /**
     * 根据用户名称查找对应的用户id
     * @param username 用户名称
     * @return 根据用户名称查找的对应用户id
     */
    @Select("SELECT id FROM user where username = #{username}")
    int getUidByUserName(@Param("username") String username);

    /**
     * 根据用户ID查找对应的用户
     * @param uid 用户id
     * @return 根据用户ID查找的对应用户
     */
    @Select("SELECT uid, username ,password, real_name, department_id, role_id, participate_in_scheduling, title," +
            "department.name as department_name, role.name as role_name " +
            "FROM user, user_info, department, role " +
            "WHERE uid = #{uid} and user.id = user_info.uid and department.id = user_info.department_id and role.id = user_info.role_id")
    User find(@Param("uid") int uid);

    /**
     * 根据用户名称查找对应的用户列表
     * @param username 用户名称
     * @return 根据用户名称查找的对应用户列表
     */
    @Select("SELECT username ,password, real_name FROM user,user_info WHERE username = #{username} and user.id = user_info.uid")
    List<User> findByName(@Param("username") String username);

    /**
     * 根据用户名称查找一个用户
     * @param username 用户名称
     * @return 根据用户名称查找的对应用户
     */
    @Select("SELECT uid, username ,password, real_name FROM user,user_info WHERE username = #{username} and user.id = user_info.uid")
    User findOneByName(@Param("username") String username);

    /**
     * 查找所有用户
     * @return 返回所有用户的列表
     */
    @Select("SELECT uid, username, password, real_name, department_id,department.name as department_name,role_id, role.name as role_name, title, participate_in_scheduling " +
            "FROM user,user_info,role,department " +
            "WHERE user.id = user_info.uid and department.id = user_info.department_id and user_info.role_id=role.id")
    List<User> findAll();

    /**
     * 向数据库的user表中插入一条记录
     * @param user 要插入数据库中的User对象
     */
    @Insert("INSERT INTO user (username, password) VALUES(#{username}, #{password})")
    void addUser(User user);

    /**
     * 向数据库的user_info表中插入一条详细记录
     * @param user 要插入数据库中的User对象
     */
    @Insert("INSERT INTO user_info (uid, real_name, department_id, role_id, participate_in_scheduling, title) " +
            "values (#{uid}, #{real_name}, #{department_id}, #{role_id}, #{participate_in_scheduling}, #{title})")
    void addUserInfo(User user);

    /**
     * 根据id更新数据库的user表中相应的记录
     * @param user 要在数据库中更新的User对象
     */
    @Update("UPDATE user SET username = #{username}, password = #{password} WHERE id = #{uid}")
    void updateUser(User user);

    /**
     * 根据id更新数据库的user_info表中相应的详细记录
     * @param user 要在数据库中更新的User对象
     */
    @Update("UPDATE user_info SET real_name = #{real_name}, department_id = #{department_id}, " +
            "participate_in_scheduling = #{participate_in_scheduling}, role_id = #{role_id}, title = #{title} " +
            "where uid = #{uid}")
    void updateUserInfo(User user);

    /**
     * 根据id从数据库中删除对应的用户
     * @param uid 要删除的用户的id
     */
    @Delete("DELETE from user where id = #{id}")
    void deleteUser(@Param("id") int uid);

    /**
     * 根据id从数据库中删除对应的用户信息
     * @param uid 要删除的用户的id
     */
    @Delete("DELETE from user_info where uid = #{uid}")
    void deleteUserInfo(@Param("uid") int uid);

    /**
     * 根据科室id和职称查找对应的医生列表
     * @param departmentId 科室id
     * @param title 职称
     * @return 根据科室id和职称查找的对应医生列表
     */
    @Select("select * from userinfo where title=${title} and department_id=${departmentId}")
    List<User> selectDoctorList(int departmentId, String title);

    /**
     * 查找所有角色
     * @return 返回所有角色的列表
     */
    @Select("SELECT * FROM role")
    List<Role> allRoles();

    /**
     * 检查数据库中是否已存在该用户id
     * @param id 要检查是否存在的用户id
     * @return 返回该用户id在数据库user表中存在的数量
     */
    @Select("SELECT count(*) FROM user WHERE id = #{id}")
    int checkIdExists(@Param("id") int id);

    /**
     * 检查数据库中是否已存在该用户名称
     * @param username 要检查是否存在的用户名称
     * @return 返回该用户名称在数据库user表中存在的数量
     */
    @Select("SELECT count(*) FROM user WHERE username = #{username}")
    int checkNameExists(@Param("username") String username);
}
