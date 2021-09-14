package com.ddmc.autotestspringboot.api.activitymanager;

import com.ddmc.autotestspringboot.request.CreateDiscountReq;
import com.ddmc.autotestspringboot.utils.Common;
import com.ddmc.autotestspringboot.utils.Env;
import com.ddmc.autotestspringboot.utils.RestHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.core5.http.ParseException;

import java.io.IOException;

public class CreateDiscountApi {

    public static CloseableHttpResponse createDiscountActivity(CreateDiscountReq createDiscountReq) throws IOException, ParseException {
        String testUrl = RestHttpClient.getUrl("application", Env.getUrl(), "createDiscountActivity");
        return RestHttpClient.post(testUrl, Common.objectToJson(createDiscountReq));

    }
}
