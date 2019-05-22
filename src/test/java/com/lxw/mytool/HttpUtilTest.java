package com.lxw.mytool;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lxw.mytool.core.bean.Response;
import com.lxw.mytool.util.HttpUtil;
import org.junit.Test;

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
}
