package edu.neu.his.bean.dailyCheck;

import edu.neu.his.bean.billRecord.BillRecord;
import edu.neu.his.bean.expenseClassification.ExpenseClassification;
import edu.neu.his.bean.outpatientCharges.OutpatientChargesRecord;
import edu.neu.his.bean.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 实现处理数据库中daily_collect、daily_detail、bill_record、expense_classification、outpatient_charges_record表的相关操作
 */
@Service
public class DailyCheckService {
    @Autowired
    private DailyCheckMapper dailyCheckMapper;

    private Float registrationFee = 0f;
    private Float total = 0f;
    private List<ExpenseClassification>  expenseClassifications;
    private float[] classtotal;

    private Map<String,String> columnsMap = new HashMap<String,String>();
    private Map<String,String> tableData = new HashMap<String,String>();
    private List<Map<String,String>> tableDatas = new ArrayList<Map<String,String>>();
    private List<ChartsData> chartsDatas = new ArrayList<ChartsData>();
    private int colNum = 0;

    /**
     * 从数据库中获得所有收费员名单
     * @return 返回所有收费员名单
     */
    @Transactional
    public List<InitUser> getTollCollector() {
        return dailyCheckMapper.getTollCollector();
    }

    /**
     * 从数据库中根据起止日期和收费员id找到日结核对报告
     * @param start_date 起始日期
     * @param end_date 截止日期
     * @param toll_collector_id 收费员id
     * @return 返回根据起止日期和收费员id找到的日结核对报告
     */
    @Transactional
    public List<Report> getReport(String start_date,String end_date,int toll_collector_id){
        List<Report> reports = dailyCheckMapper.getReport(start_date,end_date,toll_collector_id);
        total = 0f;
        registrationFee = 0f;
        for(Report report:reports){
            total += report.getCost();
            //System.out.println(report.toString());
        }
        return dailyCheckMapper.getReport(start_date,end_date,toll_collector_id);
    }

    /**
     * 计算发票汇总总金额，获得挂号费总金额
     * @param start_date 起始日期
     * @param end_date 截止日期
     * @param toll_collector_id 收费员id
     * @return 返回发票汇总总金额，获得挂号费总金额
     */
    @Transactional
    public Float[] getTotal(String start_date,String end_date,int toll_collector_id){
        Float[] t= new Float[]{total,getRegistrationFee(start_date,end_date,toll_collector_id)};
        return t;
    }

    public Float getRegistrationFee(String start_date,String end_date,int toll_collector_id){
        List<Float> fees = dailyCheckMapper.getRegistrationFees(start_date,end_date,toll_collector_id);
        for(Float fee:fees) {
            registrationFee += fee;
        }
        return registrationFee;
    }

    /**
     *从数据库中获得的所有费用科目
     * @return 返回所有费用科目
     */
    @Transactional
    public List<ExpenseClassification> getAllClassifitation(){
        return dailyCheckMapper.getAllClassifitation();
    }

    /**
     *从数据库中查询所有费用科目的金额，并计算总金额
     * @param expenseClassifications 费用科目列表
     * @param toll_collector_id 收费员id
     * @param start_date 起始日期
     * @param end_date 截止日期
     * @return 返回各个费用科目总金额
     */
    @Transactional
    public List<ClassificationFee> getClassifitationFee(List<ExpenseClassification> expenseClassifications, int toll_collector_id,String start_date,String end_date){
        List<ClassificationFee> classifitationFees = new ArrayList<ClassificationFee>();
        classtotal = new float[expenseClassifications.size()];

        expenseClassifications.forEach(expenseClassification-> {
            List<Float> classificationCosts = dailyCheckMapper.getClassifitationFee(start_date,end_date,expenseClassification.getId(), toll_collector_id);
            if (!classificationCosts.isEmpty()) {
                classificationCosts.forEach(classificationCost -> {
                    classtotal[expenseClassifications.indexOf(expenseClassification)] += classificationCost;
                });
                //classifitationFees.put(expenseClassification.getFee_name(), classtotal[expenseClassifications.indexOf(expenseClassification)]);
                classifitationFees.add(new ClassificationFee(expenseClassification.getFee_name(),classtotal[expenseClassifications.indexOf(expenseClassification)]));
            }
        });
        return classifitationFees;
        //return dailyCheckMapper.getClassifitationFee(expense_classification_id,toll_collector_id);
    }

