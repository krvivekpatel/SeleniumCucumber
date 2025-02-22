package stepdefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class MyStepdefs {
    @Given("I open the browser and navigate to the URL {string}")
    public void iOpenTheBrowserAndNavigateToTheURL(String arg0) {
    }

    @When("I enter {string} in the search field")
    public void iEnterInTheSearchField(String arg0) {
    }

    @And("click on search search button")
    public void clickOnSearchSearchButton() {
    }

    @Then("result should be displayed")
    public void resultShouldBeDisplayed() {
    }
}
