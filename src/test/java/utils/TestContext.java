package utils;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;
import java.util.Map;

public class TestContext {
    private static final ThreadLocal<TestContext> testContext = new ThreadLocal<>();

    private WebDriver driver;
    private List<Map<String, String>> testData;
    private final Logger logger; // Logger instance

    private TestContext() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions option = new ChromeOptions();
        option.addArguments("--remote-allow-origins=*");
        this.driver = new ChromeDriver(option);
        this.testData = null;
        this.logger = LogManager.getLogger(TestContext.class); // Initialize logger
    }

    public static TestContext getInstance() {
        if (testContext.get() == null) {
            testContext.set(new TestContext());
        }
        return testContext.get();
    }

    public static void clearInstance() {
        TestContext context = testContext.get();
        if (context != null) {
            context.logger.info("Cleaning up TestContext for thread: " + Thread.currentThread().getId());
            if (context.driver != null) {
                context.driver.quit();
            }
            testContext.remove();
        }
    }

    // WebDriver getters and setters
    public WebDriver getDriver() {
        return driver;
    }

    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }

    // Test data getters and setters
    public List<Map<String, String>> getTestData() {
        return testData;
    }

    public void setTestData(List<Map<String, String>> testData) {
        this.testData = testData;
    }

    // Logger getter
    public Logger getLogger() {
        return logger;
    }
}