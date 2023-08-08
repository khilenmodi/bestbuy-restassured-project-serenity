package com.bestbuy.crudtest;

import com.bestbuy.productsinfo.StoresSteps;
import com.bestbuy.testbase.StoresTestBase;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

import static org.hamcrest.Matchers.hasValue;

@RunWith(SerenityRunner.class)
public class StoresCURDTest extends StoresTestBase {


    static String name = "EuroStore";
    static String type = "superstore";

    static String address = "25, vista avanue";
    static String address2 = "Tudor Road";
    static String city = "Hatfield";
    static String state = "Hertfordshire";
    static String zip = "HA10 9EJ";
    static  double lat = 44.25;
    static  double lng = 36.47;
    static String hours = " Mon: 10-9; Tue: 10-9; Wed: 10-9; Thurs: 10-9; Fri: 10-9; Sat: 10-9; Sun: 10-8";

    static int storesId;
    @Steps
    StoresSteps storesSteps;

    @Title("This will create a new store")
    @Test
    public void test001() {
        storesSteps.createStore(name, type, address, address2, city, state, zip, lat, lng, hours).statusCode(201);
        //  productId = response.extract().path("id");
    }

    @Title("verify if the product was added to the application")
    @Test
    public void test002() {
        HashMap<String, Object> productMap = storesSteps.getStoreInfoByName(name);
        Assert.assertThat(productMap, hasValue(name));
        storesId = (int) productMap.get("id");
    }

    @Title("Update the product information and varify the updated information")
    @Test
    public void test003() {
        name = name + "_updated";
        storesSteps.updateStores(storesId, name, type, address, address2, city, state, zip, lat, lng, hours).statusCode(200);
        HashMap<String, Object> product = storesSteps.getStoreInfoByName(name);
        Assert.assertThat(product, hasValue(name));
    }

    @Title("Delete the products and verify if the product is deleted!")
    @Test
    public void test004() {
        storesSteps.deleteStoresById(storesId).statusCode(200);
        storesSteps.getStoresById(storesId).statusCode(404);
    }


}
