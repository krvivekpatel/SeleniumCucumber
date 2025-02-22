package stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.Logger;
import utils.TestContext;
import org.openqa.selenium.WebDriver;

import java.util.List;
import java.util.Map;

public class LoginSteps {
    private TestContext context = TestContext.getInstance();
    private WebDriver driver = context.getDriver();
    private Logger logger = context.getLogger();
    private List<Map<String, String>> testData;

    @Given("I am on the login page")
    public void i_am_on_the_login_page() {
        testData = context.getTestData();
        logger.info("Navigating to login page");
        driver.get("http://example.com/login"); // Replace with actual URL
    }


    @Then("I should see the dashboard")
    public void i_should_see_the_dashboard() {
        logger.info("Thread " + Thread.currentThread().getId() + ": Verifying dashboard displayed");
        // Replace with actual validation
    }

    @When("I enter username and password")
    public void iEnterUsernameAndPassword() {
        Map<String, String> data = testData.get(0); // Use first row for simplicity
        String username = data.get("username");
        String password = data.get("password");
        logger.info("Thread " + Thread.currentThread().getId() + ": Entering username=" + username + ", password=" + password);
        // Replace with actual Selenium code to enter credentials
    }
}