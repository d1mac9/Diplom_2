package site.nomoreparties.stellarburgers.api;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import site.nomoreparties.stellarburgers.api.authregister.models.RegisterRequest;
import site.nomoreparties.stellarburgers.api.order.models.Ingredients;

import java.util.List;

import static org.apache.http.HttpStatus.*;
import static site.nomoreparties.stellarburgers.api.authregister.AuthRegisterClient.registerUser;
import static site.nomoreparties.stellarburgers.api.authregister.UserGenerator.generateUser;
import static site.nomoreparties.stellarburgers.api.helpers.BaseClient.validateResponseError;
import static site.nomoreparties.stellarburgers.api.helpers.Constants.INGREDIENT_ID_PROVIDED;
import static site.nomoreparties.stellarburgers.api.helpers.Constants.SHOULD_BE_AUTHORIZED;
import static site.nomoreparties.stellarburgers.api.order.OrderClient.*;
import static site.nomoreparties.stellarburgers.api.user.UserClient.deleteUser;

@DisplayName("Создание заказа")
public class CreateOrderTest extends BaseTest {
    private Ingredients ingredients;
    private String ingredientHash;
    private String accessToken;

    @Before
    public void createTestData() {
        ingredients = new Ingredients();
        ingredientHash = "61c0c5a71d1f82001bdaaa6d";
        RegisterRequest body = generateUser();
        accessToken = registerUser(body).getAccessToken();
    }

    @After
    public void deleteTestData() {
        if (accessToken != null) {
            deleteUser(accessToken);
        }
    }

    @Test
    @DisplayName("Создание ордера без токена в запросе")
    public void shouldNotCreateOrderWithoutAccessToken() {
        ingredients.setIngredients(List.of(ingredientHash));
        Response response = sendOrderCreateRequestWithoutAccessToken(ingredients);

        checkOrderIsCreated(response);
    }

    @Test
    @DisplayName("Создание ордера с токеном в запросе")
    public void shouldCreateOrderWithAccessToken() {
        ingredients.setIngredients(List.of(ingredientHash));
        Response response = sendOrderCreateRequest(ingredients, accessToken);

        checkOrderIsCreated(response);
    }

    @Test
    @DisplayName("Нельзя создать ордер без ингредиентов")
    public void shouldNotCreateOrderWithoutIngredients() {
        ingredients.setIngredients(List.of());
        Response response = sendOrderCreateRequest(ingredients, accessToken);

        validateResponseError(response, SC_BAD_REQUEST, INGREDIENT_ID_PROVIDED);
    }

    @Test
    @DisplayName("Создание ордера со списком ингредиентов")
    public void shouldCreateOrderWithSeveralIngredients() {
        ingredients.setIngredients(List.of("61c0c5a71d1f82001bdaaa6d", "61c0c5a71d1f82001bdaaa6f"));
        Response response = sendOrderCreateRequest(ingredients, accessToken);

        checkOrderIsCreated(response);
    }

    @Test
    @DisplayName("Нельзя создать ордер с несуществующим ингредиентом")
    public void shouldNotCreateOrderWithInvalidIngredientHash() {
        String invalidIngredientHash = "123";
        ingredients.setIngredients(List.of(invalidIngredientHash));
        Response response = sendOrderCreateRequestWithoutAccessToken(ingredients);

        response.then().statusCode(SC_INTERNAL_SERVER_ERROR);
    }

}
