
package edu.neu.his.controller.hospitalAdminController;

import edu.neu.his.bean.Disease;
import edu.neu.his.config.Response;
import edu.neu.his.service.DiagnoseDirectoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//3.5 诊断目录管理
@RestController
@RequestMapping("/diagnoseDirectoryManagement")
public class DiagnoseDirectoryController {
    @Autowired
    private DiagnoseDirectoryService diagnoseDirectoryService;

    @GetMapping("/allClassification")
    @ResponseBody
    public Map allDiseaseClassification(){
        return Response.Ok(diagnoseDirectoryService.findAllDiseaseClassification());
    }

    @PostMapping("/searchAllByClassificationId")
    @ResponseBody
    public Map listAllDisease(@RequestBody Map req){
        int classification_id = (int)req.get("classification_id");
        Map data = new HashMap();
        data.put("diseases",diagnoseDirectoryService.findAll(classification_id));
        return Response.Ok(data);
    }

    @PostMapping("/findByName")
    @ResponseBody
    public Map DiseaseFindByName(@RequestBody Map req){
        String name = (String)req.get("name");
        return Response.Ok(diagnoseDirectoryService.findDiseaseByName(name));
    }

    @PostMapping("/update")
    @ResponseBody
    public Map updateNonDrugCharge(@RequestBody Map req){
        Disease disease = req2Disease(req);
        String rawId = (String)req.get("raw_id");
        if(!disease.getCode().equals(rawId) && checkIdExist(disease.getCode())) {
            return Response.Error("错误，编号重复。");
        } else {
            diagnoseDirectoryService.updateDisease(rawId,disease);
            return Response.Ok();
        }
    }

    @PostMapping("/add")
    @ResponseBody
    public Map insertDisease(@RequestBody Disease disease){
        //Disease disease = req2Disease(req);
        if(checkIdExist(disease.getCode())) {
            return Response.Error("错误，编号重复。");
        } else {
            diagnoseDirectoryService.insertDisease(disease);
            return Response.Ok();
        }
    }

    @PostMapping("/delete")
    @ResponseBody
    public Map  deleteDisease(@RequestBody Map req){
        List<String > disease_ids = (List<String>) req.get("data");
        disease_ids.forEach(id->diagnoseDirectoryService.deleteDisease(id));
        return Response.Ok();
    }

    private boolean checkIdExist(String id) {
        //检测ID存在
        return diagnoseDirectoryService.checkDiseaseExist(id);
    }

    private Disease req2Disease(Map req) {
        int id = (int) req.get("id");
        String name = (String)req.get("name");
        int classification_id = (int)req.get("classification_id");
        String pinyin = (String)req.get("pinyin");
        String code = (String)req.get("code");
        String custom_name = "";
        String custom_pinyin = "";
        if(req.containsKey("custom_name")) custom_name = (String)req.get("custom_name");
        if(req.containsKey("custom_pinyin")) custom_pinyin = (String)req.get("custom_pinyin");
        Disease disease = null;
        disease.setClassification_id(classification_id);
        disease.setId(id);
        disease.setCustom_name(custom_name);
        disease.setCustom_pinyin(custom_pinyin);
        disease.setPinyin(pinyin);
        disease.setCode(code);
        disease.setName(name);

        return disease;
    }
}