package redmine.allure.asserts;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class AllureAssert {
    @Step("Проверка равенства: \"{1}\"")
    public static void assertEquals(Object actual, Object expected) {
        Assert.assertEquals(actual, expected);
    }

    @Step("проверка истинности: {0}")
    public static void assertTrue(boolean meaning) {
        Assert.assertTrue(meaning);
    }

    @Step("проверка истинности: {0}")
    public static void assertFalse(boolean meaning) {
        Assert.assertFalse(meaning);
    }

    @Step("проверка отсутсвия: {0}")
    public static void assertNull(Object meaning) {
        Assert.assertNull(meaning);
    }

    @Step("проверка наличия объекта: {0}")
    public static void assertNotNull(Object meaning) {
        Assert.assertNotNull(meaning);
    }

    @Step("Проверка сортировки списка по \"{1}\" - {0} список не отсортирован")
    public static void assertSortedFalse(boolean webElement) {
        Assert.assertFalse(webElement);
    }

    @Step("Производится нажатие на WebElement \"{1}\"")
    public static void click(WebElement webElement) {
        webElement.click();
    }

}


