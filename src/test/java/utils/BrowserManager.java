package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

public class BrowserManager {
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private static final Logger logger = LogManager.getLogger(BrowserManager.class);

    public WebDriver getDriver() {
        if (driver.get() == null) {
            driver.set(BrowserFactory.createDriver());
            logger.info("Initialized WebDriver for thread: " + Thread.currentThread().getId());
        }
        return driver.get();
    }

    public void quitDriver() {
        if (driver.get() != null) {
            logger.info("Quitting WebDriver for thread: " + Thread.currentThread().getId());
            driver.get().quit();
            driver.remove();
        }
    }
}