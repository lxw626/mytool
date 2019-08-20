package com.lxw.mytool;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lxw.mytool.entity.MatAndCost;
import com.lxw.mytool.util.HttpUtil;
import com.lxw.mytool.util.RestTemplateUtil;
import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.junit.Test;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

public class TestRestTemplateTest {
    //    TestRestTemplate template = new TestRestTemplate();
    static RestTemplate template = new RestTemplate();

    static {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();
        connManager.setMaxTotal(300);
        connManager.setDefaultMaxPerRoute(300);
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(connManager).build();
        factory.setHttpClient(httpClient);
        template.setRequestFactory(factory);
    }

    HttpEntity httpEntity;


    @Test
    public void getRequestTest() {
        try {
//            http://10.188.26.57:9002/ac/login/dologin.action?username=xxx&password=xxx&crewId=A&shiftId=2
//            String url = "http://localhost:"+8888+"//helloGet?name=lixiewen";
//            String url = "http://10.188.26.57:9002/ac/login/dologin.action?username=tladmin&password=123456&crewId=A&shiftId=2";
//            String response = template.getForObject(url, String.class);
//            Map parse = (Map) JSON.parse(response);
//            Object data = parse.get("data");
//            Map parse1 = (Map) JSON.parse(data.toString());
//            String token = parse1.get("token").toString();
//            System.out.println(token);
            String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIwMjFGNjg2QjkwREM0MzQzQTE2MTQwMDhFMkQ5NjdGMSIsImlzcyI6IkFVVEguU0VSVkVSIiwiaWF0IjoxNTU4NTkyMjQ1LCJhdWQiOiJBVVRILkNMSUVOVCIsIm5iZiI6MTU1ODU5MjI0NSwic3ViIjoie1widXNlclBvc3ROYW1lXCI6XCLkv6Hmga_nrqHnkIblkZhcIixcImxvZ2luSWRcIjpcInRsYWRtaW5cIixcInZhbGlkRmxhZ1wiOlwiMVwiLFwic2V4XCI6XCIxXCIsXCJ1c2VyRnVsbE5hbWVQeVwiOlwidGxhZG1pblwiLFwib3JnRnVsbE5hbWVcIjpcIueCvOmTgeW3peWOglwiLFwidXNlck5hbWVDblwiOlwi5oqV5paZ5rWL6K-V6LSm5Y-3XCIsXCJvcmdJZFwiOlwiSlQtMTVcIixcInVzZXJHcm91cHNcIjpcIkcxNTQ0MzIzNzE4NTQ3XCIsXCJ1c2VyUm9sZXNcIjpcIlIxNTQ0MzIzNzQzNzc3XCIsXCJsb2dpbkdyb3VwXCI6XCIwXCIsXCJ1c2VyRHV0eVwiOlwi5ZGY5belXCIsXCJ1c2VyU2lkXCI6MTIwNDEsXCJ1c2VyVHlwZVwiOlwiMFwiLFwib3JnU2hvcnROYW1lXCI6XCLngrzpk4Hlt6XljoJcIixcImxvZ2luU2hpZnRcIjpcIjBcIixcImJ1c2luZXNzSURzXCI6e1wiUEVTLVRMR0xcIjp7XCJ0bGdsQWNjb3VudFwiOltcIjFBMzBcIixcIjFBMzZcIixcIjFBOTlcIl0sXCIxQTM2XCI6W1widGxnbFBsYW50XCJdLFwidGxnbFBsYW50XCI6W1wiMjAwMDE0ODFcIixcIjIwMDAxNTA0XCIsXCIyMDAwMTQ4OFwiLFwiMjAwMDE0ODlcIixcIjIwMDAxNDgzXCIsXCIyMDAwMTUwMlwiLFwiMjAwMDE0ODJcIixcIjIwMDAxNDgwXCIsXCIyMDAwMTUwMVwiLFwiMjAwMDE1MDlcIixcIjIwMDAxNDg1XCIsXCIyMDAwMTUxMlwiLFwiMjAwMDE0ODZcIixcIjIwMDAxNDg3XCIsXCIyMDAwMTQ5NVwiLFwiMjAwMDE0ODRcIixcIjIwMDAxNDk5XCIsXCIyMDAwMTQ5MlwiLFwiMjAwMDE0OTFcIixcIjIwMDAxNDk0XCIsXCIyMDAwMTQ5N1wiLFwiMjAwMDE0NjZcIixcIjIwMDAxNTAzXCIsXCIyMDAwMTUwN1wiLFwiMjAwMDE0OTNcIixcIjIwMDAxNTA1XCIsXCIyMDAwMTUwNlwiLFwiMjAwMDE1MDBcIixcIjIwMDAxNDk2XCIsXCIyMDQ4OTU5NVwiLFwiMjAwMDE1MDhcIixcIjIwMDAxNTExXCIsXCIyMDAwMTQ5OFwiXSxcIjFBOTlcIjpbXCJ0bGdsUGxhbnRMYlwiXSxcInRsZ2xQbGFudExiXCI6W1wiMjAwMDE0ODJcIixcIjIwMDAxNDg4XCIsXCIyMDAwMTQ4OVwiLFwiMjAwMDE1MDRcIixcIjIwMDAxNTAxXCIsXCIyMDAwMTUwMlwiLFwiMjAwMDE0ODBcIixcIjIwMDAxNTA5XCIsXCIyMDAwMTQ4M1wiLFwiMjAwMDE0ODVcIixcIjIwMDAxNDY2XCIsXCIyMDAwMTQ5OVwiLFwiMjAwMDE0ODRcIixcIjIwMDAxNDkyXCIsXCIyMDAwMTQ5N1wiLFwiMjAwMDE1MDVcIixcIjIwMDAxNDk2XCIsXCIyMDAwMTQ5NVwiLFwiMjAwMDE0OTFcIixcIjIwMDAxNTAzXCIsXCIyMDAwMTUxMlwiLFwiMjAwMDE0OTRcIixcIjIwMDAxNTA2XCIsXCIyMDAwMTQ5M1wiLFwiMjAwMDE0ODdcIixcIjIwMDAxNTA3XCIsXCIyMDAwMTQ4NlwiLFwiMjAwMDE1MDhcIixcIjIwNDg5NTk1XCIsXCIyMDAwMTQ5OFwiLFwiMjAwMDE1MTFcIixcIjIwMDAxNTAwXCJdLFwiMUEzMFwiOltcInRsZ2xQbGFudFwiXX19fSJ9.JPZqCZeXQDCtZliXj7k6B4vsle0TIYecfskWyxA4bCg";

            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", token);
            HttpEntity<String> requestEntity = new HttpEntity<String>(null, headers);
            String url2 = "http://localhost:8080/pes-tlgl-app/kc/caiLiaoreal/findByPage.action?_dc=1558594185325";
            ResponseEntity<String> rss = template.exchange(url2, HttpMethod.POST, requestEntity, String.class);
            System.out.println(rss);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void postRequestTest() {
        try {
            String url = "http://localhost:" + 8888 + "/helloRest/helloPost";
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
    public void putRequestTest() {
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
            HttpEntity<String> entity = new HttpEntity<String>(reqJsonStr, headers);
            String url = "http://localhost:" + 8888 + "/helloRest/helloPut";
            ResponseEntity<String> exchange = template.exchange(url, HttpMethod.PUT, entity, String.class);
            String body = exchange.getBody();
            System.err.println(exchange);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getRequestTestx() {
        try {
            String url = "http://10.188.26.57:9002/ac/login/dologin.action?username=tladmin&password=123456&crewId=A&shiftId=2";
            CloseableHttpResponse x = HttpUtil.getX(url);
            Header[] allHeaders = x.getAllHeaders();
            for (Header allHeader : allHeaders) {
                System.out.println(allHeader);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void postWithtokenTest() {
        try {
            this.login();
            String url = "http://localhost:8080/pes-tlgl-app/kc/caiLiaoreal/findByPage.action?_dc=1558594185325";
            ResponseEntity<String> responseEntity = template.exchange(url, HttpMethod.POST, httpEntity, String.class);
            System.out.println(responseEntity.getBody());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void login() {
        String url = "http://localhost:8080/pes-tlgl-app/ac/login/doLoginForPortal.action?username=tladmin&password=123456&crewId=0&shiftId=0";
        ResponseEntity<String> responseEntity = template.getForEntity(url, String.class);
        //添加cookie以保持状态
        HttpHeaders headers = new HttpHeaders();
        String headerValue = responseEntity.getHeaders().get("Set-Cookie").toString()
                .replace("[", "")
                .replace("]", "");
        headers.set("Cookie", headerValue);
        System.out.println("Cookie:" + headerValue);
        httpEntity = new HttpEntity(headers);
    }

    @Test
    public void getWithTokenTest() {
        String url = "http://10.5.210.67:8090/ecm-app/staffTask/findZhu.action?globalParam=&limit=10&page=1&pin=lixiewen";
        String cookie;
        cookie = this.loginForPost();
        HttpHeaders reqHeaders = new HttpHeaders();
        reqHeaders.add("Cookie", cookie);
        HttpEntity reqEntity = new HttpEntity(null, reqHeaders);
        ResponseEntity<String> responseEntity = template.getForEntity(url, String.class, reqEntity);
        HttpHeaders resHeaders = responseEntity.getHeaders();
        String resBody = responseEntity.getBody();
        System.out.println(reqHeaders);
        System.out.println(resHeaders);
        System.out.println(resBody);
    }

    @Test
    public void loginTest() {
        String cookie = this.loginForPost();
        System.out.println(cookie);
    }

    public String loginForPost() {
        String url = "http://10.5.210.67:8090/ecm-app/system/login/login.action";
        JSONObject object = new JSONObject();
        object.put("pin", "lixiewen");
        object.put("password", "123456");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity httpEntity = new HttpEntity(object, headers);
        ResponseEntity responseEntity = template.postForEntity(url, httpEntity, String.class);
        HttpHeaders responseHeaders = responseEntity.getHeaders();
        Object responsebody = responseEntity.getBody();
        System.out.println("login:" + responseHeaders);
        System.out.println("login:" + responsebody);
        //添加cookie以保持状态
        /*HttpHeaders resHeaders = responseEntity.getHeaders();
        List<String> cookies = resHeaders.get("Set-Cookie");
        System.out.println(cookies);
        String cookie = cookies.get(0);
        cookie = cookie.split(";")[0];*/
        //添加cookie以保持状态
        String headerValue = responseEntity.getHeaders().get("Set-Cookie").toString().replace("[", "");
        headerValue = headerValue.replace("]", "");
        return headerValue;
    }
    @Test
    public void postMenuTest() {
        try {
            String url = "http://10.88.248.51:9001/ac/login/menu.action";
            HttpHeaders httpHeaders = new HttpHeaders();
            String authorization = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIyRkIxRjQ5QURDMjQ0REZBODU4MkE2MjQ3QTBCMjBDMiIsImlzcyI6IkFVVEguU0VSVkVSIiwiaWF0IjoxNTU5MDI0NzI1LCJhdWQiOiJBVVRILkNMSUVOVCIsIm5iZiI6MTU1OTAyNDcyNSwic3ViIjoie1wiZ3JvdXBJZFwiOlwiQVwiLFwibG9naW5JZFwiOlwicGVzdGxcIixcIm9yZ0Z1bGxOYW1lXCI6XCLpppbpkqLpm4blm6JcIixcIm9yZ0lkXCI6XCIyMDAwMDExOFwiLFwic2V4XCI6XCIxXCIsXCJzaGlmdElkXCI6XCIyXCIsXCJ1c2VyTmFtZUNuXCI6XCLmipXmlplQRVPotoXnrqFcIixcInVzZXJTaWRcIjoxMzEwMSxcInVzZXJUeXBlXCI6XCIwXCIsXCJ2YWxpZEZsYWdcIjpcIjFcIn0ifQ.wFMxtffFm2GxWOGQpFM8nB1XbTPQCKS-Hc2axv4g7lg";
            httpHeaders.add("Authorization",authorization);
            JSONObject object = new JSONObject();
            object.put("node","root");
            ResponseEntity<String> post = RestTemplateUtil.post(url, httpHeaders, object);
//            String forObject = template.postForObject(url, String.class);
            System.out.println(post);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
