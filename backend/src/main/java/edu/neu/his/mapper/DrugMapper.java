package edu.neu.his.mapper;

import edu.neu.his.bean.Drug;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DrugMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Drug record);

    Drug selectByPrimaryKey(Integer id);

    List<Drug> selectAll();

    int updateByPrimaryKey(Drug record);
}