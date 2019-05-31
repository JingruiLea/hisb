package edu.neu.his.config;

import java.util.HashMap;

public class RegistrationConfig {
    public static String registrationAvailable = "available";
    public static String registrationCanceled = "canceled";
    public static HashMap<Integer, String> titleMap = new HashMap<>();

    public static void initTitleMap(){
        titleMap.put(1,"专家");
        titleMap.put(2,"主任");
    }
}
