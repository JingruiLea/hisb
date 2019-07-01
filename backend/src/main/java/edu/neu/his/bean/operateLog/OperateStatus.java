package edu.neu.his.bean.operateLog;

import java.util.HashMap;

/**
 * 将操作记录的状态（挂号、退号、开药）和（检查7、检验3、处置16）作为静态变量存放
 */
public class OperateStatus {
    public static String Register = "挂号";
    public static String Cancel = "退号";
    public static String PrescribeMedicine = "开药";
    public static HashMap<Integer, String> operateMap = new HashMap<>();

    public static void initOperateMap(){
        operateMap.put(3, "检验");
        operateMap.put(7, "检查");
        operateMap.put(16, "处置");
    }
}
