package edu.neu.his.bean.prescriptionTemplate;

import edu.neu.his.auto.PrescriptionTemplateMapper;
import edu.neu.his.bean.user.User;
import edu.neu.his.config.Response;
import edu.neu.his.bean.drug.DrugService;
import edu.neu.his.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/prescriptionTemplate")
public class PrescriptionTemplateController {

    @Autowired
    PrescriptionTemplateService prescriptionTemplateService;

    @Autowired
    PrescriptionTemplateMapper prescriptionTemplateMapper;

    @Autowired
    private DrugService drugService;

    @PostMapping("/create")
    public Map create(Map req){
        List<Map> drugList = (List)req.get("prescription_item_list");
        String name = (String)req.get("template_name");
        User user = Utils.getSystemUser(req);
        name = prescriptionTemplateService.rename(name);
        prescriptionTemplateService.create(user, name, drugList);
        return Response.ok(prescriptionTemplateService.findAll(user));
    }

    @PostMapping("/delete")
    public Map delete(Map req){
        prescriptionTemplateService.delete((Integer) req.get("prescription_template_id"));
        return Response.ok(prescriptionTemplateService.findAll(Utils.getSystemUser(req)));
    }

    public Map addItem(Map req){
        int prescriptionId = (int)req.get("prescription_template_id");
        List<Map> drugList = (List)req.get("prescription_item_list");
        if(!drugService.allItemValid(drugList)){
            return Response.error("该药品不存在!");
        }
        prescriptionTemplateService.addItems(prescriptionId, drugList);
        return Response.ok();
    }

    public Map deleteItem(Map req){
        int prescriptionId = (int)req.get("prescription_template_id");
        List<Map> drugList = (List)req.get("prescription_item_list");
        if(!drugService.allItemValid(drugList)){
            return Response.error("该药品不存在!");
        }
        prescriptionTemplateService.removeItems(prescriptionId, drugList);
        return Response.ok();
    }

    public Map updateItem(Map req){
        int prescriptionId = (int)req.get("prescription_template_id");
        List<Map> drugList = (List)req.get("prescription_item_list");
        if(!drugService.allItemValid(drugList)){
            return Response.error("该药品不存在!");
        }
        prescriptionTemplateService.updateItems(prescriptionId, drugList);
        return Response.ok();
    }

    @PostMapping("/update")
    public Map update(Map req){
        int prescriptionId = (int)req.get("id");
        String name = (String)req.get("template_name");
        PrescriptionTemplate prescriptionTemplate = Utils.fromMap(req, PrescriptionTemplate.class);
        PrescriptionTemplate originPrescriptionTemplate = prescriptionTemplateService.selectById(prescriptionId);
        if(originPrescriptionTemplate == null){
            return Response.error("该组套不存在!");
        }
        prescriptionTemplate.setId(originPrescriptionTemplate.getId());
        if(!name.equals(originPrescriptionTemplate.getTemplate_name())){
            name = prescriptionTemplateService.rename(name);
            prescriptionTemplate.setTemplate_name(name);
        }

        prescriptionTemplateMapper.updateByPrimaryKey(prescriptionTemplate);
        List<Map> drugList = (List)req.get("prescription_item_list");
        if(!drugService.allItemValid(drugList)){
            return Response.error("该药品不存在!");
        }
        prescriptionTemplateService.removeAllItems(prescriptionId);
        prescriptionTemplateService.addItems(prescriptionId, drugList);
        return Response.ok();
    }

    @PostMapping("/list")
    public Map list(@RequestBody Map req){
        List<PrescriptionTemplate> list = prescriptionTemplateService.findAll(Utils.getSystemUser(req));
        List personal = list.stream().filter(item->item.getType()==0).collect(Collectors.toList());
        List department = list.stream().filter(item->item.getType()==0).collect(Collectors.toList());
        List hospital = list.stream().filter(item->item.getType()==0).collect(Collectors.toList());
        Map res = new HashMap();
        res.put("personal",personal);
        res.put("department", department);
        res.put("hospital", hospital);
        return Response.ok(res);
    }

}
