package edu.neu.his.bean.drug;

import edu.neu.his.config.Response;
import edu.neu.his.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
/**
 * 实现药品管理的相关功能
 */
@RestController
@RequestMapping("/drugInfoManagement")
public class DrugManageController {
    @Autowired
    DrugService drugService;

    /**
     * 创建新的药品
     * @param req 前端传递的request，包含Drug类中的各个字段
     * @return 返回response，表示是否成功
     */
    @PostMapping("/add")
    @ResponseBody
    public Map add(@RequestBody Map req){
        Drug drug = Utils.fromMap(req,Drug.class);
        if(!drugService.existDrug(drug.getId())) {
            drugService.insertDrug(drug);
            return Response.ok();
        }else
            return Response.error("错误，该ID已存在");
    }

    /**
     * 批量删除药品
     * @param req 前端传递的request，要删除的药品id的列表
     * @return 返回response，表示是否成功
     */
    @PostMapping("/delete")
    @ResponseBody
    public Map delete(@RequestBody Map req){
        List ids = (List)req.get("data");
        ids.forEach(id->{
            if(drugService.existDrug((int)id))
                drugService.deleteDrug((int)id);
        });
        return Response.ok();
    }

    /**
     * 更新药品
     * @param req 前端传递的request，包含Drug类中的各个字段
     * @return 返回response，表示是否成功
     */
    @PostMapping("/update")
    @ResponseBody
    public Map update(@RequestBody Map req) {
        Drug drug = Utils.fromMap(req, Drug.class);
        if(drugService.existDrug(drug.getId())) {
            drugService.updateDrug(drug);
            return Response.ok();
        }else
            return Response.error("错误，该药品不存在");
    }

    /**
     * 获得所有药品的列表
     * @return 返回查找到的所有药品和状态码等信息
     */
    @PostMapping("/all")
    @ResponseBody
    public Map findAll(){
        return Response.ok(drugService.selectAllDrug());
    }

    /**
     * 根据名称查找药品
     * @param req 前端传递的request，包含“input”等字段
     * @return 返回查找结果
     */
    @PostMapping("/getDrugInfoByName")
    @ResponseBody
    public Map getDrugInfoByName(@RequestBody Map req){
        String name = (String)req.get("input");
        return Response.ok(drugService.selectDrugByName(name));
    }
}
