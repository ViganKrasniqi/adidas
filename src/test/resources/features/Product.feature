Feature: Product

  Scenario: Product journey experience
    Given I create a unique product
    And I update the unique product with different values
    When I delete the unique product
    Then I should see the product is no longer in the list

  Scenario: Check product list
    Given I want to retrieve a list of all the products
    When I submit the request to retrieve the list of all the products
    Then I should see a list of all the products



  Scenario: User attempts to retrieve a product that doesnt exist
    Given I want to retrieve a product not listed
    When I attempt to retrieve the product
    Then I should see an error message informing me that the product is not in the list

  Scenario: User attempts to create duplicate products
    Given I have an existing product
    And I want to duplicate it
    When I attempt to create a product
    Then I should see an error message informing me that the product already exists


