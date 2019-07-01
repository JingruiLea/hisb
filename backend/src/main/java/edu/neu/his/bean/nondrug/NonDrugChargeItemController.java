
package edu.neu.his.bean.nondrug;

import edu.neu.his.config.Response;
import edu.neu.his.bean.department.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 实现非药品项目管理的相关功能
 */
//3.6 非药品收费项目管理
@RestController
@RequestMapping("/nonDrugChargeItemManagement")
public class NonDrugChargeItemController {
    @Autowired
    private NonDrugChargeService nonDrugChargeService;

    @Autowired
    private DepartmentService departmentService;

    /**
     * 根据名称查找非药品收费项目
     * @param req 前端传递的request，包含“name”等字段
     * @return 返回查找结果
     */
    @PostMapping("/findByName")
    @ResponseBody
    public Map NonDrugChargeFindByName(@RequestBody Map req){
        String name = (String)req.get("name");
        return Response.ok(nonDrugChargeService.findNonDrugChargeByName(name));
    }

    /**
     * 获得所有非药品收费项目的列表
     * @return 返回查找到的所有非药品收费项目和状态码等信息
     */
    @RequestMapping("/all")
    @ResponseBody
    public Map listAllNonDrugCharge(){
        Map data = new HashMap();
        data.put("expense_classification",nonDrugChargeService.findAllExpenseClassificationNames());
        data.put("non_drug_charge",nonDrugChargeService.findAll());
        data.put("department",departmentService.findAll());
        return Response.ok(data);
    }

    /**
     * 更新非药品收费项目
     * @param nonDrugChargeItem 前端传递NonDrugChargeItem对象
     * @return 返回response，表示是否成功
     */
    @PostMapping("/update")
    @ResponseBody
    public Map updateNonDrugCharge(@RequestBody NonDrugChargeItem nonDrugChargeItem) {
        if (canUpdate(nonDrugChargeItem)) {
            nonDrugChargeService.updateNonDrugCharge(nonDrugChargeItem);
            return Response.ok();
        } else {
            return Response.error("编号冲突 或 该所属费用科目/执行科室不存在!");
        }
    }

    /**
     * 创建非药品收费项目
     * @param nonDrugChargeItem 前端传递NonDrugChargeItem对象
     * @return 返回response，表示是否成功
     */
    @PostMapping("/add")
    @ResponseBody
    public Map insertNonDrugCharge(@RequestBody NonDrugChargeItem nonDrugChargeItem){
        if (canInsert(nonDrugChargeItem)) {
            nonDrugChargeService.insertNonDrugCharge(nonDrugChargeItem);
            return Response.ok();
        } else {
            return Response.error("编号冲突 或 该所属费用科目/执行科室不存在!");
        }
    }

    /**
     * 批量删除非药品收费项目
     * @param req 前端传递的request，要删除的非药品收费项目id的列表
     * @return 返回response，表示是否成功
     */
    @PostMapping("/delete")
    @ResponseBody
    public Map  deleteNonDrugCharge(@RequestBody Map req){
        List<Integer> failed = new ArrayList<>();
        List<Integer> nonDrugChargeIds = (List<Integer>) req.get("data");
        nonDrugChargeIds.forEach(id-> {
            if (canDelete(id))
                nonDrugChargeService.deleteNonDrugCharge(id);
            else
                failed.add(id);
        });
        if(failed.isEmpty())
            return Response.ok();
        else{
            Map data = new HashMap();
            data.put("success number",nonDrugChargeIds.size()-failed.size());
            data.put("fail number",failed.size());
            data.put("fail code",failed);
            return Response.error(data);
        }
    }

    private boolean canUpdate(NonDrugChargeItem nonDrugChargeItem){
        return nonDrugChargeService.exist(nonDrugChargeItem);
    }

    private boolean canInsert(NonDrugChargeItem nonDrugChargeItem){
        return !nonDrugChargeService.exist(nonDrugChargeItem);
    }

    private boolean canDelete(int id){
        return nonDrugChargeService.canDelete(id);
    }
}