
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
        return Response.Ok(data);
    }

    @PostMapping("/update")
    @ResponseBody
    public Map updateNonDrugCharge(@RequestBody Map req){
        String code = (String)req.get("code");
        String name = (String)req.get("name");
        String format = (String)req.get("format");
        String pinyin = (String)req.get("pinyin");
        int id = (int)req.get("id");
        float fee = (float)req.get("fee");
        String fee_name = (String)req.get("fee_name");
        String department = (String)req.get("department");

        if(canUpdate(code, name, fee_name, department, id)) {
            int department_id = departmentService.findDepartmentByName(department).getId();
            int expense_classification_id = nonDrugChargeService.findExpenseClassificationIdByName(fee_name);
            NonDrugChargeItem nonDrugCharge = new NonDrugChargeItem(code,format,pinyin,name,fee,expense_classification_id,department_id);
            nonDrugChargeService.updateNonDrugCharge(nonDrugCharge);
            return Response.Ok();
        }else{
            return Response.Error("编号冲突 或 该所属费用科目/执行科室不存在!");
        }
    }

    @PostMapping("/add")
    @ResponseBody
    public Map insertNonDrugCharge(@RequestBody Map req){
        String code = (String)req.get("code");
        String name = (String)req.get("name");
        String format = (String)req.get("format");
        String pinyin = (String)req.get("pinyin");
        float fee = (float)req.get("fee");
        String fee_name = (String)req.get("fee_name");
        String department = (String)req.get("department");

        if(canOperate(name,code, fee_name, department)){
            int department_id = departmentService.findDepartmentByName(department).getId();
            int expense_classification_id = nonDrugChargeService.findExpenseClassificationIdByName(fee_name);
            NonDrugChargeItem nonDrugCharge = new NonDrugChargeItem(code,format,pinyin,name,fee,expense_classification_id,department_id);
            nonDrugChargeService.insertNonDrugCharge(nonDrugCharge);
            return Response.Ok();
        }else {
            return Response.Error("编号冲突 或 该类别不存在");
        }
    }

    @PostMapping("/delete")
    @ResponseBody
    public Map  deleteNonDrugCharge(@RequestBody Map req){
        ArrayList nonDrugCharges = (ArrayList)req.get("data");
        for(int i=0; i<nonDrugCharges.size(); i++) {
            int id = (int)((Map<String,Object>)nonDrugCharges.get(i)).get("id");
            nonDrugChargeService.deleteNonDrugCharge(id);
        }
        return Response.Ok();
    }

    private boolean canUpdate(String code, String name, String fee_name, String department, int id){
        List<NonDrugChargeItem> list = nonDrugChargeService.findAll();
        for(int i=0; i<list.size(); i++){
            if((list.get(i).getName().equals(name)||list.get(i).getCode().equals(code))
                    && list.get(i).getId()!=id)
                return false;
        }

        List<String> fees = nonDrugChargeService.findAllExpenseClassificationNames();
        for(int i=0; i<fees.size(); i++){
            if(fees.get(i).equals(fee_name))
                return true;
        }

        List<String> departments = departmentService.findAllNames();
        for(int i=0; i<departments.size(); i++){
            if(departments.get(i).equals(department))
                return true;
        }

        return false;
    }

    private boolean canOperate(String code, String name, String fee_name, String department){
        List<NonDrugChargeItem> list = nonDrugChargeService.findAll();
        for(int i=0; i<list.size(); i++){
            if((list.get(i).getName().equals(name)||list.get(i).getCode().equals(code)))
                return false;
        }

        List<String> fees = nonDrugChargeService.findAllExpenseClassificationNames();
        for(int i=0; i<fees.size(); i++){
            if(fees.get(i).equals(fee_name))
                return true;
        }

        List<String> departments = departmentService.findAllNames();
        for(int i=0; i<departments.size(); i++){
            if(departments.get(i).equals(department))
                return true;
        }

        return false;
    }
}