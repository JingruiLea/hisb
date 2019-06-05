package edu.neu.his.service;

import edu.neu.his.bean.Department;
import edu.neu.his.bean.DepartmentClassification;
import edu.neu.his.mapper.DepartmentMapper;
import edu.neu.his.util.ExcelImportation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileInputStream;
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
    public boolean canUpdate(Department department) {
        int id_num = departmentMapper.checkIdExists(department.getId());
        int name_num = departmentMapper.checkNameExists(department.getName());
        if(id_num==0 || name_num>1 || id_num>1)
            return false;
        else if(name_num==1){
            Department d = departmentMapper.findByName(department.getName());
            if(d.getId() != department.getId())
                return false;
            else
                return true;
        }else
            return true;
    }

    @Transactional
    public boolean canInsert(Department department) {
        int id_num = departmentMapper.checkIdExists(department.getId());
        int name_num = departmentMapper.checkNameExists(department.getName());
        if(id_num>=1 || name_num>=1)
            return false;
        else
            return true;
    }

    public boolean importFromFile(String pathName) {
        try {
            ExcelImportation excel = new ExcelImportation(new FileInputStream(pathName), Department.class, departmentMapper);
            excel.setColumnFields("id", "classification_id", "pinyin", "name", "type");
            excel.skipLine(1);
            Map<String, Function<String, ?>> preFunctionMap = excel.getPreFunctionMap();
            preFunctionMap.put("classification_id", departmentMapper::findClassificationIdByName);
            excel.exec();
            return true;
        } catch (Exception e)  {
            return false;
        }
    }

    @Transactional
    public boolean existClassification(Department department) {
        return departmentMapper.checkClassificationExists(department.getClassification_id())==1;
    }
}
