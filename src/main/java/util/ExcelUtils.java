package util;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;

public class ExcelUtils {

    private static XSSFWorkbook workbook;

    public static void loadExcelFile(String filePath) throws IOException {
        FileInputStream file = new FileInputStream(filePath);
        workbook = new XSSFWorkbook(file);
    }

    public static String getScenarioData(String scenarioName, int stepNumber) {
        XSSFSheet sheet = workbook.getSheet(scenarioName);
        Row row = sheet.getRow(stepNumber);
        return row.getCell(1).getStringCellValue(); // Assuming test data is in the second column
    }
}
