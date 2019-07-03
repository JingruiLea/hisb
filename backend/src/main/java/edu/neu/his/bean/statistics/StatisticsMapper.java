package edu.neu.his.bean.statistics;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Mapper
@Component("StatisticsMapper")
public interface StatisticsMapper {
    @Select("SELECT expense_classification.fee_name,sum(cost) as money " +
            "FROM outpatient_charges_record, expense_classification  " +
            "WHERE  CAST(create_time AS DATETIME) > CAST(#{start_time} AS DATETIME) " +
            "and CAST(create_time AS DATETIME) < CAST(#{end_time} AS DATETIME) " +
            "and outpatient_charges_record.expense_classification_id=expense_classification.id " +
            "and execute_department_id = #{department_id} " +
            "and status = #{status} group by expense_classification.fee_name")
    List<Map<String,Object>> getExpenseClassifications(@Param("start_time") String start_time,
                                                      @Param("end_time") String end_time,
                                                      @Param("department_id") int department_id,
                                                      @Param("status") String status);

    @Select("SELECT expense_classification.fee_name,sum(cost) as money " +
            "FROM outpatient_charges_record, expense_classification, user_info " +
            "WHERE  CAST(create_time AS DATETIME) > CAST(#{start_time} AS DATETIME) " +
            "and CAST(create_time AS DATETIME) < CAST(#{end_time} AS DATETIME) " +
            "and user_info.uid = create_user_id " +
            "and outpatient_charges_record.expense_classification_id=expense_classification.id " +
            "and user_info.department_id = #{department_id} " +
            "and status = #{status} group by expense_classification.fee_name")
    List<Map<String,Object>> getByPrescribe(@Param("start_time") String start_time,
                                                       @Param("end_time") String end_time,
                                                       @Param("department_id") int department_id,
                                                       @Param("status") String status);

    @Select("SELECT execute_department_id,sum(cost) as total " +
            "FROM outpatient_charges_record " +
            "WHERE  CAST(create_time AS DATETIME) > CAST(#{start_time} AS DATETIME) " +
            "and CAST(create_time AS DATETIME) < CAST(#{end_time} AS DATETIME) " +
            "and status = #{status} group by execute_department_id")
    List<Map<Object,Object>> getTotal(@Param("start_time") String start_time,
                                      @Param("end_time") String end_time,
                                      @Param("status") String status);

    @Select("SELECT user_info.department_id,sum(cost) as total " +
            "FROM outpatient_charges_record,user_info " +
            "WHERE  CAST(create_time AS DATETIME) > CAST(#{start_time} AS DATETIME) " +
            "and CAST(create_time AS DATETIME) < CAST(#{end_time} AS DATETIME) " +
            "and user_info.uid = create_user_id " +
            "and status = #{status} group by user_info.department_id")
    List<Map<Object,Object>> getTotalPrescribe(@Param("start_time") String start_time,
                                      @Param("end_time") String end_time,
                                      @Param("status") String status);

    @Select("SELECT create_user_id,sum(cost) as total " +
            "FROM outpatient_charges_record " +
            "WHERE CAST(create_time AS DATETIME) > CAST(#{start_time} AS DATETIME) " +
            "and CAST(create_time AS DATETIME) < CAST(#{end_time} AS DATETIME) " +
            "and status = #{status} group by create_user_id")
    List<Map<Object,Object>> getTotalUser(@Param("start_time") String start_time,
                                          @Param("end_time") String end_time,
                                          @Param("status") String status);

    @Select("SELECT expense_classification.fee_name,sum(cost) as money " +
            "FROM outpatient_charges_record, expense_classification  " +
            "WHERE  CAST(create_time AS DATETIME) > CAST(#{start_time} AS DATETIME) " +
            "and CAST(create_time AS DATETIME) < CAST(#{end_time} AS DATETIME) " +
            "and outpatient_charges_record.expense_classification_id=expense_classification.id " +
            "and create_user_id = #{user_id} " +
            "and status = #{status} group by expense_classification.fee_name")
    List<Map<String,Object>> statisticsByUser(@Param("start_time") String start_time,
                                                       @Param("end_time") String end_time,
                                                       @Param("user_id") int user_id,
                                                       @Param("status") String status);

