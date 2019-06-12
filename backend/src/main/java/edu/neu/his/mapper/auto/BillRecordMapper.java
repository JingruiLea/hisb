package edu.neu.his.mapper.auto;

import edu.neu.his.bean.BillRecord;
import java.util.List;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

@Mapper
public interface BillRecordMapper {
    @Delete({
        "delete from bill_record",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into bill_record (medical_record_id, `type`, ",
        "print_status, cost, ",
        "should_pay, truely_pay, ",
        "retail_fee, user_id, ",
        "create_time)",
        "values (#{medical_record_id,jdbcType=INTEGER}, #{type,jdbcType=VARCHAR}, ",
        "#{print_status,jdbcType=INTEGER}, #{cost,jdbcType=REAL}, ",
        "#{should_pay,jdbcType=REAL}, #{truely_pay,jdbcType=REAL}, ",
        "#{retail_fee,jdbcType=REAL}, #{user_id,jdbcType=INTEGER}, ",
        "#{create_time,jdbcType=VARCHAR})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Integer.class)
    int insert(BillRecord record);

    @Select({
        "select",
        "id, medical_record_id, `type`, print_status, cost, should_pay, truely_pay, retail_fee, ",
        "user_id, create_time",
        "from bill_record",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="medical_record_id", property="medical_record_id", jdbcType=JdbcType.INTEGER),
        @Result(column="type", property="type", jdbcType=JdbcType.VARCHAR),
        @Result(column="print_status", property="print_status", jdbcType=JdbcType.INTEGER),
        @Result(column="cost", property="cost", jdbcType=JdbcType.REAL),
        @Result(column="should_pay", property="should_pay", jdbcType=JdbcType.REAL),
        @Result(column="truely_pay", property="truely_pay", jdbcType=JdbcType.REAL),
        @Result(column="retail_fee", property="retail_fee", jdbcType=JdbcType.REAL),
        @Result(column="user_id", property="user_id", jdbcType=JdbcType.INTEGER),
        @Result(column="create_time", property="create_time", jdbcType=JdbcType.VARCHAR)
    })
    BillRecord selectByPrimaryKey(Integer id);

    @Select({
        "select",
        "id, medical_record_id, `type`, print_status, cost, should_pay, truely_pay, retail_fee, ",
        "user_id, create_time",
        "from bill_record"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="medical_record_id", property="medical_record_id", jdbcType=JdbcType.INTEGER),
        @Result(column="type", property="type", jdbcType=JdbcType.VARCHAR),
        @Result(column="print_status", property="print_status", jdbcType=JdbcType.INTEGER),
        @Result(column="cost", property="cost", jdbcType=JdbcType.REAL),
        @Result(column="should_pay", property="should_pay", jdbcType=JdbcType.REAL),
        @Result(column="truely_pay", property="truely_pay", jdbcType=JdbcType.REAL),
        @Result(column="retail_fee", property="retail_fee", jdbcType=JdbcType.REAL),
        @Result(column="user_id", property="user_id", jdbcType=JdbcType.INTEGER),
        @Result(column="create_time", property="create_time", jdbcType=JdbcType.VARCHAR)
    })
    List<BillRecord> selectAll();

    @Update({
        "update bill_record",
        "set medical_record_id = #{medical_record_id,jdbcType=INTEGER},",
          "`type` = #{type,jdbcType=VARCHAR},",
          "print_status = #{print_status,jdbcType=INTEGER},",
          "cost = #{cost,jdbcType=REAL},",
          "should_pay = #{should_pay,jdbcType=REAL},",
          "truely_pay = #{truely_pay,jdbcType=REAL},",
          "retail_fee = #{retail_fee,jdbcType=REAL},",
          "user_id = #{user_id,jdbcType=INTEGER},",
          "create_time = #{create_time,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(BillRecord record);
}