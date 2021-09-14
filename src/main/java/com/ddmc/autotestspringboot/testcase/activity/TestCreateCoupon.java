package com.ddmc.autotestspringboot.testcase.activity;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.ddmc.autotestspringboot.api.activitymanager.CreateCouponApi;
import com.ddmc.autotestspringboot.api.activitymanager.ReviewActivityApi;
import com.ddmc.autotestspringboot.api.goodsdetailpage.CouponsListApi;
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

@SuppressWarnings("unchecked")
public class TestCreateCoupon extends TestBase {

    Long startTime = DateTimeUtil.addDay(LocalDateTime.now(), 1);
    Long endTime = DateTimeUtil.addDay(LocalDateTime.now(), 10);
    Long couponActivityId;
    List<Integer> templateId;
    Long couponNum = 100L;
    List<Integer> activityIdListUnReceived;
    List<Integer> templateIdListUnReceived;

    @Test(description = "创建代金券活动")
    public void testCreateCoupon() throws IOException, ParseException {
        CreateCouponReq couponReq = new CreateCouponReq();
        CouponTemplateReq couponTemplateReq = new CouponTemplateReq();
        couponTemplateReq.setTemplateName("自动化测试券模板");
        couponTemplateReq.setBasePrice(10000L);
        couponTemplateReq.setCouponNum(couponNum);
        couponTemplateReq.setPreferentialAmount(5000L);
        couponTemplateReq.setStartTime(startTime);
        couponTemplateReq.setEndTime(endTime);

        List<CouponTemplateReq> list = new ArrayList<>();
        list.add(couponTemplateReq);
        couponReq.setCouponTemplateReqList(list);
        couponReq.setGoodsList(goodsList);
        couponReq.setActivityName("自动化测试代金券活动");
        couponReq.setActivityType(ActivityType.COUPON.getType());
        couponReq.setStartTime(startTime);
        couponReq.setEndTime(endTime);

        CloseableHttpResponse couponRes = CreateCouponApi.createCoupon(couponReq);
        JSONObject jsonObject = Common.responseToJSONobj(couponRes);
        couponActivityId = jsonObject.getJSONObject("data").getLong("activityId");
        // 审核代金券活动
        ReviewActivityApi.reviewActivity(new ReviewActivityReq(couponActivityId));
        System.out.println(jsonObject);
        assert couponRes.getCode() == 200;


    }

    @Test(description = "查询券列表",dependsOnMethods = "testCreateCoupon")
    public void couponList() throws IOException, ParseException {
        CouponsListReq couponsListReq = new CouponsListReq();
        couponsListReq.setGoodsId(goodsId);
        couponsListReq.setUserId(userId);
        CloseableHttpResponse response = CouponsListApi.couponsList(couponsListReq);
        assert response.getCode() == 200;
        JSONObject object = Common.responseToJSONobj(response);
        System.out.println(object);
        List<Integer> activityIdListAll = (List<Integer>)JSONPath.eval(object, "$.data.activityId");
        // 验证造好的代金券活动id在券列表内
        assert activityIdListAll.contains(couponActivityId.intValue());
        // 取未领取的券模板id，放在list
        activityIdListUnReceived = (List<Integer>)JSONPath.eval(object, "$.data[receiveState=0].activityId");
        templateIdListUnReceived = (List<Integer>)JSONPath.eval(object, "$.data[receiveState=0].templateId");
        // 已创建的代金券的券模板id（只能转换成list，实际里面只会对应一个值）
        templateId = (List<Integer>)JSONPath.eval(object, String.format("$.data[activityId=%d].templateId", couponActivityId));


    }

    @Test(description = "领取已创建的代金券活动的券模板",dependsOnMethods = "couponList")
    public void receiveCoupon() throws IOException, ParseException {
        ReceiveCouponReq req = new ReceiveCouponReq();
        req.setActivityId(couponActivityId);
        req.setGoodsId(goodsId);
        req.setTemplateId(templateId.get(0).longValue());
        req.setUserId(userId);
        CloseableHttpResponse response = ReceiveCouponApi.receiveCoupon(req);
        assert response.getCode() == 200;
        JSONObject jsonObject = Common.responseToJSONobj(response);
        System.out.println(jsonObject);
        System.out.println("templateId:"+ templateId.get(0).longValue());
        assert jsonObject.getString("data").equals("领取成功");
    }


    @Test(description = "领券", dependsOnMethods = "receiveCoupon")
    public void verifyCouponState() throws IOException, ParseException {
        CouponsListReq couponsListReq = new CouponsListReq();
        couponsListReq.setGoodsId(goodsId);
        couponsListReq.setUserId(userId);
        CloseableHttpResponse response = CouponsListApi.couponsList(couponsListReq);
        assert response.getCode() == 200;
        JSONObject object = Common.responseToJSONobj(response);
        System.out.println(object);
        // 验证领取优惠券后券状态是1:已领取
        List<Integer> list = (List<Integer>)JSONPath.eval(object, String.format("$.data[templateId=%d].receiveState", templateId.get(0)));
        assert list.get(0)== 1;

        // 检查券剩余数量减1
        assert DataSql.getCouponRemainNum(templateId.get(0)).getInteger("remained_num") == couponNum.intValue()-1;
    }
}
