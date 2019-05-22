package com.lxw.mytool.service;

import com.lxw.mytool.util.ColumnInfo;
import com.lxw.mytool.util.DBUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DBViewService {
    @Autowired
    JdbcTemplate jdbcTemplate;
    public Map<String,Object> getColumnNames(String sql){
        sql = sql.toUpperCase();
        String tableName = sql.substring(sql.indexOf("FROM")+4).trim();
        if(sql.contains("WHERE")){
            tableName = sql.substring(sql.indexOf("FROM")+4,sql.indexOf("WHERE")).trim();
        }
        List<ColumnInfo> columnInfos = DBUtil.getColumnInfos(tableName);
        List<String> columnNames = new ArrayList<>();
        for(ColumnInfo columnInfo:columnInfos){
            columnNames.add(columnInfo.getColumnName());
        }
        Map<String,Object> result = new HashMap<>();
        result.put("columnNames",columnNames);
        result.put("tableName",tableName);
        return result;
    }
    public Map<String,Object> addOneData(Map<String, String> data,String tableName){
        Map<String, Object> sqlInfo = this.generateInsertSql(data, tableName);
        String sql = sqlInfo.get("sql").toString();
        Object[] values = (Object[]) sqlInfo.get("values");
        int update = jdbcTemplate.update(sql,values);
        Map<String, Object> result = new HashMap();
        result.put("success", true);
        result.put("msg",update);
        return result;
    }

    public Map<String, Object> generateInsertSql(Map<String, String> data,String tableName){
        StringBuilder sql = new StringBuilder("INSERT INTO "+tableName+" (");
        StringBuilder placeHolder = new StringBuilder(" values(");
        Set<String> columns = data.keySet();
        List<Object> values = new ArrayList<>();
        for (String column : columns) {
            String value = data.get(column);
            if(value!=null&&value.trim().length()>0){
                sql.append(column+",");
                placeHolder.append("?,");
                values.add(value);
            }
        }
        sql.deleteCharAt(sql.length()-1);
        sql.append(")");
        placeHolder.deleteCharAt(placeHolder.length()-1);
        placeHolder.append(")");
        sql.append(placeHolder);
        Map<String, Object> sqlInfo = new HashMap<>();
        sqlInfo.put("sql",sql.toString());
        sqlInfo.put("values",values.toArray());
        return sqlInfo;
    }
    public Map<String,Object> updateOneData(Map<String, String> data,String tableName,String rowId){
        Map<String, Object> sqlInfo = this.generateUpdateSql(data, tableName, rowId);
        String sql = sqlInfo.get("sql").toString();
        Object[] values = (Object[]) sqlInfo.get("values");
        int update = jdbcTemplate.update(sql,values);
        Map<String, Object> result = new HashMap();
        result.put("success", true);
        result.put("msg",update);
        return result;
    }
    public Map<String, Object> generateUpdateSql(Map<String, String> data,String tableName,String rowId){
        StringBuilder sql = new StringBuilder("UPDATE "+tableName+" SET ");
        Set<String> columns = data.keySet();
        List<Object> values = new ArrayList<>();
        for (String column : columns) {
            String value = data.get(column);
            if(value!=null){
                sql.append(column+"=?,");
                values.add(value);
            }
        }
        sql.deleteCharAt(sql.length()-1);
        sql.append("WHERE ROWIDTOCHAR(ROWID) =  ?");
        values.add(rowId);
        Map<String, Object> sqlInfo = new HashMap<>();
        sqlInfo.put("sql",sql.toString());
        sqlInfo.put("values",values.toArray());
        return sqlInfo;
    }
    public Map<String, Object> generateUpdateSql(String tableName,String [] rowIds){
        Map<String, Object> sqlInfo = this.generateDeleteSql(tableName, rowIds);
        String sql = sqlInfo.get("sql").toString();
        List<Object[]> params = (List<Object[]>) sqlInfo.get("params");
        int[] ints = jdbcTemplate.batchUpdate(sql, params);
        Map<String, Object> result = new HashMap();
        result.put("success", true);
        result.put("msg",ints);
        return result;
    }
    public Map<String, Object> generateDeleteSql(String tableName,String [] rowIds){
        StringBuilder sql = new StringBuilder("DELETE FROM "+tableName+" WHERE ROWIDTOCHAR(ROWID) =  ?");
        List<Object[]> params = new ArrayList<>();
        for (String rowId : rowIds) {
            Object[] param = {rowId};
            params.add(param);
        }
        Map<String, Object> sqlInfo = new HashMap<>();
        sqlInfo.put("sql",sql.toString());
        sqlInfo.put("params",params);
        return sqlInfo;
    }

}
