package com.ddmc.autotestspringboot.testcase;

import com.ddmc.autotestspringboot.dataprovide.ReadFile;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;

public class TestDemo2 {

    @DataProvider(name = "extendData")
    public Object[][] extendData() throws IOException {
        return ReadFile.readExcel("src/main/java/com/ddmc/autotestspringboot/data/testCaseSpringBootDemo.xlsx", "extend");
    }

    @Test(dataProvider = "extendData")
    public void test22(String basePrice, String discount, String maxDiscountMoney) {
        System.out.println(basePrice);
        System.out.println(discount);
        System.out.println(maxDiscountMoney);
    }

    @Test(dependsOnMethods = "test22")
    public void test23() {
        System.out.println("hahahah");
    }
}
