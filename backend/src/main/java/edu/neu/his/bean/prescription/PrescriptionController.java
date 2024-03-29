package edu.neu.his.bean.prescription;

import edu.neu.his.config.Response;
import edu.neu.his.bean.drug.DrugService;
import edu.neu.his.util.Common;
import edu.neu.his.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 实现处方管理的相关功能
 */
@RestController
@RequestMapping("/prescription")
public class PrescriptionController {

    @Autowired
    PrescriptionService prescriptionService;

    @Autowired
    DrugService drugService;

    /**
     * 创建处方
     * @param req 前端传递的request，包含病历号和处方详情列表
     * @return 创建的处方id
     */
    @PostMapping("/create")
    public Map create(@RequestBody Map req){
        int medicalRecordId = (int)req.get("medical_record_id");
        List<Map> drugList = (List)req.get("prescription_item_list");
        int type = (int)req.get("type");
        if(!prescriptionService.recordMedicalHasSubmit(medicalRecordId)){
            return Response.error("找不到已经提交的病历!");
        }
        if(!drugService.allItemValid(drugList)){
            return Response.error("该药品不存在!");
        }
        int prescriptionId = prescriptionService.create(Utils.getSystemUser(req).getUid(), type, medicalRecordId, drugList);
        return Response.ok(prescriptionService.findById(prescriptionId));
    }

    /**
     * 添加处方详情
     * @param req 前端传递的request，包含处方id和药品列表
     * @return 状态码
     */
    @PostMapping("/addItem")
    public Map addItem(@RequestBody Map req){
        int prescriptionId = (int)req.get("prescription_id");
        List<Map> drugList = (List)req.get("drug_list");
        if(!drugService.allItemValid(drugList)){
            return Response.error("该药品不存在!");
        }
        prescriptionService.addItems(prescriptionId, drugList);
        return Response.ok();
    }

    /**
     * 删除处方详情
     * @param req 前端传递的request，包含处方id和药品列表
     * @return 状态码
     */
    @PostMapping("/deleteItem")
    public Map deleteItem(@RequestBody Map req){
        int prescriptionId = (int)req.get("prescription_id");
        List<Map> drugList = (List)req.get("drug_list");
        if(!drugService.allItemValid(drugList)){
            return Response.error("该药品不存在!");
        }
        prescriptionService.removeItems(prescriptionId, drugList);
        return Response.ok();
    }

    /**
     * 更新处方
     * @param req 前端传递的request，包含处方id和处方列表
     * @return 状态码
     */
    @PostMapping("/update")
    public Map updateItem(@RequestBody Map req){
        int prescriptionId = (int)req.get("id");
        List<Map> drugList = (List)req.get("prescription_item_list");
        if(!drugService.allItemValid(drugList)){
            return Response.error("该药品不存在!");
        }
        if(!prescriptionService.removeAllItems(prescriptionId)){
            return Response.error("没有该处方!");
        }
        prescriptionService.addItems(prescriptionId, drugList);
        return Response.ok();
    }

    /**
     * 提交处方
     * @param req 前端传递的request，包含处方id
     * @return 状态码
     */
    @PostMapping("/submit")
    public Map submit(@RequestBody Map req){
        List<Integer> prescriptionIds = (List<Integer>) req.get("id");
        for (Integer prescriptionId : prescriptionIds) {
            if(prescriptionService.findById(prescriptionId) == null){
                return Response.error("没有该处方!");
            }
            prescriptionService.submit(Utils.getSystemUser(req), prescriptionId);
        }
        return Response.ok();
    }

    /**
     * 根据处方id获得处方详情
     * @param map 前端传递的request，包含处方id
     * @return 根据处方id获得的处方详情
     */
    @PostMapping("/detail")
    public Map detail(Map map){
        int prescriptionId = (int)map.get("prescription_id");
        List res = prescriptionService.detail(prescriptionId);
        return Response.ok(res);
    }

    /**
     * 根据分类获取全部的药品目录
     * @param req 前端传递的request，包含分类
     * @return 根据分类获取的全部药品列表
     */
    @RequestMapping("allDrugs")
    public Map allDrugs(@RequestBody Map req){
        int type = (int) req.get("type");
        List res = drugService.selectAllDrug().stream().filter(item->{
            if(type == 0 && item.getType().equals(Common.ZHONGCHENGYAOTYPE)){
                return true;
            }else if(type == 0 && item.getType().equals(Common.XIYAOTYPE)){
                return true;
            }else if(type == 1 && item.getType().equals(Common.ZHONGCAOYAOTYPE)){
                return true;
            }
            return false;
        }).collect(Collectors.toList());
        return Response.ok(res);
    }

    /**
     * 根据分类获取全部的处方
     * @param req 前端传递的request，包含分类和病历号
     * @return 根据分类获取的全部处方
     */
    @RequestMapping("allPrescription")
    public Map allPrescription(@RequestBody Map req){
        int type = (int) req.get("type");
        List<Prescription> list = prescriptionService.selectAll();
        List res = list.stream().filter(item-> item.getType() == type).collect(Collectors.toList());
        return Response.ok(res);
    }

    /**
     * 删除处方
     * @param req 前端传递的request，包含处方id
     * @return 状态码
     */
    @RequestMapping("delete")
    public Map delete(@RequestBody Map req){
        List<Integer> ids = (List<Integer>) req.get("id");
        for (Integer id : ids) {
            prescriptionService.removeAllItems(id);
            if(prescriptionService.delete(id)!=1){
                return Response.error("列表错误!");
            }
        }
        return Response.ok();
    }

    /**
     * 作废处方
     * @param req 前端传递的request，包含处方id
     * @return 状态码
     */
    @RequestMapping("/cancel")
    public Map cancel(@RequestBody Map req){
        List<Integer> ids = (List<Integer>) req.get("id");
        for (Integer id : ids) {
            if(!prescriptionService.cancel(id)){
                return Response.error("没有该组套!");
            }
        }
        return Response.ok();
    }
}