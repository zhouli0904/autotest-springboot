package com.ddmc.autotestspringboot.api.activitymanager;

import com.alibaba.fastjson.JSONObject;
import com.ddmc.autotestspringboot.request.CreateCouponReq;
import com.ddmc.autotestspringboot.utils.Env;
import com.ddmc.autotestspringboot.utils.RestHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.core5.http.ParseException;

import java.io.IOException;

public class CreateCouponApi {

    public static CloseableHttpResponse createCoupon(CreateCouponReq createCouponReq) throws IOException, ParseException {
        String testUrl = RestHttpClient.getUrl("application", Env.getUrl(), "createCouponActivity");
        JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(createCouponReq));
        return RestHttpClient.post(testUrl, jsonObject);
    }
}
