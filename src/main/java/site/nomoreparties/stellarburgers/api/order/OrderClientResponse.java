package site.nomoreparties.stellarburgers.api.order;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import site.nomoreparties.stellarburgers.api.order.models.OrderResponse;

import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class OrderClientResponse {
    @Step("Проверка, что ордер создан успешно")
    public static void checkOrderIsCreated(Response response) {
        response
                .then().assertThat().statusCode(SC_OK)
                .and()
                .body("success", equalTo(true))
                .body("order.number", notNullValue())
                .body("name", notNullValue());
    }

    @Step("Проверка, что ордер создан успешно")
    public static OrderResponse checkUserOrder(Response response) {
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
