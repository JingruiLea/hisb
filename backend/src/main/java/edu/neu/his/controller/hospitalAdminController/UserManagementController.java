package edu.neu.his.controller.hospitalAdminController;

import edu.neu.his.bean.Department;
import edu.neu.his.bean.User;
import edu.neu.his.config.Response;
import edu.neu.his.service.DepartmentService;
import edu.neu.his.service.UserService;
import edu.neu.his.util.Crypto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        List<User> users = userService.allUsers();
        //去除密码
        users.forEach(user->user.setPassword(null));
        Map data = new HashMap();
        data.put("departments",departmentService.findAll());
        data.put("roles", userService.allRoles());
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
        List<Integer> user_ids = (List<Integer>)req.get("data");
        user_ids.forEach(id -> userService.deleteUser(id));
        return Response.Ok();
    }

    @PostMapping("/update")
    @ResponseBody
    public Map updateUser(@RequestBody Map req) {
        User user = map2User(req);
        userService.updateUser(user);
        return Response.Ok();
    }

    private User map2User(Map req) {
        System.out.println(req);
        User user = new User();
        if (req.containsKey("id"))
            user.setUid((int)req.get("uid"));
        user.setUsername((String)req.get("username"));
        user.setPassword(Crypto.getSHA256String((String)req.get("password")));
        user.setParticipate_in_scheduling((boolean)req.get("participate_in_scheduling"));
        user.setReal_name((String)req.get("real_name"));
        user.setTitle((String)req.get("title"));
        user.setDepartment_id((int)req.get("department_id"));
        user.setRole_id((int)req.get("role_id"));
        return user;
    }

}
