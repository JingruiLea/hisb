package edu.neu.his.service;

import edu.neu.his.bean.Department;
import edu.neu.his.bean.DepartmentClassification;
import edu.neu.his.mapper.DepartmentMapper;
import edu.neu.his.util.ExcelImportation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

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
    public List<DepartmentClassification> findAllClassification() {
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

    @Transactional
    public boolean exist(Department department) {
        return departmentMapper.checkIdExists(department.getId())!=0;
    }

    public boolean importFromFile(String pathName) {
        try {
            ExcelImportation excel = new ExcelImportation(new FileInputStream(pathName), Department.class, departmentMapper);
            excel.setIndex("id", "classification_id", "pinyin", "name", "type");
            Map<String, Function<String, ?>> preFunctionMap = excel.getPreFunctionMap();
            preFunctionMap.put("classification_id", departmentMapper::findClassificationIdByName);
            excel.exec();
            return true;
        } catch (Exception e)  {
            return false;
        }
    }
}
