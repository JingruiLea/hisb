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

    @Autowired
    private DrugService drugService;

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
        if(!prescriptionService.allCanReturn(ids))
            return Response.error("错误，有ID不存在或药品已经被退");


        ids.forEach(id->{
            PrescriptionItem prescriptionItem = prescriptionService.findPrescriptionItemById((int)id);
            prescriptionService.returnDrug(prescriptionItem);
            if(prescriptionItem.getStatus().equals(PrescriptionStatus.PrescriptionItemTaken)){
                Drug drug = drugService.selectDrugById(prescriptionItem.getDrug_id());
                int stock = drug.getStock() + prescriptionItem.getAmount();
                drug.setStock(stock);
                drugService.updateDrug(drug);
            }
        });

        return Response.ok();
    }
}
