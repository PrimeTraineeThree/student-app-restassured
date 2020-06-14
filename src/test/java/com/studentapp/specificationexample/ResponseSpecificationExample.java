package com.studentapp.specificationexample;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;

/**
 * Created by Jay
 */
public class ResponseSpecificationExample {

    private static final String API_KEY = "75e3u4sgb2khg673cbv2gjup";

    private static RequestSpecBuilder requestSpecBuilder;
    private static RequestSpecification requestSpecification;

    private static ResponseSpecBuilder responseSpecBuilder;
    private static ResponseSpecification responseSpecification;

    @BeforeClass
    public static void inIt(){
        RestAssured.baseURI = "http://api.walmartlabs.com";
        RestAssured.basePath = "/v1";

        requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.addQueryParam("query", "ipod");
        requestSpecBuilder.addQueryParam("format", "json");
        requestSpecBuilder.addQueryParam("apiKey", API_KEY);

        requestSpecification = requestSpecBuilder.build();

        responseSpecBuilder = new ResponseSpecBuilder();
        responseSpecBuilder.expectHeader("Content-Type","application/json; charset=utf-8");
        responseSpecBuilder.expectHeader("Server","Mashery Proxy");
        responseSpecBuilder.expectStatusCode(200);
        responseSpecBuilder.expectBody("numItems", equalTo(10));
        responseSpecBuilder.expectBody("query", equalTo("ipod"));

        responseSpecification = responseSpecBuilder.build();
    }

    // 1) Verify if the number of items is equal to 10
    @Test
    public void test001() {
        given()
                .spec(requestSpecification)
                .when()
                .get("/search")
                .then()
                .spec(responseSpecification);

    }

    // 3) Check Single Name in ArrayList (Apple iPod Touch 6th Generation 32GB Refurbished)
    @Test
    public void test003() {

        given()
                .spec(requestSpecification)
                .when()
                .get("/search")
                .then()
                .spec(responseSpecification)
                .body("items.name", hasItem("Apple iPod touch 7th Generation 32GB - Space Gray (New Model)"));

    }
}
