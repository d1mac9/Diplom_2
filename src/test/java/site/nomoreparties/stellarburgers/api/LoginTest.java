package site.nomoreparties.stellarburgers.api;

import io.restassured.response.Response;
import org.junit.Test;
import site.nomoreparties.stellarburgers.api.authregister.models.RegisterRequest;

import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static site.nomoreparties.stellarburgers.api.authregister.AuthRegisterClient.*;
import static site.nomoreparties.stellarburgers.api.authregister.UserGenerator.generateUser;
import static site.nomoreparties.stellarburgers.api.helpers.BaseClient.validateResponseError;
import static site.nomoreparties.stellarburgers.api.helpers.Constants.EMAIL_OR_PASSWORD_INCORRECT;
import static site.nomoreparties.stellarburgers.api.login.LoginClient.sendLoginRequest;

public class LoginTest extends BaseTest {
    RegisterRequest body;
    @Test
    public void shouldLoginExistingUser(){
        body = generateUser();
        registerUser(body);
        Response response = sendLoginRequest(body);

        checkUserIsCreatedOrLoggedIn(response, body);
    }

    @Test
    public void shouldNotLoginWithNonCorrectPasswordAndUsername(){
        body = generateUser();
        Response response = sendLoginRequest(body);

        validateResponseError(response, SC_UNAUTHORIZED, EMAIL_OR_PASSWORD_INCORRECT);
    }
}
