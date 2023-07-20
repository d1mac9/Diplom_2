package site.nomoreparties.stellarburgers.api.helpers;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.ErrorLoggingFilter;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static site.nomoreparties.stellarburgers.api.helpers.ApiUrls.BASE_PATH;
import static site.nomoreparties.stellarburgers.api.helpers.ApiUrls.BASE_URL;

public class TestConfig {
    public static void initConfig() {
        RestAssured.requestSpecification = requestSpecification;
    }

    private final static RequestSpecification requestSpecification = new RequestSpecBuilder()
            .setBaseUri(BASE_URL)
            .setBasePath(BASE_PATH)
            .setContentType(ContentType.JSON)
            .setAccept(ContentType.JSON)
            .build()
            .filters(new AllureRestAssured(), new RequestLoggingFilter(), new ResponseLoggingFilter(), new ErrorLoggingFilter());
}
