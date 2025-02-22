@Test
Feature: User Login

  # Scenario for a successful login
  Scenario: Successful -Test
    Given I open the browser and navigate to the URL "http://google.com"
    When I enter "test" in the search field
    And click on search search button
    Then result should be displayed

  Scenario: Successful -Test 2
    Given I open the browser and navigate to the URL "http://google.com"
    When I enter "test2" in the search field
    And click on search search button
    Then result should be displayed

  Scenario: Successful -Test 3
    Given I open the browser and navigate to the URL "http://google.com"
    When I enter "test3" in the search field
    And click on search search button
    Then result should be displayed


