package stepdefinitions;

import com.google.gson.JsonObject;
import controller.EnvironmentController;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.thucydides.core.annotations.Steps;
import org.apache.commons.lang3.RandomStringUtils;
import steps.ProductSteps;

import java.io.IOException;
import java.util.UUID;

import static net.serenitybdd.core.Serenity.sessionVariableCalled;
import static net.serenitybdd.core.Serenity.setSessionVariable;

public class ProductStepDef {

    @Steps
    private ProductSteps productSteps;

    private JsonObject productPayload;

    @Given("I want to know which countries are supported")
    public void iWantToKnowWhichCountriesAreSupported() {
        // Assume there is an endpoint that shows us a list of supported countries or an json file
    }

    @Given("I create a unique product")
    public void iCreateAUniqueProduct() throws IOException {
        productPayload = EnvironmentController.getJsonObject("createValidProduct");
        productSteps.createProduct(productPayload);
        productSteps.verifyProductCreatedSuccessfully();
    }

    @And("I update the unique product with different values")
    public void iUpdateTheUniqueProductWithDifferentValues() throws IOException {
        productPayload = EnvironmentController.getJsonObject("updateValidProduct");
        productSteps.updateProduct(sessionVariableCalled("productResponseID"),productPayload);
        productSteps.verifyProductUpdatedSuccessfully();
    }

    @When("I delete the unique product")
    public void iDeleteTheUniqueProduct() {
        productSteps.deleteProduct(sessionVariableCalled("productResponseID"));
        productSteps.verifyProductDeleted();
    }

    @Then("I should see the product is no longer in the list")
    public void iShouldSeeTheProductIsNoLongerInTheList() {
        productSteps.getProductByID(sessionVariableCalled("productResponseID"));
        productSteps.verifyProductIsMissing();
    }

    @Given("I want to retrieve a list of all the products")
    public void iWantToRetrieveAListOfAllTheProducts() {
        // Placeholder for BDD scenario
    }

    @When("I submit the request to retrieve the list of all the products")
    public void iSubmitTheRequestToRetrieveTheListOfAllTheProducts() {
        productSteps.getProductList();
    }

    @Then("I should see a list of all the products")
    public void iShouldSeeAListOfAllTheProducts() {
        productSteps.verifyProductList();
    }

    @Given("I want to retrieve a empty list of all products")
    public void iWantToRetrieveAEmptyListOfAllProducts() {
        // Placeholder for BDD scenario
        //to prepare the data we need to call the list, delete all entries in order to continue with the steps correctly
    }

    @When("I retrieve the list of products")
    public void iRetrieveTheListOfProducts() {
        productSteps.getProductList();
    }

    @Then("I should see an error message informing me that the list is empty")
    public void iShouldSeeAnErrorMessageInformingMeThatTheListIsEmpty() {
        productSteps.verifyEmptyProductList();
    }

    @Given("I want to retrieve a product not listed")
    public void iWantToRetrieveAProductNotListed() {
        setSessionVariable("RandomID").to(UUID.randomUUID());
        // Math.random or UUID.randomUUID
    }

    @When("I attempt to retrieve the product")
    public void iAttemptToRetrieveTheProduct() {
        productSteps.getProductByID(sessionVariableCalled("RandomID"));
    }

    @Then("I should see an error message informing me that the product is not in the list")
    public void iShouldSeeAnErrorMessageInformingMeThatTheProductIsNotInTheList() {
        productSteps.verifyProductIsMissing();
    }

    @Given("I have an existing product")
    public void iHaveAnExistingProduct() throws IOException {
        productPayload = EnvironmentController.getJsonObject("createValidProduct");
        productSteps.createProduct(productPayload);
        productSteps.verifyProductCreatedSuccessfully();
    }

    @And("I want to duplicate it")
    public void iWantToDuplicateIt() throws IOException {
        productPayload = EnvironmentController.getJsonObject("createValidProduct");
    }

    @When("I attempt to create a product")
    public void iAttemptToCreateAProduct() {
        productSteps.createProduct(productPayload);
    }

    @Then("I should see an error message informing me that the product already exists")
    public void iShouldSeeAnErrorMessageInformingMeThatTheProductAlreadyExists() {
        productSteps.verifyDuplicateProductErrorMessage();
    }



}
