package com.studentapp.specificationexample;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;

/**
 * Created by Jay
 */
public class RequestSpecificationExample {

    private static final String API_KEY = "75e3u4sgb2khg673cbv2gjup";

     private static RequestSpecBuilder builder;
     private static RequestSpecification requestSpecification;

    @BeforeClass
    public static void inIt(){
        RestAssured.baseURI = "http://api.walmartlabs.com";
        RestAssured.basePath = "/v1";

        builder = new RequestSpecBuilder();
        builder.addQueryParam("query", "ipod");
        builder.addQueryParam("format", "json");
        builder.addQueryParam("apiKey", API_KEY);

        requestSpecification = builder.build();
    }

    // 1) Verify if the number of items is equal to 10
    @Test
    public void test001() {
        given()
                .spec(requestSpecification)
                .when()
                .get("/search")
                .then()
                .statusCode(200)
                .body("numItems", equalTo(10));

    }

    // 3) Check Single Name in ArrayList (Apple iPod Touch 6th Generation 32GB Refurbished)
    @Test
    public void test003() {

        given()
                .spec(requestSpecification)
                .when()
                .get("/search")
                .then()
                .statusCode(200)
                .body("items.name", hasItem("Apple iPod touch 7th Generation 32GB - Space Gray (New Model)"));

    }

}
