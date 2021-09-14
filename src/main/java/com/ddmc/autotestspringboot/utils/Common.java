package com.ddmc.autotestspringboot.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.core5.http.io.entity.EntityUtils;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class Common {

    // 获取当前时间
    public static long getCurrentTime() throws ParseException {
        return System.currentTimeMillis();
    }

    // "2021-08-06 11:24:11" 转化成 long
    public static long getTime(String time) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.parse(time).getTime();
    }

    public static boolean compareJsonString(String str1, String str2) {
        JSONObject object1 = JSONObject.parseObject(str1);
        JSONObject object2 = JSONObject.parseObject(str2);
        return object1.equals(object2);
    }

    public static JSONObject objectToJson(Object o) {
        return JSONObject.parseObject(JSONObject.toJSONString(o));
    }

    public static String responseToString(CloseableHttpResponse response) throws IOException, org.apache.hc.core5.http.ParseException {
        return EntityUtils.toString(response.getEntity());
    }

    public static JSONObject responseToJSONobj(CloseableHttpResponse response) throws IOException, org.apache.hc.core5.http.ParseException {
        return JSONObject.parseObject(responseToString(response));

    }

    /**
     * java生成随机数字10位数
     *
     * @return
     */
    public static Long generateUserId(int length) {

        StringBuilder val = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            val.append(random.nextInt(9)+1);
        }
        return Long.parseLong(val.toString());
    }

    public static void main(String[] args) throws ParseException {
        System.out.println(new Date());
        System.out.println(getCurrentTime());
        long time = getTime("2021-08-06 11:24:11");
        System.out.println(time);

        for (int i = 0; i < 30; i++) {
            Long aLong = generateUserId(5);
            System.out.println(aLong);
        }


    }

}
