package edu.neu.his.utils;

import edu.neu.his.bean.Department;
import edu.neu.his.bean.DiseaseClassification;
import edu.neu.his.bean.NonDrugChargeItem;
import edu.neu.his.bean.User;
import edu.neu.his.mapper.DepartmentMapper;
import edu.neu.his.mapper.DiseaseMapper;
import edu.neu.his.mapper.NonDrugChargeItemMapper;
import edu.neu.his.util.ExcelImportation;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ExcelImportationImportUtilsTest {

    @Autowired
    DepartmentMapper departmentMapper;

    @Test
    @Rollback
    public void excelTest(){
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("医院科室大类对照表.xlsx");
        ExcelImportation excel = new ExcelImportation(inputStream, Department.class, departmentMapper);
        excel.setIndex("id", "classification_id", "pinyin", "name", "type");
        ((Map<String, Function<String, ?>>)excel.getPreFunctionMap()).put("classification_id", departmentMapper::findClassificationIdByName);
        excel.exec();
    }

    @Test
    public void departmentImport(){
        excelTest();
    }

    @Autowired
    NonDrugChargeItemMapper nonDrugChargeItemMapper;

    @Test
    public void nonDrugImport(){
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("non_drug_item.xlsx");
        ExcelImportation excel = new ExcelImportation(inputStream, NonDrugChargeItem.class, nonDrugChargeItemMapper);
        excel.setIndex(null, "id", "name", "format", "fee", "expense_classification_id", "department_id", "pinyin");
        //((Map<String, Function<String, ?>>)excel.getPreFunctionMap()).put("classification_id", departmentMapper::findClassificationIdByName);
        excel.exec();
    }

    @Autowired
    DiseaseMapper diseaseMapper;

    @Test
    public void diseaseClassificationImport(){
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("disease_classification.xlsx");
        ExcelImportation excel = new ExcelImportation(inputStream, DiseaseClassification.class, nonDrugChargeItemMapper);
        excel.setIndex(null, "id", "name", "format", "fee", "expense_classification_id", "department_id", "pinyin");
        //((Map<String, Function<String, ?>>)excel.getPreFunctionMap()).put("classification_id", departmentMapper::findClassificationIdByName);
        excel.exec();
    }

}
