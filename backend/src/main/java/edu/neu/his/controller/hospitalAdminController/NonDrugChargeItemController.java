
package edu.neu.his.controller.hospitalAdminController;

import edu.neu.his.bean.NonDrugChargeItem;
import edu.neu.his.config.Response;
import edu.neu.his.service.DepartmentService;
import edu.neu.his.service.NonDrugChargeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//3.6 非药品收费项目管理
@RestController
@RequestMapping("/nonDrugChargeItemManagement")
public class NonDrugChargeItemController {
    @Autowired
    private NonDrugChargeService nonDrugChargeService;

    @Autowired
    private DepartmentService departmentService;


    @PostMapping("/findByName")
    @ResponseBody
    public Map NonDrugChargeFindByName(@RequestBody Map req){
        String name = (String)req.get("name");
        return Response.Ok(nonDrugChargeService.findNonDrugChargeByName(name));
    }

    @GetMapping("/all")
    @ResponseBody
    public Map listAllNonDrugCharge(){
        Map data = new HashMap();
        data.put("expense_classification",nonDrugChargeService.findAllExpenseClassificationNames());
        data.put("non_drug_charge",nonDrugChargeService.findAll());
        data.put("department",departmentService.findAll());
        return Response.Ok(data);
    }

    @PostMapping("/update")
    @ResponseBody
    public Map updateNonDrugCharge(@RequestBody NonDrugChargeItem nonDrugChargeItem) {
        if (canUpdate(nonDrugChargeItem)) {
            nonDrugChargeService.updateNonDrugCharge(nonDrugChargeItem);
            return Response.Ok();
        } else {
            return Response.Error("编号冲突 或 该所属费用科目/执行科室不存在!");
        }
    }

    @PostMapping("/add")
    @ResponseBody
    public Map insertNonDrugCharge(@RequestBody NonDrugChargeItem nonDrugChargeItem){
        if (canInsert(nonDrugChargeItem)) {
            nonDrugChargeService.insertNonDrugCharge(nonDrugChargeItem);
            return Response.Ok();
        } else {
            return Response.Error("编号冲突 或 该所属费用科目/执行科室不存在!");
        }
    }

    @PostMapping("/delete")
    @ResponseBody
    public Map  deleteNonDrugCharge(@RequestBody Map req){
        List<String> nonDrugChargeIds = (List<String>) req.get("data");
        nonDrugChargeIds.forEach(id->nonDrugChargeService.deleteNonDrugCharge(id));
        return Response.Ok();
    }

    private boolean canUpdate(NonDrugChargeItem nonDrugChargeItem){
        return nonDrugChargeService.exist(nonDrugChargeItem);
    }

    private boolean canInsert(NonDrugChargeItem nonDrugChargeItem){
        return !nonDrugChargeService.exist(nonDrugChargeItem);
    }

}