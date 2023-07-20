package site.nomoreparties.stellarburgers.api.login;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import site.nomoreparties.stellarburgers.api.authregister.models.RegisterRequest;

import static io.restassured.RestAssured.given;
import static site.nomoreparties.stellarburgers.api.helpers.ApiUrls.AUTH_LOGIN;

public class LoginClient {
    @Step("Отправка запроса на авторизацию юзера")
    public static Response sendLoginRequest(RegisterRequest body){
        return given()
                .body(body)
                .when()
                .post(AUTH_LOGIN);
    }
}
