package site.nomoreparties.stellarburgers.api.helpers;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static org.hamcrest.Matchers.equalTo;

public class BaseClient {
    @Step("Проверка полученной валидационной ошибки")
    public static void validateResponseError(Response response, int statusCode, String message){
        response.then().assertThat().statusCode(statusCode)
                .and()
                .body("message", equalTo(message))
                .body("success",equalTo(false));
    }
}
