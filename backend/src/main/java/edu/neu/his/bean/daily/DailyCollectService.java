package edu.neu.his.bean.daily;

import edu.neu.his.bean.registration.RegistrationDailyCollectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DailyCollectService {
    @Autowired
    private RegistrationDailyCollectMapper registrationDailyCollectMapper;

    @Autowired
    private DailyCollectMapper dailyCollectMapper;

    @Autowired
    private DailyDetailMapper dailyDetailMapper;

    @Transactional
    public List<DailyCollect> findDailyCollectByUid(int uid){
        return registrationDailyCollectMapper.findDailyCollectByUid(uid);
    }

    @Transactional
    public List<DailyDetail> findDailyDetailByCollectId(int daily_collect_id){
        return registrationDailyCollectMapper.findDailyDetailById(daily_collect_id);
    }

    @Transactional
    public int insertDailyCollect(DailyCollect dailyCollect){
        return dailyCollectMapper.insert(dailyCollect);
    }

    @Transactional
    public int insertDailyDetail(DailyDetail dailyDetail){
        return dailyDetailMapper.insert(dailyDetail);
    }

    @Transactional
    public DailyCollect findDailyCollectById(int id){
        return dailyCollectMapper.selectByPrimaryKey(id);
    }
}
