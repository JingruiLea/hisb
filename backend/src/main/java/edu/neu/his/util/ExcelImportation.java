package edu.neu.his.util;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

public class ExcelImportation<T> {

    private Logger logger = LoggerFactory.getLogger(ExcelImportation.class);

    private Map<String,Integer> indexMap = new HashMap<>();
    private Map<String, Function<String, ?>> preFunctionMap = new HashMap<>();
    private InputStream inputStream;
    private Class<T> entityClass;
    private Importable<T> importable;
    private boolean hasHeader = true;

    public ExcelImportation(InputStream inputStream, Class<T> entityClass, Importable<T> importable){
        this.inputStream = inputStream;
        this.entityClass = entityClass;
        this.importable = importable;
    }

    public Map<String, Integer> getIndexMap() {
        return indexMap;
    }

    public void setIndexMap(Map<String, Integer> indexMap) {
        this.indexMap = indexMap;
    }

    public Map<String, Function<String, ?>> getPreFunctionMap() {
        return preFunctionMap;
    }

    public void setPreFunctionMap(Map<String, Function<String, ?>> preFunctionMap) {
        this.preFunctionMap = preFunctionMap;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public Function<String, ?> addPreFunction(String field, Function<String, ?> function){
         return this.preFunctionMap.put(field, function);
    }

    public void setIndex(String... fields){
        for (int i = 0; i < fields.length; i++) {
            this.indexMap.put(fields[i], i);
        }
    }

    public void exec(){
        Workbook wb = null;
        try {
            wb = new XSSFWorkbook(inputStream);
            insertEachRow(wb, hasHeader);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private <T> T parseRow(Row row, Class<T> entityClass,Map<String, Integer> indexMap, Map<String, Function<String, ?>> functionMap) throws IllegalAccessException, InstantiationException {
        Field[] fields = entityClass.getDeclaredFields();
        T instance = entityClass.newInstance();
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
                        logger.debug("Constructed an Object : {}",result.toString());
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

    private <T> void setFieldValue(T instance, Field field, Cell cell) throws IllegalAccessException {
        Class cls = field.getType();
        boolean isStringType = false;
        String strValue = null;
        if (!field.isAccessible())
            field.setAccessible(true);
        if(cell.getCellType() == CellType.STRING){
            logger.warn("Here is a type cast!");
            isStringType = true;
            strValue = cell.getStringCellValue();
        }
        if(cls.isPrimitive()){
            //cell.setCellType(CellType.STRING);
            switch (cls.getName()) {
                case "int":
                    if (isStringType) {
                        field.set(instance, Integer.parseInt(strValue));
                    } else {
                        field.set(instance, (int) cell.getNumericCellValue());
                    }
                    break;
                case "double":
                    if (isStringType) {
                        field.set(instance, Double.parseDouble(strValue));
                    } else {
                        field.set(instance, (double) cell.getNumericCellValue());
                    }
                    break;
                case "float":
                    if (isStringType) {
                        field.set(instance, Float.parseFloat(strValue));
                    } else {
                        field.set(instance, (float) cell.getNumericCellValue());
                    }
                    break;
                case "boolean":
                    field.set(instance, cell.getBooleanCellValue());
                    break;
            }
        } else {
            if(isStringType){
                field.set(instance, strValue);
            }else{
                cell.setCellType(CellType.STRING);
                field.set(instance, cell.getStringCellValue());
            }
        }
    }

    private void insertEachRow(Workbook wb, boolean header) throws InstantiationException, IllegalAccessException {
        Iterator<Sheet> sheetIterator = wb.sheetIterator();
        while (sheetIterator.hasNext()) {
            Sheet sheet = sheetIterator.next();
            int rowNums = sheet.getLastRowNum();
            int colNums = sheet.getPhysicalNumberOfRows();
            for(int i = header? 1 : 0; i <= rowNums; i++) {
                Row row = sheet.getRow(i);
                T instance = parseRow(row, entityClass,indexMap, preFunctionMap);
                importable.insert(instance);
            }
        }
    }

    public Class<T> getTClass()
    {
        Class<T> tClass = (Class<T>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        return tClass;
    }

    public boolean isHasHeader() {
        return hasHeader;
    }

    public void setHasHeader(boolean hasHeader) {
        this.hasHeader = hasHeader;
    }
}
