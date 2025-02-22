package stepDefinition;

import context.PicoContainerConfig;
import context.TestContext;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import util.BrowserFactory;
import util.TestLogger;


public class StepDefinitions {

    private TestContext testContext;
    private BrowserFactory browserFactory;
    private WebDriver driver;

    // Constructor initializes TestContext
    public StepDefinitions() {
        this.testContext = TestContext.get(); // Access the thread-local TestContext
        this.browserFactory = PicoContainerConfig.getContainer().getComponent(BrowserFactory.class);
        this.driver = browserFactory.getDriver();  // Get the WebDriver instance from the factory
    }

    @Given("I open the browser and navigate to the URL {string}")
    public void i_open_the_browser_and_navigate_to_the_URL(String url) {
        // Navigate to the provided URL
        driver.get(url);
    }

    @And("click on search search button")
    public void clickOnSearchSearchButton() {
        driver.findElement(By.name(("btnK")));
    }


    @When("I enter {string} in the search field")
    public void iEnterInTheSearchField(String arg0) {
        TestLogger.getLogger().info("Entering data: {}", arg0);
        driver.findElement(By.name("q")).sendKeys(arg0);
    }


    @Then("result should be displayed")
    public void resultShouldBeDisplayed() {
        boolean displayed =driver.findElement(By.xpath("//*[text()='See more']")).isDisplayed();
        System.out.println("Search result displayed-"+displayed);
    }
}
