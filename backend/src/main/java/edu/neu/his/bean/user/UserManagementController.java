package edu.neu.his.bean.user;

import edu.neu.his.config.Response;
import edu.neu.his.bean.department.DepartmentService;
import edu.neu.his.util.Crypto;
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

/**
 * 实现用户管理的相关功能
 */
@RestController
@RequestMapping("/userManagement")
public class UserManagementController {

    @Autowired
    private UserService userService;
    @Autowired
    private DepartmentService departmentService;

    /**
     * 获取全部用户列表
     * @return 返回查找到的所有用户和状态码等信息
     */
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

    /**
     * 创建新的用户
     * @param user 用户
     * @return 状态码
     */
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

    /**
     * 批量删除用户
     * @param req 前端传递的request，要删除的用户id的列表
     * @return 返回response，表示是否成功
     */
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

    /**
     * 更新科＝用户
     * @param req 前端传递的request，包含User类中的各个字段
     * @return 返回response，表示是否成功
     */
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

    /**
     * 将用户request转换为user对象
     * @param req 前端传递的request，包含User类中的各个字段
     * @return 用户对象
     */
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

    /**
     * 判断该用户能否进行更新
     * @param user 要进行更新的用户
     * @return 返回能否对数据库中的记录进行更新，true代表能，false代表不能
     */
    private boolean canUpdate(User user){
        return userService.canUpdate(user);
    }


    /**
     * 从文件中批量导入科室
     * @param file 需要上传的文件
     * @return 返回response，表示是否成功
     */
    @PostMapping("/import")
    @ResponseBody
    public Map batchImport(@RequestParam("file") MultipartFile file) {
        String pathName = ResourceUtils.CLASSPATH_URL_PREFIX;//想要存储文件的地址
        String pname = file.getOriginalFilename();//获取文件名（包括后缀）
        pathName += pname;
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(pathName);
            fos.write(file.getBytes()); // 写入文件
            System.out.println("文件上传成功");
            if(departmentService.importFromFile(pathName))
                return Response.ok();
            else
                return Response.error("解析失败");
        } catch (Exception e) {
            e.printStackTrace();
            return Response.error("上传失败");
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
                return Response.error("上传失败");
            }
        }
    }

}
