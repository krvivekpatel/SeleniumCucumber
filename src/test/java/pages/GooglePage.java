package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.BrowserManager;

public class GooglePage {
    private static final Logger logger = LogManager.getLogger(GooglePage.class);
    private final BrowserManager browserManager;
    private final WebDriverWait wait;
    private final WebDriver driver;

    // Locators
    private final By searchTextBox = By.name("q"); // Adjust locator as per your app
    private final By searchButton = By.name("btnK"); // Adjust locator
    private final By seeMoreLabel = By.xpath("//*[text()='See more']");  // Adjust locator


    public GooglePage(BrowserManager browserManager) {
        this.browserManager = browserManager;
        driver = browserManager.getDriver(); // Get driver here for wait initialization
        this.wait = new WebDriverWait(driver, 10);
        logger.info("Initialized Google Page for thread: " + Thread.currentThread().getId());
    }

    public void clickOnSearchSearchButton() {
        logger.info("Clicking search button");
        driver.findElement(searchButton).click();
    }
    public void iEnterInTheSearchField(String arg0) {
        logger.info("Entering data: {}", arg0);
        driver.findElement(searchTextBox).sendKeys(arg0);
    }
    public void resultShouldBeDisplayed() {
        boolean displayed =driver.findElement(seeMoreLabel).isDisplayed();
        logger.info("Search result displayed-"+displayed);
    }

    public void navigateToUrl(String url){
        driver.get(url);
    }
}