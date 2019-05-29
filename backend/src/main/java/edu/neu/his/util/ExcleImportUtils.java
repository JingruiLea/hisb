package edu.neu.his.util;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.function.Consumer;
import java.util.function.Function;


public class ExcleImportUtils {

    public void test(){
        String filename = "C:\\Users\\LJR\\his\\backend\\src\\main\\resources\\excle-data\\医院科室大类对照表.xlsx";
        Workbook wb = null;
        try {
            wb = new XSSFWorkbook(new FileInputStream(filename));
            Iterator<Sheet> sheetIterator = wb.sheetIterator();
            while (sheetIterator.hasNext()) {
                Sheet sheet = sheetIterator.next();
                int rowNums = sheet.getLastRowNum();
                int colNums = sheet.getPhysicalNumberOfRows();
                System.out.println(rowNums+" "+colNums);
                for(int i = 1; i <= rowNums; i++) {
                    Row row = sheet.getRow(i);
                    for (int j = 0; j < 2; j++) {
                        Cell cell = row.getCell(j);
                        if(cell!=null)
                            System.out.println(cell.toString()+" ");
                    }
                    //System.out.print();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void departmentImport(String filename){
        Workbook wb = null;
        try {
           wb = new XSSFWorkbook(new FileInputStream(filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(wb == null){
            return;
        }
        rowIterate(wb, true, o -> {

        });
    }

    public void rowIterate(Workbook wb, boolean header, Consumer<Row> consumer){
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
