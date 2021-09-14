package com.ddmc.autotestspringboot.dataprovide;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

public class ExcelIterator implements Iterator<Object[]> {
    File excelFile;
    Row row;
    Iterator<Row> currentRow;

    public ExcelIterator(File excelFile) throws IOException {
        super();
        this.excelFile = excelFile;
        FileInputStream fileInputStream = new FileInputStream(excelFile);
        XSSFWorkbook book = new XSSFWorkbook(fileInputStream);
        XSSFSheet sheet = book.getSheetAt(0);
    }

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public Object[] next() {
        return new Object[0];
    }
}
