package io.swagger.petstore;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class ExampleTest {

    @BeforeTest
    public void setUp() {
        RestAssured.baseURI = "https://example.org/";
    }

    @Test
    public void test() {
        given()
                .get()
                .then()
                .statusCode(200);
    }
}
