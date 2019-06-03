package edu.neu.his.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Response  {

    public static Map Ok(Object data) {
        Map res = new HashMap();
        res.put("code",200);
        res.put("data",data);
        return res;
    }

    public static Map Ok() {
        Map res = new HashMap();
        res.put("code",200);
        return res;
    }

    public static Map PermissionDenied() {
        Map res = new HashMap();
        res.put("code",403);
        res.put("msg","permission denied");
        return res;
    }


    public static Map Error(String errMsg) {
        Map res = new HashMap();
        res.put("code",500);
        res.put("msg",errMsg);
        return res;
    }

    public static Map Error(Object data) {
        Map res = new HashMap();
        res.put("code",500);
        res.put("data",data);
        return res;
    }

}
