package site.nomoreparties.stellarburgers.api.authregister;

import io.restassured.response.Response;
import site.nomoreparties.stellarburgers.api.authregister.models.RegisterRequest;
import site.nomoreparties.stellarburgers.api.authregister.models.RegisterResponse;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.*;
import static site.nomoreparties.stellarburgers.api.helpers.ApiUrls.AUTH_REGISTER;

public class AuthRegisterClient {
    public static RegisterResponse registerUser(RegisterRequest requestBody) {
        return sendRequestRegisterUser(requestBody)
                .then().assertThat().statusCode(SC_OK)
                .and()
                .extract()
                .response()
                .as(RegisterResponse.class);

    }
    public static Response sendRequestRegisterUser(RegisterRequest requestBody) {
        return given()
                .body(requestBody)
                .when()
                .post(AUTH_REGISTER);

    }

    public static void checkUserIsCreatedOrLoggedIn(Response response, RegisterRequest user){
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
