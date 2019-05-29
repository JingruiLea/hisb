package edu.neu.his.utils;

import edu.neu.his.bean.Department;
import edu.neu.his.bean.User;
import edu.neu.his.mapper.DepartmentMapper;
import edu.neu.his.util.ExcelImportation;
import edu.neu.his.util.ExcelImportUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.function.Function;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ExcelImportationImportUtilsTest {
    @Test
    public void test(){
        ExcelImportUtils ExcelImportUtils = new ExcelImportUtils();
    }

    @Test
    public void reflectTest() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        User user = new User();
        user.setUsername("hello");
        Field[] fields = User.class.getDeclaredFields();
        for(Field field : fields){
            if(field.getName().equals("department_id")){
//                String name = "department_id";
//                name = name.substring(0, 1).toUpperCase() + name.substring(1);
                Class cls = field.getType();
                if(cls.isPrimitive()){
//                    Method method = User.class.getMethod("set" + name, cls);
//                    method.invoke(user, cls.cast(22.0));
                     String cellValue = "hello";
                     field.setAccessible(true);
                     switch (cls.getName()){
                         case "int":
                             field.set(user, (int)22.0);
                             break;
                         case "double":
                             field.set(user, (double)22.0);
                             break;
                         case "float":
                             field.set(user, (float)22.0);
                             break;
                         case "boolean":
                             field.set(user, Boolean.parseBoolean(cellValue));
                             break;
                         case "java.lang.String":
                             field.set(user, cellValue);
                             break;
                     }
                }
            }
        }
        System.out.println(user);
    }

    @Test
    public void setFieldValueTest() throws NoSuchFieldException, IllegalAccessException {
        User user = new User();
        user.setUsername("hello");
        Function<String,?> function = (Function<String, Object>) s -> 3;
        Object result = function.apply("abcd");
        Field field = User.class.getDeclaredField("role_name");
        field.setAccessible(true);
        System.out.println(field.getType().getName());
        field.set(user, result);
    }


    @Test
    public void departmentImportTest(){
        ExcelImportUtils ExcelImportUtils = new ExcelImportUtils();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("医院科室大类对照表.xlsx");
        if(inputStream == null) {
            System.out.println("找不到文件");
            return;
        }
        ExcelImportUtils.departmentImport(inputStream, departmentMapper);
    }

    @Test
    @Rollback
    public void excelImportTest(){
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("医院科室大类对照表.xlsx");
        ExcelImportUtils.importExcel(inputStream, Department.class, departmentMapper, "");
    }



    @Autowired
    DepartmentMapper departmentMapper;

    @Test
    @Rollback
    public void excelTest(){
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("医院科室大类对照表.xlsx");
        ExcelImportation excel = new ExcelImportation(inputStream, Department.class, departmentMapper);
        excel.setIndex("id", "classification_id", "pinyin", "name", "type");
        Map<String, Function<String, ?>> preFunctionMap = excel.getPreFunctionMap();
        preFunctionMap.put("classification_id", departmentMapper::findClassificationIdByName);
        excel.exec();
    }
}
