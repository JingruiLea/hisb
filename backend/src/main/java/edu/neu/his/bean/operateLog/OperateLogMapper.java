package edu.neu.his.bean.operateLog;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * 该类对数据库中的operate_log表进行数据持久化操作
 */
@Mapper
@Component(value = "OperateLogMapper")
public interface OperateLogMapper {
    /**
     * 向数据库的operate_log表中插入一条记录
     * @param operateLog 要插入数据库中的OperateLog对象
     */
    @Insert("INSERT INTO operate_log(user_id, operate_id, type , bill_record_id, fee, create_time) " +
            "VALUES(#{user_id}, #{operate_id}, #{type}, #{bill_record_id}, #{fee}, #{create_time})")
    void insert(OperateLog operateLog);
}
