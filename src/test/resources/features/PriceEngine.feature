@debug
Feature: PriceEngine

  Scenario Outline: Check which countries are supported
    Given I want to know which countries are supported
    When I submit users id "<id>" and market language "<accepts_language>"
    Then I should see currency "<currency>" and price

    Examples:
      | id  | accepts_language | currency |
      | 1   | en-US            | $        |
      | 10  | en-GB            | £        |
      | 100 | es-ES            | €        |

  Scenario Outline: Check which countries are NOT supported
    Given I want to know which countries are not supported
    When I submit users id "<id>" and market language "<accepts_language>"
    Then I should see an error message that language "<accepts_language>" is not supported

    Examples:
      | id  | accepts_language |
      | 2   | de-CH            |
      | 22  | ko-KR            |
      | 222 | ru-RU            |


