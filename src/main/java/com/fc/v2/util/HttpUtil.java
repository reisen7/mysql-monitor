package com.fc.v2.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fc.v2.model.monitor.MonitorServerExample;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.lang.Nullable;
import org.apache.http.impl.client.CloseableHttpClient;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @ClassName HttpUtil
 * @Author reisen
 * @Description
 * @date 2024年11月06日
 **/
public class HttpUtil {

    /**
     * Http get请求
     * @param url 连接
     * @return 响应数据
     */
    public static String doGet(String url){

        try (CloseableHttpClient httpclient = HttpClients.createDefault()){
            CloseableHttpResponse response = null;
            HttpGet httpget = new HttpGet(url);
            response = httpclient.execute(httpget);
            int statusCode = response.getStatusLine().getStatusCode();
            if (HttpStatus.SC_OK == statusCode) {
                String result = EntityUtils.toString(response.getEntity());
                return result;
            }else{
                return "调用GET失败";
            }
        } catch (IOException e1) {
            e1.printStackTrace();
            return "调用GET失败";
        }

    }


    /**
     * Http post请求
     * @param url 连接
     * @param json 参数
     * @return
     */
    public static String doPost(String url, JSONObject json) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()){
            HttpPost post = new HttpPost(url);

            // 首先Header部分需要设定字符集为：uft-8
            post.addHeader("Content-Type", "application/json;charset=utf-8");
            // 此处也需要设定
            post.setHeader("Accept", "*/*");
            post.setHeader("Accept-Encoding", "gzip, deflate, br");
//            post.setHeader("Accept-Language", "zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3");
            post.setHeader("Connection", "keep-alive");
            post.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.97 Safari/537.36");
            post.setEntity(new StringEntity(json.toString(), Charset.forName("UTF-8"))); //设置请求参数
            HttpResponse response = httpClient.execute(post);
            int statusCode = response.getStatusLine().getStatusCode();
            if (HttpStatus.SC_OK == statusCode){
                //返回String
                String res = EntityUtils.toString(response.getEntity());
                return res;
            }else{
                return "调用POST请求失败";
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "调用POST请求失败";
        }

    }

}
