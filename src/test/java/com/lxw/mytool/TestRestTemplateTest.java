package com.lxw.mytool;

import com.alibaba.fastjson.JSON;
import com.lxw.mytool.core.bean.Response;
import com.lxw.mytool.entity.MatAndCost;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

public class TestRestTemplateTest {
    TestRestTemplate template = new TestRestTemplate();

    @Test
    public void getRequestTest(){
        try {
//            http://10.188.26.57:9002/ac/login/dologin.action?username=xxx&password=xxx&crewId=A&shiftId=2
//            String url = "http://localhost:"+8888+"//helloGet?name=lixiewen";
            String url = "http://10.188.26.57:9002/ac/login/dologin.action?username=tladmin&password=123456&crewId=A&shiftId=2";
            String response = template.getForObject(url, String.class);
            System.out.println(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void postRequestTest(){
        try {
            String url = "http://localhost:"+8888+"/helloRest/helloPost";
            MatAndCost matAndCost = new MatAndCost();
            matAndCost.setMatNr("xiezi");
            matAndCost.setMatDesc("鞋子");
            matAndCost.setCostCode("lb");
            matAndCost.setCostDesc("劳保中心");
            String forObject = template.postForObject(url, matAndCost, String.class);
            System.out.println(forObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void putRequestTest(){
        try {
            MatAndCost matAndCost = new MatAndCost();
            matAndCost.setMatNr("xiezi");
            matAndCost.setMatDesc("鞋子");
            matAndCost.setCostCode("lb");
            matAndCost.setCostDesc("劳保中心");
            String reqJsonStr = JSON.toJSONString(matAndCost);
//            String reqJsonStr = "{\"matNr\":200,\"code\":\"200\", \"englishname\":\"200\",\"name\":\"200\"}";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> entity = new HttpEntity<String>(reqJsonStr,headers);
            String url = "http://localhost:"+8888+"/helloRest/helloPut";
            ResponseEntity<String> exchange = template.exchange(url, HttpMethod.PUT, entity, String.class);
            String body = exchange.getBody();
            System.err.println(exchange);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
