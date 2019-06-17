package edu.neu.his.bean.drug;

import edu.neu.his.bean.prescription.PrescriptionItem;
import edu.neu.his.bean.outpatientCharges.OutpatientChargesRecordStatus;
import edu.neu.his.bean.prescription.PrescriptionStatus;
import edu.neu.his.config.Response;
import edu.neu.his.bean.prescription.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/drugWithdrawal")
public class DrugWithdrawController {
    @Autowired
    private PrescriptionService prescriptionService;

    @PostMapping("/list")
    @ResponseBody
    public Map list(int medical_record_id){
        List<Map> toTakeList = prescriptionService.getList(medical_record_id,
                PrescriptionStatus.PrescriptionItemToTake, OutpatientChargesRecordStatus.Charged);

        List<Map> takenList = prescriptionService.getList(medical_record_id,
                PrescriptionStatus.PrescriptionItemToTake, OutpatientChargesRecordStatus.Charged);

        toTakeList.addAll(takenList);

        return Response.ok(toTakeList);
    }

    @PostMapping("/submit")
    @ResponseBody
    public Map submit(@RequestBody Map req){
        List ids = (List)req.get("prescription_item_id");
        if(!prescriptionService.allExist(ids))
            return Response.error("错误，有ID不存在");

        ids.forEach(id->{
            PrescriptionItem prescriptionItem = prescriptionService.findPrescriptionItemById((int)id);
            if(prescriptionItem.getStatus().equals(PrescriptionStatus.PrescriptionItemToTake))
                prescriptionService.returnDrug(prescriptionItem);
        });

        return Response.ok();
    }
}
