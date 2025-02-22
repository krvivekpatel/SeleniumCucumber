package utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.util.*;

public class ExcelDataProvider {
    private static final ThreadLocal<Map<String, List<Map<String, String>>>> testData = new ThreadLocal<>();
    private static final String TEST_DATA_PATH = "src/test/resources/testdata/";

    public static void loadTestData(String featureFileName, String scenarioName) {
        String excelFileName = TEST_DATA_PATH + featureFileName.replace(".feature", ".xlsx");
        Map<String, List<Map<String, String>>> dataMap = new HashMap<>();

        try (FileInputStream fis = new FileInputStream(excelFileName);
             Workbook workbook = new XSSFWorkbook(fis)) {
            Sheet sheet = workbook.getSheet(scenarioName);
            if (sheet == null) throw new RuntimeException("Sheet '" + scenarioName + "' not found in " + excelFileName);

            List<Map<String, String>> rowDataList = new ArrayList<>();
            Row headerRow = sheet.getRow(0);
            int rowCount = sheet.getPhysicalNumberOfRows();

            for (int i = 1; i < rowCount; i++) {
                Row row = sheet.getRow(i);
                Map<String, String> rowData = new HashMap<>();
                for (int j = 0; j < headerRow.getPhysicalNumberOfCells(); j++) {
                    String header = headerRow.getCell(j).getStringCellValue();
                    Cell cell = row.getCell(j);
                    String value = cell != null ? cell.toString() : "";
                    rowData.put(header, value);
                }
                rowDataList.add(rowData);
            }
            dataMap.put(scenarioName, rowDataList);
            testData.set(dataMap);
        } catch (Exception e) {
            throw new RuntimeException("Error loading test data from " + excelFileName, e);
        }
    }

    public static List<Map<String, String>> getTestData(String scenarioName) {
        Map<String, List<Map<String, String>>> data = testData.get();
        return data != null ? data.get(scenarioName) : Collections.emptyList();
    }

    public static void clearTestData() {
        testData.remove();
    }
}