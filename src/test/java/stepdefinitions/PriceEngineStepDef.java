package stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.thucydides.core.annotations.Steps;
import steps.PriceEngineSteps;

public class PriceEngineStepDef {

    @Steps
    private PriceEngineSteps priceEngineSteps;

    @When("I submit users id {string} and market language {string}")
    public void iSubmitUsersIdAndMarketLanguage(String id, String accepts_language) {
        priceEngineSteps.submitIdAndCountry(id,accepts_language);
    }

    @Then("I should see currency {string} and price")
    public void iShouldSeeCurrencyAndPrice(String currency) {
        priceEngineSteps.verifyCurrency(currency);
    }

    @Given("I want to know which countries are not supported")
    public void iWantToKnowWhichCountriesAreNotSupported() {
        // Assume there is an endpoint that shows us a list of not supported countries or an json file
    }

    @Then("I should see an error message that language {string} is not supported")
    public void iShouldSeeAnErrorMessageThatLanguageIsNotSupported(String accepts_language) {
        priceEngineSteps.verifyCountryIsNotSupported(accepts_language);
    }


}
