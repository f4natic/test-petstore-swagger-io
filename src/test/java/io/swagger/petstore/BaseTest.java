package io.swagger.petstore;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.Step;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;

public abstract class BaseTest {

    private final String BASE_URL = System.getenv("url") == null ? "https://petstore.swagger.io" : System.getenv("url");
    private final String API = System.getenv("api") == null ? "/v2" : System.getenv("api");
    private final Parser PARSER = Parser.fromContentType("application/json;charset=utf-8");
    protected RequestSpecification specification;
    protected ObjectMapper mapper;

    @BeforeClass
    @Step("Создание Request Specification и ObjectMapper'а")
    public void setUp() {
        RequestSpecBuilder builder = new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .setBasePath(API)
                .setContentType(PARSER.getContentType())
                .setAccept(ContentType.JSON);
        specification = builder.build();
        mapper = new ObjectMapper();
    }
}
