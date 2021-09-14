package com.ddmc.autotestspringboot.testcase.goodsDetailPage;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ddmc.autotestspringboot.api.activitymanager.CreateDiscountApi;
import com.ddmc.autotestspringboot.api.activitymanager.CreatePostageApi;
import com.ddmc.autotestspringboot.api.activitymanager.ReviewActivityApi;
import com.ddmc.autotestspringboot.api.goodsdetailpage.ShowActivityTagsApi;
import com.ddmc.autotestspringboot.dataprovide.ReadFile;
import com.ddmc.autotestspringboot.request.*;
import com.ddmc.autotestspringboot.request.activityExtend.DiscountExtend;
import com.ddmc.autotestspringboot.request.activityExtend.PostageExtend;
import com.ddmc.autotestspringboot.testcase.TestBase;
import com.ddmc.autotestspringboot.utils.Common;
import com.ddmc.autotestspringboot.utils.DateTimeUtil;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.core5.http.ParseException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;


public class TestShowActivityTags extends TestBase {

    Long startTime = DateTimeUtil.addDay(LocalDateTime.now(), 1);
    Long endTime = DateTimeUtil.addDay(LocalDateTime.now(), 10);

    @DataProvider(name = "extendData")
    public Object[][] extendData() throws IOException {
        return ReadFile.readExcel(casePath, "extend");
    }

    @Test(dataProvider = "extendData")
    public void createActivity(String basePrice, String discount, String maxDiscountMoney) throws IOException, ParseException {

        CreateDiscountReq discountReq = new CreateDiscountReq();
        discountReq.setActivityName("标签测试折扣活动");
        discountReq.setActivityType(ActivityType.DISCOUNT.getType());
        discountReq.setStartTime(startTime);
        discountReq.setEndTime(endTime);
        discountReq.setExtend(new DiscountExtend(Long.parseLong(basePrice), Integer.parseInt(discount), Long.parseLong(maxDiscountMoney)));
        discountReq.setGoodsList(goodsList);
        CloseableHttpResponse discountRes = CreateDiscountApi.createDiscountActivity(discountReq);
        Long discountActivityId = Common.responseToJSONobj(discountRes).getJSONObject("data").getLong("activityId");
        ReviewActivityApi.reviewActivity(new ReviewActivityReq(discountActivityId));

        System.out.println("创建折扣活动成功");

        CreatePostageActivityReq postageReq = new CreatePostageActivityReq();
        postageReq.setActivityName("标签测试包邮活动");
        postageReq.setActivityType(ActivityType.POSTAGE.getType());
        postageReq.setStartTime(startTime);
        postageReq.setEndTime(endTime);
        postageReq.setExtend(new PostageExtend(Long.parseLong(basePrice)));
        postageReq.setGoodsList(goodsList);
        CloseableHttpResponse postageRes = CreatePostageApi.createPostageActivity(postageReq);
        Long postageActivityId = Common.responseToJSONobj(postageRes).getJSONObject("data").getLong("activityId");
        ReviewActivityApi.reviewActivity(new ReviewActivityReq(postageActivityId));

        System.out.println("创建包邮活动成功");

    }

    @Test(dependsOnMethods = "createActivity")
    public void testShowTags() throws IOException, ParseException {
        ShowActivityTagsReq tagsReq = new ShowActivityTagsReq(goodsId);
        tagsReq.setGoodsId(goodsId);
        CloseableHttpResponse response = ShowActivityTagsApi.showActivityTags(tagsReq);
        JSONObject jsonObject = Common.responseToJSONobj(response);
        System.out.println(jsonObject);

        assert response.getCode() == 200;
        JSONArray data = jsonObject.getJSONArray("data");
        List<String> lists = data.toJavaList(String.class);
        assert lists.contains("限时折扣5折");
        assert lists.contains("包邮");
    }
}
