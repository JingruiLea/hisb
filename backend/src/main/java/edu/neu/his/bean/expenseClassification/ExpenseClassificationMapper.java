package edu.neu.his.bean.expenseClassification;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 该类对数据库中的expense_classification表进行数据持久化操作
 */
@Mapper
@Component(value = "ExpenseClassificationMapper")
public interface ExpenseClassificationMapper {
    /**
     * 根据费用类别名称查找对应的费用类别id
     * @param fee_name 费用类别名称
     * @return 费用类别id
     */
    @Select("SELECT id FROM expense_classification where fee_name = #{fee_name}")
    int findClassificationIdByName(@Param("fee_name") String fee_name);

    /**
     * 根据费用类别id查找对应的费用类别
     * @param id 费用类别id
     * @return 费用类别
     */
    @Select("SELECT fee_name FROM expense_classification where id = #{id}")
    String findClassificationById(@Param("id") int id);

    /**
     * 根据费用类别名称查找对应的费用类别
     * @param name 费用类别名称
     * @return 费用类别
     */
    @Select("SELECT id, pinyin, fee_name " +
            "FROM  expense_classification " +
            "WHERE expense_classification.name = #{name}")
    ExpenseClassification findByName(@Param("name") String name);

    /**
     * 向数据库的expense_classification表中插入一条记录
     * @param expenseClassification 要插入数据库中的ExpenseClassification对象
     */
    @Insert("INSERT INTO expense_classification(id, pinyin, fee_name) " +
            "VALUES(#{id}, #{pinyin}, #{fee_name})")
    void insert(ExpenseClassification expenseClassification);

    /**
     * 根据id更新数据库的expense_classification表中相应的记录
     * @param expenseClassification 要在数据库中更新的ExpenseClassification对象
     */
    @Update("UPDATE expense_classification SET pinyin = #{pinyin}, fee_name = #{fee_name} WHERE id = #{id}")
    void update(ExpenseClassification expenseClassification);

    /**
     * 查找所有费用科目记录
     * @return 返回所有费用科目记录的列表
     */
    @Select("SELECT * FROM  expense_classification")
    List<ExpenseClassification> findAll();

    /**
     * 根据id从数据库中删除对应的费用科目
     * @param id 要删除的费用科目的id
     */
    @Delete("DELETE FROM expense_classification WHERE id=#{id}")
    void delete(@Param("id") int id);

    /**
     * 检查数据库中该费用科目id存在的条数
     * @param expenseClassification 要检查存在条数的费用科目
     * @return 该费用科目id在数据库expense_classification表中存在的数量
     */
    @Select("SELECT count(*) FROM expense_classification where id = #{id}")
    int checkIdExistNums(ExpenseClassification expenseClassification);

    /**
     * 检查数据库中该费用科目名称存在的条数
     * @param expenseClassification 要检查存在条数的费用科目
     * @return 该费用科目名称在数据库expense_classification表中存在的数量
     */
    @Select("SELECT count(*) FROM expense_classification where fee_name = #{fee_name}")
    int checkNameExistNums(ExpenseClassification expenseClassification);

    /**
     * 检查数据库中该费用科目id存在的条数
     * @param id 要检查存在条数的费用科目id
     * @return 该费用科目id在数据库expense_classification表中存在的数量
     */
    @Select("SELECT count(*) FROM expense_classification where id = #{id}")
    int checkId(@Param("id") int id);

}
