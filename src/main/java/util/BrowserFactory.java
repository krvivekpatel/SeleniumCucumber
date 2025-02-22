package util;


import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;


public class BrowserFactory {

    // ThreadLocal WebDriver to store browser instances for each test thread
    private static ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    // This method returns a WebDriver instance for the current thread (test)
    public WebDriver getDriver() {
        // Check if the WebDriver is already created for the current thread
        if (driverThreadLocal.get() == null) {
            WebDriverManager.chromedriver().setup();  // Automatically download and set up ChromeDriver
            ChromeOptions option = new ChromeOptions();
            option.addArguments("--remote-allow-origins=*");
            driverThreadLocal.set(new ChromeDriver(option));
            driverThreadLocal.get().manage().window().maximize();
        }
        return driverThreadLocal.get();
    }

    // This method closes the WebDriver session for the current thread
    public void closeDriver() {
        if (driverThreadLocal.get() != null) {
            driverThreadLocal.get().quit();
            driverThreadLocal.remove();  // Remove the WebDriver from the current thread
        }
    }
}