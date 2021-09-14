package com.ddmc.autotestspringboot.testcase.goodsDetailPage;

import com.alibaba.fastjson.JSONObject;
import com.ddmc.autotestspringboot.api.activitymanager.CreateCouponApi;
import com.ddmc.autotestspringboot.api.activitymanager.ReviewActivityApi;
import com.ddmc.autotestspringboot.api.goodsdetailpage.ReceiveCouponApi;
import com.ddmc.autotestspringboot.dataprovide.DataSql;
import com.ddmc.autotestspringboot.request.*;
import com.ddmc.autotestspringboot.testcase.TestBase;
import com.ddmc.autotestspringboot.utils.Common;
import com.ddmc.autotestspringboot.utils.DateTimeUtil;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.core5.http.ParseException;
import org.testng.annotations.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TestReceiveCoupon extends TestBase {

    Long startTime = DateTimeUtil.addDay(LocalDateTime.now(), 1);
    Long endTime = DateTimeUtil.addDay(LocalDateTime.now(), 10);
    Long couponActivityId;
    Integer templateId;
    Long couponNum = 10L;

    @Test(enabled = true, priority = 1)
    public void createCouponActivity() throws IOException, ParseException {
        CreateCouponReq couponReq = new CreateCouponReq();
        CouponTemplateReq couponTemplateReq = new CouponTemplateReq();
        couponTemplateReq.setTemplateName("自动化测试券模板并发");
        couponTemplateReq.setBasePrice(10000L);
        couponTemplateReq.setCouponNum(couponNum);
        couponTemplateReq.setPreferentialAmount(5000L);
        couponTemplateReq.setStartTime(startTime);
        couponTemplateReq.setEndTime(endTime);

        List<CouponTemplateReq> list = new ArrayList<>();
        list.add(couponTemplateReq);
        couponReq.setCouponTemplateReqList(list);
        couponReq.setGoodsList(goodsList);
        couponReq.setActivityName("自动化测试代金券活动并发");
        couponReq.setActivityType(ActivityType.COUPON.getType());
        couponReq.setStartTime(startTime);
        couponReq.setEndTime(endTime);

        CloseableHttpResponse couponRes = CreateCouponApi.createCoupon(couponReq);
        JSONObject jsonObject = Common.responseToJSONobj(couponRes);
        couponActivityId = jsonObject.getJSONObject("data").getLong("activityId");
        // 审核代金券活动
        ReviewActivityApi.reviewActivity(new ReviewActivityReq(couponActivityId));
        templateId = (Integer)jsonObject.getJSONObject("data").getJSONArray("templateIdList").get(0);
        System.out.println(jsonObject);
        assert couponRes.getCode() == 200;
    }

    // threadPoolSize 线程数  invocationCount 该方法执行的总次数
    @Test(threadPoolSize = 12, invocationCount = 12, enabled = true, priority = 2)
    public void synchronizedReceiveCoupon() throws IOException, ParseException {
        // 随机生成8位数的用户id
        Long userId = Common.generateUserId(8);
        ReceiveCouponReq req = new ReceiveCouponReq();
        req.setActivityId(couponActivityId);
        req.setGoodsId(goodsId);
        req.setTemplateId(templateId.longValue());
        req.setUserId(userId);
        CloseableHttpResponse response = ReceiveCouponApi.receiveCoupon(req);
        JSONObject jsonObject = Common.responseToJSONobj(response);
        System.out.println(jsonObject);
    }

    @Test(priority = 3, description = "校验检查结果")
    public void checkResult() {
        JSONObject jsonObject = DataSql.getReceiveCouponSuccess(templateId);
        System.out.println(jsonObject);
        // 校验领取记录表中领取成功的记录和券总数一致
        assert jsonObject.getInteger("num") == couponNum.intValue();
        JSONObject couponRemainNum = DataSql.getCouponRemainNum(templateId);
        // 校验券模板的剩余券数量为0
        System.out.println(couponRemainNum);
        assert couponRemainNum.getInteger("remained_num") == 0;
    }

}
