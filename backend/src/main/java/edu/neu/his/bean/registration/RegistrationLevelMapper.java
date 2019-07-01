package edu.neu.his.bean.registration;

import edu.neu.his.bean.registration.RegistrationLevel;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 该类对数据库中的registration_level表进行数据持久化操作
 */
@Mapper
@Component(value = "RegistrationLevelMapper")
public interface RegistrationLevelMapper {
    /**
     * 根据挂号名称名称查找对应的挂号等级
     * @param name 挂号名称
     * @return 根据挂号名称名称查找的对应挂号等级
     */
    @Select("SELECT * FROM registration_level WHERE name = #{name}")
    RegistrationLevel findByName(@Param("name") String name);

    /**
     * 向数据库的registration_level表中插入一条记录
     * @param registration_level 挂号等级
     */
    @Insert("INSERT INTO registration_level (id, name, is_default, seq_num, fee) VALUES(#{id},#{name}, #{is_default}, #{seq_num}, #{fee})")
    void insert(RegistrationLevel registration_level);

    /**
     * 根据id更新数据库的registration_level表中相应的记录
     * @param registration_level 挂号等级
     */
    @Update("UPDATE registration_level SET name = #{name}, is_default = #{is_default}, seq_num = #{seq_num}, fee = #{fee} WHERE id = #{id}")
    void update(RegistrationLevel registration_level);

    /**
     * 查找所有挂号等级记录
     * @return 返回所有挂号等级记录的列表
     */
    @Select("SELECT * FROM registration_level ORDER BY id")
    List<RegistrationLevel> findAll();

    /**
     * 根据id从数据库中删除对应的挂号等级
     * @param id 挂号等级id
     */
    @Delete("DELETE FROM registration_level WHERE id=#{id}")
    void delete(int id);

    /**
     * 根据id查找对应的挂号等级
     * @param id 挂号等级id
     * @return 根据id查找的对应挂号等级
     */
    @Select("SELECT * FROM registration_level WHERE id = #{id}")
    RegistrationLevel findById(@Param("id") int id);

    /**
     * 根据id查找对应的挂号等级
     * @param id 挂号等级id
     * @return 根据id查找的对应挂号等级
     */
    @Select("select * FROM registration_level WHERE id=#{id}")
    RegistrationLevel find(int id);

    /**
     * 检查数据库中是否已存在该挂号等级id
     * @param id 要检查是否存在的挂号等级id
     * @return 返回该挂号等级id在数据库department表中存在的数量
     */
    @Select("SELECT count(*) FROM registration_level WHERE id = #{id}")
    int checkIdExists(@Param("id") int id);

    /**
     * 检查数据库中是否已存在该挂号等级名称
     * @param name 要检查是否存在的挂号等级名称
     * @return 返回该挂号等级名称在数据库department表中存在的数量
     */
    @Select("SELECT count(*) FROM registration_level WHERE name = #{name}")
    int checkNameExists(@Param("name") String name);

    /**
     * 查找默认挂号等级
     * @return 默认挂号等级
     */
    @Select("SELECT * FROM registration_level WHERE is_default = true ORDER BY id")
    List<RegistrationLevel> findDefault();
}
