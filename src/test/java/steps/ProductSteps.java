package steps;

import com.google.gson.JsonObject;
import coreAPI.ProductClient;
import model.Product;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Steps;
import com.google.gson.GsonBuilder;

import java.util.Arrays;
import java.util.List;

import static net.serenitybdd.core.Serenity.setSessionVariable;
import static org.assertj.core.api.Assertions.assertThat;
import static net.serenitybdd.rest.SerenityRest.then;
import static javax.servlet.http.HttpServletResponse.*;

public class ProductSteps {

    private Product product;
    private Product updatedProduct;
    private List<Product> productList;

    @Steps
    private ProductClient productClient;

    public void createProduct(JsonObject productPayload) {
        productClient.createProduct(productPayload);
        product = SerenityRest.lastResponse().jsonPath().getObject("", Product.class);
        setSessionVariable("productResponseID").to(product.getId());

    }

    public void updateProduct(int productResponseID, JsonObject productPayload) {
        productClient.updateProduct(productResponseID,productPayload);
        updatedProduct = SerenityRest.lastResponse().jsonPath().getObject("", Product.class);
        setSessionVariable("updatedProductResponseID").to(updatedProduct.getId());
    }

    public void deleteProduct(int productResponseID) {
        productClient.deleteProduct(productResponseID);
    }

    public void getProductByID(int productResponseID) {
        productClient.getProductByID(productResponseID);
    }

    public void verifyProductCreatedSuccessfully() {
        assertThat(product.getId()).isNotEmpty();
        assertThat(product.getName()).isNotEmpty();
        assertThat(product.getName()).isNotEmpty();
        assertThat(product.getUUID()).isNotEmpty();
        then().assertThat().statusCode(SC_CREATED);
    }

    public void verifyProductUpdatedSuccessfully() {
       
        assertThat(updatedProduct.getId()).isNotEmpty();
        assertThat(updatedProduct.getName()).isNotEmpty();
        assertThat(updatedProduct.getName()).isNotEmpty();
        assertThat(updatedProduct.getUUID()).isNotEmpty();
        then().assertThat().statusCode(SC_OK);
    }

    public void verifyProductDeleted() {
        then().assertThat().statusCode(SC_NO_CONTENT);
    }

    public void verifyProductIsMissing() {
        then().assertThat().statusCode(SC_NOT_FOUND);
    }

    public void getProductList() {
        productClient.getProductList();
    }

    public void verifyProductList() {
        then().assertThat().statusCode(SC_OK);
        Product[] tempProductList = new GsonBuilder().create().fromJson(SerenityRest.lastResponse().body().prettyPrint(), Product[].class);
        productList = Arrays.asList(tempProductList);
        assertThat(productList.size()).isPositive();
    }

    public void verifyEmptyProductList() {
        then().assertThat().statusCode(SC_OK);
        assertThat(SerenityRest.lastResponse().jsonPath().get("message").toString()).isEqualTo("List is empty, please create a product");
    }

    public void verifyDuplicateProductErrorMessage() {
        then().assertThat().statusCode(SC_BAD_REQUEST);
        assertThat(SerenityRest.lastResponse().jsonPath().get("message").toString()).isEqualTo("Product already exists, duplicates aren't allowed");
    }

}
