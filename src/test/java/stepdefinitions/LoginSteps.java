package stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import utils.DriverManager;
import utils.ExcelDataProvider;
import org.openqa.selenium.WebDriver;

import java.util.List;
import java.util.Map;

public class LoginSteps {
    private WebDriver driver = DriverManager.getDriver();
    private List<Map<String, String>> testData;

    @Given("I am on the login page")
    public void i_am_on_the_login_page() {
        testData = ExcelDataProvider.getTestData("Successful login with valid credentials");
        driver.get("http://example.com/login"); // Replace with actual URL
    }

    @When("I enter username {string} and password {string}")
    public void i_enter_username_and_password(String usernameKey, String passwordKey) {
        // For simplicity, assume we use the first row of test data
        Map<String, String> data = testData.get(0);
        String username = data.get("username");
        String password = data.get("password");
        System.out.println("Thread " + Thread.currentThread().getId() + ": Username=" + username + ", Password=" + password);
        // Replace with actual Selenium code to enter credentials
    }

    @Then("I should see the dashboard")
    public void i_should_see_the_dashboard() {
        // Replace with actual validation
        System.out.println("Thread " + Thread.currentThread().getId() + ": Dashboard displayed");
    }
}