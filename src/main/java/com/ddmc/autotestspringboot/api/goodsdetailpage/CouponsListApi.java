package com.ddmc.autotestspringboot.api.goodsdetailpage;

import com.ddmc.autotestspringboot.request.CouponsListReq;
import com.ddmc.autotestspringboot.utils.Common;
import com.ddmc.autotestspringboot.utils.Env;
import com.ddmc.autotestspringboot.utils.RestHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.core5.http.ParseException;

import java.io.IOException;

public class CouponsListApi {

    public static CloseableHttpResponse couponsList(CouponsListReq couponsListReq) throws IOException, ParseException {
        String testUrl = RestHttpClient.getUrl("application", Env.getUrl(), "showCouponsList");
        return RestHttpClient.post(testUrl, Common.objectToJson(couponsListReq));
    }
}
