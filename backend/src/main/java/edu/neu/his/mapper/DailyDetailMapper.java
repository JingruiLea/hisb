package edu.neu.his.mapper;

import edu.neu.his.bean.DailyDetail;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component(value = "DailyDetailMapper")
public interface DailyDetailMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DailyDetail record);

    DailyDetail selectByPrimaryKey(Integer id);

    List<DailyDetail> selectAll();

    int updateByPrimaryKey(DailyDetail record);
}