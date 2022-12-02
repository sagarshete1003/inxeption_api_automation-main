@Regression @EasyOrder
Feature: My Business Test

  Scenario: Prerequisites - Get Auth-Token
    When user execute HTTP request method: "POST" endpoint: "https://qa-mybiz-app.inxeption.net/" and payload:
      | payload    | mybiz_login                         |
      | email      | test2@inxeption.com |
      | password | Tester11                     |
    And verify response from last request to "https://qa-mybiz-app.inxeption.net/" includes:
      | statusCode | ^200                     |
    Then user save "data.login.token" from the response from "https://qa-mybiz-app.inxeption.net/" into the variable "@token"

  Scenario: Making sure that token order session channel product can be fetch
    When user execute HTTP request method: "POST" endpoint: "https://qa-marketplace-app.inxeption.net/" with token: "@token" and payload:
      | payload    | tokenPurchase          |
    Then user execute HTTP request method: "POST" endpoint: "https://qa-marketplace-app.inxeption.net/" with token: "@token" and payload:
      | payload    | bizTokenTxns          |
    And verify response from last request to "https://qa-marketplace-app.inxeption.net/" includes:
      | statusCode | ^200                     |

