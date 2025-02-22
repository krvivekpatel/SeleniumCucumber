Feature: Login functionality

  Scenario Outline: Successful login with valid credentials
    Given I am on the login page
    When I enter username "<username>" and password "<password>"
    Then I should see the dashboard

    Examples:
      | username | password |
      | user1    | pass1    |
      | user2    | pass2    |