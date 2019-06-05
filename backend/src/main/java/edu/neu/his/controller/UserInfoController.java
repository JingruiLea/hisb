package edu.neu.his.controller;

import edu.neu.his.bean.User;
import edu.neu.his.config.Response;
import edu.neu.his.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserInfoController {
    @Autowired
    private UserService userService;

    @PostMapping("/info")
    @ResponseBody
    public Map getUserInfo(@RequestBody Map req) {
        //System.out.println(req);
        int uid = (int)req.get("_uid");
        User user = userService.findByUid(uid);
        if(user!=null) {
            user.setPassword(null);
            return Response.Ok(user);
        }else
            return Response.Error("该用户不存在！");
    }

}