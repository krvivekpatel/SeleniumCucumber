@Test
Feature: Login

  # Scenario for a successful login
  Scenario: Successful
    Given I am on the login page
    When I enter username and password
    And I should see the dashboard

  Scenario: Successful-1
    Given I am on the login page
    When I enter username and password
    And I should see the dashboard

  Scenario: Successful-2
    Given I am on the login page
    When I enter username and password
    And I should see the dashboard