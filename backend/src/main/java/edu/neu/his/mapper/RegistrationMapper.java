package edu.neu.his.mapper;

import edu.neu.his.bean.Registration;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RegistrationMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Registration record);

    Registration selectByPrimaryKey(Integer id);

    List<Registration> selectAll();

    int updateByPrimaryKey(Registration record);
}