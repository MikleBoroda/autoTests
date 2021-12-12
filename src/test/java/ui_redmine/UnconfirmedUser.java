package ui_redmine;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import redmine.model.user.Status;
import redmine.model.user.User;
import redmine.ui.BrowserUtils;
import ui_test.BaseUITest;

import static org.testng.Assert.*;

public class UnconfirmedUser extends BaseUITest {
    private User user;

    @BeforeMethod(description =
            "1. Заведен пользователь в системе.\n" +
                    "2. Пользователь не подтвержден администратором и не заблокирован")
    public void prepareFixtures() {
        user = new User() {{
            setStatus(Status.UNACCEPTED);
        }}.create();

        openBrowser();
        headerPage.loginButton.click();
        loginPage.login(user);

    }

    @Test(description = "Авторизация неподтвержденным пользователем")
    public void unconfirmedUserTest() {
        WebElement flashError = browser.getDriver().findElement(By.xpath("//div[@id='content']//div[@id='flash_error']"));

        //1. Отображается домашняя страница
        assertEquals(headerPage.homePage.getText(), "Домашняя страница");

        //Отображается ошибка с текстом "Ваша учётная запись создана и ожидает подтверждения администратора."
        assertEquals(flashError.getText(), "Ваша учётная запись создана и ожидает подтверждения администратора.");

        //В заголовке страницы не отображаются элементы "Моя страница"
        assertFalse(BrowserUtils.isElementCurrentlyDisplayed(headerPage.myPage));

        //В заголовке страницы отображаются элементы "Войти", "Регистрация"
        assertTrue(headerPage.loginButton.isDisplayed());
        assertTrue(headerPage.registerButton.isDisplayed());


    }


}
