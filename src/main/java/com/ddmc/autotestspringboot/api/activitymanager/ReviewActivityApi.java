package com.ddmc.autotestspringboot.api.activitymanager;

import com.alibaba.fastjson.JSONObject;
import com.ddmc.autotestspringboot.request.ReceiveCouponReq;
import com.ddmc.autotestspringboot.request.ReviewActivityReq;
import com.ddmc.autotestspringboot.utils.Common;
import com.ddmc.autotestspringboot.utils.Env;
import com.ddmc.autotestspringboot.utils.RestHttpClient;
import com.mongodb.util.JSON;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.core5.http.ParseException;

import java.io.IOException;

public class ReviewActivityApi {

    public static CloseableHttpResponse reviewActivity(ReviewActivityReq reviewActivityReq) throws IOException, ParseException {
        String testUrl = RestHttpClient.getUrl("application", Env.getUrl(), "reviewActivity");
        JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(reviewActivityReq));
        return RestHttpClient.post(testUrl, jsonObject);
    }
}
