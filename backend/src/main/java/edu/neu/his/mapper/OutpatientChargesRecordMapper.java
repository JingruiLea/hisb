package edu.neu.his.mapper;

import edu.neu.his.bean.OutpatientChargesRecord;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component(value = "OutpatientChargesRecordMapper")
public interface OutpatientChargesRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OutpatientChargesRecord record);

    OutpatientChargesRecord selectByPrimaryKey(Integer id);

    List<OutpatientChargesRecord> selectAll();

    int updateByPrimaryKey(OutpatientChargesRecord record);
}