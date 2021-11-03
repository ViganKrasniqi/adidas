package steps;

import coreAPI.*;
import model.PriceEngine;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Steps;

import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static javax.servlet.http.HttpServletResponse.SC_OK;
import static net.serenitybdd.rest.SerenityRest.then;
import static org.assertj.core.api.Assertions.assertThat;

public class PriceEngineSteps {

    private PriceEngine priceEngine;

    @Steps
    PriceEngineClient priceEngineClient;

    public void submitIdAndCountry(String id, String accepts_language) {
        priceEngineClient.getPriceAndCurrency(id,accepts_language);
    }

    public void verifyCurrency(String currency) {
        priceEngine = SerenityRest.lastResponse().jsonPath().getObject("", PriceEngine.class);
        assertThat(priceEngine.getCurrency()).isEqualToIgnoringCase(currency);
        assertThat(priceEngine.getPrice()).isPositive();
        then().assertThat().statusCode(SC_OK);
        /*
        assertThat(SerenityRest.lastResponse().jsonPath().get("currency").toString()).isEqualToIgnoringCase(currency);
        assertThat(SerenityRest.lastResponse().jsonPath().get("price").toString()) make it to int then check for non negative value
        SerenityRest.lastResponse().statusCode().equals(200)...
         */
    }

    public void verifyCountryIsNotSupported(String accepts_language) {
        assertThat(SerenityRest.lastResponse().jsonPath().get("").toString()).contains("Country "+accepts_language+" not supported");
        then().assertThat().statusCode(SC_BAD_REQUEST);
    }
}
