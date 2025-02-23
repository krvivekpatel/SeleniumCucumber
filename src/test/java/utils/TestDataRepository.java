package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.*;

public class TestDataRepository {
    private static final String TEST_DATA_PATH = "src/test/resources/testdata/";
    private static final Logger logger = LogManager.getLogger(TestDataRepository.class);

    public List<Map<String, String>> readTestData(String featureFileName, String scenarioName) {
        String excelFileName = TEST_DATA_PATH + featureFileName.replace(".feature", ".xlsx");
        List<Map<String, String>> rowDataList = new ArrayList<>();

        logger.info("Reading test data for feature: " + featureFileName + ", scenario: " + scenarioName);
        try (FileInputStream fis = new FileInputStream(excelFileName)) {
            Workbook workbook = new XSSFWorkbook(fis);
            Sheet sheet = workbook.getSheet(scenarioName);
            if (sheet == null) {
                logger.warn("Sheet '" + scenarioName + "' not found, returning empty data");
                return rowDataList; // Return empty list instead of throwing exception
            }

            Row headerRow = sheet.getRow(0);
            int rowCount = sheet.getPhysicalNumberOfRows();
            logger.debug("Found " + (rowCount - 1) + " data rows in sheet: " + scenarioName);

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
            logger.info("Successfully read test data for scenario: " + scenarioName);
            return rowDataList;
        } catch (Exception e) {
            logger.error("Error reading test data from " + excelFileName, e);
            throw new RuntimeException("Error reading test data", e);
        }
    }

    public void writeTestData(String featureFileName, String scenarioName, Map<String, String> newData) {
        String excelFileName = TEST_DATA_PATH + featureFileName.replace(".feature", ".xlsx");
        logger.info("Writing test data to Excel for feature: " + featureFileName + ", scenario: " + scenarioName);

        try (FileInputStream fis = new FileInputStream(excelFileName);
             Workbook workbook = new XSSFWorkbook(fis)) {
            Sheet sheet = workbook.getSheet(scenarioName);
            if (sheet == null) {
                sheet = workbook.createSheet(scenarioName);
                Row headerRow = sheet.createRow(0);
                int cellIndex = 0;
                for (String key : newData.keySet()) {
                    headerRow.createCell(cellIndex++).setCellValue(key);
                }
            }

            int rowCount = sheet.getPhysicalNumberOfRows();
            Row newRow = sheet.createRow(rowCount);
            int cellIndex = 0;
            for (String value : newData.values()) {
                newRow.createCell(cellIndex++).setCellValue(value);
            }

            try (FileOutputStream fos = new FileOutputStream(excelFileName)) {
                workbook.write(fos);
                logger.info("Successfully wrote test data to Excel for scenario: " + scenarioName);
            }
        } catch (Exception e) {
            logger.error("Error writing test data to " + excelFileName, e);
            throw new RuntimeException("Error writing test data", e);
        }
    }
}