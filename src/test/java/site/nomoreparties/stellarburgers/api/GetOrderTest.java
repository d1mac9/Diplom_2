package site.nomoreparties.stellarburgers.api;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import site.nomoreparties.stellarburgers.api.authregister.models.RegisterRequest;
import site.nomoreparties.stellarburgers.api.order.OrderClientResponse;
import site.nomoreparties.stellarburgers.api.order.models.Ingredients;
import site.nomoreparties.stellarburgers.api.order.models.OrderResponse;

import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static site.nomoreparties.stellarburgers.api.authregister.AuthRegisterClientRequest.registerUser;
import static site.nomoreparties.stellarburgers.api.authregister.UserGenerator.generateUser;
import static site.nomoreparties.stellarburgers.api.helpers.BaseClient.validateResponseError;
import static site.nomoreparties.stellarburgers.api.helpers.Constants.SHOULD_BE_AUTHORIZED;
import static site.nomoreparties.stellarburgers.api.order.OrderClientRequest.*;
import static site.nomoreparties.stellarburgers.api.user.UserClientRequest.deleteUser;

@DisplayName("Получение ордера по юзеру")
public class GetOrderTest extends BaseTest {
    private String ingredientHash;
    private String accessToken;

    @Before
    public void createTestData() {
        Ingredients ingredients = new Ingredients();
        ingredientHash = "61c0c5a71d1f82001bdaaa6d";
        RegisterRequest body = generateUser();
        accessToken = registerUser(body).getAccessToken();
        ingredients.setIngredients(List.of(ingredientHash));
        Response response = sendOrderCreateRequest(ingredients, accessToken);
        OrderClientResponse.checkOrderIsCreated(response);
    }

    @After
    public void deleteTestData() {
        if (accessToken != null) {
            deleteUser(accessToken);
        }
    }

    @Test
    public void shouldGetOrderByUserAndCheckIngredients() {
        Response response = sendGetOrderRequest(accessToken);
        OrderResponse order = OrderClientResponse.checkUserOrder(response);
        String actualResult = order.getOrders().get(0).getIngredients().get(0);
        assertEquals("Ингредиенты не совапдают", ingredientHash, actualResult);
    }

    @Test
    public void shouldNotGetOrderWithoutAccessToken() {
        Response response = sendGetOrderRequestWithoutAccessToken();

        validateResponseError(response, SC_UNAUTHORIZED, SHOULD_BE_AUTHORIZED);
    }
}
