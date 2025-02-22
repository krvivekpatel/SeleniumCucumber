package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.util.*;

public class ExcelDataProvider {
    private static final String TEST_DATA_PATH = "src/test/resources/testdata/";
    private static final Logger logger = LogManager.getLogger(ExcelDataProvider.class);

    public static void loadTestData(String featureFileName, String scenarioName) {
        String excelFileName = TEST_DATA_PATH + featureFileName.replace(".feature", ".xlsx");
        List<Map<String, String>> rowDataList = new ArrayList<>();

        logger.info("Loading test data for feature: " + featureFileName + ", scenario: " + scenarioName);
        try (FileInputStream fis = new FileInputStream(excelFileName);
             Workbook workbook = new XSSFWorkbook(fis)) {
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
            TestContext.getInstance().setTestData(rowDataList);
            logger.info("Successfully loaded test data for scenario: " + scenarioName);
        } catch (Exception e) {
            logger.error("Error loading test data from " + excelFileName, e);
            throw new RuntimeException("Error loading test data from " + excelFileName, e);
        }
    }

    public static List<Map<String, String>> getTestData() {
        return TestContext.getInstance().getTestData();
    }
}