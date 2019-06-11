package edu.neu.his.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.neu.his.bean.User;
import edu.neu.his.mapper.UserMapper;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Component
public class Utils {


    public static Utils initUtils;

    @PostConstruct
    public void init() {
        initUtils = this;
    }


    public static String getSystemTime(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(new Date());
    }

    public static <T> T fromMap(Map map, Class<T> tClass){
        if(map.get("_uid")!=null){
            map.put("user_id", map.get("_uid"));
        }
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        String json = JSONObject.fromObject(map).toString();
        T billRecord = null;
        try {
            billRecord = objectMapper.readValue(json, tClass);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return billRecord;
    }


    @Autowired
    private UserMapper userMapper;

    public static User getSystemUser(Map req){
        return initUtils.userMapper.find((int)req.get("_uid"));
    }
}
