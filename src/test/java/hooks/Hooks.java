package hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import utils.BrowserManager;
import utils.TestContext;
import utils.TestDataRepository;

import java.nio.file.Files;
import java.nio.file.Paths;

public class Hooks {
    private static final Logger logger = LogManager.getLogger(Hooks.class);
    private final TestContext context;
    private final BrowserManager browserManager;
    private final TestDataRepository dataRepository;
    private String logFilePath;

    public Hooks(TestContext context, BrowserManager browserManager, TestDataRepository dataRepository) {
        this.context = context;
        this.browserManager = browserManager;
        this.dataRepository = dataRepository;
    }

    @Before
    public void setUp(Scenario scenario) {
        String featureFileName = scenario.getUri().getPath().substring(scenario.getUri().getPath().lastIndexOf("/") + 1);
        context.setFeatureFileName(featureFileName); // Set in TestContext
        String scenarioName = scenario.getName().replaceAll("[^a-zA-Z0-9]", "_");
        ThreadContext.put("scenarioName", scenarioName);
        logFilePath = "logs/" + scenarioName + ".log";

        logger.info("Starting scenario: " + scenarioName + " from feature: " + featureFileName);
        context.setTestData(dataRepository.readTestData(featureFileName, scenario.getName()));
        browserManager.getDriver();
    }

    @After
    public void tearDown(Scenario scenario) {
        logger.info("Finished scenario: " + scenario.getName() + ", Status: " + scenario.getStatus());
        try {
            String logContent = new String(Files.readAllBytes(Paths.get(logFilePath)));
            scenario.attach(logContent, "text/plain", "Scenario Log: " + scenario.getName());
            logger.info("Embedded log file for scenario: " + scenario.getName());
        } catch (Exception e) {
            logger.error("Failed to embed log file for scenario: " + scenario.getName(), e);
        }
        browserManager.quitDriver();
        ThreadContext.clearMap();
    }
}