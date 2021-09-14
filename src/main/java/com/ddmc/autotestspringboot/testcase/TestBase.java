package com.ddmc.autotestspringboot.testcase;

import org.testng.annotations.BeforeClass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestBase {

    public Map<String, String> header;
    public String casePath;
    public List<Long> goodsList = new ArrayList<>();
    public Long goodsId = 20210718L;
    public Long userId = 123L;

    @BeforeClass
    public void setUp() {
        header = new HashMap<>();
        header.put("Content-Type", "application/json");
        casePath = "src/main/java/com/ddmc/autotestspringboot/data/testCaseSpringBootDemo.xlsx";
        goodsList.add(goodsId);
    }

}
