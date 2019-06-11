package edu.neu.his.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.sf.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class Utils {
    public static String getSystemTime(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(new Date());
    }

    public static <T> T fromMap(Map map, Class<T> tClass) throws IOException {
        if(map.get("_uid")!=null){
            map.put("user_id", map.get("_uid"));
        }
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        String json = JSONObject.fromObject(map).toString();
        T object = objectMapper.readValue(json, tClass);
        return object;
    }
}
