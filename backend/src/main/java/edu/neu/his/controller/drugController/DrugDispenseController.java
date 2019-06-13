package edu.neu.his.controller.drugController;

import edu.neu.his.config.OutpatientChargesRecordStatus;
import edu.neu.his.config.PrescriptionStatus;
import edu.neu.his.config.Response;
import edu.neu.his.service.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/drugDispense")
public class DrugDispenseController {
    @Autowired
    private PrescriptionService prescriptionService;

    @PostMapping("/list")
    @ResponseBody
    public Map list(int medical_record_id){
        List<Map> result = prescriptionService.getList(medical_record_id,
                PrescriptionStatus.PrescriptionItemToTake, OutpatientChargesRecordStatus.Charged);

        return Response.ok(result);
    }
}
