package com.ddmc.autotestspringboot.api.activitymanager;

import com.alibaba.fastjson.JSONObject;
import com.ddmc.autotestspringboot.request.CreatePostageActivityReq;
import com.ddmc.autotestspringboot.utils.Common;
import com.ddmc.autotestspringboot.utils.Env;
import com.ddmc.autotestspringboot.utils.HttpClientUtils;
import com.ddmc.autotestspringboot.utils.RestHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.core5.http.ParseException;

import java.io.IOException;

public class CreatePostageApi {

    public static CloseableHttpResponse createPostageActivity(CreatePostageActivityReq createPostageActivityReq) throws IOException, ParseException {
        String testUrl = RestHttpClient.getUrl("application", Env.getUrl(), "createPostageActivity");
        return RestHttpClient.post(testUrl, Common.objectToJson(createPostageActivityReq));

    }
}
