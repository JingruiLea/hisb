package edu.neu.his.bean.user;

import edu.neu.his.bean.department.Department;
import edu.neu.his.bean.department.DepartmentMapper;
import edu.neu.his.config.Response;
import edu.neu.his.bean.department.DepartmentService;
import edu.neu.his.util.Crypto;
import edu.neu.his.util.ExcelImportation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@RestController
@RequestMapping("/userManagement")
public class UserManagementController {

    @Autowired
    private UserService userService;
    @Autowired
    private DepartmentService departmentService;

    @Autowired UserMapper userMapper;

    @RequestMapping("/all")
    @ResponseBody
    public Map selectAllUser() {
        List<User> users = userService.allUsers();
        //去除密码
        users.forEach(user->user.setPassword(null));
        Map data = new HashMap();
        data.put("departments",departmentService.findAll());
        data.put("roles", userService.allRoles());
        data.put("users",users);
        return Response.ok(data);
    }

    @PostMapping("/add")
    @ResponseBody
    public Map addNewUser(@RequestBody User user) {
        if(canInsert(user)) {
            userService.addUser(user);
            return Response.ok();
        }else
            return Response.error("id或名称冲突");
    }
    private boolean canInsert(User user){
        return userService.canInsert(user);
    }

    @PostMapping("/delete")
    @ResponseBody
    public Map deleteUser(@RequestBody Map req) {
        List<Integer> failed = new ArrayList<>();
        List<Integer> user_ids = (List<Integer>)req.get("data");
        user_ids.forEach(id -> {
            if(canDelete(id))
                userService.deleteUser(id);
            else
                failed.add(id);
        });
        if(failed.isEmpty())
            return Response.ok();
        else{
            Map data = new HashMap();
            data.put("success number",user_ids.size()-failed.size());
            data.put("fail number",failed.size());
            data.put("fail id",failed);
            return Response.error(data);
        }
    }
    private boolean canDelete(int id){
        return userService.canDelete(id)!=0;
    }

    @PostMapping("/update")
    @ResponseBody
    public Map updateUser(@RequestBody Map req) {
        User user = map2User(req);
        if(canUpdate(user)) {
            userService.updateUser(user);
            return Response.ok();
        }else
            return Response.error("名称冲突 或 id不存在");
    }

    private User map2User(Map req) {
        System.out.println(req);
        User user = new User();
        if (req.containsKey("uid"))
            user.setUid((int)req.get("uid"));
        user.setUsername((String)req.get("username"));
        user.setPassword(Crypto.md5((String)req.get("password")));
        user.setParticipate_in_scheduling((boolean)req.get("participate_in_scheduling"));
        user.setReal_name((String)req.get("real_name"));
        user.setTitle((String)req.get("title"));
        user.setDepartment_id((int)req.get("department_id"));
        user.setRole_id((int)req.get("role_id"));
        return user;
    }

    private boolean canUpdate(User user){
        return userService.canUpdate(user);
    }

    @Autowired
    DepartmentMapper departmentMapper;


    @PostMapping("/import")
    @ResponseBody
    public Map batchImport(@RequestParam("file") MultipartFile file) throws IOException {
        ExcelImportation excel = new ExcelImportation(
                file.getInputStream(),
                User.class,
                userService);
        excel.skipLine(1);
        excel.setColumnFields("uid", "username", "role_id", "real_name", "title", "department_id");
        ((Map<String, Function<String, ?>>)
                excel.getPreFunctionMap()).put("department_id",
                str -> departmentMapper.findByName(str).getId()
        );
        ((Map<String, Function<String, ?>>)
                excel.getPreFunctionMap()).put("role_id",
                str -> {
                    List<Role> roles = userMapper.allRoles();
                    for (Role role : roles) {
                        if(role.getName().equals(str)){
                            return role.getId();
                        }
                    }
                    return 0;
                }
        );
        excel.exec();
        return Response.ok();
    }

}
