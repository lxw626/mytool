package com.lxw.mytool.service;

import com.lxw.mytool.dao.HelloDao;
import com.lxw.mytool.util.ColumnInfo;
import com.lxw.mytool.util.DBUtil;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @autor lixiewen
 * @date 2019/4/20-21:36
 */
@Service
public class HelloService {
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    HelloDao helloDao;
    public Map<String,Object> helloJdbcTemplate(String sql,int start,int end){
        int count = this.findCount(sql);
        sql = "SELECT * FROM ( " +
                " SELECT rowidtochar(rowid) AS row_id,tmp_page.*, rownum AS row_num " +
                " FROM ( " +
                sql +
                " ) tmp_page " +
                "WHERE rownum <= ? " +
                " )" +
                " WHERE row_num > ? ";
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql,end,start);
        Map<String,Object> result = new HashMap<>();
        result.put("results",list.size());
        result.put("totalProperty",count);
        result.put("list",list);
        return result;
    }
    public List<Map<String, Object>> findBySql(String sql){
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
        return list;
    }
    public List<Map<String, Object>> helloMyBatis(Integer sid){
        List<Map<String, Object>> list = helloDao.helloMyBatis(sid);
        return list;
    }
    public int findCount(String sql){
        Integer count = jdbcTemplate.queryForObject(this.getCountSql(sql), Integer.class);
        return count;
    }
    /**
     * 拼接查询总数的sql
     * @param sql
     * @return
     */
    private String getCountSql(String sql){
        sql = sql.replaceAll(";", "");
        String countSql = "select count(0) COUNT "+sql.substring(sql.toLowerCase().indexOf("from"));
        return countSql;

    }
}
