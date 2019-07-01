package edu.neu.his.bean.registration;

import edu.neu.his.config.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 实现挂号等级管理的相关功能
 */
@RestController
@RequestMapping("/registrationLevelManagement")
public class RegistrationLevelController {
    @Autowired
    private RegistrationLevelService registrationLevelService;

    /**
     * 获得所有挂号等级的列表
     * @return 返回查找到的所有挂号等级和状态码等信息
     */
    @RequestMapping("/all")
    @ResponseBody
    public Map listAllRegistration_level(){
        return Response.ok(registrationLevelService.findAll());
    }

    /**
     * 更新挂号等级
     * @param registration_level 挂号等级
     * @return 状态码
     */
    @PostMapping("/update")
    @ResponseBody
    public Map updateRegistration_level(@RequestBody RegistrationLevel registration_level){
        if(canUpdate(registration_level)) {
            registrationLevelService.updateDepartment(registration_level);
            return Response.ok();
        }else
            return Response.error("名称冲突");
    }

    /**
     * 创建新的挂号等级
     * @param registration_level 挂号等级
     * @return 状态码
     */
    @PostMapping("/add")
    @ResponseBody
    public Map  insertRegistration_level(@RequestBody RegistrationLevel registration_level){
        if(canInsert(registration_level)) {
            registrationLevelService.insertRegistration_level(registration_level);
            return Response.ok();
        }else
            return Response.error("id或名称冲突");
    }

    /**
     * 批量删除挂号等级
     * @param req 前端传递的request，要删除的挂号等级id的列表
     * @return 状态码
     */
    @PostMapping("/delete")
    @ResponseBody
    public Map deleteRegistration_level(@RequestBody Map req){
        List<Integer> failed = new ArrayList<>();
        List<Integer> registration_level_ids = (List<Integer>)req.get("data");
        registration_level_ids.forEach(id -> {
            if(canDelete(id))
                registrationLevelService.deleteRegistration_level(id);
            else
                failed.add(id);
        });
        if(failed.isEmpty())
            return Response.ok();
        else{
            Map data = new HashMap();
            data.put("success number",registration_level_ids.size()-failed.size());
            data.put("fail number",failed.size());
            data.put("fail id",failed);
            return Response.error(data);
        }
    }

    /**
     * 判断该挂号等级能否插入数据库
     * @param registration_level 挂号等级
     * @return 返回能否插入数据库，true代表能，false代表不能
     */
    private boolean canInsert(RegistrationLevel registration_level){
        return registrationLevelService.canInsert(registration_level);
    }

    /**
     * 判断该挂号等级能否进行更新
     * @param registration_level 挂号等级
     * @return 返回能否对数据库中的记录进行更新，true代表能，false代表不能
     */
    private boolean canUpdate(RegistrationLevel registration_level){
        return registrationLevelService.canUpdate(registration_level);
    }

    /**
     * 判断该挂号等级能否进行删除
     * @param id 挂号等级id
     * @return 返回能否对数据库中的记录进行删除，true代表能，false代表不能
     */
    private boolean canDelete(int id){
        return registrationLevelService.canDelete(id)!=0;
    }
}

