package edu.neu.his.bean.dailyCheck;

import edu.neu.his.bean.user.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component("DailyCheckMapper")
public interface DailyCheckMapper {

    @Select("SELECT uid, username, password, real_name, department_id,department.name as department_name,role_id, role.name as role_name, title, participate_in_scheduling " +
            "FROM user,user_info,role,department " +
            "WHERE user.id = user_info.uid and department.id = user_info.department_id and user_info.role_id=role.id  and role_id=2")
    List<User> getTollCollector();
}
