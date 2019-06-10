package edu.neu.his.controller.registeredTollCollectorController;

import edu.neu.his.bean.BillRecord;
import edu.neu.his.bean.DailyDetail;
import edu.neu.his.bean.UserPrincipal;
import edu.neu.his.config.Auth;
import edu.neu.his.config.Response;
import edu.neu.his.service.BillRecordService;
import edu.neu.his.service.DailyCollectService;
import edu.neu.his.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/registrationDailyCollect")
public class RegistrationDailyCollectController {
    @Autowired
    private UserService userService;

    @Autowired
    private DailyCollectService dailyCollectService;

    @Autowired
    private BillRecordService billRecordService;

    @GetMapping("/list")
    @ResponseBody
    public Map list(@RequestBody Map req){
        int uid = Auth.uid(req);
        if(userService.findByUid(uid)!=null)
            return Response.Ok(dailyCollectService.findDailyCollectByUid(uid));
        else
            return Response.Error("错误，该用户ID不存在");
    }

    @GetMapping("/detail")
    @ResponseBody
    public Map detail(@RequestBody Map req){
        int daily_collect_id = (int)req.get("daily_collect_id");
        List<DailyDetail> dailyDetailList = dailyCollectService.findDailyDetailById(daily_collect_id);
        List<BillRecord> billRecordList = new ArrayList<>();

        if(dailyDetailList==null)
            return Response.Error("错误，该日结ID不存在");

        dailyDetailList.forEach(dailyDetail -> {
            billRecordList.add(billRecordService.findById(dailyDetail.getBill_record_id()));
        });
        return Response.Ok(billRecordList);
    }
}
