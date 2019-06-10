package edu.neu.his.controller;

import edu.neu.his.bean.Registration;
import edu.neu.his.config.RegistrationConfig;
import edu.neu.his.config.Response;
import edu.neu.his.service.MedicalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/outpatientDoctor")
public class MedicalRecordController {
    @Autowired
    private MedicalRecordService medicalRecordService;

    @GetMapping("/registrationInfo")
    @ResponseBody
    public Map registrationInfo(@RequestBody Map req){
        String type = (String)req.get("type");
        String medical_certificate_number = (String)req.get("medical_certificate_number");
        return Response.Ok(medicalRecordService.find(type,medical_certificate_number, RegistrationConfig.registrationAvailable));
    }

    @GetMapping("/recordHistory")
    @ResponseBody
    public Map recordHistory(@RequestBody Map req){
        String type = (String)req.get("type");
        String medical_certificate_number = (String)req.get("medical_certificate_number");
        return Response.Ok(medicalRecordService.findHistory(type,medical_certificate_number));
    }

}
