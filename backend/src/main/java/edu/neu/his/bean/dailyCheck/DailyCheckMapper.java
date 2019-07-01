package edu.neu.his.bean.dailyCheck;

import edu.neu.his.bean.billRecord.BillRecord;
import edu.neu.his.bean.expenseClassification.ExpenseClassification;
import edu.neu.his.bean.outpatientCharges.OutpatientChargesRecord;
import edu.neu.his.bean.user.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 该类对数据库中的daily_collect、daily_detail、bill_record、expense_classification、outpatient_charges_record表进行数据持久化操作
 */
@Mapper
public interface DailyCheckMapper {
    /**
     * 查找所有收费员
     * @return 返回所有收费员列表
     */
    @Select("SELECT uid as id, real_name as name FROM user_info WHERE role_id=2")
    List<InitUser> getTollCollector();

    /**
     * 根据起止日期和收费员id查找日结核对报告
     * @param start_date 起始日期
     * @param end_date 截止日期
     * @param toll_collector_id 收费员id
     * @return 返回对应日结核对报告列表
     */
    @Select("SELECT medical_record_id, cost, bill_record.id, bill_record.print_status "+
            "FROM daily_collect, daily_detail,bill_record "+
            "WHERE CAST(start_time AS DATETIME) >= CAST(#{start_date} AS DATETIME) and CAST(end_time AS DATETIME) <= CAST(#{end_date} AS DATETIME) "+
            "and bill_record.user_id=#{toll_collector_id} and daily_collect.id=daily_detail.daily_collect_id "+
            "and daily_detail.bill_record_id=bill_record.id and daily_collect.checked=0")
    List<Report> getReport(String start_date,String end_date,int toll_collector_id);

    /*@Select("SELECT cost FROM registration WHERE medical_record_id=#{medical_record_id}")
    Float getRegistrationFee(int medical_record_id);*/

    /**
     * 根据起止日期和收费员id查找挂号总金额
     * @param start_date 起始日期
     * @param end_date 截止日期
     * @param toll_collector_id 收费员id
     * @return 返回挂号总金额
     */
    @Select("SELECT cost " +
            "FROM daily_collect, daily_detail,bill_record, operate_log " +
            "WHERE CAST(start_time AS DATETIME) >= CAST(#{start_date} AS DATETIME) and CAST(end_time AS DATETIME) <= CAST(#{end_date} AS DATETIME) " +
            "and bill_record.user_id=#{toll_collector_id} and daily_collect.id=daily_detail.daily_collect_id " +
            "and daily_detail.bill_record_id=bill_record.id and daily_collect.checked=0 and bill_record.id=operate_log.bill_record_id and operate_log.type=\"挂号\"")
    List<Float> getRegistrationFees(String start_date,String end_date,int toll_collector_id);

    /**
     * 查找所有费用科目
     * @return 返回费用科目列表
     */
    @Select("SELECT * FROM expense_classification")
    List<ExpenseClassification> getAllClassifitation();

    /**
     * 根据起止日期和费用科目查找对应收费记录金额
     * @param start_date 起始日期
     * @param end_date 截止日期
     * @param expense_classification_id 费用科目id
     * @param toll_collector_id 收费员id
     * @return 返回收费记录金额
     */
    @Select("SELECT DISTINCT outpatient_charges_record.cost "+
            "FROM daily_collect, daily_detail, bill_record, outpatient_charges_record "+
            "WHERE CAST(start_time AS DATETIME) > CAST(#{start_date} AS DATETIME) and CAST(end_time AS DATETIME) < CAST(#{end_date} AS DATETIME) "+
            "and daily_collect.id=daily_detail.daily_collect_id and daily_detail.bill_record_id=bill_record.id " +
            "and bill_record.medical_record_id=outpatient_charges_record.medical_record_id "+
            "and expense_classification_id=#{expense_classification_id} and outpatient_charges_record.collect_user_id=#{toll_collector_id}")
    List<Float> getClassifitationFee(String start_date,String end_date,int expense_classification_id,int toll_collector_id);

    //@Update("UPDATE daily_collect SET check = true "+
    //        "FROM daily_detail,bill_record "+
    //        "WHERE CAST(start_time AS DATETIME) > CAST(#{start_date} AS DATETIME) and CAST(end_time AS DATETIME) < CAST(#{end_date} AS DATETIME) and daily_collect.user_id=#{checker_id} "+
    //        "and daily_collect.id=daily_detail.daily_collect_id and daily_detail.bill_record_id=bill_record.id and bill_record.user_id=#{toll_collector_id}")

