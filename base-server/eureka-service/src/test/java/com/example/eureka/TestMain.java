package com.example.eureka;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import com.example.eureka.controller.ExcelUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;

/**
 * @Author: LaoGaoChuang
 * @Date : 2018/11/28 15:47
 */
public class TestMain {
    @Test
    public void testA() throws IOException {
        File a = new File("d:/a.xls");
        Workbook workbok = ExcelUtil.getWorkbok(new FileInputStream(a), a);
        Sheet sheet = workbok.getSheetAt(0);
        for (Row row : sheet) {
            //如果当前行没有数据，跳出循环
            int columnTotalNum = row.getPhysicalNumberOfCells();
            System.out.println("总列数：" + columnTotalNum);
            System.out.println("最大列数：" + row.getLastCellNum());
            int end = row.getLastCellNum();
            for (int i = 0; i < end; i++) {
                Cell cell = row.getCell(i);
                if(cell == null) {
                    System.out.print("null" + "\t");
                    continue;
                }
                Object obj = ExcelUtil.getValue(cell);
                System.out.print(obj + "\t");
            }
        }
    }
}
