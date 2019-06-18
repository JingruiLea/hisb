package edu.neu.his.bean.prescriptionTemplate;

import edu.neu.his.bean.user.User;
import edu.neu.his.config.Response;
import edu.neu.his.bean.drug.DrugService;
import edu.neu.his.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
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
    private DrugService drugService;

    @PostMapping("/create")
    public Map create(Map req){
        List<Map> drugList = (List)req.get("prescription_item_list");
        String name = (String)req.get("name");
        User user = Utils.getSystemUser(req);
        name = prescriptionTemplateService.rename(name);
        int prescriptionId = prescriptionTemplateService.create(user, name, drugList);
        return Response.ok(prescriptionTemplateService.findAll(user));
    }

    @PostMapping("/delete")
    public Map delete(Map req){
        prescriptionTemplateService.delete((Integer) req.get("prescription_template_id"));
        return Response.ok(prescriptionTemplateService.findAll(Utils.getSystemUser(req)));
    }

    @PostMapping("/addItem")
    public Map addItem(Map req){
        int prescriptionId = (int)req.get("prescription_template_id");
        List<Map> drugList = (List)req.get("drug_list");
        if(!drugService.allItemValid(drugList)){
            return Response.error("该药品不存在!");
        }
        prescriptionTemplateService.addItems(prescriptionId, drugList);
        return Response.ok();
    }

    @PostMapping("/deleteItem")
    public Map deleteItem(Map req){
        int prescriptionId = (int)req.get("prescription_template_id");
        List<Map> drugList = (List)req.get("drug_list");
        if(!drugService.allItemValid(drugList)){
            return Response.error("该药品不存在!");
        }
        prescriptionTemplateService.removeItems(prescriptionId, drugList);
        return Response.ok();
    }

    @PostMapping("/updateItem")
    public Map updateItem(Map req){
        int prescriptionId = (int)req.get("prescription_template_id");
        List<Map> drugList = (List)req.get("drug_list");
        if(!drugService.allItemValid(drugList)){
            return Response.error("该药品不存在!");
        }
        prescriptionTemplateService.updateItems(prescriptionId, drugList);
        return Response.ok();
    }

    @PostMapping("/list")
    public Map list(Map req){
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
