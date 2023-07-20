package site.nomoreparties.stellarburgers.api.user;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import site.nomoreparties.stellarburgers.api.authregister.models.RegisterRequest;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_ACCEPTED;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.equalTo;
import static site.nomoreparties.stellarburgers.api.helpers.ApiUrls.AUTH_USER;
import static site.nomoreparties.stellarburgers.api.helpers.Constants.USER_SUCCESSFULLY_REMOVED;

public class UserClient {
    @Step("Отправка запроса на изменение юзера")
    public static Response sendRequestChangeUser(RegisterRequest user){
        return given()
                .body(user)
                .when()
                .patch(AUTH_USER);
    }
    @Step("Проверка обвновления данных у юзера")
    public static Response sendRequestChangeUserWithAccessToken(RegisterRequest user, String accessToken){
        return given()
                .header("Authorization", accessToken)
                .body(user)
                .when()
                .patch(AUTH_USER);
    }
    @Step("Проверка обвновления данных у юзера")
    public static void checkUserIsUpdated(Response response, RegisterRequest user){
        response
                .then().assertThat().statusCode(SC_OK)
                .and()
                .body("success", equalTo(true))
                .body("user.email", equalTo(user.getEmail()))
                .body("user.name", equalTo(user.getName()));
    }
    @Step("Удаление юзера")
    public static void deleteUser(String accessToken){
        given()
                .header("authorization", accessToken)
                .when()
                .delete(AUTH_USER)
                .then().assertThat().statusCode(SC_ACCEPTED)
                .body("message", equalTo(USER_SUCCESSFULLY_REMOVED));
    }
}
