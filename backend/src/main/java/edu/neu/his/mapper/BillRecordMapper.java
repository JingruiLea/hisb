package edu.neu.his.mapper;

import edu.neu.his.bean.BillRecord;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component(value = "BillRecordMapper")
public interface BillRecordMapper {

}
