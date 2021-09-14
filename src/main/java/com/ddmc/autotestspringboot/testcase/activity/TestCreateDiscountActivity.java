package com.ddmc.autotestspringboot.testcase.activity;

import com.ddmc.autotestspringboot.api.activitymanager.CreateDiscountApi;
import com.ddmc.autotestspringboot.request.ActivityType;
import com.ddmc.autotestspringboot.request.CreateDiscountReq;
import com.ddmc.autotestspringboot.request.activityExtend.DiscountExtend;
import com.ddmc.autotestspringboot.testcase.TestBase;
import com.ddmc.autotestspringboot.utils.DateTimeUtil;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.core5.http.ParseException;
import org.testng.annotations.Test;

import java.io.IOException;
import java.time.LocalDateTime;

public class TestCreateDiscountActivity extends TestBase {

    @Test(description = "创建折扣活动成功", groups = "activity")
    public void testCreateDiscountActivity01() throws IOException, ParseException {
        CreateDiscountReq req = new CreateDiscountReq();
        req.setActivityName("自动化测试"+ ActivityType.DISCOUNT.getDesc());
        req.setActivityType(ActivityType.DISCOUNT.getType());
        req.setStartTime(DateTimeUtil.addDay(LocalDateTime.now(), 1));
        req.setEndTime(DateTimeUtil.addDay(LocalDateTime.now(), 10));
        DiscountExtend discountExtend = new DiscountExtend();
        discountExtend.setBasePrice(5000L);
        discountExtend.setDiscount(50);
        discountExtend.setMaxDiscountMoney(1000L);
        req.setExtend(discountExtend);
        req.setGoodsList(goodsList);
        CloseableHttpResponse response = CreateDiscountApi.createDiscountActivity(req);
        assert response.getCode() == 200;
    }
}
