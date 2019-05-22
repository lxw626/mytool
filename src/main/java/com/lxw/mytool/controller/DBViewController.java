package com.lxw.mytool.controller;

import com.lxw.mytool.service.DBViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("dbViewController")
public class DBViewController {
    @Autowired
    DBViewService dbViewService;

    @RequestMapping("getColumnNames")
    public Map<String, Object> getColumnNames(String sql) {
        Map<String, Object> result = dbViewService.getColumnNames(sql);
        return result;
    }

    @RequestMapping("addOneData")
    public Map<String, Object> addOneData(HttpServletRequest request) {
        Map<String, String> data = new HashMap<String, String>();
        Enumeration<String> parameterNames = request.getParameterNames();
        String tableName = "";
        while (parameterNames.hasMoreElements()) {
            String parameterName = (String) parameterNames.nextElement();
            String paramValue = request.getParameter(parameterName);
            if(parameterName.equals("tableName")){
                tableName = paramValue;
                System.out.println(tableName);
            }else{
                data.put(parameterName,paramValue);

            }
        }
        Map<String, Object> result = dbViewService.addOneData(data, tableName);
        return result;
    }
    @RequestMapping("updateOneData")
    public Map<String, Object> updateOneData(HttpServletRequest request) {
        Map<String, String> data = new HashMap<String, String>();
        Enumeration<String> parameterNames = request.getParameterNames();
        String tableName = "";
        String rowId = "";
        while (parameterNames.hasMoreElements()) {
            String parameterName = (String) parameterNames.nextElement();
            String paramValue = request.getParameter(parameterName);
            if(parameterName.equals("tableName")){
                tableName = paramValue;
            }else if(parameterName.equals("rowId")){
                rowId = paramValue;
            }else{
                data.put(parameterName,paramValue);

            }
        }
        Map<String, Object> result = dbViewService.updateOneData(data, tableName,rowId);
        return result;
    }
    @RequestMapping("deleteData")
    public Map<String, Object> deleteData(String tableName, String [] rowIds) {
        Map<String, Object> result = dbViewService.generateUpdateSql(tableName, rowIds);
        return result;
    }
}
