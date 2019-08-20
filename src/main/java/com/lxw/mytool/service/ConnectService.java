package com.lxw.mytool.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lxw.mytool.entity.Connect;
import com.lxw.mytool.mapper.ConnectMapper;
import com.lxw.mytool.util.ExcelUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ConnectService {
    @Autowired
    private ConnectMapper connectMapper;
    @Autowired
    JdbcTemplate jdbcTemplate;
    public Map<String, Object> findByPage(Map<String,String> param,int page,int limit){
        PageHelper.startPage(page,limit,true);
        Page<Connect> connects = (Page<Connect>) connectMapper.selectAll(param);
        List<Connect> result = connects.getResult();
        long total = connects.getTotal();
        Map<String, Object> maps = new HashMap<>();
        maps.put("items",result);
        maps.put("totalProperty",total);
        maps.put("results",result.size());
        return maps;
    }
    public void add(Connect connect){
        connectMapper.insert(connect);
    }
    public void updateByPrimaryKey(Connect connect){
        connectMapper.updateByPrimaryKey(connect);
    }
    public void deleteByPrimaryKey(Integer sid){
        connectMapper.deleteByPrimaryKey(sid);
    }
    public String exportExcel(HttpServletResponse response, String fileName){
        String sql = "select * from connect";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql);
        HSSFWorkbook wb = ExcelUtil.getHSSFWorkbook("連接", maps);
        ExcelUtil.exportExcel(response,wb,fileName);
        return null;
    }
}
