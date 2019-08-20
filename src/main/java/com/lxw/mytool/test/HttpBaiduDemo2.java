package com.lxw.mytool.test;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
public class HttpBaiduDemo2 {
    public static void main(String[] args) throws IOException {
        String keyWord = "湖北西塞山工业园区";
        searchEverything(keyWord);
    }

    public static void searchEverything(String keyWord) throws IOException {
        URL url = new URL("http://www.baidu.com/s?wd="+keyWord);//搜索功能在二级域名中
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setDoInput(true);
        httpURLConnection.setRequestMethod("GET");
        // httpURLConnection.setRequestProperty();
        /**
         * 接收数据
         */
        InputStream inputStream = httpURLConnection.getInputStream();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] b = new byte[1024];
        int len = 0;
        while (true) {
            len = inputStream.read(b);
            if (len == -1) {
                break;
            }
            byteArrayOutputStream.write(b, 0, len);
        }
        System.out.println(byteArrayOutputStream.toString());
    }
}
