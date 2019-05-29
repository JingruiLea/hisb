package edu.neu.his.mapper;

import edu.neu.his.bean.SettlementCategory;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component(value = "SettlementCategoryMapper")
public interface SettlementCategoryMapper {

    @Select("SELECT * FROM settlement_category")
    List<SettlementCategory> findAll();

    @Insert("INSERT INTO settlement_category (id,name) values (#{id},#{name})")
    void addSettlementCategory(SettlementCategory settlementCategory);

    @Delete("DELETE FROM settlement_category WHERE name = #{name}")
    void deleteSettlementCategoryByName(@Param("name") String name);

    @Delete("DELETE FROM settlement_category WHERE id = #{id}")
    void deleteSettlementCategoryById(@Param("id") int id);

    @Update("UPDATE settlement_category set name = #{name} WHERE id = #{id}")
    void updateSettlementCategory(SettlementCategory settlementCategory);
}
