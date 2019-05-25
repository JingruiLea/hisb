package edu.neu.his.service;

import edu.neu.his.bean.DepartmentClassification;
import edu.neu.his.mapper.DepartmentClassificationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DepartmentClassificationService {
    @Autowired
    private DepartmentClassificationMapper departmentClassificationMapper;

    @Transactional
    public int getIdByName(String name) {
        return departmentClassificationMapper.selectIdByName(name);
    }

    @Transactional
    public List<DepartmentClassification> getAllClassification() {
        return departmentClassificationMapper.selectAll();
    }

}
