package com.lxw.mytool.controller;

import com.lxw.mytool.core.bean.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/connect")
public class ConnectController {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @RequestMapping("/findByPage")
    public Response findByPage(){
        String sql = "select * from connect";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql);
        return new Response().successItem(maps);
    }
}
