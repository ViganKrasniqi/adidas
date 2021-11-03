<h1 align="center">Adidas assignment</h1>

# Install and requirements

1. Docker
2. Java JDK
3. Jmeter
4. Any IDE

Note:
You can find the assignment here  [Vigan Krasniqi assignment](https://github.com/ViganKrasniqi/adidas). 

**Docker compose to run the products services all in one**

It runs a mongo db, product service and price engine after running the following command.

`docker-compose up`

To run the tests type ```mvn verify``` from the terminal

```shell
mvn clean install
```

## Executing the tests
To execute the entire set of tests write on the command promp

```shell
mvn test
```
The test results will be in `target/site/serenity` directory.

## 📁 Folder structure
```
.idea/
src/
├─ main/
│  ├─ java/
│  │  ├─ controller/
│  │  ├─ EnvironmentController.java
│  │  │
│  │  ├─ CoreAPI/
│  │  │  ├─ Endpoints.enum
│  │  │  ├─ MobiquityClient.java
│  │  │
│  │  ├─ model/
│  │  ├─ Environment.java
│  │  ├─ PriceEngine.java
│  │  ├─ Product.java
│  │  ├─ ProductComplete.java
│  │  │
│  │  ├─ resources/
│  │  │  ├─ createValidProduct/
│  │  │  ├─ updateValidProduct.json
│  │  │
├─ test/
│  ├─ java/
│  │  ├─ stepdefinitions/
│  │  │  ├─ PriceEngineStepDef.java
│  │  │  ├─ ProductStepDef.java
│  │  │
│  │  ├─ steps/
│  │  │  ├─ PriceEngineSteps.java
│  │  │  ├─ ProductSteps.java
│  │  ├─ CucumberTestSuite.java
│  │
│  ├─ resources/
│  │  ├─ features/
│  │  │  ├─ PriceEngine.feature
│  │  │  ├─ Product.feature
.gitattributes
.gitignore
docker-compose.yml
pom.xml
README.md
Mobiquity.iml
serenity.properties
```
## Performance test analysis

We have run a performance test on the following endpoints:
1. GetProductList
2. Get Product By ID
3. Create Product
4. Delete Product
5. Update Product

The findings are as followed:

After running the performance test I've noticed that with a range of 10-50 its stable were as if we increase the calls 100-200 the API fell down rapidly from 40.2m/s about 14.4 m/s.
Latency spiked to a range which the developers must take action to change the code structure. Due to limitations of running the test locally the system can handle a certain amount of requests
80-120 at best. If we increase it to 150-500 users then it drastically falls down. Since the assignment was time sensitive I could not conduct more performance test
as the scale was increasing the time to wait for the output in order to aggregate the data.

Suggestions:

The core logic of how the price engine behaves must be changed
Change the schema of API to reduce repeating information overall in the system
Develop pagination in the API so we load numbers of products in a API call

##Bugs found

**Title** - User is able to get empty list retrieves instead of error message

**Desccription** - Given the user wants to retrieve a list of all the products and there are no products, the system allows it. It retrieves an empty list instead of showing an error

**Steps to reproduce:**
1. Retrieve the products
2. If there are products then delete them (situational)
3. Retrieve the products again


**Actual results:** Successful 200 response status code

**Expected results:** You should also see a message notifying you that there aren't any products

**App version:** Swagger 0.1.0 OAS3

---

**Title** - User attemps to get a product that doesn't exist

**Desccription** - Given a user tried to retrieve a product that doesn't exist instead of being shown an error message not found the server crashes

**Steps to reproduce:**
1. Retrieve the list
2. Attempt to retrieve an item that doesn't exist

**Actual results:** 500 error status code

**Expected results:** Display an erroe message Product doesn't exist or not found 404 status code

**App version:** Swagger 0.1.0 OAS3

---

**Title** - User is able to create duplicate products

**Description** - Given a user creates a product successfully, when he attemps to create the same identical product the system allows it.
It creates duplicate items in the database such that once you try to retrieve the items then it picks up the first duplicate id from the database.

**Steps to reproduce:**
1. Create a product with correct data {id, name, description}
2. Try to create the same product with identical {id, name, description}

**Actual results:** Successful 200 response status code

**Expected results:** Error, you shouldn't create the same product with the same ID, Status code 400 Bad Request

**App version:** Swagger 0.1.0 OAS3

---
**Title** - When we update an existing product, we are unable update the product ID only the name and description

**Desccription** - Given a user wants to update/modify a product's ID, the system will not recognize and save the changes. While if the user updates/modifies the name and description of the product, it proceeds without an issue

**Steps to reproduce:**
1. Retrieve the list of products
2. If there are no products, then you create a product with correct data
3. Update the said product's ID

**Actual results:** Successful 200 response status code, ID is not changed

**Expected results:** ID should be changed after the update actions, or if forbidden, the user should receive a message that notifies them for restricted actions regarding the ID update

**App version:** Swagger 0.1.0 OAS3

---

**Title** - Attempt to update a product that does not exist.

**Desccription** - Given I want to update a product that does not exist, I am getting a possitive status code and no error message that notifies me the product does not exist. I can also send empty spaces or strings in the ID field.

**Steps to reproduce:**
1. Retrieve the list of products
2. Given the list has products or empty, attempt to retrieve a product with a random ID not included anywhere in the list

**Actual results:** Successful 200 response status code

**Expected results:** Error, the user should be notified that the product with the required ID does not exist in the list of products, 404 status code Not Found

**App version:** Swagger 0.1.0 OAS3

---

**Title** - Attempt to delete a product

**Desccription** - Given I want to delete a product, I am getting a positive status code but the product is not being deleted.

**Steps to reproduce:**
1. Retrieve the list of products
2. Given the list has products, attempt to delete a random product with the product's ID, that's included in the list

**Actual results:** Successful 200 response status code, but the product still remains in the list

**Expected results:** The product with the given ID should be deleted from the list of the products, or the user should be notified that the product with the required ID cannot be deleted for any specific reason and return an error status code

**App version:** Swagger 0.1.0 OAS3

---

**Title** - User is able to retrieve price-engine with any type of id

**Desccription** - Given a user attempts to retrieve a price-engine with text, unique character, empty space, long text, negative integer. The request is successful 200 status code instead of getting an error that ID should be integer with non negative length-n.

**Steps to reproduce:**
1.Attempt to retrieve a price-engine with wrong data such as id
2. id : "Asadasdasdads" or
3. id : -123213123      or
4. id : "@#!!@##@!!@#"  or
5. id : " "             or

**Actual results:** Successful 200 response status code
**Expected results:** Error, the id should be coded to accept integer instead of String

**App version:** Swagger 0.1.0 OAS3

---

##Summary

If we develop an automation team ensure that the way of working, tools, designs patterns, labels and many others are the same with the team to ensure
a quality of work within the team members. Culture and how we structure our code, tests, test cases, user scenarios, documentation of our work.

Have a stable CI/CD pipeline in order to track issues faster. Slowly increase our test coverage by having visibility throughout the entire team sharing the test
scenarios output in either slack or teams. Reduce technical debt and document the processes to transfer knowledge within the team members
