package edu.neu.his.bean.prescription;

import edu.neu.his.config.Response;
import edu.neu.his.bean.drug.DrugService;
import edu.neu.his.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/prescription")
public class PrescriptionController {

    @Autowired
    PrescriptionService prescriptionService;

    @Autowired
    DrugService drugService;

    @PostMapping("/create")
    public Map create(Map req){
        int medicalRecordId = (int)req.get("medical_record_id");
        List<Map> drugList = (List)req.get("drug_list");
        int type = (int)req.get("type");
        if(!prescriptionService.recordMedicalHasSubmit(medicalRecordId)){
            return Response.error("找不到已经提交的病历!");
        }
        if(!drugService.allItemValid(drugList)){
            return Response.error("该药品不存在!");
        }
        int prescriptionId = prescriptionService.create(Utils.getSystemUser(req).getUid(), type, medicalRecordId, drugList);
        return Response.ok(prescriptionService.findById(prescriptionId));
    }

    @PostMapping("/addItem")
    public Map addItem(Map req){
        int prescriptionId = (int)req.get("prescription_id");
        List<Map> drugList = (List)req.get("drug_list");
        if(!drugService.allItemValid(drugList)){
            return Response.error("该药品不存在!");
        }
        prescriptionService.addItems(prescriptionId, drugList);
        return Response.ok();
    }

    @PostMapping("/deleteItem")
    public Map deleteItem(Map req){
        int prescriptionId = (int)req.get("prescription_id");
        List<Map> drugList = (List)req.get("drug_list");
        if(!drugService.allItemValid(drugList)){
            return Response.error("该药品不存在!");
        }
        prescriptionService.removeItems(prescriptionId, drugList);
        return Response.ok();
    }

    @PostMapping("/updateItem")
    public Map updateItem(Map req){
        int prescriptionId = (int)req.get("prescription_id");
        List<Map> drugList = (List)req.get("drug_list");
        if(!drugService.allItemValid(drugList)){
            return Response.error("该药品不存在!");
        }
        prescriptionService.updateItems(prescriptionId, drugList);
        return Response.ok();
    }

    @PostMapping("/submit")
    public Map submit(Map map){
        int prescriptionId = (int)map.get("prescription_id");
        prescriptionService.submit(Utils.getSystemUser(map), prescriptionId);
        return Response.ok();
    }

    @PostMapping("/detail")
    public Map detail(Map map){
        int prescriptionId = (int)map.get("prescription_id");
        List res = prescriptionService.detail(prescriptionId);
        return Response.ok(res);
    }
}