package coreAPI;

import com.google.gson.JsonObject;
import net.serenitybdd.rest.SerenityRest;

public class ProductClient{

    public void createProduct(JsonObject productPayload) {
        SerenityRest.given()
                .header("Accept","application/json")
                .header("Content-Type","application/json; charset=utf-8")
                .body(productPayload)
                .post(Endpoints.BaseProductURL.getPath() + Endpoints.PRODUCT.getPath());
    }


    public void updateProduct(int productResponseID, JsonObject productPayload) {
        SerenityRest.given()
                .header("Accept","application/json")
                .header("Content-Type","application/json; charset=utf-8")
                .body(productPayload)
                .put(Endpoints.BaseProductURL.getPath() + Endpoints.PRODUCT.getPath() + "/" + productResponseID);
    }

    public void deleteProduct(int productResponseID) {
        SerenityRest.given()
                .header("Accept","application/json")
                .header("Content-Type","application/json; charset=utf-8")
                .delete(Endpoints.BaseProductURL.getPath() + Endpoints.PRODUCT.getPath() + "/" + productResponseID);
    }

    public void getProductByID(int productResponseID) {
        SerenityRest.given()
                .header("Accept","application/json")
                .header("Content-Type","application/json; charset=utf-8")
                .get(Endpoints.BaseProductURL.getPath() + Endpoints.PRODUCT.getPath() +"/"+ productResponseID);
    }

    public void getProductList() {
        SerenityRest.given()
                .header("Accept","application/json")
                .header("Content-Type","application/json; charset=utf-8")
                .get(Endpoints.BaseProductURL.getPath() + Endpoints.PRODUCT.getPath());
    }
}