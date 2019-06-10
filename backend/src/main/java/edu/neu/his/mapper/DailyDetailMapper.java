package edu.neu.his.mapper;

import edu.neu.his.bean.DailyDetail;
import java.util.List;

public interface DailyDetailMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DailyDetail record);

    DailyDetail selectByPrimaryKey(Integer id);

    List<DailyDetail> selectAll();

    int updateByPrimaryKey(DailyDetail record);
}