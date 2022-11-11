Feature: Test login functionality

  In order to view content I am interested in
  As a user of marketalertum
  I want to be able to my alerts
  I want to be able to see alerts with all their details
  Depending on how many alerts should be visible

  Scenario: Valid login
    Given I am a user of marketalertum
    When I login using valid credentials
    Then I should see my alerts

  Scenario: Invalid login
    Given I am a user of marketalertum
    When I login using invalid credentials
    Then I should see the login screen again

  Scenario: Alert layout
    Given I am a user of marketalertum
    Given I am an administrator of the website and I upload 3 alerts
    When I view a list of alerts
    Then each alert should contain an icon
    And each alert should contain a heading
    And each alert should contain a description
    And each alert should contain an image
    And each alert should contain a price
    And each alert should contain a link to the original product website

  Scenario: Alert limit
    Given I am a user of marketalertum
    Given I am an administrator of the website and I upload more than 5 alerts
    When I view a list of alerts
    Then I should see 5 alerts

  Scenario Outline: Icon check
    Given I am a user of marketalertum
    Given I am an administrator of the website and I upload an alert of type "<alert-type>"
    When I view a list of alerts
    Then I should see 1 alerts
    And the icon displayed should be "<iconfilename>"

    Examples:
    |alert-type | iconfilename          |
    |1          | https://www.marketalertum.com/images/icon-car.png          |
    |2          | https://www.marketalertum.com/images/icon-boat.png         |
    |3          | https://www.marketalertum.com/images/icon-property-rent.jpg|
    |4          | https://www.marketalertum.com/images/icon-property-sale.jpg|
    |5          | https://www.marketalertum.com/images/icon-toys.png         |
    |6          | https://www.marketalertum.com/images/icon-electronics.png  |
