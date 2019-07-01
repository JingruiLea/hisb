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

/**
 * 实现处方组套管理的相关功能
 */
@RestController
@RequestMapping("/prescriptionTemplate")
public class PrescriptionTemplateController {

    @Autowired
    PrescriptionTemplateService prescriptionTemplateService;

    @Autowired
    PrescriptionTemplateMapper prescriptionTemplateMapper;

    @Autowired
    private DrugService drugService;

    /**
     * 创建处方组套
     * @param req 前端传递的request，包含组套名称和处方组套列表
     * @return 处方组套列表
     */
    @PostMapping("/create")
    public Map create(@RequestBody Map req){
        List<Map> drugList = (List)req.get("prescription_item_list");
        String name = (String)req.get("template_name");
        User user = Utils.getSystemUser(req);
        name = prescriptionTemplateService.rename(name);
        prescriptionTemplateService.create(req ,user, name, drugList);
        return Response.ok(prescriptionTemplateService.findAll(user));
    }

    /**
     * 删除处方组套
     * @param req 前端传递的request，包含处方id列表
     * @return 处方组套列表
     */
    @PostMapping("/delete")
    public Map delete(@RequestBody Map req){
        List<Integer> prescriptionIds = (List<Integer>) req.get("id");
        for (Integer prescriptionId : prescriptionIds) {
            PrescriptionTemplate prescriptionTemplate = prescriptionTemplateService.selectById(prescriptionId);
            if(prescriptionTemplate == null){
                return Response.error("该组套不存在!");
            }
            prescriptionTemplateService.delete(prescriptionId);
        }
        return Response.ok(prescriptionTemplateService.findAll(Utils.getSystemUser(req)));
    }

    public Map addItem(@RequestBody Map req){
        int prescriptionId = (int)req.get("prescription_template_id");
        List<Map> drugList = (List)req.get("prescription_item_list");
        if(!drugService.allItemValid(drugList)){
            return Response.error("该药品不存在!");
        }
        prescriptionTemplateService.addItems(prescriptionId, drugList);
        return Response.ok();
    }

    public Map deleteItem(@RequestBody Map req){
        int prescriptionId = (int)req.get("prescription_template_id");
        List<Map> drugList = (List)req.get("prescription_item_list");
        if(!drugService.allItemValid(drugList)){
            return Response.error("该药品不存在!");
        }
        prescriptionTemplateService.removeItems(prescriptionId, drugList);
        return Response.ok();
    }

    public Map updateItem(@RequestBody Map req){
        int prescriptionId = (int)req.get("prescription_template_id");
        List<Map> drugList = (List)req.get("prescription_item_list");
        if(!drugService.allItemValid(drugList)){
            return Response.error("该药品不存在!");
        }
        prescriptionTemplateService.updateItems(prescriptionId, drugList);
        return Response.ok();
    }

    /**
     * 更新处方组套
     * @param req 前端传递的request，包含处方id、处方详情列表和组套名称
     * @return 状态码
     */
    @PostMapping("/update")
    public Map update(@RequestBody Map req){
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
            originPrescriptionTemplate.setTemplate_name(name);
        }
        originPrescriptionTemplate.setDisplay_type(prescriptionTemplate.getDisplay_type());
        originPrescriptionTemplate.setType(prescriptionTemplate.getType());
        prescriptionTemplateMapper.updateByPrimaryKey(originPrescriptionTemplate);
        List<Map> drugList = (List)req.get("prescription_item_list");
        if(!drugService.allItemValid(drugList)){
            return Response.error("该药品不存在!");
        }
        prescriptionTemplateService.removeAllItems(prescriptionId);
        prescriptionTemplateService.addItems(prescriptionId, drugList);
        return Response.ok();
    }

    /**
     * 获得所有处方状态的列表并按照类型分类
     * @return 返回查找到的按照类型分类的所有处方状态的列表
     */
    @PostMapping("/list")
    public Map list(@RequestBody Map req){
        int type = (int) req.get("type");
        List<PrescriptionTemplate> list = prescriptionTemplateService.findAll(Utils.getSystemUser(req));
        List personal = list.stream().filter(item->item.getType()==type && item.getDisplay_type() == 0).collect(Collectors.toList());
        List department = list.stream().filter(item->item.getType()==type && item.getDisplay_type() == 1).collect(Collectors.toList());
        List hospital = list.stream().filter(item->item.getType()==type && item.getDisplay_type() == 2).collect(Collectors.toList());
        Map res = new HashMap();
        res.put("personal",personal);
        res.put("department", department);
        res.put("hospital", hospital);
        return Response.ok(res);
    }

    /**
     * 根据模版id获得组套详情
     * @param req 前端传递的request，包含组套id
     * @return 根据组套id获得组套详情
     */
    @PostMapping("/detail")
    public Map detail(@RequestBody Map req){
        int templateId = (int) req.get("id");
        PrescriptionTemplate originPrescriptionTemplate = prescriptionTemplateService.selectById(templateId);
        if(originPrescriptionTemplate == null){
            return Response.error("该组套不存在!");
        }
        return Response.ok(prescriptionTemplateService.detail(templateId));
    }

}
