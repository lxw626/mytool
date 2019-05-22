package com.lxw.mytool.util;


import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

/**
 * Created by zcl on 2018/12/21.
 */
public class HttpClientUtil {
    private static final Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);
    private static HttpClientUtil ins;
    private HttpClient httpClient;


    private HttpClientUtil() {
        httpClient = new HttpClient();

    }

    public static HttpClientUtil getInstance() {
        if (ins == null) {
            synchronized (HttpClientUtil.class) {
                ins = new HttpClientUtil();
            }
        }
        return ins;
    }


    /**
     * 下载文件
     *
     * @param fileUrl
     * @param filePath
     */
    public void downloadFile(String fileUrl, String filePath) {
        try {
            URL url = new URL(fileUrl);
            FileUtils.copyURLToFile(url, new File(filePath));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取url内容
     *
     * @param url
     * @return
     */
    public String get(String url) {
        byte[] file = getByte(url);
        if (file != null) {
            return new String(file);
        }
        return "";
    }

    /**
     * 获取url返回的二进制形式
     *
     * @param url
     * @return
     */
    public byte[] getByte(String url) {
        GetMethod getMethod = new GetMethod(url);
        getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
                new DefaultHttpMethodRetryHandler());
        getMethod.setRequestHeader("X-Requested-With", "XMLHttpRequest");
        getMethod.setRequestHeader("User-Agent", "Mozilla/5.0");
        synchronized (ins) {
            int statusCode = 0;
            try {
                statusCode = httpClient.executeMethod(getMethod);
                if (statusCode != HttpStatus.SC_OK) {
                    logger.error("Method failed: " + getMethod.getStatusLine());
                }
                return getMethod.getResponseBody();
            } catch (Exception e) {
                logger.error("https link error", e);
            } finally {
                getMethod.releaseConnection();
            }
        }
        return null;
    }

    /**
     * 只请求Get连接 不用返回
     *
     * @param url
     */
    public void sendGet(String url) {
        GetMethod getMethod = new GetMethod(url);
        getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
                new DefaultHttpMethodRetryHandler());
        getMethod.setRequestHeader("X-Requested-With", "XMLHttpRequest");
        getMethod.setRequestHeader("User-Agent", "Mozilla/5.0");
        synchronized (ins) {
            try {
                httpClient.executeMethod(getMethod);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * get数据
     *
     * @param url
     * @return
     * @throws Exception
     */
    public String getRequest(String url) throws IllegalStateException, IOException {
        HttpClient client = new HttpClient();
        StringBuilder sb = new StringBuilder();
        InputStream ins = null;
        GetMethod method = new GetMethod(url);
        try {
            // Execute the method.
            int statusCode = client.executeMethod(method);
            if (statusCode == HttpStatus.SC_OK) {
                ins = method.getResponseBodyAsStream();
                byte[] b = new byte[1024];
                int r_len = 0;
                while ((r_len = ins.read(b)) > 0) {
                    sb.append(new String(b, 0, r_len, method
                            .getResponseCharSet()));
                }
            } else {
                logger.error("getRequest errorcode" + statusCode);
            }
        } catch (HttpException e) {
            logger.error("getRequest https link error", e);
        } catch (IOException e) {
            logger.error("getRequest https link error", e);
        } finally {
            method.releaseConnection();
            if (ins != null) {
                ins.close();
            }
        }
        return sb.toString();
    }

    /**
     * get数据,设置超时时间
     *
     * @param url
     * @return
     * @throws Exception
     */
    public String getRequestWithTimeout(String url) throws IllegalStateException, IOException {
        HttpClient client = new HttpClient();
        // 设置请求超时时间
        client.getHttpConnectionManager().getParams().setConnectionTimeout(2000);
        // 设置响应超时时间
        client.getHttpConnectionManager().getParams().setSoTimeout(2000);

        StringBuilder sb = new StringBuilder();
        InputStream ins = null;
        GetMethod method = new GetMethod(url);
        try {
            // Execute the method.
            int statusCode = client.executeMethod(method);
            if (statusCode == HttpStatus.SC_OK) {
                ins = method.getResponseBodyAsStream();
                byte[] b = new byte[1024];
                int r_len = 0;
                while ((r_len = ins.read(b)) > 0) {
                    sb.append(new String(b, 0, r_len, "UTF-8"));
                }
            } else {
                logger.error("getRequestWithTimeout errorcode" + statusCode);
            }
        } catch (HttpException e) {
            logger.error("getRequestWithTimeout https link error, url=" + url, e);
        } catch (IOException e) {
            logger.error("getRequestWithTimeout https link error, url=" + url, e);
        } finally {
            method.releaseConnection();
            if (ins != null) {
                ins.close();
            }
        }
        return sb.toString();
    }

    /**
     * post数据
     *
     * @param url
     * @return
     * @throws Exception
     */
    public String postRequest(String url) throws IllegalStateException, IOException {
        HttpPost post = new HttpPost(url);
        RequestConfig requestConfig = RequestConfig.custom().setRelativeRedirectsAllowed(true).build();
        post.setConfig(requestConfig);

        String reUrl = "";

        try {
            // Execute the method.
            HttpResponse httpResponse = new DefaultHttpClient().execute(post);
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_MOVED_TEMPORARILY) {
                post.abort();//释放post请求
                reUrl = httpResponse.getLastHeader("Location").getValue();
            }
        } catch (HttpException e) {
            logger.error("Fatal protocol violation: " + e);
        } catch (IOException e) {
            logger.error("Fatal transport error: " + e);
        }
        return reUrl;
    }

    // 发送一个GET请求,参数形式key1=value1&key2=value2...
    public String post(String path, String params) {
        HttpURLConnection httpConn = null;
        BufferedReader in = null;
        PrintWriter out = null;
        try {
            URL url = new URL(path);
            httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setRequestMethod("POST");
            httpConn.setDoInput(true);
            httpConn.setDoOutput(true);

            //发送post请求参数
            out = new PrintWriter(httpConn.getOutputStream());
            out.println(params);
            out.flush();

            //读取响应
            if (httpConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                StringBuffer content = new StringBuffer();
                String tempStr = "";
                in = new BufferedReader(new InputStreamReader(httpConn.getInputStream()));
                while ((tempStr = in.readLine()) != null) {
                    content.append(tempStr);
                }
                return content.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
                out.close();
                httpConn.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 发送第三方广告请求
     *
     * @param url
     * @param params
     * @param appid
     * @param source
     * @return
     */
    public String sendAdHubpost(String url, String params, String appid, String source) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("RequestApp", appid);
            conn.setRequestProperty("RequestSource", source);
            conn.setRequestProperty("Content-Type", "application/json");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(params);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    /**
     * post请求，x-www-form-urlencoded 方式
     * @param url
     * @param params
     * @param
     * @return
     * @throws Exception
     */
    public  String post_urlencodeed(String url, String params) throws Exception {

        CloseableHttpClient httpclient = HttpClients.createDefault();

        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(2000).setConnectTimeout(2000).build();//设置请求和传输超时时间

        HttpPost httpPost = new HttpPost(url);// 创建httpPost

        httpPost.setConfig(requestConfig);

        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        String charSet = "UTF-8";
        StringEntity entity = new StringEntity(params, charSet);
        httpPost.setEntity(entity);
        CloseableHttpResponse response = null;

        try {

            response = httpclient.execute(httpPost);
            StatusLine status = response.getStatusLine();
            int state = status.getStatusCode();
            if (state == HttpStatus.SC_OK) {
                HttpEntity responseEntity = response.getEntity();
                String jsonString = EntityUtils.toString(responseEntity);
                return jsonString;
            }
            else{
                logger.error("请求返回:"+state+"("+url+")");
            }
        }
        finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 参数为json
     * @param url
     * @param jsonStr
     * @return
     * @throws IOException
     */
    public String postJson(String url, String jsonStr) throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        StringEntity stringEntity = new StringEntity(jsonStr, "application/json", "utf-8");
        httpPost.setEntity(stringEntity);

        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(httpPost);
            HttpEntity resultEntity = response.getEntity();
            //System.out.println(entity1.getContentEncoding());
            InputStream inputStream = resultEntity.getContent();
            String content = IOUtils.toString(inputStream);
            EntityUtils.consume(resultEntity);//目的是关闭流
            return content;
        } finally {
            httpPost.releaseConnection();
            if (response != null)
                response.close();
        }

    }

    /**
     * 参数为json
     * @param url
     * @param header
     * @param jsonStr
     * @return
     * @throws IOException
     */
    public String postJson(String url, Map<String, String> header, String jsonStr) throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        StringEntity stringEntity = new StringEntity(jsonStr, "application/json", "utf-8");
        httpPost.setEntity(stringEntity);

        // header
        for (Map.Entry<String, String> entry : header.entrySet()) {
            httpPost.addHeader(entry.getKey(), entry.getValue());
        }

        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(httpPost);
            HttpEntity resultEntity = response.getEntity();
            InputStream inputStream = resultEntity.getContent();
            String content = IOUtils.toString(inputStream);
            EntityUtils.consume(resultEntity);//目的是关闭流
            return content;
        } finally {
            httpPost.releaseConnection();
            if (response != null)
                response.close();
        }
    }
}

