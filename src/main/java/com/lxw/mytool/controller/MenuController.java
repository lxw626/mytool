package com.lxw.mytool.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lxw.mytool.core.bean.Response;
import com.lxw.mytool.util.RestTemplateUtil;
import com.lxw.mytool.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class MenuController {
    @Autowired
    JdbcTemplate jdbcTemplate;
    @RequestMapping("/getOracleMenus")
   public ResponseEntity<String> getOracleMenus() {
        String url = "http://10.88.248.51:9001/ac/login/menu.action";
        HttpHeaders httpHeaders = new HttpHeaders();
        String authorization = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIyRkIxRjQ5QURDMjQ0REZBODU4MkE2MjQ3QTBCMjBDMiIsImlzcyI6IkFVVEguU0VSVkVSIiwiaWF0IjoxNTU5MDI0NzI1LCJhdWQiOiJBVVRILkNMSUVOVCIsIm5iZiI6MTU1OTAyNDcyNSwic3ViIjoie1wiZ3JvdXBJZFwiOlwiQVwiLFwibG9naW5JZFwiOlwicGVzdGxcIixcIm9yZ0Z1bGxOYW1lXCI6XCLpppbpkqLpm4blm6JcIixcIm9yZ0lkXCI6XCIyMDAwMDExOFwiLFwic2V4XCI6XCIxXCIsXCJzaGlmdElkXCI6XCIyXCIsXCJ1c2VyTmFtZUNuXCI6XCLmipXmlplQRVPotoXnrqFcIixcInVzZXJTaWRcIjoxMzEwMSxcInVzZXJUeXBlXCI6XCIwXCIsXCJ2YWxpZEZsYWdcIjpcIjFcIn0ifQ.wFMxtffFm2GxWOGQpFM8nB1XbTPQCKS-Hc2axv4g7lg";
        httpHeaders.add("Authorization", authorization);
        JSONObject object = new JSONObject();
        object.put("node", "root");
        ResponseEntity<String> post = RestTemplateUtil.post(url, httpHeaders, object);
        return post;
    }
    @RequestMapping("/getLocalMenus")
    public Object getLocalMenus() {
        Object parse = JSON.parse(menu.replaceAll("\n", ""));
        return parse;
    }
    @RequestMapping("/getMysqlMenus")
    public Response getMenus() {
        String sql  = "select * from menu";
        List<Map<String, Object>> menus = jdbcTemplate.queryForList(sql);
        List<Map<String, Object>> items = new ArrayList<>();
        for(int i=0;i<menus.size();i++){
            Map<String, Object> map = menus.get(i);
            map.put("leaf",true);
            map.put("connected",true);
            items.add(map);
        }
        return new Response().successItem(items);
    }
    String menu = "{\n" +
            "    \"meta\": {\n" +
            "        \"success\": true,\n" +
            "        \"message\": \"操作成功!\"\n" +
            "    },\n" +
            "    \"items\": [\n" +
            "        {\n" +
            "            \"sid\": 15664,\n" +
            "            \"version\": 3,\n" +
            "            \"createdBy\": \"admin\",\n" +
            "            \"createdDt\": \"2018-12-04 08:35:57\",\n" +
            "            \"updatedBy\": \"admin\",\n" +
            "            \"updatedDt\": \"2018-12-04 14:19:41\",\n" +
            "            \"parentSid\": 15640,\n" +
            "            \"resId\": \"TL_MD\",\n" +
            "            \"resName\": \"主数据\",\n" +
            "            \"resType\": \"1\",\n" +
            "            \"resLevel\": 2,\n" +
            "            \"resSeq\": 1,\n" +
            "            \"resVisibility\": 1,\n" +
            "            \"leaf\": false,\n" +
            "            \"iconCls\": \"bullet_blue\",\n" +
            "            \"items\": [\n" +
            "                {\n" +
            "                    \"sid\": 15804,\n" +
            "                    \"version\": 2,\n" +
            "                    \"createdBy\": \"admin\",\n" +
            "                    \"createdDt\": \"2018-12-04 15:04:10\",\n" +
            "                    \"updatedBy\": \"admin\",\n" +
            "                    \"updatedDt\": \"2018-12-04 15:04:21\",\n" +
            "                    \"parentSid\": 15664,\n" +
            "                    \"resId\": \"MD02\",\n" +
            "                    \"resName\": \"参数分类管理\",\n" +
            "                    \"resType\": \"1\",\n" +
            "                    \"resPageUrl\": \"referenceDataView\",\n" +
            "                    \"resLevel\": 3,\n" +
            "                    \"resSeq\": 2,\n" +
            "                    \"resVisibility\": 1,\n" +
            "                    \"leaf\": true,\n" +
            "                    \"iconCls\": \"bullet_red\",\n" +
            "                    \"resPackage\": \"主数据\",\n" +
            "                    \"connected\": true\n" +
            "                },\n" +
            "                {\n" +
            "                    \"sid\": 15806,\n" +
            "                    \"version\": 1,\n" +
            "                    \"createdBy\": \"admin\",\n" +
            "                    \"createdDt\": \"2018-12-04 15:06:25\",\n" +
            "                    \"parentSid\": 15664,\n" +
            "                    \"resId\": \"MD06\",\n" +
            "                    \"resName\": \"编码生成配置\",\n" +
            "                    \"resType\": \"1\",\n" +
            "                    \"resPageUrl\": \"codeRulesView\",\n" +
            "                    \"resLevel\": 3,\n" +
            "                    \"resSeq\": 4,\n" +
            "                    \"resVisibility\": 1,\n" +
            "                    \"leaf\": true,\n" +
            "                    \"iconCls\": \"bullet_red\",\n" +
            "                    \"resPackage\": \"主数据\",\n" +
            "                    \"connected\": true\n" +
            "                },\n" +
            "                {\n" +
            "                    \"sid\": 17759,\n" +
            "                    \"version\": 2,\n" +
            "                    \"createdBy\": \"admin\",\n" +
            "                    \"createdDt\": \"2019-01-24 11:16:59\",\n" +
            "                    \"updatedBy\": \"admin\",\n" +
            "                    \"updatedDt\": \"2019-01-28 16:47:37\",\n" +
            "                    \"parentSid\": 15664,\n" +
            "                    \"resId\": \"MD09\",\n" +
            "                    \"resName\": \"库存检核配置\",\n" +
            "                    \"resType\": \"1\",\n" +
            "                    \"resPageUrl\": \"kuCunJianHeView\",\n" +
            "                    \"resLevel\": 3,\n" +
            "                    \"resSeq\": 8,\n" +
            "                    \"resVisibility\": 1,\n" +
            "                    \"leaf\": true,\n" +
            "                    \"iconCls\": \"bullet_red\",\n" +
            "                    \"resPackage\": \"主数据\",\n" +
            "                    \"connected\": true\n" +
            "                }\n" +
            "            ],\n" +
            "            \"resPackage\": \"主数据\",\n" +
            "            \"connected\": true\n" +
            "        },\n" +
            "        {\n" +
            "            \"sid\": 15777,\n" +
            "            \"version\": 1,\n" +
            "            \"createdBy\": \"admin\",\n" +
            "            \"createdDt\": \"2018-12-04 14:43:29\",\n" +
            "            \"parentSid\": 15640,\n" +
            "            \"resId\": \"OTHER\",\n" +
            "            \"resName\": \"分摊管理\",\n" +
            "            \"resType\": \"1\",\n" +
            "            \"resLevel\": 2,\n" +
            "            \"resSeq\": 2,\n" +
            "            \"resVisibility\": 1,\n" +
            "            \"leaf\": false,\n" +
            "            \"iconCls\": \"bullet_blue\",\n" +
            "            \"items\": [\n" +
            "                {\n" +
            "                    \"sid\": 17687,\n" +
            "                    \"version\": 3,\n" +
            "                    \"createdBy\": \"admin\",\n" +
            "                    \"createdDt\": \"2019-01-20 10:02:14\",\n" +
            "                    \"updatedBy\": \"admin\",\n" +
            "                    \"updatedDt\": \"2019-01-20 11:33:50\",\n" +
            "                    \"parentSid\": 15777,\n" +
            "                    \"resId\": \"OTHER10\",\n" +
            "                    \"resName\": \"分摊配置\",\n" +
            "                    \"resType\": \"1\",\n" +
            "                    \"resLevel\": 3,\n" +
            "                    \"resSeq\": 1,\n" +
            "                    \"resVisibility\": 1,\n" +
            "                    \"leaf\": false,\n" +
            "                    \"iconCls\": \"bullet_blue\",\n" +
            "                    \"items\": [\n" +
            "                        {\n" +
            "                            \"sid\": 17692,\n" +
            "                            \"version\": 3,\n" +
            "                            \"createdBy\": \"admin\",\n" +
            "                            \"createdDt\": \"2019-01-20 10:11:32\",\n" +
            "                            \"updatedBy\": \"admin\",\n" +
            "                            \"updatedDt\": \"2019-01-20 10:30:14\",\n" +
            "                            \"parentSid\": 17687,\n" +
            "                            \"resId\": \"OTHER04\",\n" +
            "                            \"resName\": \"分摊规则配置\",\n" +
            "                            \"resType\": \"1\",\n" +
            "                            \"resPageUrl\": \"parameters\",\n" +
            "                            \"resLevel\": 4,\n" +
            "                            \"resSeq\": 4,\n" +
            "                            \"resVisibility\": 1,\n" +
            "                            \"leaf\": true,\n" +
            "                            \"iconCls\": \"bullet_red\",\n" +
            "                            \"resPackage\": \"分摊配置\",\n" +
            "                            \"connected\": true\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"sid\": 17693,\n" +
            "                            \"version\": 3,\n" +
            "                            \"createdBy\": \"admin\",\n" +
            "                            \"createdDt\": \"2019-01-20 10:12:43\",\n" +
            "                            \"updatedBy\": \"admin\",\n" +
            "                            \"updatedDt\": \"2019-01-20 10:30:14\",\n" +
            "                            \"parentSid\": 17687,\n" +
            "                            \"resId\": \"OTHER08\",\n" +
            "                            \"resName\": \"分摊数据上传规则\",\n" +
            "                            \"resType\": \"1\",\n" +
            "                            \"resPageUrl\": \"mesFtsynRuleView\",\n" +
            "                            \"resLevel\": 4,\n" +
            "                            \"resSeq\": 5,\n" +
            "                            \"resVisibility\": 1,\n" +
            "                            \"leaf\": true,\n" +
            "                            \"iconCls\": \"bullet_red\",\n" +
            "                            \"resPackage\": \"分摊配置\",\n" +
            "                            \"connected\": true\n" +
            "                        }\n" +
            "                    ],\n" +
            "                    \"resPackage\": \"分摊管理\",\n" +
            "                    \"connected\": true\n" +
            "                },\n" +
            "                {\n" +
            "                    \"sid\": 15784,\n" +
            "                    \"version\": 6,\n" +
            "                    \"createdBy\": \"admin\",\n" +
            "                    \"createdDt\": \"2018-12-04 14:45:34\",\n" +
            "                    \"updatedBy\": \"admin\",\n" +
            "                    \"updatedDt\": \"2019-01-14 13:58:15\",\n" +
            "                    \"parentSid\": 15777,\n" +
            "                    \"resId\": \"OTHER03\",\n" +
            "                    \"resName\": \"分摊任务告警\",\n" +
            "                    \"resType\": \"1\",\n" +
            "                    \"resPageUrl\": \"TaskView\",\n" +
            "                    \"resLevel\": 3,\n" +
            "                    \"resSeq\": 5,\n" +
            "                    \"resVisibility\": 1,\n" +
            "                    \"leaf\": true,\n" +
            "                    \"iconCls\": \"bullet_red\",\n" +
            "                    \"resPackage\": \"分摊管理\",\n" +
            "                    \"connected\": true\n" +
            "                }\n" +
            "            ],\n" +
            "            \"resPackage\": \"分摊管理\",\n" +
            "            \"connected\": true\n" +
            "        }\n" +
            "    ],\n" +
            "    \"success\": true\n" +
            "}";
}
