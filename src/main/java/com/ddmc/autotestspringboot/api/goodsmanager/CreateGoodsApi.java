package com.ddmc.autotestspringboot.api.goodsmanager;

import com.alibaba.fastjson.JSONObject;
import com.ddmc.autotestspringboot.request.CreateGoodsReq;
import com.ddmc.autotestspringboot.utils.Env;
import com.ddmc.autotestspringboot.utils.RestHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.core5.http.ParseException;

import java.io.IOException;
import java.util.Map;

public class CreateGoodsApi {

    public static CloseableHttpResponse createGoods(CreateGoodsReq createGoodsReq, Map<String, String> header) throws IOException, ParseException {
        JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(createGoodsReq));
        String testUrl = RestHttpClient.getUrl("application", Env.getUrl(), "createGoods");
        System.out.println(testUrl);
        return RestHttpClient.post(testUrl, jsonObject, header);
    }

    public static CloseableHttpResponse createGoods(CreateGoodsReq createGoodsReq, Map<String, String> header, String testUrl) throws IOException, ParseException {
        JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(createGoodsReq));
        return RestHttpClient.post(testUrl, jsonObject, header);
    }
}
