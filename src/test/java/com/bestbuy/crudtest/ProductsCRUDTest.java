package com.bestbuy.crudtest;

import com.bestbuy.productsinfo.ProductsSteps;
import com.bestbuy.testbase.ProductsTestBase;
import com.bestbuy.utils.TestUtils;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

import static org.hamcrest.Matchers.hasValue;

@RunWith(SerenityRunner.class)
public class ProductsCRUDTest extends ProductsTestBase {

    static String name = "TCL Portable Air Conditioner White" + TestUtils.getRandomValue() ;
    static  String type = "Tesco" + TestUtils.getRandomValue();
   static  int price = 279 ;
    static int shipping = 120;
    static String upc = "AA" + TestUtils.getRandomValue();
    static String description = "Compatible with select electronic devices"  + TestUtils.getRandomValue();
    static String manufacturer = "Handyman"  + TestUtils.getRandomValue();
    static String model = "H5P24W"  + TestUtils.getRandomValue();

    static int productId;
    @Steps
    ProductsSteps productsSteps;


    @Title("This will create a new product")
    @Test
    public void test001(){
      productsSteps.createProducts(name,type,price,shipping,upc,description,manufacturer,model).statusCode(201);
      //  productId = response.extract().path("id");
    }

    @Title("verify if the product was added to the application")
    @Test
    public void test002(){
        HashMap<String, Object> productMap = productsSteps.getProductInfoByName(name);
        Assert.assertThat(productMap, hasValue(name));
        productId = (int) productMap.get("id");
    }

    @Title("Update the product information and varify the updated information")
    @Test
    public void test003(){
        name = name + "_updated";
        productsSteps.updateProducts(productId, name, type, price, shipping, upc, description, manufacturer, model).statusCode(200);
        HashMap<String, Object> product = productsSteps.getProductInfoByName(name);
        Assert.assertThat(product,hasValue(name));
    }
    @Title("Delete the products and verify if the product is deleted!")
    @Test
    public void test004(){
        productsSteps.deleteProductById(productId).statusCode(200);
        productsSteps.getProductById(productId).statusCode(404);
    }



}
