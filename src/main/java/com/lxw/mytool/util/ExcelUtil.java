package com.lxw.mytool.util;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ExcelUtil {
    public static List<List<String>> excel2List(File file, int sheetIndex,boolean showEmptyRow,boolean showEmptyCell) throws Exception {
//        解析结果,二维数组
        List<List<String>> listRows = new ArrayList<>();

//        判断是否是Excel文件
        String fileName = file.getName();
        if (!fileName.matches("^.+\\.(?i)(xls)$") && !fileName.matches("^.+\\.(?i)(xlsx)$")) {
            throw new RuntimeException("上传文件格式不正确");
        }
//        判断Excel的版本号
        boolean isExcel2003 = true;
        if (fileName.matches("^.+\\.(?i)(xlsx)$")) {
            isExcel2003 = false;
        }
        InputStream is = new FileInputStream(file);
//        根据版本号获取工作簿
        Workbook wb = null;
        if (isExcel2003) {
            wb = new HSSFWorkbook(is);
        } else {
            wb = new XSSFWorkbook(is);
        }
//        获取指定的sheet页
        Sheet sheet = wb.getSheetAt(sheetIndex);
//      注意:要用<=getLastRowNum
        for (int r = 0; r <= sheet.getLastRowNum(); r++) {
            Row row = sheet.getRow(r);
            List<String> listRow = new ArrayList<>();
//       遇到空行忽略下面代码继续执行,否则row.getLastCellNum()报空指针
            if (row == null) {
                if(showEmptyRow){
                    listRows.add(listRow);
                }
            }else{
//                注意:c < row.getLastCellNum()否则生成的List最后会多一个空元素
                for (int c = 0; c < row.getLastCellNum(); c++) {
                    Cell cell = row.getCell(c);
                    if (cell != null) {
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        String cellValue = cell.getStringCellValue();
                        listRow.add(cellValue);
                    }else {
                        if(showEmptyCell){
                            listRow.add("");
                        }
                    }
                }
                listRows.add(listRow);
            }
        }
        return listRows;
    }

    /**
     * 导出Excel
     * @param sheetName sheet名称
     * @return
     */
    public static HSSFWorkbook getHSSFWorkbook(String sheetName, List<Map<String,Object>> data){

        // 第一步，创建一个HSSFWorkbook，对应一个Excel文件
        HSSFWorkbook wb = new HSSFWorkbook();

        // 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet(sheetName);

        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
        HSSFRow row = sheet.createRow(0);

        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
        style.setWrapText(true);

        //声明列对象
        HSSFCell cell = null;
        if(data.size()>0){
            Set<String> titles = data.get(0).keySet();
            int titleIndex = 0;
            //创建标题
            for (String title : titles) {
                cell = row.createCell(titleIndex);
                cell.setCellValue(title);
                cell.setCellStyle(style);
                titleIndex++;
            }
            //创建内容
            for(int i=0;i<data.size();i++){
                row = sheet.createRow(i + 1);
                int cellIndex =0;
                for (String title : titles) {
                    //将内容按顺序赋给对应的列对象
                    Object o = data.get(i).get(title);
                    String s = "";
                    if(o!=null){
                        s = o.toString();
                    }
                    row.createCell(cellIndex).setCellValue(s);
                    cellIndex++;
                }
            }
        }
        return wb;
    }
}
