package edu.neu.his.bean.expenseClassification;

import edu.neu.his.util.Utils;
import org.springframework.web.bind.annotation.RestController;
import edu.neu.his.config.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

/**
 * 实现费用类别管理的相关功能
 */
@RestController
@RequestMapping("/expenseClassificationManage")
public class ExpenseClassificationController {
    @Autowired
    private ExpenseClassificationService expenseClassificationService;

    /**
     * 获得所有费用类别的列表
     * @return 查找到的所有费用类别的列表
     */
    @RequestMapping("/all")
    @ResponseBody
    public Map findAll(){
        return Response.ok(expenseClassificationService.findAll());
    }

    /**
     * 更新费用类别
     * @param expenseClassification 前端传递的费用类别
     * @return 返回response，表示是否成功
     */
    @PostMapping("/update")
    @ResponseBody
    public Map updateExpenseClassification(@RequestBody ExpenseClassification expenseClassification) {
        if (canUpdate(expenseClassification)) {
            expenseClassificationService.updateExpenseClassification(expenseClassification);
            return Response.ok();
        } else {
            return Response.error("编号冲突 或 该费用科目不存在!");
        }
    }

    /**
     * 创建费用类别
     * @param req 前端传递的request，包含ExpenseClassification类中的各个字段
     * @return 返回response，表示是否成功
     */
    @PostMapping("/add")
    @ResponseBody
    public Map insertExpenseClassification(@RequestBody Map req){
        ExpenseClassification expenseClassification = Utils.fromMap(req,ExpenseClassification.class);
        if(canInsert(expenseClassification)) {
            expenseClassificationService.insertExpenseClass(expenseClassification);
            return Response.ok();
        }else
            return Response.error("错误, 该ID或名称已存在");
    }

    /**
     * 删除费用类别
     * @param req 前端传递的request，包含费用类别id列表
     * @return 返回response，表示是否成功
     */
    @PostMapping("/delete")
    @ResponseBody
    public Map  deleteExpenseClassification(@RequestBody Map req){
        List ids = (List)req.get("idArr");
        ids.forEach(id->{
            if(canDelete((int)id))
                expenseClassificationService.deleteExpenseClassification((int)id);
        });
        return Response.ok();
    }

    private boolean canUpdate(ExpenseClassification expenseClassification){
        return expenseClassificationService.exist(expenseClassification);
    }

    private boolean canInsert(ExpenseClassification expenseClassification){
        return !expenseClassificationService.exist(expenseClassification);
    }

    private boolean canDelete(int id){
        return expenseClassificationService.canDelete(id);
    }
}
