package edu.neu.his.bean.exam;

import edu.neu.his.bean.registration.OutpatientRegistrationMapper;
import edu.neu.his.bean.registration.Registration;
import edu.neu.his.config.Response;
import edu.neu.his.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
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
        List res = new ArrayList();
        Registration registration = null;
        switch (type){
            case 0:
                res = outpatientRegistrationMapper.findMedicalRecordLikeName(input);
                break;
            case 1:
                res = outpatientRegistrationMapper.findRegistrationByIdNumber(input);
                break;
            case 2:
                res = outpatientRegistrationMapper.findRegistrationByMedicalCertificateNumber(input);
                break;
            case 3:
                registration = outpatientRegistrationMapper.findRegistrationById(Integer.parseInt(input));
                break;
        }
        if(registration != null){
            res.add(registration);
        }
        return Response.ok(res);
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

    @PostMapping("/allExam")
    public Map allExam(@RequestBody Map req){
        int medicalRecordId = (int) req.get("medical_record_id");
        List res = examService.list(medicalRecordId, Utils.getSystemUser(req));
        return Response.ok(res);
    }
}
