package com.ddmc.autotestspringboot.api.goodsdetailpage;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.ddmc.autotestspringboot.api.activitymanager.CreateDiscountApi;
import com.ddmc.autotestspringboot.api.activitymanager.CreatePostageApi;
import com.ddmc.autotestspringboot.api.activitymanager.ReviewActivityApi;
import com.ddmc.autotestspringboot.dataprovide.DataSql;
import com.ddmc.autotestspringboot.request.CreateDiscountReq;
import com.ddmc.autotestspringboot.request.CreatePostageActivityReq;
import com.ddmc.autotestspringboot.request.ReviewActivityReq;
import com.ddmc.autotestspringboot.request.ShowActivityTagsReq;
import com.ddmc.autotestspringboot.utils.Common;
import com.ddmc.autotestspringboot.utils.Env;
import com.ddmc.autotestspringboot.utils.RestHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.core5.http.ParseException;

import java.io.IOException;

public class ShowActivityTagsApi {

    public static CloseableHttpResponse showActivityTags(ShowActivityTagsReq tagsReq) throws IOException, ParseException {
        // 在数据库里查找一个 有价格 上架 有库存 的商品id
//        Long goodsId = (Long) DataSql.getVaildGoods().get("id");
        // 创建折扣活动 并审核
//        CloseableHttpResponse discountRes = CreateDiscountApi.createDiscountActivity(discountReq);
//        JSONObject jsonObject1 = Common.responseToJSONobj(discountRes);
//        Integer discountId = (Integer) jsonObject1.getJSONObject("data").get("activityId");
//        ReviewActivityApi.reviewActivity(new ReviewActivityReq(discountId.longValue()));
//        // 创建包邮活动 并审核
//        CloseableHttpResponse postageRes = CreatePostageApi.createPostageActivity(postageReq);
//        Integer postageId = (Integer) Common.responseToJSONobj(postageRes).getJSONObject("data").get("activityId");
//        ReviewActivityApi.reviewActivity(new ReviewActivityReq(postageId.longValue()));

        String testUrl = RestHttpClient.getUrl("application", Env.getUrl(), "showActivityTags");
        JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(tagsReq));
        return RestHttpClient.post(testUrl, jsonObject);
    }


}
