package com.ddmc.autotestspringboot.request;

import lombok.Data;


@Data
public class CouponTemplateReq {
    private String templateName;

    private Long basePrice;

    private Long preferentialAmount;

    private Long startTime;

    private Long endTime;

    private Long couponNum;

}
