package com.ddmc.autotestspringboot.testcase.goods;

import com.alibaba.fastjson.JSONObject;
import com.ddmc.autotestspringboot.api.goodsmanager.CreateGoodsApi;
import com.ddmc.autotestspringboot.dataprovide.ReadFile;
import com.ddmc.autotestspringboot.request.CreateGoodsReq;
import com.ddmc.autotestspringboot.testcase.TestBase;
import com.ddmc.autotestspringboot.utils.Common;
import com.ddmc.autotestspringboot.utils.Env;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.core5.http.ParseException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Arrays;

public class TestCreateGoods extends TestBase {

    @DataProvider(name = "data")
    public Object[][] data() throws IOException {
        System.out.println(casePath);
        System.out.println(Arrays.toString(ReadFile.readExcel(casePath, "createGoods")));
        return ReadFile.readExcel(casePath, "createGoods");
    }

    @Test
    public void testCreateOneGoods() throws IOException, ParseException {
        CreateGoodsReq createGoodsReq = new CreateGoodsReq();
        createGoodsReq.setGoodsName("无籽西瓜");
        createGoodsReq.setOriginPrice(1000L);
        createGoodsReq.setGoodsNum(100L);
        createGoodsReq.setOnShelf(true);
        CloseableHttpResponse response = CreateGoodsApi.createGoods(createGoodsReq, header);
        assert response.getCode() == 200;
    }

    @Test(dataProvider = "data")
    public void testCreateGoodsBatch(String url, String goodsName, String originPrice, String goodsNum, String onShelf) throws IOException, ParseException {
        CreateGoodsReq createGoodsReq = new CreateGoodsReq();
        createGoodsReq.setGoodsName(goodsName);
        createGoodsReq.setGoodsNum(Long.parseLong(goodsNum));
        createGoodsReq.setOriginPrice(Long.parseLong(originPrice));
        createGoodsReq.setOnShelf(Boolean.parseBoolean(onShelf));
        CloseableHttpResponse response = CreateGoodsApi.createGoods(createGoodsReq, header, Env.getUrl()+url);
        assert response.getCode() == 200;
        System.out.println(Common.responseToString(response));
    }

}
