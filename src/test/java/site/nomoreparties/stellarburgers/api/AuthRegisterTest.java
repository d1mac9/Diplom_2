package site.nomoreparties.stellarburgers.api;

import io.restassured.response.Response;
import org.junit.Test;
import site.nomoreparties.stellarburgers.api.authregister.models.RegisterRequest;

import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static site.nomoreparties.stellarburgers.api.authregister.AuthRegisterClient.checkUserIsCreatedOrLoggedIn;
import static site.nomoreparties.stellarburgers.api.authregister.AuthRegisterClient.sendRequestRegisterUser;
import static site.nomoreparties.stellarburgers.api.authregister.UserGenerator.generateUser;
import static site.nomoreparties.stellarburgers.api.helpers.BaseClient.validateResponseError;
import static site.nomoreparties.stellarburgers.api.helpers.Constants.EMAIL_PASSWORD_NAME_REQUIRED;
import static site.nomoreparties.stellarburgers.api.helpers.Constants.USER_ALREADY_EXIST;

public class AuthRegisterTest extends BaseTest {
    RegisterRequest body;

    @Test
    public void shouldCreateNewUser() {
        body = generateUser();
        Response response = sendRequestRegisterUser(body);

        checkUserIsCreatedOrLoggedIn(response, body);
    }

    @Test
    public void shouldNotCreateExistingUser() {
        body = generateUser();
        sendRequestRegisterUser(body);
        Response response = sendRequestRegisterUser(body);

        validateResponseError(response, SC_FORBIDDEN, USER_ALREADY_EXIST);
    }

    @Test
    public void shouldNotCreateUserWithoutEmail() {
        body = new RegisterRequest();
        body.setPassword("123456");
        body.setName("Test1231234");

        Response response = sendRequestRegisterUser(body);
        validateResponseError(response, SC_FORBIDDEN, EMAIL_PASSWORD_NAME_REQUIRED);
    }
}
