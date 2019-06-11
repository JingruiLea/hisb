package edu.neu.his.controller.registeredTollCollectorController;

import edu.neu.his.bean.BillRecord;
import edu.neu.his.bean.DailyCollect;
import edu.neu.his.bean.DailyDetail;
import edu.neu.his.config.Auth;
import edu.neu.his.config.Response;
import edu.neu.his.service.BillRecordService;
import edu.neu.his.service.DailyCollectService;
import edu.neu.his.service.UserService;
import edu.neu.his.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/dailyCollect")
public class RegistrationDailyCollectController {
    @Autowired
    private UserService userService;

    @Autowired
    private DailyCollectService dailyCollectService;

    @Autowired
    private BillRecordService billRecordService;

    @GetMapping("/list")
    @ResponseBody
    public Map list(int _uid){
        if(userService.findByUid(_uid)!=null)
            return Response.Ok(dailyCollectService.findDailyCollectByUid(_uid));
        else
            return Response.Error("错误，该用户ID不存在");
    }

    @GetMapping("/detail")
    @ResponseBody
    public Map detail(int daily_collect_id){
        List<DailyDetail> dailyDetailList = dailyCollectService.findDailyDetailByCollectId(daily_collect_id);
        List<BillRecord> billRecordList = new ArrayList<>();

        if(dailyDetailList.size()==0)
            return Response.Error("错误，该日结ID不存在");

        dailyDetailList.forEach(dailyDetail -> {
            billRecordList.add(billRecordService.findById(dailyDetail.getBill_record_id()));
        });
        return Response.Ok(billRecordList);
    }

    @PostMapping("/collect")
    @ResponseBody
    public Map collect(@RequestBody Map req){
        int uid = Auth.uid(req);
        String start_time = (String)req.get("start_time");
        String end_time = (String)req.get("end_time");

        if(start_time.compareTo(end_time)>=0 || end_time.compareTo(Utils.getSystemTime())>0)
            return Response.Error("错误，开始时间不小于结束时间或结束时间大于当前时间");

        List<BillRecord> billRecordList = billRecordService.findByUserIdAndTime(uid,start_time,end_time);
        if(billRecordList.size()==0)
            return Response.Error("不存在符合条件的票据记录");

        //添加日结记录
        DailyCollect dailyCollect = new DailyCollect();
        dailyCollect.setUser_id(uid);
        dailyCollect.setStart_time(start_time);
        dailyCollect.setEnd_time(end_time);
        int daily_collect_id = dailyCollectService.insertDailyCollect(dailyCollect);

        //添加日结详细信息
        billRecordList.forEach(billRecord -> {
            DailyDetail dailyDetail = new DailyDetail();
            dailyDetail.setBill_record_id(billRecord.getId());
            dailyDetail.setDaily_collect_id(daily_collect_id);
            dailyCollectService.insertDailyDetail(dailyDetail);
        });

        return Response.Ok(dailyCollectService.findDailyCollectById(daily_collect_id));
    }
}
