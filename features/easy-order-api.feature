@Regression @EasyOrder
Feature: Easy Order Test

  Scenario: Prerequisites - Get Auth-Token
    When user execute HTTP request method: "POST" endpoint: "https://qa-marketplace-app.inxeption.net/" and payload:
      | payload    | login                         |
      | email      | test2@inxeption.com |
      | passphrase | Tester11                      |
    And verify response from last request to "https://qa-marketplace-app.inxeption.net/" includes:
      | statusCode | ^200                     |
    Then user save "data.customerLogin.token" from the response from "https://qa-marketplace-app.inxeption.net/" into the variable "@token"

  Scenario: Making sure that easy order session channel product can be fetch
    When user execute HTTP request method: "POST" endpoint: "https://qa-marketplace-app.inxeption.net/" with token: "@token" and payload:
      | payload    | session_channel          |
    And verify response from last request to "https://qa-marketplace-app.inxeption.net/" includes:
      | statusCode | ^200                     |

  Scenario: Making sure fetching easy order default channel
    When user execute HTTP request method: "POST" endpoint: "https://qa-marketplace-app.inxeption.net/" with token: "@token" and payload:
      | payload    | default_channel          |
    And verify response from last request to "https://qa-marketplace-app.inxeption.net/" includes:
      | statusCode | ^200                     |

  Scenario: Making sure fetching Contact Account
    When user execute HTTP request method: "POST" endpoint: "https://qa-marketplace-app.inxeption.net/" with token: "@token" and payload:
      | payload    | contact_account          |
      | token      | @token                   |
    And verify response from last request to "https://qa-marketplace-app.inxeption.net/" includes:
      | statusCode | ^200                     |

  Scenario: Making sure fetching Orders
    When user execute HTTP request method: "POST" endpoint: "https://qa-marketplace-app.inxeption.net/" with token: "@token" and payload:
      | payload    | session_contract_account |
      | token      | @token                   |
    And verify response from last request to "https://qa-marketplace-app.inxeption.net/" includes:
      | statusCode | ^200                     |

  Scenario: Making sure fetching Token Conversion Rate
    When user execute HTTP request method: "POST" endpoint: "https://qa-marketplace-app.inxeption.net/" with token: "@token" and payload:
      | payload    | session_contract_account |
      | token      | @token                   |
    And verify response from last request to "https://qa-marketplace-app.inxeption.net/" includes:
      | statusCode | ^200                     |

  Scenario: Making sure fetching Payment Settings
    When user execute HTTP request method: "POST" endpoint: "https://qa-marketplace-app.inxeption.net/" with token: "@token" and payload:
      | payload    | session_contract_account |
      | token      | @token                   |
    And verify response from last request to "https://qa-marketplace-app.inxeption.net/" includes:
      | statusCode | ^200                     |
