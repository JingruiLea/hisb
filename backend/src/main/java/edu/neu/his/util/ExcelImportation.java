package edu.neu.his.util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

public class ExcelImportation<T> {

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
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(wb == null){
            return;
        }
        rowIterate(wb, hasHeader, row -> {
            try {
                T instance = parseRow(row, entityClass,indexMap, preFunctionMap);
                importable.insert(instance);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
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

    private void rowIterate(Workbook wb, boolean header, Consumer<Row> consumer){
        Iterator<Sheet> sheetIterator = wb.sheetIterator();
        while (sheetIterator.hasNext()) {
            Sheet sheet = sheetIterator.next();
            int rowNums = sheet.getLastRowNum();
            int colNums = sheet.getPhysicalNumberOfRows();
            for(int i = header? 1 : 0; i <= rowNums; i++) {
                Row row = sheet.getRow(i);
                consumer.accept(row);
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
