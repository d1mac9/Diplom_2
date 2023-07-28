package site.nomoreparties.stellarburgers.api.order;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import site.nomoreparties.stellarburgers.api.order.models.Ingredients;

import static io.restassured.RestAssured.given;
import static site.nomoreparties.stellarburgers.api.helpers.ApiUrls.ORDERS;

public class OrderClientRequest {
    @Step("Отправка запроса на создание ордера без токена")
    public static Response sendOrderCreateRequestWithoutAccessToken(Ingredients ingredients) {
        return given()
                .body(ingredients)
                .when()
                .post(ORDERS);
    }

    @Step("Отправка запроса на создание ордера")
    public static Response sendOrderCreateRequest(Object ingredients, String accessToken) {
        return given()
                .header("Authorization", accessToken)
                .body(ingredients)
                .when()
                .post(ORDERS);
    }

    @Step("Отправка запроса на получение ордера по юзеру")
    public static Response sendGetOrderRequest(String accessToken) {
        return given()
                .header("Authorization", accessToken)
                .when()
                .get(ORDERS);
    }

    @Step("Отправка запроса на получение ордера по юзеру без токена")
    public static Response sendGetOrderRequestWithoutAccessToken() {
        return given()
                .when()
                .get(ORDERS);
    }

}
