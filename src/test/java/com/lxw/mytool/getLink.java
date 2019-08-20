package com.lxw.mytool;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 首先客户端口先获取大量的百度的关键词 然后比对关键词信息
 *
 * @author timeless <834916321@qq.com>
 */
public class getLink {

    /**
     * 获取 title 标签 遍历的时候直接使用就好了
     *
     * 如果 带着 www 不能访问 就执行 不带者www的
     *
     * @author timeless<834916321@qq.com>
     * @return String title 没获取到则返回空
     */
    public static List<String> getLinkArr(String url) {
        try {
            String charset = "UTF8";
            String htmlsource = getHtmlSource(url, charset);
            //现在有个问题是 百度的 带参数的练级不可以
            List<String> baiduLinkList = getBaiduLink(htmlsource);
            //这个地方可以获取重定向之后的
            return baiduLinkList;
        } catch (IllegalArgumentException ex) {
            System.out.println("不合法的参数：" + ex.toString());
            return new ArrayList<String>();
        }
    }

    /**
     * 根据网址返回网页的源码 getHtmlSource
     *
     * @param htmlUrl 网站url
     * @param charset 网站的编码已经获取的网站编码 防止出现乱码
     * @return 网站的源代码 这样效率有点底 下个版本要改为 之获取前边的几行就好F
     */
    public static String getHtmlSource(String htmlUrl, String charset) {
        URL url;
        StringBuffer sb = new StringBuffer();
        try {
            url = new URL(htmlUrl);
            URLConnection myurlcon = url.openConnection();
            myurlcon.setConnectTimeout(5000);
            myurlcon.setReadTimeout(5000);
            BufferedReader in = null;
            if (!charset.equals("")) {
                in = new BufferedReader(new InputStreamReader(myurlcon.getInputStream(), charset));//读取网页全部内容
            } else {
                in = new BufferedReader(new InputStreamReader(myurlcon.getInputStream()));//读取网页全部内容
            }
            // 现在有个问题  编码  怎么动态获取编码
            String temp;
            while ((temp = in.readLine()) != null) {
                sb.append(temp);
//                System.out.println(temp);
            }
            in.close();
        } catch (ConnectException ex) {
            System.out.println("链接异常：" + ex.toString());
        } catch (UnknownHostException e) {
            System.out.println("未知主机错误:" + e.toString());
        } catch (SocketTimeoutException ex) {
            System.out.println("读取超时:" + ex.toString());
        } catch (MalformedURLException ex) {
            System.out.println("你输入的URL格式有问题！请仔细输入:" + ex.toString());
        } catch (IOException e) {
            System.out.println("io 问题:" + e.toString());
        } catch (IllegalArgumentException ex) {
            System.out.println("不合法的参数：" + ex.toString());
        }
        return sb.toString();
    }

    /**
     * 获取百度的链接
     */
    public static List<String> getBaiduLink(String htmlSource) {
        List<String> list = new ArrayList<String>();
        try {
            //懒惰模式匹配  现在还是有问题的
//            String mat = "[\\.|>](([0-9a-z-]+?)\\.(com|cn|cc|net|org|gov|edu|biz|info|tv|pro|name|coop|cc|club|site|xyz|int|ren|co|hk|me|mobi|(net\\.cn)|(gov\\.cn)|(org\\.cn)|(com\\.cn)|(cn\\.com)))[/|<]";
            //修正不获取获取域名时候已-开头的
            String mat = "[\\.|>](([0-9a-zA-Z]([0-9a-z-])+?)\\.(com|cn|cc|net|org|gov|edu|biz|info|tv|pro|name|coop|cc|club|site|xyz|int|ren|co|hk|me|mobi|(net\\.cn)|(gov\\.cn)|(org\\.cn)|(com\\.cn)|(cn\\.com)))[/|<]";
            Pattern pattern = Pattern.compile(mat);
            Matcher ma = pattern.matcher(htmlSource);
            String link = "";
            while (ma.find()) {
                link = ma.group(1).toString();
                //这个应该改成数组或者link的形式  然后判断是不是已经包含了
                if (!link.equals("baidu.com") && !link.equals("bdstatic.com") && !link.equals("baiducontent.com") && !link.equals("taobao.com") && !link.equals("nuomi.com") && !link.equals("alibaba.com")&& !link.equals("qq.com")) {
                    if (!list.contains(link)) {
                        list.add(link);
                        System.out.println(ma.group(0));
                    }
                }
            }
        } catch (Exception ex) {
            System.out.println("执正则表达式获取域名出错" + ex.toString());
        }
        return list;
    }
    /**
     * 获取重定向之后的链接
     */
    private static String getRedirectUrl(String path) throws Exception {
        HttpURLConnection conn = (HttpURLConnection) new URL(path).openConnection();
        conn.setInstanceFollowRedirects(false);
        conn.setConnectTimeout(5000);
        return conn.getHeaderField("Location");
    }

    public static void main(String[] args) {//     百度 文件搜索   一般的话最多就 76页 pn 到 750
        List<String> list = new ArrayList<String>();
        for (int i = 1; i <= 76; i++) {
            //第一页不显示pn 选项第二页开始pn=1;
            String key = "企业邮箱登录入口";
            int pn = i * 10 - 10;
            String baiduUrl = "http://www.baidu.com/s?wd=%s&pn=%d&ie=utf-8";
            baiduUrl = String.format(baiduUrl, key, pn);
            System.out.println(baiduUrl);
            List<String> perpageList = getLinkArr(baiduUrl);
            for (Iterator<String> iterator = perpageList.iterator(); iterator.hasNext();) {
                String next = iterator.next();
                if (!list.contains(next)) {//排重
                    list.add(next);
                    System.out.println(next);
                }
            }
            System.out.println(list.size());
        }
    }
}