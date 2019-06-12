package edu.neu.his.controller.drugController;

import edu.neu.his.bean.Prescription;
import edu.neu.his.bean.PrescriptionItem;
import edu.neu.his.config.OutpatientChargesRecordStatus;
import edu.neu.his.config.PrescriptionStatus;
import edu.neu.his.config.Response;
import edu.neu.his.service.PrescriptionService;
import edu.neu.his.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/drugDispense")
public class DrugDispenseController {
    @Autowired
    private PrescriptionService prescriptionService;

    @GetMapping("/list")
    @ResponseBody
    public Map list(int medical_record_id){
        List<Map> result = new ArrayList<>();
        List<Prescription> prescriptionList = prescriptionService.findByMedicalRecordId(medical_record_id);
        prescriptionList.forEach(prescription -> {
            int prescription_id = prescription.getId();
            List<PrescriptionItem> list = prescriptionService.findByPrescriptionAndStatus(prescription_id,
                    PrescriptionStatus.PrescriptionItemToTake, OutpatientChargesRecordStatus.Charged);
            Map prescriptionMap = Utils.objectToMap(prescription);
            prescriptionMap.put("drug_item",list);
            result.add(prescriptionMap);
        });

        return Response.ok(result);
    }
}
