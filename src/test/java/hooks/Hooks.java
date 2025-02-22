package hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.logging.log4j.Logger;
import utils.ExcelDataProvider;
import utils.TestContext;

public class Hooks {
    private Logger logger = TestContext.getInstance().getLogger();

    @Before
    public void setUp(Scenario scenario) {
        String featureFileName = scenario.getUri().getPath().substring(scenario.getUri().getPath().lastIndexOf("/") + 1);
        String scenarioName = scenario.getName();
        logger.info("Starting scenario: " + scenarioName + " from feature: " + featureFileName);
        ExcelDataProvider.loadTestData(featureFileName, scenarioName);
        TestContext.getInstance().getDriver(); // Ensure WebDriver is initialized
        logger.info("WebDriver initialized for thread: " + Thread.currentThread().getId());
    }

    @After
    public void tearDown(Scenario scenario) {
        logger.info("Finished scenario: " + scenario.getName() + ", Status: " + scenario.getStatus());
        TestContext.clearInstance();
    }
}