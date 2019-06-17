package edu.neu.his.bean.daily;

import edu.neu.his.bean.daily.DailyCollect;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component(value = "DailyCollectMapper")
public interface DailyCollectMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DailyCollect record);

    DailyCollect selectByPrimaryKey(Integer id);

    List<DailyCollect> selectAll();

    int updateByPrimaryKey(DailyCollect record);
}