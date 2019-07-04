
package edu.neu.his.bean.diagnosis;

import edu.neu.his.bean.disease.Disease;
import edu.neu.his.bean.disease.DiseaseMapper;
import edu.neu.his.config.Response;
import edu.neu.his.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//3.5 诊断目录管理
@RestController
@RequestMapping("/diagnose")
public class DiagnoseController {
    @Autowired
    DiseaseMapper diseaseMapper;

    @Autowired MedicalRecordDiagnoseService medicalRecordDiagnoseService;

    @RequestMapping("/submitEnd")
    public Map submitEnd(@RequestBody Map req){
        int medical_record_id = (int) req.get("medical_record_id");
        Integer diagnose_id;
        MedicalRecordDiagnose medicalRecordDiagnose = medicalRecordDiagnoseService.findDiagnoseByMedicalRecordId(medical_record_id, true);
        if(medicalRecordDiagnose == null){
            medicalRecordDiagnose = new MedicalRecordDiagnose();
            medicalRecordDiagnose.setMedical_record_id(medical_record_id);
            medicalRecordDiagnose.setIs_end(true);
            diagnose_id = medicalRecordDiagnoseService.insertDiagnose(medicalRecordDiagnose);
        }else{
            diagnose_id = medicalRecordDiagnose.getId();
            medicalRecordDiagnoseService.deleteAllByDiagnoseId(diagnose_id);
        }
        //删除现有诊断子目
        Map diagnose = (Map)req.get("diagnose");
        List westernList = (List)diagnose.get("western_diagnose");
        List chineseList = (List)diagnose.get("chinese_diagnose");

        //更新诊断子目
        westernList.forEach(object->{
            Map itemMap = (Map)object;
            Utils.initMap(itemMap);
            MedicalRecordDiagnoseItem medicalRecordDiagnoseItem = Utils.fromMap(itemMap,MedicalRecordDiagnoseItem.class);
            medicalRecordDiagnoseItem.setMedical_record_diagnose_id(diagnose_id);
            medicalRecordDiagnoseItem.setDiagnose_type(DiagnoseItemType.Western);
            medicalRecordDiagnoseService.insertDiagnoseItem(medicalRecordDiagnoseItem);
        });
        chineseList.forEach(object->{
            Map itemMap = (Map)object;
            Utils.initMap(itemMap);
            MedicalRecordDiagnoseItem medicalRecordDiagnoseItem = Utils.fromMap(itemMap,MedicalRecordDiagnoseItem.class);
            medicalRecordDiagnoseItem.setMedical_record_diagnose_id(diagnose_id);
            medicalRecordDiagnoseItem.setDiagnose_type(DiagnoseItemType.Chinese);
            medicalRecordDiagnoseService.insertDiagnoseItem(medicalRecordDiagnoseItem);
        });
        return Response.ok();
    }
}