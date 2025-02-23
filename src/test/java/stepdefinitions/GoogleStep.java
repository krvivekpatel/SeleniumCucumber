package stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.en.And;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pages.LoginPage;
import pages.GooglePage;
import utils.BrowserManager;
import utils.TestContext;
import utils.TestDataRepository;

public class GoogleStep {
    private static final Logger logger = LogManager.getLogger(LoginSteps.class);
    private final TestContext context;
    private final BrowserManager browserManager;
    private final TestDataRepository dataRepository;
    private final GooglePage googlePage;

    public GoogleStep(TestContext context, BrowserManager browserManager, TestDataRepository dataRepository, GooglePage googlePage) {
        this.context = context;
        this.browserManager = browserManager;
        this.dataRepository = dataRepository;
        this.googlePage = googlePage;
        logger.info("Initialized LoginSteps with test data: " + (context.getTestData() != null ? context.getTestData().size() + " rows" : "null"));
    }

    @Given("I open the browser and navigate to the URL {string}")
    public void i_open_the_browser_and_navigate_to_the_URL(String url) {
        googlePage.navigateToUrl(url);
    }

    @And("click on search search button")
    public void clickOnSearchSearchButton() {
        googlePage.clickOnSearchSearchButton();
    }


    @When("I enter {string} in the search field")
    public void iEnterInTheSearchField(String arg0) {
        googlePage.iEnterInTheSearchField(arg0);

    }


    @Then("result should be displayed")
    public void resultShouldBeDisplayed() {
        googlePage.resultShouldBeDisplayed();
    }

}