package edu.neu.his.bean.dailyCheck;

import edu.neu.his.bean.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DailyCheckService {
    @Autowired
    private DailyCheckMapper dailyCheckMapper;

    @Transactional
    public List<User> getTollCollector() {
        return dailyCheckMapper.getTollCollector();
    }
}
