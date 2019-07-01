package edu.neu.his.bean.registration;

import edu.neu.his.bean.registration.Registration;
import edu.neu.his.bean.user.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 该类对数据库中的registration表进行数据持久化操作
 */
@Mapper
@Component(value = "OutpatientRegistrationMapper")
public interface OutpatientRegistrationMapper {
    /**
     * 根据科室id、挂号等级id和当前日期查找对应的用户
     * @param department_id 科室id
     * @param registration_level_id 挂号等级id
     * @param curr_date 当前日期
     * @return 根据科室id、挂号等级id和当前日期查找的对应用户列表
     */
    @Select("SELECT DISTINCT user_info.uid, username, password, real_name, department_id,department.name as department_name,role_id, role.name as role_name, title, participate_in_scheduling " +
            "FROM user,user_info,role,department, doctor_scheduling " +
            "WHERE user.id = user_info.uid and department.id = user_info.department_id and user_info.role_id=role.id " +
            "and user_info.department_id = #{department_id} and doctor_scheduling.registration_level_id = #{registration_level_id} and doctor_scheduling.uid = user.id and schedule_date=#{curr_date}")
    List<User> findByDepartmentAndTitle(@Param("department_id") int department_id, @Param("registration_level_id") int registration_level_id, @Param("curr_date") String curr_date);

    /**
     * 向数据库的registration表中插入一条记录
     * @param registration 要在数据库中插入的Registration对象
     */
    @Insert("INSERT INTO registration (address,age,birthday,consultation_date,medical_category,patient_name," +
            "outpatient_doctor_id,registration_department_id,settlement_category_id,registration_source,gender," +
            "medical_insurance_diagnosis,id_number,medical_certificate_number, status, cost, registration_category)" +
            " VALUES(#{address},#{age}, #{birthday}, #{consultation_date}, #{medical_category}, " +
            "#{patient_name}, #{outpatient_doctor_id}, #{registration_department_id}, #{settlement_category_id}, " +
            "#{registration_source}, #{gender}, #{medical_insurance_diagnosis}, #{id_number}, " +
            "#{medical_certificate_number}, #{status}, #{cost}, #{registration_category})")
    @Options(useGeneratedKeys = true, keyProperty = "medical_record_id")
    void insert(Registration registration);

    /**
     * 根据病历号查找对应的挂号信息
     * @param medical_record_id 病历号
     * @return 根据病历号查找的对应挂号信息
     */
    @Select("SELECT * FROM registration WHERE registration.medical_record_id = #{medical_record_id}")
    Registration findRegistrationById(@Param("medical_record_id") int medical_record_id);

    /**
     * 根据id更新数据库的registration表中相应的记录
     * @param registration 要在数据库中更新的Registration对象
     */
    @Update("UPDATE registration SET status = #{status} WHERE medical_record_id = #{medical_record_id}")
    void update(Registration registration);

    /**
     * 根据医疗证号查找对应的挂号信息
     * @param medical_certificate_number 医疗证号
     * @return 根据医疗证号查找的对应挂号信息
     */
    @Select("SELECT * FROM registration WHERE medical_certificate_number = #{medical_certificate_number}")
    List<Registration> findRegistrationByMedicalCertificateNumber(@Param("medical_certificate_number") String medical_certificate_number);

    /**
     * 根据医生id查找对应的挂号信息
     * @param outpatient_doctor_id 医生id
     * @return 根据医生id查找的对应挂号信息
     */
    @Select("SELECT * FROM registration WHERE outpatient_doctor_id = #{outpatient_doctor_id}")
    List<Registration> findRegistrationByDoctor(@Param("outpatient_doctor_id") int outpatient_doctor_id);

    /**
     * 根据身份证号查找对应的挂号信息
     * @param id_number 身份证号
     * @return 根据身份证号查找的对应挂号信息
     */
    @Select("SELECT * FROM registration WHERE id_number = #{id_number}")
    List<Registration> findRegistrationByIdNumber(@Param("id_number") String id_number);

    /**
     * 根据医疗证号和状态查找对应的挂号信息
     * @param medical_certificate_number 医疗证号
     * @param status 状态
     * @return 根据医疗证号和状态查找的对应挂号信息
     */
    @Select("SELECT * FROM registration WHERE medical_certificate_number = #{medical_certificate_number} and status = #{status}")
    List<Registration> findRegistrationByMedicalCertificateNumberAndStatus(@Param("medical_certificate_number") String medical_certificate_number,
                                                                           @Param("status") String status);

    /**
     * 根据身份证号和状态查找对应的挂号信息
     * @param id_number 身份证号
     * @param status 状态
     * @return 根据身份证号和状态查找的对应挂号信息
     */
    @Select("SELECT * FROM registration WHERE id_number = #{id_number} and status = #{status}")
    List<Registration> findRegistrationByIdNumberAndStatus(@Param("id_number") String id_number, @Param("status") String status);

    /**
     * 根据患者姓名查找对应的病历号
     * @param patient_name 患者姓名
     * @return 根据患者姓名查找的对应病历号
     */
    @Select("SELECT medical_record_id FROM registration WHERE patient_name = #{patient_name}")
    List<Integer> findMedicalRecordIdByName(@Param("patient_name") String patient_name);

    /**
     * 根据患者姓名模糊搜索对应的病历号
     * @param patient_name 患者姓名
     * @return 根据患者姓名模糊搜索的对应病历号
     */
    @Select("SELECT * FROM registration WHERE patient_name LIKE #{patient_name}")
    List<Registration> findMedicalRecordLikeName(@Param("patient_name") String patient_name);
}
