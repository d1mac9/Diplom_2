package site.nomoreparties.stellarburgers.api.user;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import site.nomoreparties.stellarburgers.api.authregister.models.RegisterRequest;

import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.equalTo;

public class UserClientResponse {
    @Step("Проверка обвновления данных у юзера")
    public static void checkUserIsUpdated(Response response, RegisterRequest user) {
        response
                .then().assertThat().statusCode(SC_OK)
                .and()
                .body("success", equalTo(true))
                .body("user.email", equalTo(user.getEmail()))
                .body("user.name", equalTo(user.getName()));
    }
}
