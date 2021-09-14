package com.ddmc.autotestspringboot.dataprovide;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ddmc.autotestspringboot.utils.Mysql;

public class DataSql {

    // 在数据库里查找一个 有价格 上架 有库存 的商品id
    public static JSONObject getVaildGoods() {
        String sql = "select id from goods_detail where price is not null and on_shelf = 1 and goods_num != 0 order by update_time desc limit 1";
        String db = "test";
        return Mysql.queryOne(sql, db);
    }

    public static JSONObject getReceiveCouponSuccess(Integer templateId) {
        String sql = String.format("select count(*) as num from received_coupon_record where template_id = %d and receive_state = 1", templateId);
        String db = "test";
        return Mysql.queryOne(sql, db);
    }

    public static JSONObject getReceiveCouponUnSuccess(Integer templateId) {
        String sql = String.format("select count(*) as num from received_coupon_record where template_id = %d and receive_state = 0", templateId);
        String db = "test";
        return Mysql.queryOne(sql, db);
    }

    public static JSONObject getCouponRemainNum(Integer templateId) {
        String sql = String.format("select remained_num from coupon_template where id = %d", templateId);
        String db = "test";
        return Mysql.queryOne(sql, db);
    }

    public static void main(String[] args) {
        JSONObject vaildGoods = getVaildGoods();
        System.out.println(vaildGoods.get("id"));
    }

}
