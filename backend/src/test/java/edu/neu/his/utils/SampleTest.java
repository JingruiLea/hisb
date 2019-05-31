package edu.neu.his.utils;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.function.Consumer;

public class SampleTest {
    @Test
    public void cellToStringTest() throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("non_drug_item.xlsx");
        Workbook wb = new XSSFWorkbook(inputStream);
        rowIterate(wb, true, r -> {
            System.out.println(r.getCell(1).toString());
        });
    }

    private void rowIterate(Workbook wb, boolean header, Consumer<Row> consumer) {
        Iterator<Sheet> sheetIterator = wb.sheetIterator();
        while (sheetIterator.hasNext()) {
            Sheet sheet = sheetIterator.next();
            int rowNums = sheet.getLastRowNum();
            int colNums = sheet.getPhysicalNumberOfRows();
            for (int i = header ? 1 : 0; i <= rowNums; i++) {
                Row row = sheet.getRow(i);
                consumer.accept(row);
            }
        }
    }

}
