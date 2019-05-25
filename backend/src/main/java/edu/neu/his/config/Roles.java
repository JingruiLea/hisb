package edu.neu.his.config;

import java.util.ArrayList;
import java.util.List;

public class Roles {
    public static int RegisteredTollCollector = 1;
    public static int OutpatientDoctor = 2;
    public static int DoctorOfTechnology = 3;
    public static int PharmacyOperator = 4;
    public static int FinancialAdmin = 5;
    public static int HospitialAdmin = 6;

    public static List<String> roleList() {
        List roles = new ArrayList();
        roles.add("门诊收费员");
        roles.add("门诊医生");
        roles.add("医技医生");
        roles.add("药房操作员");
        roles.add("财务管理员");
        roles.add("医院管理员");
        return roles;
    }
}
