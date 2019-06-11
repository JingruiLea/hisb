package edu.neu.his.controller.outpatientMedicalRecordController;

import edu.neu.his.bean.Exam;
import edu.neu.his.util.Common;
import edu.neu.his.util.Utils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/exam")
public class ExamController {

    @PostMapping("/create")
    public Map create(@RequestBody Map req){
        Exam exam = new Exam();
        exam.setCreate_time(Utils.getSystemTime());
        exam.setStatus(Common.ZANCUN);
        exam.setMedical_record_id((int)req.get("medical_record_id"));
        exam.setUser_id((int)req.get("_uid"));
        exam.setType();
    }
}