    /**
     *更新数据库中的日结记录
     * @param start_date 起始日期
     * @param end_date 截止日期
     * @param toll_collector_id 收费员id
     * @param checker_id 核对人员id
     */
    @Transactional
    public void confirmCheck(String start_date,String end_date,int toll_collector_id,int checker_id) {
        dailyCheckMapper.confirmCheck(start_date,end_date,toll_collector_id,checker_id);
    }

    /**
     *查找数据库中符合条件的日结记录数量
     * @param start_date 起始日期
     * @param end_date 截止日期
     * @param toll_collector_id 收费员id
     * @param checker_id 核对人员id
     * @return 返回是否存在符合条件的日结核对记录
     */
    @Transactional
    public boolean exist(String start_date,String end_date,int toll_collector_id,int checker_id) {
        return dailyCheckMapper.checkIdExistNums(start_date,end_date,toll_collector_id,checker_id)>0;
    }

    /**
     *从数据库中查找票据记录
     * @param start_date 起始日期
     * @param end_date 截止日期
     * @return 返回满足条件的票据记录
     */
    @Transactional
    public List<BillRecord> history(String start_date, String end_date) {
        return dailyCheckMapper.history(start_date, end_date);
    }

    /**
     *查找数据库中所有科室的列表
     * @param start_date 起始日期
     * @param end_date 截止日期
     * @return 返回查找到的所有科室列表
     */
    @Transactional
    public List<Column> getDepartmentColumns(String start_date, String end_date){
        List<Column> columns = new ArrayList<Column>();
        columnsMap.clear();
        //tableData.clear();

        columns.add(new Column("科室名称","col_1"));
        columns.add(new Column("看诊人次","col_2"));
        columns.add(new Column("发票数量","col_3"));
        columnsMap.put("科室名称","col_1");
        columnsMap.put("看诊人次","col_2");
        columnsMap.put("发票数量","col_3");
        //tableData.put("","col_1");
        //tableData.put("","col_2");
        //tableData.put("","col_3");
        List<String> depColx;
        depColx = dailyCheckMapper.getDepartmentColumns(start_date, end_date);
        int i = 0;


        for(String s:depColx){
            columns.add(new Column(s,"col"+i));
            columnsMap.put(s,"col"+i);
            //tableData.put("col"+i,"");
            i++;
        }
        System.out.println("columnsMap:"+columnsMap);
        colNum = i;
        return columns;
    }

    /**
     *从数据库中查询科室总金额和每个费用科目总金额
     * @param start_date 起始日期
     * @param end_date 截止日期
     * @return 返回科室总金额和每个费用科目总金额
     */
    @Transactional
    public List<Map<String,String>> getDepartmentCheck(String start_date,String end_date){
        List<ObjectCount> departmentCounts = dailyCheckMapper.getDepartmentCount(start_date,end_date);
        List<ObjectSum> departmentSums =  dailyCheckMapper.getDepartmentSum(start_date,end_date);
        //需要提交的表格中所有数据
        List<ObjectCheck> departmentChecks = new ArrayList<ObjectCheck>();
        Map<String,Float> sum;
        Float chartsDataSum = 0f;

        //每个科室的名字，人次，订单数
        for(ObjectCount departmentCount:departmentCounts){
            chartsDataSum = 0f;
            ObjectCheck departmentCheck = new ObjectCheck(departmentCount.getName(),departmentCount.getPerson_time(),departmentCount.getBill_num());
            //每个费用科目的名字，总额
            for(ObjectSum departmentSum:departmentSums){
                if(departmentSum.getName().equals(departmentCount.getName())){
                    sum = departmentCheck.getSum();
                    sum.put(columnsMap.get(departmentSum.getFee_name()),departmentSum.getSum());
                    departmentCheck.setSum(sum);
                    chartsDataSum += departmentSum.getSum();
                }
            }
            departmentChecks.add(departmentCheck);
            chartsDatas.add(new ChartsData(departmentCount.getName(),chartsDataSum));
        }


        int key = 1;
        for(ObjectCheck objectCheck:departmentChecks){
            tableData = new HashMap<String,String>();
            tableData.put("col_1",objectCheck.getName());
            tableData.put("col_2",String.valueOf(objectCheck.getPerson_time()));
            tableData.put("col_3",String.valueOf(objectCheck.getBill_num()));
            for(int i=0;i<colNum;i++){
                sum = objectCheck.getSum();
                if(sum.containsKey("col"+i)){
                    tableData.put("col"+i,sum.get("col"+i).toString());
                }else{
                    tableData.put("col"+i,"0");
                }
            }
            tableData.put("key",String.valueOf(key));
            key++;
            tableDatas.add(tableData);
        }
        return tableDatas;
    }

