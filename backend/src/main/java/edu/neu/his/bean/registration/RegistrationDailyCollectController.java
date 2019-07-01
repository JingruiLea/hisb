package edu.neu.his.bean.registration;

import edu.neu.his.bean.billRecord.BillRecord;
import edu.neu.his.bean.daily.DailyCollect;
import edu.neu.his.bean.daily.DailyDetail;
import edu.neu.his.config.Auth;
import edu.neu.his.config.Response;
import edu.neu.his.bean.billRecord.BillRecordService;
import edu.neu.his.bean.daily.DailyCollectService;
import edu.neu.his.bean.user.UserService;
import edu.neu.his.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 实现收费员日结的相关功能
 */
@RestController
@RequestMapping("/dailyCollect")
public class RegistrationDailyCollectController {
    @Autowired
    private UserService userService;

    @Autowired
    private DailyCollectService dailyCollectService;

    @Autowired
    private BillRecordService billRecordService;

    /**
     * 获得日结历史列表
     * @param req 前端传递的request，包含隐式用户id
     * @return 查找到的日结历史列表
     */
    @RequestMapping("/list")
    @ResponseBody
    public Map list(@RequestBody Map req){
        int _uid = Auth.uid(req);
        if(userService.findByUid(_uid)!=null)
            return Response.ok(dailyCollectService.findDailyCollectByUid(_uid));
        else
            return Response.error("错误，该用户ID不存在");
    }

    /**
     * 根据名称查找日结详细信息
     * @param req 前端传递的request，包含日结id
     * @return 日结的票据记录列表
     */
    @RequestMapping("/detail")
    @ResponseBody
    public Map detail(@RequestBody Map req){
        int daily_collect_id = (int) req.get("daily_collect_id");
        List<DailyDetail> dailyDetailList = dailyCollectService.findDailyDetailByCollectId(daily_collect_id);
        List<BillRecord> billRecordList = new ArrayList<>();

        if(dailyDetailList.size()==0)
            return Response.error("错误，该日结ID不存在");

        dailyDetailList.forEach(dailyDetail -> {
            billRecordList.add(billRecordService.findById(dailyDetail.getBill_record_id()));
        });
        return Response.ok(billRecordList);
    }

    /**
     * 根据起止时间日结
     * @param req 前端传递的request，包含起止时间
     * @return 日结记录
     */
    @PostMapping("/collect")
    @ResponseBody
    public Map collect(@RequestBody Map req){
        int uid = Auth.uid(req);
        String start_time = (String)req.get("start_time");
        String end_time = (String)req.get("end_time");

        if(start_time.compareTo(end_time)>=0 || end_time.compareTo(Utils.getSystemTime())>0)
            return Response.error("错误，开始时间不小于结束时间或结束时间大于当前时间");

        List<BillRecord> billRecordList = billRecordService.findByUserIdAndTime(uid,start_time,end_time);
        if(billRecordList.size()==0)
            return Response.error("不存在符合条件的票据记录");

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

        return Response.ok(dailyCollectService.findDailyCollectById(daily_collect_id));
    }
}
