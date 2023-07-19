package site.nomoreparties.stellarburgers.api.login;

import io.restassured.response.Response;
import site.nomoreparties.stellarburgers.api.authregister.models.RegisterRequest;

import static io.restassured.RestAssured.given;
import static site.nomoreparties.stellarburgers.api.helpers.ApiUrls.AUTH_LOGIN;

public class LoginClient {
    public static Response sendLoginRequest(RegisterRequest body){
        return given()
                .body(body)
                .when()
                .post(AUTH_LOGIN);
    }
}
