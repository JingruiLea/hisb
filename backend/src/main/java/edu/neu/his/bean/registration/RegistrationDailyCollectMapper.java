package edu.neu.his.bean.registration;

import edu.neu.his.bean.daily.DailyCollect;
import edu.neu.his.bean.daily.DailyDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 该类对数据库中的daily_collect、daily_detail表进行数据持久化操作
 */
@Mapper
@Component(value = "RegistrationDailyCollectMapper")
public interface RegistrationDailyCollectMapper {
    /**
     * 根据用户id查找对应的日结记录
     * @param uid 用户id
     * @return 根据用户id查找的对应日结记录
     */
    @Select("SELECT id, start_time, end_time, user_id FROM daily_collect WHERE user_id = #{uid}")
    List<DailyCollect> findDailyCollectByUid(@Param("uid") int uid);

    /**
     * 根据日结记录id查找对应的日结详情
     * @param daily_collect_id 日结记录id
     * @return 根据日结记录id查找的对应日结详情
     */
    @Select("select * from daily_detail where daily_collect_id = #{daily_collect_id}")
    List<DailyDetail> findDailyDetailById(@Param("daily_collect_id") int daily_collect_id);

}
