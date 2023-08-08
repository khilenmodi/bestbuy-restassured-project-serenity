package com.bestbuy.productsinfo;

import com.bestbuy.constants.EndPoints;
import com.bestbuy.model.ProductPojo;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;


import java.util.HashMap;

public class ProductsSteps {

    @Step("Creating products with name : {0}, type : {1}, price : {2}, shipping : {3}, upc : {4}, description : {5}, manufacturer : {6}" +
            "model : {7}")
    public ValidatableResponse createProducts(String name,String type,int price,int shipping,String upc,String description,String manufacturer,
                                              String model) {
        ProductPojo productPojo = new ProductPojo();
        productPojo.setName(name);
        productPojo.setType(type);
        productPojo.setPrice(price);
        productPojo.setShipping(shipping);
        productPojo.setUpc(upc);
        productPojo.setDescription(description);
        productPojo.setManufacturer(manufacturer);
        productPojo.setModel(model);
        return SerenityRest.given()
                .contentType(ContentType.JSON)
                .when()
                .body(productPojo)
                .post()
                .then();
    }

    @Step("Getting the Product information with product id : {0}")
    public HashMap<String, Object> getProductInfoByName(String name){
        String s1 = "data.findAll{it.name='";
         String s2 = "'}.get(0)";
        return SerenityRest.given().log().all()
                .when()
                .get()
                .then().statusCode(200)
                .extract()
                .path(s1 + name + s2);
    }

    @Step("updating  product information with productid : {0}, ")
    public ValidatableResponse updateProducts (int productsId,String name,String type,int price,int shipping,String upc,String description,String manufacturer,
                                                 String model){
        ProductPojo productPojo = new ProductPojo();
        productPojo.setName(name);
        productPojo.setType(type);
        productPojo.setPrice(price);
        productPojo.setShipping(shipping);
        productPojo.setUpc(upc);
        productPojo.setDescription(description);
        productPojo.setManufacturer(manufacturer);
        productPojo.setModel(model);

        return SerenityRest.given()
                .header("Content-Type","application/json")
                .pathParam("productID",productsId)
                .body(productPojo)
                .when()
                .put(EndPoints.UPDATE_PRODUCT_BY_ID)
                .then();

    }

    @Step
    public ValidatableResponse deleteProductById(int productsId){
        return  SerenityRest.given()
                .pathParam("productID", productsId)
                .when()
                .delete(EndPoints.DELETE_PRODUCT_BY_ID)
                .then();
    }

    @Step
    public ValidatableResponse getProductById(int productsId){
        return SerenityRest.given()
                .pathParam("productID", productsId)
                .when()
                .get(EndPoints.GET_PRODUCT_BY_ID)
                .then();
    }

}
