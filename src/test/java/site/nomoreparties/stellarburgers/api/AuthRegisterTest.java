package site.nomoreparties.stellarburgers.api;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import site.nomoreparties.stellarburgers.api.authregister.models.RegisterRequest;

import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static site.nomoreparties.stellarburgers.api.authregister.AuthRegisterClientRequest.sendRequestRegisterUser;
import static site.nomoreparties.stellarburgers.api.authregister.AuthRegisterClientResponse.checkUserIsCreatedOrLoggedIn;
import static site.nomoreparties.stellarburgers.api.authregister.UserGenerator.generateUser;
import static site.nomoreparties.stellarburgers.api.helpers.BaseClient.validateResponseError;
import static site.nomoreparties.stellarburgers.api.helpers.Constants.EMAIL_PASSWORD_NAME_REQUIRED;
import static site.nomoreparties.stellarburgers.api.helpers.Constants.USER_ALREADY_EXIST;
import static site.nomoreparties.stellarburgers.api.user.UserClientRequest.deleteUser;

@DisplayName("Регистрация юзера")
public class AuthRegisterTest extends BaseTest {
    private RegisterRequest body;
    private String accessToken;

    @Before
    public void createTestData() {
        body = generateUser();
    }

    @After
    public void deleteTestData() {
        if (accessToken != null) {
            deleteUser(accessToken);
        }
    }

    @Test
    @DisplayName("Создание нового юзера")
    public void shouldCreateNewUser() {
        Response response = sendRequestRegisterUser(body);
        accessToken = response.then().extract().path("accessToken");

        checkUserIsCreatedOrLoggedIn(response, body);
    }

    @Test
    @DisplayName("Нельзя создать существующего юзера")
    public void shouldNotCreateExistingUser() {
        sendRequestRegisterUser(body);
        Response response = sendRequestRegisterUser(body);

        validateResponseError(response, SC_FORBIDDEN, USER_ALREADY_EXIST);
    }

    @Test
    @DisplayName("Нельзя создать юзера без поля email")
    public void shouldNotCreateUserWithoutEmail() {
        body = new RegisterRequest();
        body.setPassword("123456");
        body.setName("Test1231234");

        Response response = sendRequestRegisterUser(body);
        validateResponseError(response, SC_FORBIDDEN, EMAIL_PASSWORD_NAME_REQUIRED);
    }
}
