package com.lxw.mytool.controller;

import com.lxw.mytool.core.bean.BaseController;
import com.lxw.mytool.core.bean.Response;
import com.lxw.mytool.entity.Connect;
import com.lxw.mytool.service.ConnectService;
import org.apache.poi.ss.format.CellFormatType;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.Writer;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/connect")
public class ConnectController extends BaseController {

    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    ConnectService connectService;

    @RequestMapping("/findByPage")
    public Response findByPage(HttpServletRequest request){
        Map<String, String> params = this.getParam(request);
        Integer page = 0;
        Integer limit = 10;
        if(params.containsKey("page")){
            page = Integer.valueOf(params.get("page"));
            params.remove("page");
        }
        if(params.containsKey("limit")){
            limit = Integer.valueOf(params.get("limit"));
            params.remove("limit");
        }
        Map<String, Object> map = connectService.findByPage(params,page, limit);
        return new Response().success(map);
    }
    @RequestMapping("/exportExcel")
    public Response exportExcel(HttpServletResponse response,
                                @RequestParam(value="excelTitle", defaultValue="导出数据列表") String excelTitle,
                                @RequestParam(value="excelName", defaultValue="export_data_") String excelName,
                                @RequestParam(value="excel2007", defaultValue="true") Boolean excel2007){
        System.out.println("excelTitle:"+excelTitle);
        System.out.println("excelName:"+excelName);
        System.out.println("excel2007:"+excel2007);
        connectService.exportExcel(response,"连接.xls");
        return new Response().success();
    }
    @RequestMapping("/importExcel")
    public void importExcel(HttpServletResponse response,@RequestParam("excelFile") MultipartFile file) throws Exception{
        InputStream in = file.getInputStream();
        Workbook wb = WorkbookFactory.create(in);
        Sheet sheet = wb.getSheetAt(0);
        int lastRowNum = sheet.getLastRowNum();
        System.out.println("lastRowNum:"+lastRowNum);
        for (int r = 0; r <= lastRowNum; r++) {
            Row row = sheet.getRow(r);
            short lastCellNum = row.getLastCellNum();
            System.out.println("lastCellNum:"+lastCellNum);
            for (int c = 0; c < lastCellNum; c++) {
                Cell cell = row.getCell(c);
                cell.setCellType(Cell.CELL_TYPE_STRING);
                String cellValue = cell.getStringCellValue();
                System.out.println(cellValue);
            }
        }
        ajaxJson(response,true,"上传成功");

    }
    protected String ajaxJson(HttpServletResponse response,boolean result, String message) {
        String strs = "{\"success\":" + result + ",\"message\":\"" + message + "\"}";
        jsonWriter(response,strs);
        return strs;
    }
    protected void jsonWriter(HttpServletResponse response,String str) {
        Writer writer = null;
        try {
            try {
                response.setCharacterEncoding("UTF-8");
                writer = response.getWriter();
                writer.write(str);
            } finally {
                writer.flush(); // 强制输出所有内容
                writer.close(); // 关闭
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @RequestMapping("/add")
    public Response add(Connect connect){
        connectService.add(connect);
        return new Response().success("新增成功");
    }
    @RequestMapping("/update")
    public Response update(Connect connect){
        connectService.updateByPrimaryKey(connect);
        return new Response().success("修改成功");
    }
    @RequestMapping("/delete")
    public Response delete(@RequestBody List<Integer> sids){
        for (Integer sid : sids) {
            connectService.deleteByPrimaryKey(sid);
        }
        return new Response().success("删除成功");
    }
}
