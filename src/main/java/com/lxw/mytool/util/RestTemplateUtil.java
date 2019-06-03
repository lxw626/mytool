package com.lxw.mytool.util;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

public class RestTemplateUtil {
    private static RestTemplate restTemplate = new RestTemplate();

    static {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();
        connManager.setMaxTotal(300);
        connManager.setDefaultMaxPerRoute(300);
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(connManager).build();
        factory.setHttpClient(httpClient);
        restTemplate.setRequestFactory(factory);
    }

    public static ResponseEntity<String> post(String url,Object object){
        HttpEntity reqEntity = new HttpEntity(object);
        ResponseEntity<String> resEntity = restTemplate.postForEntity(url, reqEntity, String.class);
        return resEntity;
    }
    public static ResponseEntity<String> post(String url,Object object,String cookie){
        HttpHeaders reqHeaders = new HttpHeaders();
        reqHeaders.add("Cookie",cookie);
        HttpEntity reqEntity = new HttpEntity(object,reqHeaders);
        ResponseEntity<String> resEntity = restTemplate.postForEntity(url, reqEntity, String.class);
        return resEntity;
    }
    public static ResponseEntity<String> post(String url,HttpHeaders reqHeaders,Object object){
        HttpEntity reqEntity = new HttpEntity(object, reqHeaders);
        ResponseEntity<String> resEntity = restTemplate.postForEntity(url, reqEntity, String.class);
        return resEntity;
    }
    public static ResponseEntity<String> get(String url){
        ResponseEntity<String> resEntity = restTemplate.getForEntity(url,String.class);
        return resEntity;
    }
    public static ResponseEntity<String> get(String url,Object object){
        ResponseEntity<String> resEntity = restTemplate.getForEntity(url,String.class,object);
        return resEntity;
    }
    public static ResponseEntity<String> get(String url,Object object,String cookie){
        HttpHeaders reqHeaders = new HttpHeaders();
        reqHeaders.add("Cookie",cookie);
        HttpEntity reqEntity = new HttpEntity(object,reqHeaders);
        ResponseEntity<String> resEntity = restTemplate.getForEntity(url,String.class,reqEntity);
        return resEntity;
    }
    public static ResponseEntity<String> get(String url,HttpHeaders reqHeaders,Object object){
        HttpEntity reqEntity = new HttpEntity(object, reqHeaders);
        ResponseEntity<String> resEntity = restTemplate.getForEntity(url,String.class,reqEntity);
        return resEntity;
    }

}