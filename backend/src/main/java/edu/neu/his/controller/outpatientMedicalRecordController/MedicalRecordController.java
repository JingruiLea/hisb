package edu.neu.his.controller.outpatientMedicalRecordController;

import edu.neu.his.bean.MedicalRecord;
import edu.neu.his.bean.Registration;
import edu.neu.his.config.Auth;
import edu.neu.his.config.MedicalRecordStatus;
import edu.neu.his.config.RegistrationConfig;
import edu.neu.his.config.Response;
import edu.neu.his.service.MedicalRecordService;
import edu.neu.his.service.OutpatientRegistrationService;
import edu.neu.his.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/medicalRecord")
public class MedicalRecordController {
    @Autowired
    private MedicalRecordService medicalRecordService;

    @Autowired
    private OutpatientRegistrationService outpatientRegistrationService;

    @PostMapping("/getPatientList")
    @ResponseBody
    public Map getPatientList(@RequestBody Map req){
        Map data = new HashMap();
        int uid = Auth.uid(req);
        List<Registration> list = outpatientRegistrationService.findByDoctor(uid);
        List<Registration> waitList = new ArrayList<>();
        List<Registration> pendList = new ArrayList<>();
        list.forEach(registration -> {
            int medical_record_id = registration.getMedical_record_id();
            MedicalRecord medicalRecord = medicalRecordService.findMedicalRecordById(medical_record_id);
            if(medicalRecord==null)
                waitList.add(registration);
            else if(medicalRecord.getStatus().equals(MedicalRecordStatus.TemporaryStorage))
                pendList.add(registration);
        });

        data.put("waiting",waitList);
        data.put("pending",pendList);
        return Response.ok(data);
    }

    @PostMapping("/registrationInfo")
    @ResponseBody
    public Map registrationInfo(@RequestBody Map req){
        String type = (String)req.get("type");
        String medical_certificate_number = (String)req.get("medical_certificate_number");
        return Response.ok(medicalRecordService.find(type,medical_certificate_number, RegistrationConfig.registrationAvailable));
    }

    @PostMapping("/recordHistory")
    @ResponseBody
    public Map recordHistory(@RequestBody Map req){
        String type = (String)req.get("type");
        String medical_certificate_number = (String)req.get("medical_certificate_number");
        return Response.ok(medicalRecordService.findHistory(type,medical_certificate_number));
    }

    @PostMapping("/getMedicalRecord")
    @ResponseBody
    public Map createMedicalRecord(int medical_record_id){
        if(!medicalRecordService.canOperateMedicalRecord(medical_record_id))
            return Response.error("错误，挂号不存在或该挂号已完成/已取消");

        MedicalRecord medicalRecord = medicalRecordService.findMedicalRecordById(medical_record_id);
        if(medicalRecord!=null)
            return Response.ok(medicalRecord);

        medicalRecord = new MedicalRecord();
        medicalRecord.setId(medical_record_id);
        medicalRecord.setStatus(MedicalRecordStatus.TemporaryStorage);
        medicalRecord = init(medicalRecord);

        medicalRecordService.insertMedicalRecord(medicalRecord);
        return Response.ok(medicalRecord);
    }

    @PostMapping("/updateMedicalRecord")
    @ResponseBody
    public Map updateMedicalRecord(@RequestBody Map req){
        int medical_record_id = (int)req.get("medical_record_id");
        if(medicalRecordService.findMedicalRecordById(medical_record_id)!=null){
            MedicalRecord medicalRecord = Utils.fromMap(req,MedicalRecord.class);
            medicalRecord.setId(medical_record_id);
            medicalRecord.setStatus(MedicalRecordStatus.Committed);
            medicalRecordService.updateMedicalRecord(medicalRecord);
            return Response.ok();
        }else {
            return Response.error("错误，该病历不存在");
        }
    }

    private MedicalRecord init(MedicalRecord medicalRecord){
        medicalRecord.setAllergy_history("");
        medicalRecord.setChief_complaint("");
        medicalRecord.setCurrent_medical_history("");
        medicalRecord.setCurrent_treatment_situation("");
        medicalRecord.setPast_history("");
        medicalRecord.setPhysical_examination("");
        medicalRecord.setCreate_time(Utils.getSystemTime());
        return medicalRecord;
    }
}
