package site.nomoreparties.stellarburgers.api.authregister;

import io.qameta.allure.Step;
import site.nomoreparties.stellarburgers.api.authregister.models.RegisterRequest;

import java.util.Random;

public class UserGenerator {
    private static final Random rnd = new Random();
    @Step("Генерация объекта нового юзера")
    public static RegisterRequest generateUser() {
        RegisterRequest user = new RegisterRequest();

        user.setName(generateUsername());
        user.setPassword(generatePassword());
        user.setEmail(generateEmail());

        return user;
    }
    public static String generateUsername(){
        return "Bessonov" + rnd.nextInt(999999);
    }
    public static String generatePassword(){
        return String.valueOf(rnd.nextInt(999999));
    }
    public static String generateEmail(){
        return "autotest" + rnd.nextInt(999999) + "@yandex.ru";
    }
}
