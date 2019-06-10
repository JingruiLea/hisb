package edu.neu.his.service;

import edu.neu.his.bean.DailyCollect;
import edu.neu.his.bean.DailyDetail;
import edu.neu.his.mapper.RegistrationDailyCollectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DailyCollectService {
    @Autowired
    private RegistrationDailyCollectMapper registrationDailyCollectMapper;

    @Transactional
    public List<DailyCollect> findDailyCollectByUid(int uid){
        return registrationDailyCollectMapper.findDailyCollectByUid(uid);
    }

    @Transactional
    public List<DailyDetail> findDailyDetailById(int daily_collect_id){
        return registrationDailyCollectMapper.findDailyDetailById(daily_collect_id);
    }

}
