package hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import utils.DriverManager;
import utils.ExcelDataProvider;

public class Hooks {
    @Before
    public void setUp(Scenario scenario) {
        String featureFileName = scenario.getUri().getPath().substring(scenario.getUri().getPath().lastIndexOf("/") + 1);
        String scenarioName = scenario.getName();
        ExcelDataProvider.loadTestData(featureFileName, scenarioName);
        DriverManager.getDriver(); // Initialize WebDriver
    }

    @After
    public void tearDown() {
        DriverManager.quitDriver();
        ExcelDataProvider.clearTestData();
    }
}