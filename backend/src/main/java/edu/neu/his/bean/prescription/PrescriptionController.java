package edu.neu.his.bean.prescription;

import edu.neu.his.config.Response;
import edu.neu.his.bean.drug.DrugService;
import edu.neu.his.util.Common;
import edu.neu.his.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        List<Map> drugList = (List)req.get("prescription_item_list");
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

    public Map addItem(Map req){
        int prescriptionId = (int)req.get("prescription_id");
        List<Map> drugList = (List)req.get("drug_list");
        if(!drugService.allItemValid(drugList)){
            return Response.error("该药品不存在!");
        }
        prescriptionService.addItems(prescriptionId, drugList);
        return Response.ok();
    }

    public Map deleteItem(Map req){
        int prescriptionId = (int)req.get("prescription_id");
        List<Map> drugList = (List)req.get("drug_list");
        if(!drugService.allItemValid(drugList)){
            return Response.error("该药品不存在!");
        }
        prescriptionService.removeItems(prescriptionId, drugList);
        return Response.ok();
    }

    @PostMapping("/update")
    public Map updateItem(Map req){
        int prescriptionId = (int)req.get("prescription_id");
        List<Map> drugList = (List)req.get("prescription_item_list");
        if(!drugService.allItemValid(drugList)){
            return Response.error("该药品不存在!");
        }
        prescriptionService.removeAllItems(prescriptionId);
        prescriptionService.addItems(prescriptionId, drugList);
        return Response.ok();
    }

    @PostMapping("/submit")
    public Map submit(Map req){
        int prescriptionId = (int)req.get("prescription_id");
        List<Map> drugList = (List)req.get("prescription_item_list");
        if(!drugService.allItemValid(drugList)){
            return Response.error("该药品不存在!");
        }
        prescriptionService.removeAllItems(prescriptionId);
        prescriptionService.addItems(prescriptionId, drugList);
        prescriptionService.submit(Utils.getSystemUser(req), prescriptionId);
        return Response.ok();
    }

    @PostMapping("/detail")
    public Map detail(Map map){
        int prescriptionId = (int)map.get("prescription_id");
        List res = prescriptionService.detail(prescriptionId);
        return Response.ok(res);
    }

    @RequestMapping("allDrugs")
    public Map allDrugs(@RequestBody Map req){
        int type = (int) req.get("type");
        List res = drugService.selectAllDrug().stream().filter(item->{
            if(type == 0 && item.getDosage_form().equals(Common.ZHONGCHENGYAOTYPE)){
                return true;
            }else if(type == 0 && item.getDosage_form().equals(Common.XIYAOTYPE)){
                return true;
            }else if(type == 1 && item.getDosage_form().equals(Common.ZHONGCAOYAOTYPE)){
                return true;
            }
            return false;
        }).collect(Collectors.toList());
        return Response.ok(res);
    }

    @RequestMapping("allPrescription")
    public Map allPrescription(@RequestBody Map req){
        int type = (int) req.get("type");
        List<Prescription> list = prescriptionService.selectAll();
        List res = list.stream().filter(item-> item.getType() == type).collect(Collectors.toList());
        return Response.ok(res);
    }

    @RequestMapping("delete")
    public Map delete(@RequestBody Map req){
        List<Integer> ids = (List<Integer>) req.get("id");
        for (Integer id : ids) {
            prescriptionService.removeAllItems(id);
            if(prescriptionService.delete(id)!=1){
                return Response.error("列表错误!");
            }
        }
        return Response.ok();
    }
}