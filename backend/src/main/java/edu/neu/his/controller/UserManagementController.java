package edu.neu.his.controller;

import edu.neu.his.bean.Department;
import edu.neu.his.bean.User;
import edu.neu.his.config.Response;
import edu.neu.his.config.Roles;
import edu.neu.his.service.DepartmentService;
import edu.neu.his.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/userManagement")
public class UserManagementController {

    @Autowired
    private UserService userService;
    @Autowired
    private DepartmentService departmentService;

    @GetMapping("/all")
    @ResponseBody
    public Map selectAllUser() {
        List<User> users = userService.findAll();
        //去除密码
        users.forEach(user->user.setPassword(null));
        Map data = new HashMap();
        data.put("departmentClassification",departmentService.findAllNames());
        data.put("roles", Roles.roleList());
        data.put("users",users);
        return Response.Ok(data);
    }

    @PostMapping("/add")
    @ResponseBody
    public Map addNewUser(@RequestBody Map req) {
        User user = map2User(req);
        if (user==null)
            return Response.Error("该科室 或 该用户角色不存在");
        userService.addUser(user);
        return Response.Ok();
    }

    @PostMapping("/delete")
    @ResponseBody
    public Map deleteUser(@RequestBody Map req) {
        List<Map> users = (List<Map>)req.get("data");
        users.forEach(user -> userService.deleteUser((int)user.get("uid")));
        return Response.Ok();
    }

    @PostMapping("/update")
    @ResponseBody
    public Map updateUser(@RequestBody Map req) {
        User user = map2User(req);
        if (user==null)
            return Response.Error("该科室 或 该用户角色不存在");
        user.setUid((int)req.get("uid"));
        userService.updateUser(user);
        return Response.Ok();
    }

    private User map2User(Map req) {
        User user = new User();
        Department department = departmentService.findDepartmentByName((String)req.get("department_name"));
        if(department==null) return null;
        user.setDepartment_id(department.getId());
        user.setDepartment_name((String)req.get("department_name"));
        user.setUsername((String)req.get("username"));
        user.setPassword((String)req.get("password"));
        user.setParticipate_in_scheduling((boolean)req.get("participate_in_scheduling"));
        user.setReal_name((String)req.get("real_name"));
        user.setRole((String)req.get("role"));
        user.setTitle((String)req.get("title"));
        return user;
    }

}
