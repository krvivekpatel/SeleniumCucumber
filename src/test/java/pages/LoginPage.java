package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.BrowserManager;

import java.time.Duration;

public class LoginPage {
    private static final Logger logger = LogManager.getLogger(LoginPage.class);
    private final BrowserManager browserManager;
    private final WebDriverWait wait;
    private  WebDriver driver;
    // Locators
    private final By usernameField = By.id("username"); // Adjust locator as per your app
    private final By passwordField = By.id("password"); // Adjust locator
    private final By loginButton = By.id("submit");  // Adjust locator
    private final By dashboardIndicator = By.xpath("//*[@class='post-title']"); // Adjust locator

    public LoginPage(BrowserManager browserManager) {
        this.browserManager = browserManager;
        this.driver = browserManager.getDriver(); // Get driver here for wait initialization
        this.wait = new WebDriverWait(driver, 10);
        logger.info("Initialized LoginPage for thread: " + Thread.currentThread().getId());
    }

    public void navigateToLoginPage(String url) {
        logger.info("Navigating to login page: " + url);
        browserManager.getDriver().get(url);
    }

    public void enterUsername(String username) {
        driver = browserManager.getDriver();
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(usernameField));
        element.clear();
        element.sendKeys(username);
        logger.info("Entered username: " + username);
    }

    public void enterPassword(String password) {
        driver = browserManager.getDriver();
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordField));
        element.clear();
        element.sendKeys(password);
        logger.info("Entered password: " + password);
    }

    public void clickLoginButton() {
        driver = browserManager.getDriver();
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(loginButton));
        element.click();
        logger.info("Clicked login button");
    }

    public boolean isDashboardDisplayed() {
        driver = browserManager.getDriver();
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(dashboardIndicator));
            logger.info("Dashboard is displayed");
            return true;
        } catch (Exception e) {
            logger.warn("Dashboard not displayed", e);
            return false;
        }
    }
}