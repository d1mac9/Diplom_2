package site.nomoreparties.stellarburgers.api;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import site.nomoreparties.stellarburgers.api.authregister.models.RegisterRequest;

import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static site.nomoreparties.stellarburgers.api.authregister.AuthRegisterClient.*;
import static site.nomoreparties.stellarburgers.api.authregister.UserGenerator.generateUser;
import static site.nomoreparties.stellarburgers.api.helpers.BaseClient.validateResponseError;
import static site.nomoreparties.stellarburgers.api.helpers.Constants.EMAIL_OR_PASSWORD_INCORRECT;
import static site.nomoreparties.stellarburgers.api.login.LoginClient.sendLoginRequest;

@DisplayName("Авторизация юзера")
public class LoginTest extends BaseTest {
    private RegisterRequest body;
    @Before
    public void createTestData(){
        body = generateUser();
    }
    @Test
    @DisplayName("Авторизация существующего юзера")
    public void shouldLoginExistingUser(){
        registerUser(body);
        Response response = sendLoginRequest(body);

        checkUserIsCreatedOrLoggedIn(response, body);
    }

    @Test
    @DisplayName("Неуспешная авторизация юзера с некорректным паролем и именем")
    public void shouldNotLoginWithNonCorrectPasswordAndUsername(){
        Response response = sendLoginRequest(body);

        validateResponseError(response, SC_UNAUTHORIZED, EMAIL_OR_PASSWORD_INCORRECT);
    }
}
