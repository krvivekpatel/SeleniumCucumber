package stepDefinition;

import context.PicoContainerConfig;
import context.TestContext;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import util.*;
public class Hooks {

    private TestContext testContext;
    private BrowserFactory browserFactory;


    public Hooks(BrowserFactory browserFactory, TestContext testContext) {
        this.browserFactory = browserFactory;
        this.testContext = testContext;
    }

    // Before hook for each scenario - sets up the browser session
    @Before
    public void setUp(Scenario scenario) {
        // Set up TestContext for the scenario
        TestContext testContext = TestContext.get();
        testContext.setScenarioName(scenario.getName());
        testContext.setFeatureFileName(scenario.getUri().toString());
        testContext.setScenarioData("stepIndex", 0);  // Initialize step index for scenario
        TestLogger.setScenarioLogger("test");
        TestLogger.getLogger().info("Scenario name set: {}", testContext.getScenarioName());


    }

    // After hook for each scenario - closes the browser session
    @After
    public void tearDown(Scenario scenario) {
        // Clean up WebDriver and TestContext after each scenario
        TestLogger.getLogger().info("After hook: {}", testContext.getScenarioName());
        browserFactory.closeDriver();
        TestContext.cleanup(); // Clean up the thread-local TestContext
        TestLogger.clearLogger();
        TestLogger.getLogger().info("Browser closed");
    }
}
