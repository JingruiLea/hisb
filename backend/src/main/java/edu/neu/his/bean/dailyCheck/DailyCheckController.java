package edu.neu.his.bean.dailyCheck;

import edu.neu.his.bean.daily.DailyCollect;
import edu.neu.his.bean.daily.DailyCollectMapper;
import edu.neu.his.bean.daily.DailyCollectService;
import edu.neu.his.bean.user.User;
import edu.neu.his.bean.user.UserService;
import edu.neu.his.config.Auth;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import edu.neu.his.config.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/dailyCheck")
public class DailyCheckController {
    @Autowired
    private DailyCheckService dailyCheckService;

    @Autowired
     UserService userService;
    @Autowired
    DailyCollectMapper dailyCollectMapper;
    @Autowired
    DailyCollectService dailyCollectService;

    @RequestMapping("/init")
    @ResponseBody
    public Map init(){
        List<User> list = dailyCheckService.getTollCollector();
        for (User user : list) {
            user.setPassword(null);
        }
        return Response.ok(list);
    }

    @RequestMapping("/list")
    @ResponseBody
    public Map list(@RequestBody Map req){
        int _uid = Auth.uid(req);
        if(userService.findByUid(_uid)!=null)
            return Response.ok(dailyCollectMapper.selectAll());
        else
            return Response.error("错误，该用户ID不存在");
    }

    @RequestMapping("/check")
    @ResponseBody
    public Map check(@RequestBody Map req){
        int id = (int)req.get("id");
        DailyCollect dailyCollect = dailyCollectService.findDailyCollectById(id);
        dailyCollect.setChecked(true);
        dailyCollectService.updateDailyCollect(dailyCollect);
        return Response.ok();
    }
}
