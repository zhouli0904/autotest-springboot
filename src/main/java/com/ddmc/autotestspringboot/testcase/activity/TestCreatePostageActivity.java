package com.ddmc.autotestspringboot.testcase.activity;

import com.ddmc.autotestspringboot.api.activitymanager.CreatePostageApi;
import com.ddmc.autotestspringboot.request.ActivityType;
import com.ddmc.autotestspringboot.request.CreatePostageActivityReq;
import com.ddmc.autotestspringboot.request.activityExtend.PostageExtend;
import com.ddmc.autotestspringboot.testcase.TestBase;
import com.ddmc.autotestspringboot.utils.DateTimeUtil;
import com.ddmc.autotestspringboot.utils.Env;
import com.ddmc.autotestspringboot.utils.RestHttpClient;
import org.apache.hc.core5.http.ParseException;
import org.testng.annotations.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TestCreatePostageActivity extends TestBase {

    @Test(description = "创建包邮活动成功", groups = "activity")
    public void testCreatePostageActivity01() throws IOException, ParseException {
        CreatePostageActivityReq req = new CreatePostageActivityReq();
        req.setActivityName("自动化测试"+ ActivityType.POSTAGE.getDesc());
        req.setActivityType(ActivityType.POSTAGE.getType());
        req.setStartTime(DateTimeUtil.addDay(LocalDateTime.now(), 1));
        req.setEndTime(DateTimeUtil.addDay(LocalDateTime.now(), 10));
        PostageExtend postageExtend = new PostageExtend();
        postageExtend.setBasePrice(3000L);
        req.setExtend(postageExtend);
//        List<Long> list = new ArrayList<>();
//        list.add(20210663L);
        req.setGoodsList(goodsList);
        CreatePostageApi.createPostageActivity(req);
    }
}
