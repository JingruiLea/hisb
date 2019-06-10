package edu.neu.his.mapper;

import edu.neu.his.bean.Registration;
import edu.neu.his.bean.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component(value = "OutpatientRegistrationMapper")
public interface OutpatientRegistrationMapper {
    @Select("SELECT uid, username, password, real_name, department_id,department.name as department_name,role_id, role.name as role_name, title, participate_in_scheduling " +
            "FROM user,user_info,role,department " +
            "WHERE user.id = user_info.uid and department.id = user_info.department_id and user_info.role_id=role.id " +
            "and user_info.department_id = #{department_id} and user_info.title = #{title}")
    List<User> findByDepartmentAndTitle(@Param("department_id") int department_id, @Param("title") String title);

    @Insert("INSERT INTO registration (address,age,birthday,consultation_date,medical_category,patient_name," +
            "outpatient_doctor_id,registration_department_id,settlement_category_id,registration_source,gender," +
            "medical_insurance_diagnosis,id_number,medical_certificate_number, status, cost, registration_category)" +
            " VALUES(#{address},#{age}, #{birthday}, #{consultation_date}, #{medical_category}, " +
            "#{patient_name}, #{outpatient_doctor_id}, #{registration_department_id}, #{settlement_category_id}, " +
            "#{registration_source}, #{gender}, #{medical_insurance_diagnosis}, #{id_number}, " +
            "#{medical_certificate_number}, #{status}, #{cost}, #{registration_category})")
    @Options(useGeneratedKeys = true, keyProperty = "medical_record_id")
    void insert(Registration registration);

    @Select("SELECT * FROM registration WHERE registration.medical_record_id = #{medical_record_id}")
    Registration findRegistrationById(@Param("medical_record_id") int medical_record_id);

    @Update("UPDATE registration SET status = #{status} WHERE medical_record_id = #{medical_record_id}")
    void update(Registration registration);
}