    /**
     * 根据起止日期、收费员id、核对人员id将日结记录中的checked属性更新为true
     * @param start_date 起始日期
     * @param end_date 截止日期
     * @param toll_collector_id 收费员id
     * @param checker_id 核对人员id
     */
    @Update("UPDATE daily_collect dc " +
            "JOIN daily_detail dd " +
            "ON dc.id=dd.daily_collect_id and dc.user_id=#{checker_id} and CAST(dc.start_time AS DATETIME) > CAST(#{start_date} AS DATETIME) and CAST(dc.end_time AS DATETIME) < CAST(#{end_date} AS DATETIME)" +
            "JOIN bill_record br " +
            "ON dd.bill_record_id=br.id and br.user_id=#{toll_collector_id} " +
            "SET dc.checked = true ")
    void confirmCheck(String start_date,String end_date,int toll_collector_id,int checker_id);

    /**
     *根据起止日期、收费员id、核对人员id查找对应日结记录数量
     * @param start_date 起始日期
     * @param end_date 截止日期
     * @param toll_collector_id 收费员id
     * @param checker_id 核对人员id
     * @return 返回日结记录数量
     */
    @Select("SELECT count(*) "+
            "FROM daily_collect, daily_detail, bill_record "+
            "WHERE CAST(start_time AS DATETIME) > CAST(#{start_date} AS DATETIME) and CAST(end_time AS DATETIME) < CAST(#{end_date} AS DATETIME) and daily_collect.user_id=#{checker_id} " +
            "and daily_collect.id=daily_detail.daily_collect_id and daily_detail.bill_record_id=bill_record.id and bill_record.user_id=#{toll_collector_id}")
    int checkIdExistNums(String start_date,String end_date,int toll_collector_id,int checker_id);

    /**
     *根据起止日期查找对应票据记录
     * @param start_date 起始日期
     * @param end_date 截止日期
     * @return 返回票据记录
     */
    @Select("SELECT bill_record.id, medical_record_id, type, print_status, cost, should_pay, truely_pay,retail_fee, bill_record.user_id, bill_record.create_time "+
            "FROM daily_collect, daily_detail, bill_record "+
            "WHERE CAST(start_time AS DATETIME) > CAST(#{start_date} AS DATETIME) and CAST(end_time AS DATETIME) < CAST(#{end_date} AS DATETIME) " +
            "and daily_collect.id=daily_detail.daily_collect_id and daily_detail.bill_record_id=bill_record.id")
    List<BillRecord> history(String start_date, String end_date);

    /**
     *根据起止日期查找科室名称
     * @param start_date 起始日期
     * @param end_date 截止日期
     * @return 返回对应科室名称列表
     */
    @Select("SELECT DISTINCT expense_classification.fee_name " +
            "FROM daily_collect, daily_detail, bill_record, user_info, outpatient_charges_record , expense_classification  " +
            "WHERE CAST(start_time AS DATETIME) > CAST(#{start_date} AS DATETIME) and CAST(end_time AS DATETIME) < CAST(#{end_date} AS DATETIME) " +
            "and daily_collect.id=daily_detail.daily_collect_id and daily_detail.bill_record_id=bill_record.id " +
            "and bill_record.medical_record_id=outpatient_charges_record.medical_record_id and outpatient_charges_record.expense_classification_id=expense_classification.id")
    List<String> getDepartmentColumns(String start_date, String end_date);

    /**
     *根据起止日期查找各科室票据数量
     * @param start_date 起始日期
     * @param end_date 截止日期
     * @return 返回各科室票据数量列表
     */
    @Select("SELECT department.name, count(bill_record.medical_record_id) as person_time, count(bill_record.id) as bill_num "+
            "FROM daily_collect, daily_detail,bill_record,user_info,department "+
            "WHERE CAST(start_time AS DATETIME) > CAST(#{start_date} AS DATETIME) and CAST(end_time AS DATETIME) < CAST(#{end_date} AS DATETIME) "+
            " and daily_collect.id=daily_detail.daily_collect_id "+
            "and daily_detail.bill_record_id=bill_record.id and "+
            "bill_record.user_id=user_info.uid and user_info.department_id=department.id "+
            "GROUP BY department.id")
    List<ObjectCount> getDepartmentCount(String start_date,String end_date);

