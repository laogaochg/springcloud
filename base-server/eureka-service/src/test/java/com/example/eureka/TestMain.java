package com.example.eureka;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;

import com.example.eureka.controller.ExcelUtil;
import freemarker.template.utility.DateUtil;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.junit.Test;

/**
 * @Author: LaoGaoChuang
 * @Date : 2018/11/28 15:47
 */
public class TestMain {
    @Test
    public void testAA() throws IOException {
        HSSFWorkbook wb = new HSSFWorkbook();
        // 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet("sheetName");
        sheet.setColumnWidth(2,9000);
//        sheet.setDefaultRowHeightInPoints((short)30);
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 11)); //
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 11)); //
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell = row.createCell(0);
        row.setHeightInPoints((short) 30);
        cell.setCellValue("产品编码+套餐编码+实例编码");
        cell.setCellStyle(getFirstRowStyle(wb));
        wb.write(new File("d:/a.xlsx"));
    }

    /**
     * 第一行的样式
     */
    private HSSFCellStyle getFirstRowStyle(HSSFWorkbook wb) {
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        HSSFFont font = wb.createFont();
        font.setFontName("宋体");
        font.setBold(true);//粗体显示
        font.setFontHeightInPoints((short) 18);//设置字体大小
        style.setFont(font);
        return style;
    }

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
                if (cell == null) {
                    System.out.print("null" + "\t");
                    continue;
                }
                Object obj = ExcelUtil.getValue(cell);
                System.out.print(obj + "\t");
            }
        }
    }
}
