package edu.neu.his.bean.medicalRecord;

/**
 * 将病历的状态（已提交、暂存、诊毕；个人0、科室1、全院2）作为静态变量存放
 */
public class MedicalRecordStatus {
    public static String Committed = "已提交";
    public static String TemporaryStorage = "暂存";
    public static String Finished = "诊毕";
    public static int Personal = 0;
    public static int Department = 1;
    public static int Public = 2;
}
