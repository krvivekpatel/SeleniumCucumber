package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.*;

public class ExcelDataProvider {
    private static final String TEST_DATA_PATH = "src/test/resources/testdata/";
    private static final Logger logger = LogManager.getLogger(ExcelDataProvider.class);

    // Read test data from Excel
    public static void loadTestData(String featureFileName, String scenarioName, TestContext context) {
        String excelFileName = TEST_DATA_PATH + featureFileName.replace(".feature", ".xlsx");
        List<Map<String, String>> rowDataList = new ArrayList<>();

        logger.info("Loading test data for feature: " + featureFileName + ", scenario: " + scenarioName);
        try (FileInputStream fis = new FileInputStream(excelFileName)) {
            Workbook workbook = new XSSFWorkbook(fis);
            Sheet sheet = workbook.getSheet(scenarioName);
            if (sheet == null) {
                logger.error("Sheet '" + scenarioName + "' not found in " + excelFileName);
                throw new RuntimeException("Sheet '" + scenarioName + "' not found in " + excelFileName);
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
            context.setTestData(rowDataList);
            logger.info("Successfully loaded test data for scenario: " + scenarioName);
        } catch (Exception e) {
            logger.error("Error loading test data from " + excelFileName, e);
            throw new RuntimeException("Error loading test data from " + excelFileName, e);
        }
    }

    // Write test data to Excel
    public static void writeTestData(String featureFileName, String scenarioName, TestContext context, Map<String, String> newData) {
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

            // Append new row with data
            int rowCount = sheet.getPhysicalNumberOfRows();
            Row newRow = sheet.createRow(rowCount);
            int cellIndex = 0;
            for (String value : newData.values()) {
                newRow.createCell(cellIndex++).setCellValue(value);
            }

            // Write to file
            try (FileOutputStream fos = new FileOutputStream(excelFileName)) {
                workbook.write(fos);
                logger.info("Successfully wrote test data to Excel for scenario: " + scenarioName);
            }

            // Update TestContext with new data
            List<Map<String, String>> updatedData = context.getTestData() != null ? new ArrayList<>(context.getTestData()) : new ArrayList<>();
            updatedData.add(newData);
            context.setTestData(updatedData);
        } catch (Exception e) {
            logger.error("Error writing test data to " + excelFileName, e);
            throw new RuntimeException("Error writing test data to " + excelFileName, e);
        }
    }
}