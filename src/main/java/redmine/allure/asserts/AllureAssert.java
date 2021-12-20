package redmine.allure.asserts;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.testng.Assert;

public class AllureAssert {
    @Step("Проверка равенства: {2}")
    public static void assertEquals(Object actual, Object expected, String message) {
        Assert.assertEquals(actual, expected, message);
    }

    @Step("Проверка равенства")
    public static void assertEquals(Object actual, Object expected) {
        Assert.assertEquals(actual, expected);
    }
}
