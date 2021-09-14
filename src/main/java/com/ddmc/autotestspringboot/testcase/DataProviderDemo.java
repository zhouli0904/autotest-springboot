package com.ddmc.autotestspringboot.testcase;

import com.ddmc.autotestspringboot.dataprovide.ExcelIterator;
import org.testng.annotations.DataProvider;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

public class DataProviderDemo {

    @DataProvider(name = "provider")
    private Iterator<Object[]> provider() throws IOException {
        return new ExcelIterator(new File("/Users/admin/Downloads/test.xlsx"));
    }
}
