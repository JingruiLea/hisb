package edu.neu.his.mapper;

import edu.neu.his.bean.DailyCollect;
import edu.neu.his.bean.DailyDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component(value = "RegistrationDailyCollectMapper")
public interface RegistrationDailyCollectMapper {
    @Select("SELECT id, start_time, end_time, user_id, user.username as user_name " +
            "FROM user,daily_collect " +
            "WHERE user.id = daily_collect.user_id " +
            "and user.id = #{uid}")
    List<DailyCollect> findDailyCollectByUid(@Param("uid") int uid);

    @Select("select * from daily_detail where daily_collect_id = #{daily_collect_id}")
    List<DailyDetail> findDailyDetailById(@Param("daily_collect_id") int daily_collect_id);

}
