package edu.neu.his.controller.outpatientMedicalRecordController;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.neu.his.bean.MedicalRecord;
import edu.neu.his.config.MedicalRecordStatus;
import edu.neu.his.config.RegistrationConfig;
import edu.neu.his.config.Response;
import edu.neu.his.service.MedicalRecordService;
import edu.neu.his.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
;
import java.io.IOException;
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

    @PostMapping("/createMedicalRecord")
    @ResponseBody
    public Map createMedicalRecord(int medical_record_id){
        if(medicalRecordService.findMedicalRecordById(medical_record_id)!=null)
            return Response.Error("错误，该病历已存在");

        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setId(medical_record_id);
        medicalRecord.setStatus(MedicalRecordStatus.TemporaryStorage);
        medicalRecord = init(medicalRecord);

        medicalRecordService.insertMedicalRecord(medicalRecord);
        return Response.Ok(medicalRecord);
    }

    @PostMapping("/updateMedicalRecord")
    @ResponseBody
    public Map updateMedicalRecord(@RequestBody Map req) throws IOException {
        int medical_record_id = (int)req.get("medical_record_id");
        if(medicalRecordService.findMedicalRecordById(medical_record_id)!=null){
            MedicalRecord medicalRecord = Utils.fromMap(req,MedicalRecord.class);
            medicalRecord.setStatus(MedicalRecordStatus.Committed);
            medicalRecordService.updateMedicalRecord(medicalRecord);
            return Response.Ok();
        }else {
            return Response.Error("错误，该病历不存在");
        }
    }

    private MedicalRecord init(MedicalRecord medicalRecord){
        medicalRecord.setAllergy_history("");
        medicalRecord.setChief_complaint("");
        medicalRecord.setChinese_initial_diagnosis("");
        medicalRecord.setCurrent_medical_history("");
        medicalRecord.setCurrent_treatment_situation("");
        medicalRecord.setEnd_diagnosis("");
        medicalRecord.setPast_history("");
        medicalRecord.setWestern_initial_diagnosis("");
        medicalRecord.setPhysical_examination("");
        medicalRecord.setCreate_time(Utils.getSystemTime());
        return medicalRecord;
    }
}
