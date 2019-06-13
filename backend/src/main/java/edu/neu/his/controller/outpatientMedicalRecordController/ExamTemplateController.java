package edu.neu.his.controller.outpatientMedicalRecordController;

import edu.neu.his.bean.ExamTemplate;
import edu.neu.his.bean.ExamTemplateItem;
import edu.neu.his.bean.User;
import edu.neu.his.config.Response;
import edu.neu.his.service.ExamTemplateItemService;
import edu.neu.his.service.ExamTemplateService;
import edu.neu.his.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/examTemplate")
public class ExamTemplateController {

    @Autowired
    ExamTemplateService examTemplateService;

    @Autowired
    ExamTemplateItemService examTemplateItemService;

    @PostMapping("/list")
    public Map list(@RequestBody Map req){
        int type = (int)req.get("type");
        List<ExamTemplate> res = examTemplateService.findAll(Utils.getSystemUser(req))
                .stream().filter(item -> item.getType() == type)
                .collect(Collectors.toList());
        return Response.ok(res);
    }

    @PostMapping("/create")
    public Map create(@RequestBody Map req){
        User user = Utils.getSystemUser(req);
        String templateName = (String)req.get("template_name");
        while(examTemplateService.selectByName(templateName) != null){
            templateName += "(1)";
        }
        List<Integer> nonDrugIdList = (List<Integer>) req.get("non_drug_id_list");
        ExamTemplate examTemplate = Utils.fromMap(req, ExamTemplate.class);
        examTemplate.setDepartment_id(user.getDepartment_id());
        examTemplate.setTemplate_name(templateName);
        examTemplate.setCreate_time(Utils.getSystemTime());
        Integer examTemplateId = examTemplateService.insert(examTemplate);
        nonDrugIdList.forEach(nonDrugId ->{
            ExamTemplateItem examTemplateItem = new ExamTemplateItem();
            examTemplateItem.setExam_template_id(examTemplateId);
            examTemplateItem.setNon_drug_item_id(nonDrugId);
            examTemplateItemService.insert(examTemplateItem);
        });
        return Response.ok(examTemplateService.findAll(Utils.getSystemUser(req)));
    }

    @PostMapping("/detail")
    public Map detail(int examTemplateId){
        return Response.ok(examTemplateItemService.detail(examTemplateId));
    }

    @PostMapping("delete")
    public Map delete(Map req){
        List<Integer> ids = (List<Integer>) req.get("id");
        ids.forEach(id->{
            if(examTemplateService.selectById(id) == null){
                return;
            }
            examTemplateService.deleteByTemplateId(id);
        });
        return Response.ok(examTemplateService.findAll(Utils.getSystemUser(req)));
    }

    @PostMapping("/addItem")
    public Map addOne(@RequestBody Map map){
        int examId = (int)map.get("exam_template_id ");
        ExamTemplate exam = examTemplateService.selectById(examId);
        if(exam == null){
            return Response.error("找不到该检查/检验/处置组套!");
        }
        List<Integer> nonDrugIds = (List<Integer>) map.get("non_drug_id");
        nonDrugIds.forEach(id->{
            ExamTemplateItem examItem = new ExamTemplateItem();
            examItem.setNon_drug_item_id(id);
            examItem.setExam_template_id(examId);
            examTemplateItemService.insert(examItem);
        });
        Map<String, Object> res = new HashMap();
        res.put("non_drug_item_id", examTemplateService.getNonDrugItemIdListById(exam.getId()));
        return Response.ok(res);
    }


    @PostMapping("/delItem")
    public Map delOne(@RequestBody Map map) {
        int examId = (int)map.get("exam_id");
        ExamTemplate exam = examTemplateService.selectById(examId);
        if(exam == null){
            return Response.error("找不到该检查/检验/处置单!");
        }
        List<Integer> nonDrugIds = (List<Integer>) map.get("non_drug_id");
        nonDrugIds.forEach(nonDrugId->{
            ExamTemplateItem examItem = examTemplateItemService.selectByDetail(nonDrugId, examId);
            examTemplateItemService.deleteById(examItem.getId());
        });
        Map<String, Object> res = new HashMap();
        res.put("non_drug_item_id", examTemplateService.getNonDrugItemIdListById(exam.getId()));
        return Response.ok(res);
    }
}
