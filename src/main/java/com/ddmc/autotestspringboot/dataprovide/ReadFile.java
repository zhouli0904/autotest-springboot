package com.ddmc.autotestspringboot.dataprovide;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class ReadFile {

    public static Object[][] readExcel(String filePath, String sheetName) throws IOException {
        File file = new File(filePath);
        FileInputStream fileInputStream = new FileInputStream(file);
        XSSFWorkbook book = new XSSFWorkbook(fileInputStream);
//        XSSFSheet sheet = book.getSheetAt(sheetId);
        XSSFSheet sheet = book.getSheet(sheetName);
        int rows = sheet.getPhysicalNumberOfRows();
        int cells = sheet.getRow(rows-1).getPhysicalNumberOfCells();
        // cells-2 表示最后2列数据不要
        Object[][] ddtData = new Object[rows-1][cells-2];
        for (int i = 1; i < rows; i++) {
            if (sheet.getRow(i) == null) {
                continue;
            }
            // cells-2 与ddtData定义的列值要一样
            for (int j = 0; j < cells-2; j++) {
                if (sheet.getRow(i).getCell(j) == null) {
                    continue;
                }
                XSSFCell cell = sheet.getRow(i).getCell(j);
                cell.setCellType(Cell.CELL_TYPE_STRING);
                String cellValue = cell.getStringCellValue();
                ddtData[i-1][j] = cellValue;
            }
        }

        return ddtData;

    }

    /**
     * 读取指定目录下的文件，返回字符串
     *
     * @param path 路径
     * @return 字符串，读取异常，返回null
     */
    public static String readFileToString(String path) {
        try {
            File file = ResourceUtils.getFile(path);
            return FileUtils.readFileToString(file);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String[] readExcelFirstRow(String path, String sheetName) throws IOException {
        File file = new File(path);
        InputStream is = new FileInputStream(file);
        XSSFWorkbook book = new XSSFWorkbook(is);
        XSSFSheet sheet = book.getSheet(sheetName);
        short cellNum = sheet.getRow(0).getLastCellNum();
        String[] par = new String[cellNum-3];
        for (int i = 0; i < cellNum-3; i++) {
            if (sheet.getRow(0).getCell(i) == null) {
                continue;
            }
            XSSFCell cell = sheet.getRow(0).getCell(i);
            cell.setCellType(Cell.CELL_TYPE_STRING);
            par[i] = cell.getStringCellValue();
        }

        return par;
    }

    public static String getStringPar(String path, String sheetName) throws IOException {
        StringBuilder sb = new StringBuilder();
        String[] strings = readExcelFirstRow(path, sheetName);
        for (int i = 0; i < strings.length; i++) {
            if (i == strings.length-1) {
                sb.append("string ").append(strings[i]);
            }else {
                sb.append("string ").append(strings[i]).append(",");
            }

        }
        return sb.toString();
    }

    public static void main(String[] args) throws IOException {
        Object[][] objects = readExcel("src/main/java/com/ddmc/autotestspringboot/data/testCaseSpringBootDemo.xlsx", "extend");
        System.out.println(Arrays.deepToString(objects));
        //        String s = readFile2Str("/Users/zl/Documents/Java/得物/marketing-auto-test-master/src/main/resources/params/poseidon/studentVenueEdit_01.txt");
//        String s = readFileToString("params/poseidon/studentVenueEdit_01.txt");

//        System.out.println(s);

//        String[] strings = readExcelFirstRow("src/main/java/com/ddmc/autoapi/data/testCase.xlsx", "InterestDiscount");
//        System.out.println(Arrays.toString(strings));
//        String interestDiscount = getStringPar("src/main/java/com/ddmc/autoapi/data/testCase.xlsx", "InterestDiscount");
//        System.out.println(interestDiscount);

    }
}
