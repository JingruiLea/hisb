package edu.neu.his.controller;

import edu.neu.his.config.Auth;
import edu.neu.his.config.MedicalRecordStatus;
import edu.neu.his.config.Response;
import edu.neu.his.service.MedicalRecordTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("outpatientDoctor/medicalRecordTemplate")
public class MedicalRecordTemplateController {
    @Autowired
    private MedicalRecordTemplateService medicalRecordTemplateService;

    @GetMapping("/list")
    @ResponseBody
    public Map list(@RequestBody Map req){
        int type = (int)req.get("type");
        if(type == MedicalRecordStatus.SelectByUserId){
            int uid = Auth.uid(req);
            return Response.ok(medicalRecordTemplateService.SelectByUserId(uid));
        }else if(type == MedicalRecordStatus.SelectByDepartmentId){
            int department_id = (int)req.get("department_id");
            return Response.ok(medicalRecordTemplateService.SelectByDepartmentId(department_id));
        }else
            return Response.ok(medicalRecordTemplateService.SelectAll());
    }

}
