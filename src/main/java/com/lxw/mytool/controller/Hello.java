package com.lxw.mytool.controller;

import com.lxw.mytool.service.HelloService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @autor lixiewen
 * @date 2019/4/20-20:26
 */
@RestController
public class Hello {
    @Autowired
    HelloService helloService;
    Logger logger = LoggerFactory.getLogger(Hello.class);

    @GetMapping("/helloGet")
    public String helloGet(String name){
        logger.info(name);
        return name;
    }

    @PostMapping("/hello")
    public String hello(String name){
        logger.info("hello:"+name);
        return "hello:"+name;
    }
    @RequestMapping("/helloJdbcTemplate")
    public Map<String,Object> helloJdbcTemplate(String sql,int start,int limit){
        int end = start+limit;
        Map<String,Object> map = helloService.helloJdbcTemplate(sql,start,end);
        return map;
    }
    @PostMapping("/helloMyBatis")
    public List<Map<String, Object>> helloMyBatis(Integer sid){
        List<Map<String, Object>> list = helloService.helloMyBatis(sid);
        return list;
    }
}
