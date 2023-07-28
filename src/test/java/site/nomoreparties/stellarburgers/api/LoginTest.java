package site.nomoreparties.stellarburgers.api;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import site.nomoreparties.stellarburgers.api.authregister.AuthRegisterClientResponse;
import site.nomoreparties.stellarburgers.api.authregister.models.RegisterRequest;

import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static site.nomoreparties.stellarburgers.api.authregister.AuthRegisterClientRequest.registerUser;
import static site.nomoreparties.stellarburgers.api.authregister.UserGenerator.generateUser;
import static site.nomoreparties.stellarburgers.api.helpers.BaseClient.validateResponseError;
import static site.nomoreparties.stellarburgers.api.helpers.Constants.EMAIL_OR_PASSWORD_INCORRECT;
import static site.nomoreparties.stellarburgers.api.login.LoginClient.sendLoginRequest;
import static site.nomoreparties.stellarburgers.api.user.UserClientRequest.deleteUser;

@DisplayName("Авторизация юзера")
public class LoginTest extends BaseTest {
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
    @DisplayName("Авторизация существующего юзера")
    public void shouldLoginExistingUser() {
        accessToken = registerUser(body).getAccessToken();
        Response response = sendLoginRequest(body);

        AuthRegisterClientResponse.checkUserIsCreatedOrLoggedIn(response, body);
    }

    @Test
    @DisplayName("Неуспешная авторизация юзера с некорректным паролем и именем")
    public void shouldNotLoginWithNonCorrectPasswordAndUsername() {
        Response response = sendLoginRequest(body);

        validateResponseError(response, SC_UNAUTHORIZED, EMAIL_OR_PASSWORD_INCORRECT);
    }
}
