package com.ddmc.autotestspringboot.request;

import com.ddmc.autotestspringboot.request.activityExtend.DiscountExtend;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
public class CreateDiscountReq extends CreateActivityReq{

    private DiscountExtend extend;

    public CreateDiscountReq(String activityName, Long startTime, Long endTime, Integer activityType, List<Long> goodsList, DiscountExtend extend) {
        super(activityName, startTime, endTime, activityType, goodsList);
        this.extend = extend;
    }

    public CreateDiscountReq() {
    }
}
