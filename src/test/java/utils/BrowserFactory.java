package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.io.FileInputStream;
import java.time.Duration;
import java.util.Properties;

public class BrowserFactory {
    private static final Properties config = loadConfig();

    private static Properties loadConfig() {
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream("src/test/resources/config.properties")) {
            props.load(fis);
        } catch (Exception e) {
            props.setProperty("browser", "chrome");
            props.setProperty("headless", "false");
            props.setProperty("windowWidth", "1920");
            props.setProperty("windowHeight", "1080");
            props.setProperty("implicitWait", "10");
            props.setProperty("pageLoadTimeout", "30");
        }
        return props;
    }

    public static WebDriver createDriver() {
        String browser = System.getProperty("browser", config.getProperty("browser", "chrome")).toLowerCase();
        boolean headless = Boolean.parseBoolean(config.getProperty("headless", "false"));
        int width = Integer.parseInt(config.getProperty("windowWidth", "1920"));
        int height = Integer.parseInt(config.getProperty("windowHeight", "1080"));
        long implicitWait = Long.parseLong(config.getProperty("implicitWait", "10"));
        long pageLoadTimeout = Long.parseLong(config.getProperty("pageLoadTimeout", "30"));

        WebDriver driver;
        switch (browser) {
            case "firefox":
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.setHeadless(headless);
                firefoxOptions.addArguments("-width=" + width);
                firefoxOptions.addArguments("-height=" + height);
                driver = new FirefoxDriver(firefoxOptions);
                break;
            case "chrome":
            default:
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.setHeadless(headless);
                chromeOptions.addArguments("--window-size=" + width + "," + height);
                chromeOptions.addArguments("--disable-gpu");
                chromeOptions.addArguments("--remote-allow-origins=*");
                driver = new ChromeDriver(chromeOptions);
                break;
        }

        //driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));
        //driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(pageLoadTimeout));
        return driver;
    }
}