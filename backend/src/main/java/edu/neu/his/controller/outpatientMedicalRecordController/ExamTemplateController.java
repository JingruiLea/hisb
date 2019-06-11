package edu.neu.his.controller.outpatientMedicalRecordController;

import edu.neu.his.bean.ExamTemplate;
import edu.neu.his.bean.User;
import edu.neu.his.config.Response;
import edu.neu.his.service.ExamTemplateService;
import edu.neu.his.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/examTemplate")
public class ExamTemplateController {

    @Autowired
    ExamTemplateService examTemplateService;

    @GetMapping("/list")
    public Map list(int type){
        List<ExamTemplate> res = examTemplateService.findAll()
                .stream().filter(item -> item.getType() == type)
                .collect(Collectors.toList());
        return Response.ok(res);
    }

    @GetMapping("/create")
    public Map create(Map req){
        User user = Utils.getSystemUser(req);
        String templateName = (String)req.get("template_name");
        if(examTemplateService.selectByName(templateName) != null){
            templateName += "(1)";
        }
        List<Integer> nonDrugIdList = (List<Integer>) req.get("non_drug_id_list");
        ExamTemplate examTemplate = Utils.fromMap(req, ExamTemplate.class);
        examTemplate.setDepartment_id(user.getDepartment_id());
        examTemplate.setTemplate_name(templateName);
        examTemplate.setCreate_time(Utils.getSystemTime());
        Integer examTemplateId = examTemplateService.insert(examTemplate);

        return Response.ok(examTemplateService.findAll());
    }

}
