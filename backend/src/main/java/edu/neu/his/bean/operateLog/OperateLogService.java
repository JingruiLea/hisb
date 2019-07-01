package edu.neu.his.bean.operateLog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 实现处理数据库中operate_log表的相关操作
 */
@Service
public class OperateLogService {
    @Autowired
    private OperateLogMapper operateLogMapper;

    /**
     * 向数据库中插入一条操作记录
     * @param operateLog 要插入数据库中的OperateLog对象
     */
    @Transactional
    public void insertOperateLog(OperateLog operateLog) {
        operateLogMapper.insert(operateLog);
    }
}
