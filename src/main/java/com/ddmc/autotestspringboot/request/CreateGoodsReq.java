package com.ddmc.autotestspringboot.request;

import lombok.Data;

@Data
public class CreateGoodsReq {

    private String goodsName;

    private Long originPrice;

    private Long goodsNum;

    private Boolean onShelf;
}
