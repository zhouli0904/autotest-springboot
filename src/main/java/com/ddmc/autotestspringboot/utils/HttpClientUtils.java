package com.ddmc.autotestspringboot.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

public class HttpClientUtils {

    private static ResourceBundle bundle;
    private static final HttpClient httpClient = new DefaultHttpClient();

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

    public static JSONObject post(String testUrl, JSONObject param) throws IOException {

        HttpPost httpPost = new HttpPost(testUrl);
        httpPost.setHeader("content-type" , "application/json");
        HttpEntity entity = new StringEntity(param.toString(), "utf-8");
        httpPost.setEntity(entity);
        HttpResponse response = httpClient.execute(httpPost);
        String s = EntityUtils.toString(response.getEntity(), "utf-8");
        JSONObject jsonObject = (JSONObject) JSONObject.parse(s);

        return jsonObject;
    }

    public static JSONObject get(String testUrl, JSONObject param) throws URISyntaxException, IOException {
        URIBuilder uriBuilder = new URIBuilder(testUrl);
        Set<Map.Entry<String, Object>> entries = param.entrySet();
        for (Map.Entry<String, Object> entry : entries) {
            uriBuilder.setParameter(entry.getKey(), (String) entry.getValue());
        }
        URI uri = uriBuilder.build();
        System.out.println(uri);
        HttpGet httpGet = new HttpGet(uri);
        HttpResponse response = httpClient.execute(httpGet);
        String s = EntityUtils.toString(response.getEntity(), "utf-8");
        JSONObject jsonObject = (JSONObject) JSONObject.parse(s);
        return jsonObject;
    }



    public static void main(String[] args) throws IOException {
    }

}
