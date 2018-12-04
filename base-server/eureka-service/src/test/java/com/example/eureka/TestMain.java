package com.example.eureka;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        String row1Value = "";
        String row2Value = "";
        HSSFWorkbook wb = new HSSFWorkbook();
        // 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet("sheet1");
        sheet.setColumnWidth(2, 9000);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 11)); //
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 11)); //
//        String[] row3 = "日期,入库单,送货单,名称,规格型号,单位,数量,单价,件数,金额,备注".split(",");
        String[] row3 = "2,3,4,7,99,单位,数量,单价,件数,金额,备注".split(",");
        int rowCount = 0;
        HSSFRow row = sheet.createRow(rowCount++);
        HSSFCell cell = row.createCell(0);
        row.setHeightInPoints((short) 30);
        cell.setCellValue(row1Value);
        cell.setCellStyle(getFirstRowStyle(wb));
        row = sheet.createRow(rowCount++);
        cell = row.createCell(0);
        cell.setCellValue(row2Value);
        cell.setCellStyle(getFirstRowStyle(wb));
        row = sheet.createRow(rowCount++);
        for (int i = 0; i < row3.length; i++) {
            HSSFCell cell1 = row.createCell(i);
            cell1.setCellValue(row3[i]);
            cell1.setCellStyle(getBaseRowStyle(wb));
        }
        List<List<Object>> data = getData();
        for (int i = 0; i < data.size(); i++) {
            row = sheet.createRow(rowCount++);
            for (int j = 0; j < data.get(i).size(); j++) {
                Object o = data.get(i).get(j);
                HSSFCell cell1 = row.createCell(j);
                if (o instanceof String) {
                    cell1.setCellValue((String) o);
                } else {
                    cell1.setCellValue(Double.valueOf(o + ""));
                }
                cell1.setCellStyle(getBaseRowStyle(wb));
            }
        }
        int rows = rowCount++;
        sheet.addMergedRegion(new CellRangeAddress(rows, rows, 0, 11)); //
        rows = rowCount++;
        sheet.addMergedRegion(new CellRangeAddress(rows, rows, 0, 11)); //
        wb.write(new File("d:/a.xlsx"));
    }

    public List<List<Object>> getData() {
        List<List<Object>> result = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            List<Object> row = new ArrayList<>();
            for (int j = 0; j < 12; j++) {
                if (j % 2 == 0) {
                    row.add(j);
                } else {
                    row.add(j + "");
                }
            }
            result.add(row);
        }
        return result;
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

    /**
     * 基础的样式
     */
    private HSSFCellStyle getBaseRowStyle(HSSFWorkbook wb) {
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
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
