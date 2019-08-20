package com.lxw.mytool;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lxw.mytool.core.bean.Response;
import com.lxw.mytool.util.HttpUtil;
import org.apache.commons.beanutils.BeanMap;
import org.apache.commons.beanutils.BeanUtils;
import org.junit.Test;
import org.springframework.http.HttpHeaders;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class HttpUtilTest {
    @Test
    public void getTest() throws Exception {
        String url = "http://10.188.26.57:9002/ac/login/dologin.action?username=tladmin&password=123456&crewId=A&shiftId=2";
        String responseText = HttpUtil.get(url);
        System.out.println(responseText);
        Object parse = JSON.parse(responseText);
        Response response = JSON.toJavaObject((JSON) parse, Response.class);
    }
    @Test
    public void postTest() throws Exception {
        String logInUrl = "http://10.188.26.57:9002/ac/login/dologin.action?username=tladmin&password=123456&crewId=A&shiftId=2";
        String logInResponse = HttpUtil.get(logInUrl);
        Map parse = (Map) JSON.parse(logInResponse);
        Object data = parse.get("data");
        Map parse1 = (Map) JSON.parse(data.toString());
        String token = parse1.get("token").toString();
        System.out.println(token);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", token);
        String url = "http://localhost:8080/pes-tlgl-app/io/tl-storComment/findLeftJoin.action?_dc=1558511432804";
        Map param = new HashMap();
        param.put("errReasonX",1);
        param.put("page",1);
        param.put("start",0);
        param.put("start",15);
        String s = JSONObject.toJSONString(param);
        String responseText = HttpUtil.post(url,s);
        System.out.println(responseText);
    }
    @Test
    public void getWithTokenTest() throws IOException {
        String url = "http://10.5.210.67:8090/ecm-app/staffTask/findZhu.action?globalParam=&limit=10&page=1&pin=lixiewen";
        String cookis= "SESSIONID=c99cc3d8-be60-4a3d-a255-8a857a6f0d5e";
        JSONObject object = new JSONObject();
        object.put("limit",10);
        object.put("page",1);
        object.put("pin","lixiewen");
        String post = HttpUtil.get(url, cookis);
        System.out.println("post"+post);
    }
}
