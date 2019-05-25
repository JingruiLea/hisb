package edu.neu.his.service;

import edu.neu.his.bean.Department;
import edu.neu.his.mapper.DepartmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DepartmentService {
    @Autowired
    private DepartmentMapper departmentMapper;

    @Transactional
    public void updateDepartment(Department department) {
        departmentMapper.update(department);
    }

    @Transactional
    public Department findDepartmentByName(String name) {
        return departmentMapper.findByName(name);
    }

    @Transactional
    public void insertDepartment(Department department) {
        departmentMapper.insert(department);
    }

    @Transactional
    public List<Department> findAll() {
        return departmentMapper.findAll();
    }

    @Transactional
    public List<String> findAllNames() {return departmentMapper.findAllNames();}

    @Transactional
    public List<String> findAllClassification() {
        return departmentMapper.findAllClassification();
    }

    @Transactional
    public void deleteDepartment(int id) {
        departmentMapper.deleteDepartment(id);
    }

    @Transactional
    public int findClassificationIdByName(String name) {
        int id = 0;
        try {
             id = departmentMapper.findClassificationIdByName(name);
        } catch (Exception e) {
            id = -1;
        }
        return id;
    }
}
