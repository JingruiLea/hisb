package edu.neu.his.bean.statistics;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Mapper
@Component("StatisticsMapper")
public interface StatisticsMapper {
    @Select("SELECT expense_classification.fee_name,sum(cost) as money " +
            "FROM outpatient_charges_record, expense_classification  " +
            "WHERE  CAST(create_time AS DATETIME) > CAST(#{start_time} AS DATETIME) " +
            "and CAST(create_time AS DATETIME) < CAST(#{end_time} AS DATETIME) " +
            "and outpatient_charges_record.expense_classification_id=expense_classification.id " +
            "and execute_department_id = #{department_id} " +
            "and status = #{status} group by expense_classification.fee_name")
    List<Map<String,Object>> getExpenseClassifications(@Param("start_time") String start_time,
                                                      @Param("end_time") String end_time,
                                                      @Param("department_id") int department_id,
                                                      @Param("status") String status);
}
