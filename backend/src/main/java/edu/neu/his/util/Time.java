package edu.neu.his.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Time {
    public static String createTime(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String create_time = df.format(new Date());
        return create_time;
    }
}
