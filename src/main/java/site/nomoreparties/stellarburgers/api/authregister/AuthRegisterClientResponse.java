package site.nomoreparties.stellarburgers.api.authregister;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import site.nomoreparties.stellarburgers.api.authregister.models.RegisterRequest;

import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class AuthRegisterClientResponse {
    @Step("Проверка, что юзер авторизован или создан успешно")
    public static void checkUserIsCreatedOrLoggedIn(Response response, RegisterRequest user) {
        response
                .then().assertThat().statusCode(SC_OK)
                .and()
                .body("success", equalTo(true))
                .body("user.email", equalTo(user.getEmail()))
                .body("user.name", equalTo(user.getName()))
                .body("accessToken", notNullValue())
                .body("refreshToken", notNullValue());
    }
}
