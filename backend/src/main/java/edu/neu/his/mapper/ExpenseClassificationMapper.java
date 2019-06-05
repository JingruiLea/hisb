package edu.neu.his.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Mapper
@Component(value = "ExpenseClassificationMapper")
public interface ExpenseClassificationMapper {
    @Select("SELECT id FROM expense_classification where fee_name = #{fee_name}")
    int findClassificationIdByName(@Param("fee_name") String fee_name);
}
