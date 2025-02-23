Feature: Login functionality

  @smoke
  Scenario Outline: Login
    Given I am on the login page
    When I enter username "<username>" and password "<password>"
    Then I should see the dashboard
    When I write new login data to excel
    Then I re-read the updated excel data
  @smoke
  Scenario Outline: Login
    Given I am on the login page
    When I enter username "<username>" and password "<password>"
    Then I should see the dashboard
    When I write new login data to excel
    Then I re-read the updated excel data
    Examples:
      | username | password |
      | user1    | pass1    |