    @Select("SELECT medical_record_id,sum(cost) as total " +
            "FROM outpatient_charges_record " +
            "WHERE CAST(create_time AS DATETIME) > CAST(#{start_time} AS DATETIME) " +
            "and CAST(create_time AS DATETIME) < CAST(#{end_time} AS DATETIME) " +
            "and create_user_id = #{doctor_id} " +
            "and status = #{status} group by medical_record_id")
    List<Map<Object,Object>> getTotalPatient(@Param("start_time") String start_time,
                                             @Param("end_time") String end_time,
                                             @Param("status") String status,
                                             @Param("doctor_id") int doctor_id);

    @Select("SELECT expense_classification.fee_name,sum(cost) as money " +
            "FROM outpatient_charges_record, expense_classification  " +
            "WHERE  CAST(create_time AS DATETIME) > CAST(#{start_time} AS DATETIME) " +
            "and CAST(create_time AS DATETIME) < CAST(#{end_time} AS DATETIME) " +
            "and outpatient_charges_record.expense_classification_id=expense_classification.id " +
            "and medical_record_id = #{medical_record_id} " +
            "and status = #{status} group by expense_classification.fee_name")
    List<Map<String,Object>> statisticsByDoctor(@Param("start_time") String start_time,
                                               @Param("end_time") String end_time,
                                               @Param("medical_record_id") int medical_record_id,
                                               @Param("status") String status);

    @Select("SELECT medical_record_id,sum(non_drug_charge_item.fee) as total " +
            "FROM exam,exam_item,non_drug_charge_item " +
            "WHERE CAST(create_time AS DATETIME) > CAST(#{start_time} AS DATETIME) " +
            "and CAST(create_time AS DATETIME) < CAST(#{end_time} AS DATETIME) " +
            "and user_id = #{doctor_id} and exam.id=exam_item.exam_id " +
            "and exam_item.non_drug_item_id=non_drug_charge_item.id " +
            "and non_drug_charge_item.department_id = #{department_id} " +
            "and exam_item.status = #{status} group by medical_record_id")
    List<Map<Object,Object>> getTotalRegister(@Param("start_time") String start_time,
                                              @Param("end_time") String end_time,
                                              @Param("status") String status,
                                              @Param("doctor_id") int doctor_id,
                                              @Param("department_id") int department_id);

    @Select("SELECT expense_classification.fee_name,sum(cost) as money " +
            "FROM outpatient_charges_record, expense_classification, exam_item,exam  " +
            "WHERE  CAST(outpatient_charges_record.create_time AS DATETIME) > CAST(#{start_time} AS DATETIME) " +
            "and CAST(outpatient_charges_record.create_time AS DATETIME) < CAST(#{end_time} AS DATETIME) " +
            "and outpatient_charges_record.expense_classification_id=expense_classification.id " +
            "and outpatient_charges_record.medical_record_id = #{medical_record_id} " +
            "and outpatient_charges_record.item_id = exam_item.id " +
            "and exam_item.exam_id = exam.id and execute_department_id = #{department_id} " +
            "and exam.user_id = #{doctor_id} and outpatient_charges_record.type = #{type} " +
            "and outpatient_charges_record.status = #{status} group by expense_classification.fee_name")
    List<Map<String,Object>> statisticsByRegister(@Param("start_time") String start_time,
                                                  @Param("end_time") String end_time,
                                                  @Param("medical_record_id") int medical_record_id,
                                                  @Param("status") String status,
                                                  @Param("doctor_id") int doctor_id,
                                                  @Param("department_id") int department_id,
                                                  @Param("type") int type);
}
