package site.nomoreparties.stellarburgers.api;

import org.junit.BeforeClass;

import static site.nomoreparties.stellarburgers.api.helpers.TestConfig.initConfig;

public class BaseTest {
    @BeforeClass
    public static void setUp(){
        initConfig();
    }
}
