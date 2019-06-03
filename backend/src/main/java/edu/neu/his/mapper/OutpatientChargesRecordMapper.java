package edu.neu.his.mapper;

import edu.neu.his.bean.OutpatientChargesRecord;
import java.util.List;

public interface OutpatientChargesRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OutpatientChargesRecord record);

    OutpatientChargesRecord selectByPrimaryKey(Integer id);

    List<OutpatientChargesRecord> selectAll();

    int updateByPrimaryKey(OutpatientChargesRecord record);
}