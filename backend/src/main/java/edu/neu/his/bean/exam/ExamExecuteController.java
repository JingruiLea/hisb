package edu.neu.his.bean.exam;

import edu.neu.his.bean.registration.OutpatientRegistrationMapper;
import edu.neu.his.config.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/examExcute")
public class ExamExecuteController {

    @Autowired
    ExamItemService examItemService;

    @Autowired
    ExamService examService;


    @Autowired
    OutpatientRegistrationMapper outpatientRegistrationMapper;

    @RequestMapping("/searchRegistration")
    public Map searchRegistration(@RequestBody Map req){
        int type = (int) req.get("type");
        String input = (String) req.get("input");
        switch (type){
            case 0:
                outpatientRegistrationMapper.findMedicalRecordLikeName(input);
                break;
            case 1:
                outpatientRegistrationMapper.findRegistrationByIdNumber(input);
                break;
            case 2:
                outpatientRegistrationMapper.findRegistrationByMedicalCertificateNumber(input);
                break;
            case 3:
                break;
        }
        return null;
    }

    @PostMapping("register")
    public Map register(@RequestBody Map req){
        List<Integer> examItemIds = (List<Integer>) req.get("exam_item_id");
        if(examItemService.register(examItemIds)){
            return Response.ok();
        }else {
            return Response.error("列表错误!");
        }
    }
}
