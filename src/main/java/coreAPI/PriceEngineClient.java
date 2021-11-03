package coreAPI;

import net.serenitybdd.rest.SerenityRest;

public class PriceEngineClient {

    public void getPriceAndCurrency(String id, String accepts_language) {
        SerenityRest.given()
                .header("accept","application/json")
                .header("Accept-Language",accepts_language)
                .header("content-type","application/json; charset=utf-8")
                .get(Endpoints.BasePriceEngineURL.getPath() + Endpoints.PRODUCT.getPath() + id);
    }
}
