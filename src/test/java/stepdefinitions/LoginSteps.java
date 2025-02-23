package stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pages.LoginPage;
import utils.BrowserManager;
import utils.TestContext;
import utils.TestDataRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginSteps {
    private static final Logger logger = LogManager.getLogger(LoginSteps.class);
    private final TestContext context;
    private final BrowserManager browserManager;
    private final TestDataRepository dataRepository;
    private final LoginPage loginPage;

    public LoginSteps(TestContext context, BrowserManager browserManager, TestDataRepository dataRepository) {
        this.context = context;
        this.browserManager = browserManager;
        this.dataRepository = dataRepository;
        this.loginPage = new LoginPage(browserManager); // Pass BrowserManager
        logger.info("Initialized LoginSteps with test data: " + (context.getTestData() != null ? context.getTestData().size() + " rows" : "null"));
    }

    @Given("I am on the login page")
    public void i_am_on_the_login_page() {
        loginPage.navigateToLoginPage("https://practicetestautomation.com/practice-test-login/");
    }

    @When("I enter username {string} and password {string}")
    public void i_enter_username_and_password(String usernameKey, String passwordKey) {
        List<Map<String, String>> testData = context.getTestData();
        Map<String, String> data = testData.get(0);
        String username = data.get("username");
        String password = data.get("password");
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        loginPage.clickLoginButton();
    }

    @Then("I should see the dashboard")
    public void i_should_see_the_dashboard() {
        if (!loginPage.isDashboardDisplayed()) {
            logger.error("Dashboard not displayed as expected");
            throw new AssertionError("Dashboard not displayed");
        }
    }

    @When("I write new login data to excel")
    public void i_write_new_login_data_to_excel() {
        Map<String, String> newData = new HashMap<>();
        newData.put("username", "newUser");
        newData.put("password", "newPass");
        dataRepository.writeTestData(context.getFeatureFileName(), "Successful login with valid credentials", newData);
        context.setTestData(dataRepository.readTestData(context.getFeatureFileName(), "Successful login with valid credentials"));
        logger.info("Wrote new login data to Excel: " + newData);
    }

    @Then("I re-read the updated excel data")
    public void i_re_read_the_updated_excel_data() {
        context.setTestData(dataRepository.readTestData(context.getFeatureFileName(), "Successful login with valid credentials"));
        List<Map<String, String>> testData = context.getTestData();
        logger.info("Re-read updated test data: " + testData.size() + " rows");
        for (Map<String, String> row : testData) {
            logger.info("Row: " + row);
        }
    }

}