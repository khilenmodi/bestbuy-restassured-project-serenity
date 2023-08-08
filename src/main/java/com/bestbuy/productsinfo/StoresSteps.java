package com.bestbuy.productsinfo;

import com.bestbuy.constants.EndPoints;
import com.bestbuy.model.StorePojo;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

import java.util.HashMap;

public class StoresSteps {
    @Step("Creating stores with name : {0}, type : {1}, address : {2}, address2 : {3},  city : {4}, state : {5}, zip : {6}" +
            "lat: {7}, lng : {8},hours: {9}  ")
    public ValidatableResponse createStore(String name, String type, String address,String address2,String city, String state, String zip,
                                              double lat, double lng,String hours) {
        StorePojo storePojo = new StorePojo();
        storePojo.setName(name);
        storePojo.setType(type);
        storePojo.setAddress(address);
        storePojo.setAddress2(address2);
        storePojo.setCity(city);
        storePojo.setState(state);
        storePojo.setZip(zip);
        storePojo.setLat(lat);
        storePojo.setLng(lng);
        storePojo.setHours(hours);
        return SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .when()
                .body(storePojo)
                .post()
                .then();
    }

    @Step("Getting the Product information with Store id : {0}")
    public HashMap<String, Object> getStoreInfoByName(String name){
        String s1 = "data.findAll{it.name='";
        String s2 = "'}.get(0)";
        return SerenityRest.given()
                .when()
                .get()
                .then().statusCode(200)
                .extract()
                .path(s1 + name + s2);
    }

    @Step("updating  stores information with StoresId : {0}, ")
    public ValidatableResponse updateStores (int storesId,String name, String type, String address,String address2,String city, String state, String zip,
                                             double lat, double lng,String hours){
        StorePojo storePojo = new StorePojo();
        storePojo.setName(name);
        storePojo.setType(type);
        storePojo.setAddress(address);
        storePojo.setAddress2(address2);
        storePojo.setCity(city);
        storePojo.setState(state);
        storePojo.setZip(zip);
        storePojo.setLat(lat);
        storePojo.setLng(lng);
        storePojo.setHours(hours);

        return SerenityRest.given().log().all()
                .header("Content-Type","application/json")
                .pathParam("StoresID",storesId)
                .body(storePojo)
                .when()
                .put(EndPoints.UPDATE_STORES_BY_ID)
                .then();

    }

    @Step
    public ValidatableResponse deleteStoresById(int storesId){
        return  SerenityRest.given()
                .pathParam("StoresID", storesId)
                .when()
                .delete(EndPoints.DELETE_STORES_BY_ID)
                .then();
    }

    @Step
    public ValidatableResponse getStoresById(int storesId){
        return SerenityRest.given()
                .pathParam("StoresID", storesId)
                .when()
                .get(EndPoints.GET_STORES_BY_ID)
                .then();
    }
}
