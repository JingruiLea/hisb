package edu.neu.his.bean.settlementCategory;

import edu.neu.his.config.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 实现结算类型管理的相关功能
 */
@RestController
@RequestMapping("/settlementCategoryManagement")
public class SettlementCategoryManagementController {
    @Autowired
    SettlementCategoryService settlementCategoryService;

    /**
     * 获得所有结算类型的列表
     * @return 返回查找到的所有结算类型和状态码等信息
     */
    @RequestMapping("/all")
    @ResponseBody
    public Map getAllSettlementCategory() {
        return Response.ok(settlementCategoryService.findAll());
    }

    /**
     * 批量删除结算类型
     * @param req 前端传递的request，要删除的结算类型id的列表
     * @return 返回删除成功和失败的结算类型数量，以及删除失败的结算类型id
     */
    @PostMapping("/delete")
    @ResponseBody
    public Map deleteSettlementCategory(@RequestBody Map req) {
        List<Integer> failed = new ArrayList<>();
        List<Integer> settlementCategoryIds = (List<Integer>)req.get("data");
        settlementCategoryIds.forEach(id -> {
            if(canDelete(id))
                settlementCategoryService.deleteSettlementCategoryById(id);
            else
                failed.add(id);
        });
        if(failed.isEmpty())
            return Response.ok();
        else{
            Map data = new HashMap();
            data.put("success number",settlementCategoryIds.size()-failed.size());
            data.put("fail number",failed.size());
            data.put("fail id",failed);
            return Response.error(data);
        }
    }

    /**
     * 创建新的结算类型
     * @param settlementCategory 前端传递的request，包含SettlementCategory类中的各个字段
     * @return 返回response，表示是否成功
     */
    @PostMapping("/add")
    @ResponseBody
    public Map addAllSettlementCategory(@RequestBody SettlementCategory settlementCategory) {
        if(canInsert(settlementCategory)) {
            settlementCategoryService.addSettlementCategory(settlementCategory);
            return Response.ok();
        }else
            return Response.error("id或名称冲突");
    }

    /**
     * 更新结算类型
     * @param settlementCategory 前端传递的request，包含SettlementCategory类中的各个字段
     * @return 返回response，表示是否成功
     */
    @PostMapping("/update")
    @ResponseBody
    public Map updateAllSettlementCategory(@RequestBody SettlementCategory settlementCategory) {
        if(canUpdate(settlementCategory)) {
            settlementCategoryService.update(settlementCategory);
            return Response.ok();
        }else
            return Response.error("名称冲突 或 id不存在");
    }

    /**
     * 判断该结算类型能否插入数据库
     * @param settlementCategory 要插入数据库的SettlementCategory对象
     * @return 返回能否插入数据库，true代表能，false代表不能
     */
    private boolean canInsert(SettlementCategory settlementCategory){
        return settlementCategoryService.canInsert(settlementCategory);
    }

    /**
     * 判断该结算类型能否进行更新
     * @param settlementCategory 要更新的SettlementCategory对象
     * @return 返回能否对数据库中的记录进行更新，true代表能，false代表不能
     */
    private boolean canUpdate(SettlementCategory settlementCategory){
        return settlementCategoryService.canUpdate(settlementCategory);
    }

    /**
     * 判断该结算类型能否进行删除
     * @param id 要删除的SettlementCategory对象
     * @return 返回能否对数据库中的记录进行删除，true代表能，false代表不能
     */
    private boolean canDelete(int id){
        return settlementCategoryService.canDelete(id)!=0;
    }

}