    /**
     *根据起止日期查找各科室票据金额
     * @param start_date 起始日期
     * @param end_date 截止日期
     * @return 返回各科室票据金额
     */
    @Select("SELECT department.name, expense_classification.fee_name, sum(bill_record.cost) as sum " +
            "FROM daily_collect, daily_detail, bill_record, user_info, department, outpatient_charges_record , expense_classification  " +
            "WHERE CAST(start_time AS DATETIME) > CAST(#{start_date} AS DATETIME) and CAST(end_time AS DATETIME) < CAST(#{end_date} AS DATETIME) "+
            "and daily_collect.id=daily_detail.daily_collect_id and daily_detail.bill_record_id=bill_record.id and bill_record.user_id=user_info.uid "+
            "and user_info.department_id=department.id and daily_detail.bill_record_id=outpatient_charges_record.bill_record_id  "+
            "and outpatient_charges_record.expense_classification_id=expense_classification.id " +
            "GROUP BY department.id, expense_classification.fee_name")
    List<ObjectSum> getDepartmentSum(String start_date,String end_date);



    //@Select("SELECT DISTINCT real_name " +
    //        "FFROM daily_collect, daily_detail, bill_record, user_info  " +
    //        "WHERE CAST(start_time AS DATETIME) > CAST(#{start_date} AS DATETIME) and CAST(end_time AS DATETIME) < CAST(#{end_date} AS DATETIME) " +
    //        "and daily_collect.id=daily_detail.daily_collect_id and daily_detail.bill_record_id=bill_record.id and bill_record.user_id=user_info.uid")

    /**
     *根据起止日期查找医生名称
     * @param start_date 起始日期
     * @param end_date 截止日期
     * @return 返回对应医生名称列表
     */
    @Select("SELECT DISTINCT expense_classification.fee_name " +
            "FROM daily_collect, daily_detail, bill_record, user_info, outpatient_charges_record , expense_classification  " +
            "WHERE CAST(start_time AS DATETIME) > CAST(#{start_date} AS DATETIME) and CAST(end_time AS DATETIME) < CAST(#{end_date} AS DATETIME) " +
            "and daily_collect.id=daily_detail.daily_collect_id and daily_detail.bill_record_id=bill_record.id " +
            "and bill_record.medical_record_id=outpatient_charges_record.medical_record_id and outpatient_charges_record.expense_classification_id=expense_classification.id")
    List<String> getUserColumns(String start_date, String end_date);

    /**
     *根据起止日期查找各医生票据数量
     * @param start_date 起始日期
     * @param end_date 截止日期
     * @return 返回各医生票据数量列表
     */
    @Select("SELECT user_info.real_name as name, count(bill_record.medical_record_id) as person_time, count(bill_record.id) as bill_num "+
            "FROM daily_collect, daily_detail,bill_record,user_info "+
            "WHERE CAST(start_time AS DATETIME) > CAST(#{start_date} AS DATETIME) and CAST(end_time AS DATETIME) < CAST(#{end_date} AS DATETIME) and daily_collect.id=daily_detail.daily_collect_id "+
            "and daily_detail.bill_record_id=bill_record.id and bill_record.user_id=user_info.uid "+
            "GROUP BY user_info.uid")
    List<ObjectCount> getUserCount(String start_date,String end_date);

    /**
     *根据起止日期查找各医生票据金额
     * @param start_date 起始日期
     * @param end_date 截止日期
     * @return 返回各医生票据金额
     */
    @Select("SELECT user_info.real_name as name, expense_classification.fee_name, sum(bill_record.cost) as sum " +
            "FROM daily_collect, daily_detail, bill_record, user_info, outpatient_charges_record , expense_classification  " +
            "WHERE CAST(start_time AS DATETIME) > CAST(#{start_date} AS DATETIME) and CAST(end_time AS DATETIME) < CAST(#{end_date} AS DATETIME) "+
            "and daily_collect.id=daily_detail.daily_collect_id and daily_detail.bill_record_id=bill_record.id and bill_record.user_id=user_info.uid "+
            "and daily_detail.bill_record_id=outpatient_charges_record.bill_record_id  "+
            "and outpatient_charges_record.expense_classification_id=expense_classification.id " +
            "GROUP BY user_info.uid, expense_classification.fee_name")
    List<ObjectSum> getUserSum(String start_date,String end_date);
}
