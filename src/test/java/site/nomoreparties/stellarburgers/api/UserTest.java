package site.nomoreparties.stellarburgers.api;

import io.restassured.response.Response;
import org.junit.Test;
import site.nomoreparties.stellarburgers.api.authregister.models.RegisterRequest;

import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static site.nomoreparties.stellarburgers.api.authregister.AuthRegisterClient.registerUser;
import static site.nomoreparties.stellarburgers.api.authregister.UserGenerator.*;
import static site.nomoreparties.stellarburgers.api.helpers.BaseClient.validateResponseError;
import static site.nomoreparties.stellarburgers.api.helpers.Constants.SHOULD_BE_AUTHORIZED;
import static site.nomoreparties.stellarburgers.api.user.UserClient.*;

public class UserTest extends BaseTest {
    RegisterRequest body;
    @Test
    public void shouldChangeUserEmailAndUsername(){
        body = generateUser();
        String accessToken = registerUser(body).getAccessToken();
        body.setEmail(generateEmail());
        body.setName(generateUsername());
        body.setPassword(null);
        Response response = sendRequestChangeUserWithAccessToken(body, accessToken);

        checkUserIsUpdated(response, body);
    }
    @Test
    public void shouldNotChangeUserWithoutAuthorizationToken(){
        body = generateUser();
        registerUser(body);
        body.setEmail(generateEmail());
        body.setName(generateUsername());
        body.setPassword(null);
        Response response = sendRequestChangeUser(body);

        validateResponseError(response, SC_UNAUTHORIZED, SHOULD_BE_AUTHORIZED);
    }


}
