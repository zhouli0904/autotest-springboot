package com.ddmc.autotestspringboot.testcase;

import org.testng.ITestContext;
import org.testng.annotations.Test;

public class TestDemo {

    @Test(dependsOnGroups = {"test"})
    public void test011() {
        System.out.println("test011-----");
    }

    @Test(dependsOnMethods = {"test01"})
    public void test02() {
        System.out.println("test02--");
    }

    @Test(groups = {"test"})
    public void test01() throws InterruptedException {
        Thread.sleep(1000);
        System.out.println("test01--");
    }

    @Test
    public void test(ITestContext context) {
        context.setAttribute("name", "ZhangSan");
    }

    @Test
    public void test2(ITestContext context) {
        System.out.println(context.getAttribute("name"));
    }
}
