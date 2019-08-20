package com.lxw.mytool;

import com.alibaba.fastjson.JSONObject;
import com.lxw.mytool.config.GlobalConfig;
import com.lxw.mytool.util.IOUtil;
import com.lxw.mytool.util.RestTemplateUtil;
import org.junit.Test;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RestTemplateTest {
    static String cookie;
    {
        cookie = loginToBaoGongXiTong();
    }


    @Test
    public void testLogin(){
        String cookie = this.loginToBaoGongXiTong();
        System.out.println(cookie);
    }

    /**
     * 用post方式登录到到报工系统()
     * @return
     */
    public String loginToBaoGongXiTong() {
        String url = "http://10.5.210.67:8090/ecm-app/system/login/login.action";
        JSONObject object = new JSONObject();
        object.put("pin", "lixiewen");
        object.put("password", "123456");
        ResponseEntity<String> post = RestTemplateUtil.post(url, object);
        HttpHeaders headers = post.getHeaders();
        List<String> cookies = headers.get("Set-Cookie");
        String cookie = cookies.get(0).toString();
        return cookie;
    }

    /**
     * 测试带cookie的get方法
     */
    @Test
    public void testGet(){
        String url = "http://10.5.210.67:8090/ecm-app/staffTask/findZhu.action?globalParam=&limit=10&page=1&pin=lixiewen";
        JSONObject object = new JSONObject();
        object.put("limit",10);
        object.put("page",1);
        object.put("pin","lixiewen");
        ResponseEntity<String> responseEntity = RestTemplateUtil.get(url, object, cookie);
        String body = responseEntity.getBody();
        System.out.println(body);
    }
    /**
     * 测试带cookie的post方法
     */
    @Test
    public void testPost(){
        String url = "http://10.5.210.67:8090/ecm-app/staffTask/modifyAndSubmit.action";
        String jsonStr = "{\"pin\":\"lixiewen\",\"orgSid\":326,\"taskDate\":\"2019-05-25 00:00:00\",\"turnsWork\":2,\"taskPeriods\":[{\"taskPeriod\":1,\"projectSids\":[{\"projectSid\":\"133\",\"taskSid\":\"106\",\"taskTimes\":\"4\"}]},{\"taskPeriod\":2,\"projectSids\":[{\"projectSid\":\"133\",\"taskSid\":\"106\",\"taskTimes\":\"4\"}]}]}";
        JSONObject object = JSONObject.parseObject(jsonStr);
        ResponseEntity<String> responseEntity = RestTemplateUtil.post(url, object, cookie);
        String body = responseEntity.getBody();
        System.out.println(body);
    }
    /**
     * 测试带cookie的post方法
     */
    @Test
    public void testPost2(){
        String url = "http://tlpes.sgjtsteel.com/bl/upload.action?dir=image";
        HttpHeaders headers = new HttpHeaders();
        String myCookie = "AlteonP=A9npIhk5vAon0xJudXu2Vw$$; TLGL_JSESSIONTESTID=_Ug_8hz-vIU5UDo2Le_cShdVhuHPni7Vr4i_swHgWBD1pjvAUu8F!-644574347; goeasyNode=9";
        headers.add("Cookie",myCookie);
        String contentType = "multipart/form-data; boundary=----WebKitFormBoundaryV1rHJ8zZIFu1s7d8";
        headers.add("Content-Type",contentType);
        File file = new File("D:\\360MoveData\\Users\\lixiewen\\Desktop\\劳保图片\\image1.png");
       Map param = new HashMap<>();
       param.put("imgFile",file);
       JSONObject jo = new JSONObject();
       jo.put("imgFile",file);
        ResponseEntity<String> responseEntity = RestTemplateUtil.post(url, headers, jo);
        String body = responseEntity.getBody();
        System.out.println(body);
    }
    /**
     * 带着cookie上传文件成功啦
     */
    @Test
    public void uploadFilewithTest(){
        File dir = new File("D:\\360MoveData\\Users\\lixiewen\\Desktop\\img44");
        File[] files = dir.listFiles();
        PrintWriter pw = IOUtil.getPrintWriter(GlobalConfig.loggerPath + "urls.txt", true);
        for (File file : files) {
            String url = this.uploadFilewith(file);
            pw.println(url);
        }
        pw.flush();
        pw.close();
    }

    public String uploadFilewith(File file){
        String url = "http://tlpes.sgjtsteel.com/bl/upload.action?dir=image";
        HttpHeaders headers = new HttpHeaders();
        String myCookie = "AlteonP=A9npIhk5vAon0xJudXu2Vw$$; TLGL_JSESSIONTESTID=_Ug_8hz-vIU5UDo2Le_cShdVhuHPni7Vr4i_swHgWBD1pjvAUu8F!-644574347; goeasyNode=9";
        headers.add("Cookie",myCookie);
        String contentType = "multipart/form-data; boundary=----WebKitFormBoundaryV1rHJ8zZIFu1s7d8";
        headers.add("Content-Type",contentType);
//        RestTemplate rest = new RestTemplate();
        FileSystemResource resource = new FileSystemResource(file);
        MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
        param.add("imgFile", resource);
        ResponseEntity<String> responseEntity = RestTemplateUtil.post(url, headers, param);
        String body = responseEntity.getBody();
        System.out.println(body);
        JSONObject object = JSONObject.parseObject(body);
        Object url1 = object.get("url");
        return url1.toString();
    }
}
