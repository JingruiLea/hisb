package edu.neu.his.bean.user;

import edu.neu.his.config.Response;
import edu.neu.his.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserInfoController {
    @Autowired
    private UserService userService;

    @Autowired
    UserMapper userMapper;

    @PostMapping("/info")
    @ResponseBody
    public Map getUserInfo(@RequestBody Map req) {
        //System.out.println(req);
        int uid = (int)req.get("_uid");
        User user = userService.findByUid(uid);
        if(user!=null) {
            user.setPassword(null);
            Map res = Utils.objectToMap(user);
            res.put("roles", new String[]{"admin"});
            return Response.ok(res);
        }else
            return Response.error("用户信息读取失败，请检查数据库约束！");
    }

    @PostMapping("/login")
    @ResponseBody
    public Map login(@RequestBody Map req) {
        //System.out.println(req);
        String username = (String)req.get("username");
        String password = (String)req.get("password");
        User user = userMapper.findOneByName(username);

        if(user==null){
            return Response.error("用户名或密码错误");
        }

        if(DigestUtils.md5DigestAsHex(password.getBytes()).equals(user.getPassword())){
            Map res = new HashMap();
            res.put("token", user.getUid());
            return Response.ok(res);
        }else{
            return Response.error("用户名或密码错误");
        }
    }

    @PostMapping("/logout")
    @ResponseBody
    public Map logout(@RequestBody Map req) {
        //System.out.println(req);
        return Response.ok();
    }
}
