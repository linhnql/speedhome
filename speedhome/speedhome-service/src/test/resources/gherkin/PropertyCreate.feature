Feature: Create Property

  Scenario Outline: Successful Create Property
    Given user request to create property with "<name>" "<desc>" "<type>" "<price>"
    When the user do action create property
    Then user action is successful with "<code>"
    Examples:
      | code  |  name            |  desc          | type           | price     |
      | 200   |  Property Name 1 | Desc 1         | RESIDENTIAL    | 1500      |
      | 200   |  Property Name 2 | Desc 2         | AGRICULTURE    | 2000      |
      | 200   |  Property Name 3 | Desc 3         | COMMERCIAL     | 2500      |
      | 200   |  Property Name 4 | Desc 4         | INDUSTRIAL     | 3000      |

  Scenario Outline: Create Property Failed
    Given user request to create property with "<name>" "<desc>" "<type>" "<price>"
    When the user do action create property
    Then user action is failed with "<code>"
    Examples:
      | code  |  name            |  desc          | type           | price     |
      | 400   |                  | Desc 1         | RESIDENTIAL    | 1500      |