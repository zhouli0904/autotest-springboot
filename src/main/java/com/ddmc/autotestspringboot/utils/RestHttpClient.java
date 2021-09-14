package com.ddmc.autotestspringboot.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public class RestHttpClient {

    private static ResourceBundle bundle;

    public static String getUrl(String type, String url1, String urlKey) {
        String testUrl = "";
        if (type.equals("discount")) {
            bundle = ResourceBundle.getBundle("discount", Locale.CHINA);
        }else if (type.equals("search")) {
            bundle = ResourceBundle.getBundle("search", Locale.CHINA);
        }else if (type.equals("application")) {
            bundle = ResourceBundle.getBundle("application", Locale.CHINA);
        }
        if (bundle != null) {
            testUrl = url1 + bundle.getString(urlKey);
        }
        return testUrl;
    }

    public static CloseableHttpResponse post(String url, String entityString, Map<String, String> headerMap) throws IOException, ParseException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(new StringEntity(entityString, ContentType.APPLICATION_JSON));
        for (Map.Entry<String, String> entry : headerMap.entrySet()) {
            httpPost.addHeader(entry.getKey(), entry.getValue());
        }
         return client.execute(httpPost);

    }

    public static CloseableHttpResponse post(String url, JSONObject entity, Map<String, String> headerMap) throws IOException, ParseException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(new StringEntity(entity.toString(), ContentType.APPLICATION_JSON));
        for (Map.Entry<String, String> entry : headerMap.entrySet()) {
            httpPost.addHeader(entry.getKey(), entry.getValue());
        }
        return client.execute(httpPost);

    }

    public static CloseableHttpResponse post(String url, JSONObject entity) throws IOException, ParseException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(new StringEntity(entity.toString(), ContentType.APPLICATION_JSON));
        httpPost.addHeader("Content-Type", "application/json");
        CloseableHttpResponse execute = client.execute(httpPost);
        return execute;

    }


    public static CloseableHttpResponse get(String url, Map<String, String> headerMap) throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        for (Map.Entry<String, String> entry : headerMap.entrySet()) {
            httpGet.addHeader(entry.getKey(), entry.getValue());
        }
        return client.execute(httpGet);
    }

    public static CloseableHttpResponse get(String url) throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        return client.execute(httpGet);
    }

    public static void main(String[] args) throws IOException, ParseException {
        JSONObject jsonObject = JSONObject.parseObject("{\n    \"userId\": \"202106091745\",\n    \"channel\": \"ios\",\n    \"commodity\": {\n        \"salePrice\": 500,\n        \"skuId\": \"202108090001\",\n        \"items\": [\n            \"funds1\",\n            \"funds2\"\n        ]\n    }\n}");
        Map<String, String> map = new HashMap<>();
        map.put("Content-Type", "application/json");
        CloseableHttpResponse post = post("http://localhost:8881/interestDiscount", jsonObject, map);
        System.out.println(post.getCode());
    }

}
