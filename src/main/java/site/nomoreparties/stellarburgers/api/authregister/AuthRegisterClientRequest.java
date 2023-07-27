package site.nomoreparties.stellarburgers.api.authregister;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import site.nomoreparties.stellarburgers.api.authregister.models.RegisterRequest;
import site.nomoreparties.stellarburgers.api.authregister.models.RegisterResponse;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_OK;
import static site.nomoreparties.stellarburgers.api.helpers.ApiUrls.AUTH_REGISTER;

public class AuthRegisterClientRequest {
    @Step("Получение объекта юзера после запроса на регистрацию юзера")
    public static RegisterResponse registerUser(RegisterRequest requestBody) {
        return sendRequestRegisterUser(requestBody)
                .then().assertThat().statusCode(SC_OK)
                .and()
                .extract()
                .response()
                .as(RegisterResponse.class);

    }

    @Step("Отправка запроса на регистрацию юзера")
    public static Response sendRequestRegisterUser(RegisterRequest requestBody) {
        return given()
                .body(requestBody)
                .when()
                .post(AUTH_REGISTER);

    }


}
