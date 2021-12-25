package redmine.allure.asserts;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class AllureAssert {
    @Step("Проверка равенства: \"{2}\"")
    public static void assertEquals(Object actual, Object expected, String message) {
        Assert.assertEquals(actual, expected, message);
    }

    @Step("Проверка равенства: \"{1}\"")
    public static void assertEquals(Object actual, Object expected) {
        Assert.assertEquals(actual, expected);
    }

    @Step("проверка отображения элемента: \"{1}\" есть на странице")
    public static void assertTrue(boolean webElement, String text) {
        Assert.assertTrue(webElement);
    }



    @Step("Проверка отсуствия элемента: \"{1}\" отсутствует на странице")
    public static void assertFalse(boolean webElement, String description) {
        Assert.assertFalse(webElement);
    }

    @Step("Проверка сортировки списка по \"{1}\" - {0} список не отсортирован")
    public static void assertSortedFalse(boolean webElement, String description){
        Assert.assertFalse(webElement);
    }
    @Step("Проверка сортировки списка по \"{1}\" - {0} список  отсортирован")
    public static void assertSortedTrue(boolean webElement, String description){
        Assert.assertFalse(webElement);
    }

    @Step("Производится нажатие на WebElement \"{1}\"")
    public static void click(WebElement webElement, String description){
        webElement.click();
    }

}


