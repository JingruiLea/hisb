package edu.neu.his.bean.dignosis;

import edu.neu.his.config.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("commonDiagnosis")
public class DiagnosisTemplateController {
    @Autowired
    private DiagnosisTemplateService diagnosisTemplateService;

    @GetMapping("/list")
    @ResponseBody
    public Map list(){
        return Response.ok(diagnosisTemplateService.selectAll());
    }
}
