package edu.neu.his.util;

import edu.neu.his.bean.Department;
import edu.neu.his.mapper.DepartmentMapper;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

public class ExcelImportUtils {
    public static <T> void importExcel(InputStream inputStream, Class<T> tClass, Importable<T> importable, String... fields){
        importExcel(inputStream, tClass, importable, true, fields);
    }

    public static <T> void importExcel(InputStream inputStream, Class<T> tClass, Importable<T> importable, boolean header,String... fields){
        if(inputStream == null) {
            System.out.println("找不到文件");
            return;
        }
        Map<String, Integer> indexMap = new HashMap<>();
        for(int i=0; i<fields.length; i++){
            if(fields[i] != null)
                indexMap.put(fields[i], i);
        }
        Workbook wb = null;
        try {
            wb = new XSSFWorkbook(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        rowIterate(wb, header, row -> {
            try {
                T instance = parseRow(row, tClass, indexMap);
                importable.insert(instance);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public static void departmentImport(InputStream inputStream, DepartmentMapper departmentMapper){
        Workbook wb = null;
        try {
           wb = new XSSFWorkbook(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(wb == null){
            return;
        }
        Map<String ,Function<String, ?>> functionMap = new HashMap<>();
        Map<String, Integer> indexMap = new HashMap<>();
        indexMap.put("id", 0);
        indexMap.put("classification_id", 1);
        indexMap.put("pinyin", 2);
        indexMap.put("name", 3);
        indexMap.put("type", 4);

        functionMap.put("classification_id", departmentMapper::findClassificationIdByName);

        rowIterate(wb, true, row -> {
            try {
                Department department = parseRow(row, Department.class, indexMap, functionMap);
                //System.out.println(department);
                departmentMapper.insert(department);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        });
    }

    private static <T> T parseRow(Row row, Class<T> t, Map<String, Integer> indexMap) throws IllegalAccessException, InstantiationException {
        Field[] fields = t.getDeclaredFields();
        T instance = t.newInstance();
        for (Field field : fields) {
            for (String key : indexMap.keySet()) {
                int cellIndex = indexMap.get(key);
                Cell cell = row.getCell(cellIndex);
                if (field.getName().equals(key)) {
                    setFieldValue(instance, field, cell);
                }
            }
        }
        return instance;
    }

    private static <T> T parseRow(Row row, Class<T> t, Map<String, Integer> indexMap, Map<String, Function<String, ?>> functionMap) throws IllegalAccessException, InstantiationException {
        Field[] fields = t.getDeclaredFields();
        T instance = t.newInstance();
                for (String key : indexMap.keySet()) {
                    for (Field field : fields) {
                        if (field.getName().equals(key)) {
                            int cellIndex = indexMap.get(key);
                            Cell cell = row.getCell(cellIndex);
                            if (functionMap.get(key) != null) {
                                String originInput = cell.getStringCellValue();
                                Function<String, ?> function = functionMap.get(key);
                                Object result = function.apply(originInput);
                                Class cls = field.getType();
                                if (!field.isAccessible())
                                    field.setAccessible(true);
                                field.set(instance, result);
                            }else{
                                setFieldValue(instance, field, cell);
                            }
                        }
            }
        }
        return instance;
    }

    private static <T> void setFieldValue(T instance, Field field, Cell cell) throws IllegalAccessException {
        Class cls = field.getType();
        if (!field.isAccessible())
            field.setAccessible(true);
        switch (cls.getName()) {
            case "int":
                field.set(instance, (int) cell.getNumericCellValue());
                break;
            case "double":
                field.set(instance, cell.getNumericCellValue());
                break;
            case "float":
                field.set(instance, (float) cell.getNumericCellValue());
                break;
            case "boolean":
                field.set(instance, cell.getBooleanCellValue());
                break;
            case "java.lang.String":
                field.set(instance, cell.getStringCellValue());
                break;
        }
    }

    private static void rowIterate(Workbook wb, boolean header, Consumer<Row> consumer){
        Iterator<Sheet> sheetIterator = wb.sheetIterator();
        while (sheetIterator.hasNext()) {
            Sheet sheet = sheetIterator.next();
            int rowNums = sheet.getLastRowNum();
            int colNums = sheet.getPhysicalNumberOfRows();
            System.out.println(rowNums+" "+colNums);
            for(int i = header? 1 : 0; i <= rowNums; i++) {
                Row row = sheet.getRow(i);
                consumer.accept(row);
            }
        }
    }
}
