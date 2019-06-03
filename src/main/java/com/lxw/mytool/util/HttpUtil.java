package com.lxw.mytool.util;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class HttpUtil {
    private static CloseableHttpClient httpClient;
    private static RequestConfig requestConfig = RequestConfig.custom()
            .setSocketTimeout(5000)
            .setConnectTimeout(5000)
            .setConnectionRequestTimeout(5000)
            .build();

    static {
        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();
        connManager.setMaxTotal(300);
        connManager.setDefaultMaxPerRoute(300);
        httpClient = HttpClients.custom().setConnectionManager(connManager).build();
    }

    // get请求
    public static String get(String url) throws IOException {
        HttpGet httpget = new HttpGet(url);
        httpget.setConfig(requestConfig);
        try (CloseableHttpResponse response = httpClient.execute(httpget)) {
            HttpEntity entity = response.getEntity();
            return EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
        }
    }
    // get请求
    public static String get(String url,String cookie) throws IOException {
        HttpGet httpget = new HttpGet(url);
        httpget.setConfig(requestConfig);
        httpget.setHeader("Cookie",cookie);
        try (CloseableHttpResponse response = httpClient.execute(httpget)) {
            HttpEntity entity = response.getEntity();
            return EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
        }
    }
    public static CloseableHttpResponse getX(String url) throws IOException {
        HttpGet httpget = new HttpGet(url);
        httpget.setConfig(requestConfig);
        try (CloseableHttpResponse response = httpClient.execute(httpget)) {
            return response;
        }
    }

    // post请求
    public static String post(String url, String json) throws IOException {
        HttpEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(entity);
        httpPost.setConfig(requestConfig);
        try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
            return EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
        }
    }
    // post请求
    public static String post(String url, String json,String cookis) throws IOException {
        HttpEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(entity);
        httpPost.setHeader("Cookie", cookis);
        httpPost.setHeader("Accept", "application/json, text/javascript, */*; q=0.01");
        httpPost.setConfig(requestConfig);
        try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
            return EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
        }
    }
}
