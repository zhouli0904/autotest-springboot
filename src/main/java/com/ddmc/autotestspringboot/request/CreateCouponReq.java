package com.ddmc.autotestspringboot.request;

import lombok.Data;

import java.util.List;

@Data
public class CreateCouponReq extends CreateActivityReq{

    private List<CouponTemplateReq> couponTemplateReqList;

}
