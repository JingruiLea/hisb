package edu.neu.his.mapper;

import edu.neu.his.bean.BillRecord;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.springframework.stereotype.Component;

@Mapper
@Component(value = "BillRecordMapper")
public interface BillRecordMapper {
    @Insert("INSERT INTO bill_record(medical_record_id, type , print_status, " +
            "cost, should_pay, truely_pay, retail_fee, user_id, creat_time) " +
        "VALUES(#{medical_record_id}, #{type}, #{print_status}, " +
            "#{cost}, #{should_pay}, #{truely_pay}, #{retail_fee}, #{user_id}, #{creat_time})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(BillRecord billRecord);
}
