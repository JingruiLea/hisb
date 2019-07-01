package edu.neu.his.bean.settlementCategory;

import edu.neu.his.bean.settlementCategory.SettlementCategory;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 该类对数据库中的settlement_category表进行数据持久化操作
 */
@Mapper
@Component(value = "SettlementCategoryMapper")
public interface SettlementCategoryMapper {

    /**
     * 查找所有结算类别
     * @return 返回所有结算类别的列表
     */
    @Select("SELECT * FROM settlement_category")
    List<SettlementCategory> findAll();

    /**
     * 向数据库的settlement_category表中插入一条记录
     * @param settlementCategory 要插入数据库中的SettlementCategory对象
     */
    @Insert("INSERT INTO settlement_category (id,name) values (#{id},#{name})")
    void addSettlementCategory(SettlementCategory settlementCategory);

    /**
     * 根据名称从数据库中删除对应的结算类别
     * @param name 要删除的结算类别名称
     */
    @Delete("DELETE FROM settlement_category WHERE name = #{name}")
    void deleteSettlementCategoryByName(@Param("name") String name);

    /**
     * 根据id从数据库中删除对应的结算类别
     * @param id 要删除的结算类别的id
     */
    @Delete("DELETE FROM settlement_category WHERE id = #{id}")
    void deleteSettlementCategoryById(@Param("id") int id);

    /**
     * 根据id更新数据库的settlement_category表中相应的记录
     * @param settlementCategory 要在数据库中更新的SettlementCategory对象
     */
    @Update("UPDATE settlement_category set name = #{name} WHERE id = #{id}")
    void updateSettlementCategory(SettlementCategory settlementCategory);

    /**
     * 检查数据库中是否已存在该结算类别id
     * @param id 要检查是否存在的结算类别id
     * @return 返回该结算类别id在数据库settlement_category表中存在的数量
     */
    @Select("SELECT count(*) FROM settlement_category WHERE id = #{id}")
    int checkIdExists(@Param("id") int id);

    /**
     * 检查数据库中是否已存在该结算类别名称
     * @param name 要检查是否存在的结算类别名称
     * @return 返回该结算类别名称在数据库settlement_category表中存在的数量
     */
    @Select("SELECT count(*) FROM settlement_category WHERE name = #{name}")
    int checkNameExists(@Param("name") String name);

    /**
     * 根据名称从数据库中查找对应的结算类别
     * @param name 结算类别名称
     * @return  根据名称从数据库中查找的对应结算类别
     */
    @Select("SELECT * FROM settlement_category WHERE name = #{name}")
    SettlementCategory findByName(@Param("name") String name);
}
