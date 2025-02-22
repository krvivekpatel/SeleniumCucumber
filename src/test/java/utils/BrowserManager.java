package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.io.FileInputStream;
import java.time.Duration;
import java.util.Properties;

public class BrowserManager {
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private static final Logger logger = LogManager.getLogger(BrowserManager.class);
    private static final Properties config = loadConfig();

    public enum BrowserType {
        CHROME,
        FIREFOX
    }

    // Load configuration from properties file
    private static Properties loadConfig() {
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream("src/test/resources/config.properties")) {
            props.load(fis);
            logger.info("Loaded configuration from config.properties");
        } catch (Exception e) {
            logger.error("Failed to load config.properties, using defaults", e);
            // Set default values
            props.setProperty("browser", "chrome");
            props.setProperty("headless", "false");
            props.setProperty("windowWidth", "1920");
            props.setProperty("windowHeight", "1080");
            props.setProperty("implicitWait", "10");
            props.setProperty("pageLoadTimeout", "30");
        }
        return props;
    }

    // Get browser type from config or system property
    private static BrowserType getBrowserType() {
        String browser = System.getProperty("browser", config.getProperty("browser", "chrome")).toLowerCase();
        switch (browser) {
            case "firefox":
                return BrowserType.FIREFOX;
            case "chrome":
            default:
                return BrowserType.CHROME;
        }
    }

    // Initialize WebDriver with options
    public static WebDriver initializeDriver() {
        if (driver.get() == null) {
            BrowserType browserType = getBrowserType();
            boolean headless = Boolean.parseBoolean(config.getProperty("headless", "false"));
            int width = Integer.parseInt(config.getProperty("windowWidth", "1920"));
            int height = Integer.parseInt(config.getProperty("windowHeight", "1080"));
            long implicitWait = Long.parseLong(config.getProperty("implicitWait", "10"));
            long pageLoadTimeout = Long.parseLong(config.getProperty("pageLoadTimeout", "30"));

            switch (browserType) {
                case CHROME:
                    WebDriverManager.chromedriver().setup();
                    ChromeOptions chromeOptions = new ChromeOptions();
                    chromeOptions.setHeadless(headless);
                    chromeOptions.addArguments("--window-size=" + width + "," + height);
                    chromeOptions.addArguments("--disable-gpu"); // Recommended for headless
                    driver.set(new ChromeDriver(chromeOptions));
                    logger.info("Initialized ChromeDriver with options: headless=" + headless + ", size=" + width + "x" + height + " for thread: " + Thread.currentThread().getId());
                    break;
                case FIREFOX:
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    firefoxOptions.setHeadless(headless);
                    firefoxOptions.addArguments("-width=" + width);
                    firefoxOptions.addArguments("-height=" + height);
                    driver.set(new FirefoxDriver(firefoxOptions));
                    logger.info("Initialized FirefoxDriver with options: headless=" + headless + ", size=" + width + "x" + height + " for thread: " + Thread.currentThread().getId());
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported browser type: " + browserType);
            }

            // Apply timeouts
            //driver.get().manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));
            //driver.get().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(pageLoadTimeout));
            logger.info("Set implicit wait to " + implicitWait + "s and page load timeout to " + pageLoadTimeout + "s");
        }
        return driver.get();
    }

    // Get the current WebDriver instance
    public static WebDriver getDriver() {
        if (driver.get() == null) {
            return initializeDriver();
        }
        return driver.get();
    }

    // Quit the WebDriver and clean up
    public static void quitDriver() {
        if (driver.get() != null) {
            logger.info("Quitting WebDriver for thread: " + Thread.currentThread().getId());
            driver.get().quit();
            driver.remove();
        }
    }
}