    /**
     *获得渲染统计图需要的数据
     * @return 返回渲染统计图需要的数据
     */
    @Transactional
    public List<ChartsData> getChartsDatas(){
        return chartsDatas;
    }

    //用户工作量统计
    /*@Transactional
    public List<ObjectCheck> getUserCheck(String start_date,String end_date){
        List<ObjectCount> userCounts = dailyCheckMapper.getUserCount(start_date,end_date);
        List<ObjectSum> userSums =  dailyCheckMapper.getUserSum(start_date,end_date);
        List<ObjectCheck> userChecks = new ArrayList<ObjectCheck>();
        Map<String,Float> sum;

        for(ObjectCount userCount:userCounts){
            ObjectCheck userCheck = new ObjectCheck(userCount.getName(),userCount.getPerson_time(),userCount.getBill_num());
            for(ObjectSum userSum:userSums){
                if(userSum.getName().equals(userCount.getName())){
                    sum = userCheck.getSum();
                    sum.put(userSum.getFee_name(),userSum.getSum());
                    userCheck.setSum(sum);
                }
            }
            userChecks.add(userCheck);
        }
        return userChecks;
    }*/

    /**
     *查找数据库中所有医生的列表
     * @param start_date 起始日期
     * @param end_date 截止日期
     * @return 返回查找到的所有医生列表
     */
    @Transactional
    public List<Column> getUserColumns(String start_date, String end_date){
        List<Column> columns = new ArrayList<Column>();
        columnsMap.clear();

        columns.add(new Column("医生姓名","col_1"));
        columns.add(new Column("看诊人次","col_2"));
        columns.add(new Column("发票数量","col_3"));
        columnsMap.put("医生姓名","col_1");
        columnsMap.put("看诊人次","col_2");
        columnsMap.put("发票数量","col_3");
        List<String> userColx;
        userColx = dailyCheckMapper.getUserColumns(start_date, end_date);
        int i = 0;


        for(String s:userColx){
            columns.add(new Column(s,"col"+i));
            columnsMap.put(s,"col"+i);
            i++;
        }
        colNum = i;
        return columns;
    }

    /**
     *从数据库中查询医生总金额和每个费用科目总金额
     * @param start_date 起始日期
     * @param end_date 截止日期
     * @return 返回医生总金额和每个费用科目总金额
     */
    @Transactional
    public List<Map<String,String>> getUserCheck(String start_date,String end_date){
        List<ObjectCount> userCounts = dailyCheckMapper.getUserCount(start_date,end_date);
        List<ObjectSum> userSums =  dailyCheckMapper.getUserSum(start_date,end_date);
        List<ObjectCheck> userChecks = new ArrayList<ObjectCheck>();
        Map<String,Float> sum;
        Float chartsDataSum = 0f;

        for(ObjectCount userCount:userCounts){
            chartsDataSum = 0f;
            ObjectCheck userCheck = new ObjectCheck(userCount.getName(),userCount.getPerson_time(),userCount.getBill_num());
            for(ObjectSum userSum:userSums){
                if(userSum.getName().equals(userCount.getName())){
                    sum = userCheck.getSum();
                    sum.put(columnsMap.get(userSum.getFee_name()),userSum.getSum());
                    userCheck.setSum(sum);
                    chartsDataSum += userSum.getSum();
                }
            }
            userChecks.add(userCheck);
            chartsDatas.add(new ChartsData(userCount.getName(),chartsDataSum));
        }



        int key = 1;
        for(ObjectCheck objectCheck:userChecks){
            tableData = new HashMap<String,String>();
            tableData.put("col_1",objectCheck.getName());
            tableData.put("col_2",String.valueOf(objectCheck.getPerson_time()));
            tableData.put("col_3",String.valueOf(objectCheck.getBill_num()));
            for(int i=0;i<colNum;i++){
                sum = objectCheck.getSum();
                if(sum.containsKey("col"+i)){
                    tableData.put("col"+i,sum.get("col"+i).toString());
                }else{
                    tableData.put("col"+i,"0");
                }
            }
            tableData.put("key",String.valueOf(key));
            key++;
            tableDatas.add(tableData);
        }
        return tableDatas;
    }
}
