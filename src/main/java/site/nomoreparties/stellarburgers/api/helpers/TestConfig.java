package site.nomoreparties.stellarburgers.api.helpers;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;
import static site.nomoreparties.stellarburgers.api.helpers.ApiUrls.BASE_PATH;
import static site.nomoreparties.stellarburgers.api.helpers.ApiUrls.BASE_URL;

public class TestConfig {
    public static void initConfig() {
        RestAssured.baseURI = BASE_URL;
        RestAssured.basePath = BASE_PATH;
        RestAssured.requestSpecification = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON);
        RestAssured.filters(new AllureRestAssured(), new RequestLoggingFilter(), new ResponseLoggingFilter());
    }
}
