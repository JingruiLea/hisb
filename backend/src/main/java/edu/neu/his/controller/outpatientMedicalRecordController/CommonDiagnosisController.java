package edu.neu.his.controller.outpatientMedicalRecordController;

import edu.neu.his.config.Response;
import edu.neu.his.service.CommonDiagnosisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("commonDiagnosis")
public class CommonDiagnosisController {
    @Autowired
    private CommonDiagnosisService commonDiagnosisService;

    @GetMapping("/list")
    @ResponseBody
    public Map list(){
        return Response.ok(commonDiagnosisService.selectAll());
    }
}
