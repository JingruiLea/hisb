package edu.neu.his.service;

import edu.neu.his.bean.OperateLog;
import edu.neu.his.mapper.OperateLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OperateLogService {
    @Autowired
    private OperateLogMapper operateLogMapper;

    @Transactional
    public void insertOperateLog(OperateLog operateLog) {
        operateLogMapper.insert(operateLog);
    }
}
