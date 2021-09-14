package com.ddmc.autotestspringboot.request;

import lombok.Data;

@Data
public class ReceiveCouponReq {

    private Long userId;

    private Long templateId;

    private Long goodsId;

    private Long activityId;

}
