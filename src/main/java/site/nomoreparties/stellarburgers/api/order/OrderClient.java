package site.nomoreparties.stellarburgers.api.order;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import site.nomoreparties.stellarburgers.api.order.models.Ingredients;
import site.nomoreparties.stellarburgers.api.order.models.OrderResponse;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static site.nomoreparties.stellarburgers.api.helpers.ApiUrls.ORDERS;

public class OrderClient {
    @Step("Отправка запроса на создание ордера без токена")
    public static Response sendOrderCreateRequestWithoutAccessToken(Ingredients ingredients){
        return given()
                .body(ingredients)
                .when()
                .post(ORDERS);
    }
    @Step("Отправка запроса на создание ордера")
    public static Response sendOrderCreateRequest(Object ingredients, String accessToken){
        return given()
                .header("Authorization", accessToken)
                .body(ingredients)
                .when()
                .post(ORDERS);
    }
    @Step("Проверка, что ордер создан успешно")
    public static void checkOrderIsCreated(Response response){
        response
                .then().assertThat().statusCode(SC_OK)
                .and()
                .body("success", equalTo(true))
                .body("order.number", notNullValue())
                .body("name", notNullValue());
    }
    @Step("Отправка запроса на получение ордера по юзеру")
    public static Response sendGetOrderRequest(String accessToken){
        return given()
                .header("Authorization", accessToken)
                .when()
                .get(ORDERS);
    }
    @Step("Отправка запроса на получение ордера по юзеру без токена")
    public static Response sendGetOrderRequestWithoutAccessToken(){
        return given()
                .when()
                .get(ORDERS);
    }
    @Step("Проверка, что ордер создан успешно")
    public static OrderResponse checkUserOrder(Response response){
        return response
                .then().assertThat().statusCode(SC_OK)
                .and()
                .body("success", equalTo(true))
                .and()
                .extract()
                .response()
                .as(OrderResponse.class);
    }

}
