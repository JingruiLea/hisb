package edu.neu.his.bean.outpatientCharges;

/**
 * 将缴费记录的状态（已缴费、已退费、未缴费）和（检查1、检验1、处置0）作为静态变量存放
 */
public class OutpatientChargesRecordStatus {
    public static String Charged = "已缴费";
    public static String Refunded = "已退费";
    public static String ToCharge = "未缴费";

    public static int Prescription = 0;
    public static int Exam = 1;
}
