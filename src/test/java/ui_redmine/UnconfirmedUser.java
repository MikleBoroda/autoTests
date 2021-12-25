package ui_redmine;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import redmine.model.user.Status;
import redmine.model.user.User;
import redmine.ui.BrowserUtils;

import static redmine.allure.asserts.AllureAssert.*;
import static redmine.allure.asserts.AllureAssert.click;

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

        click(headerPage.loginButton, "\"Войти\"");
        loginPage.login(user);
        assertEquals(headerPage.homePage.getText(), "Домашняя страница");
        assertEquals(headerPage.flashError.getText(), "Ваша учётная запись создана и ожидает подтверждения администратора.");
        assertFalse(BrowserUtils.isElementCurrentlyDisplayed(headerPage.myPage), "Моя страница");

        //В заголовке страницы отображаются элементы "Войти", "Регистрация"
        assertTrue(headerPage.loginButton.isDisplayed(), "Войти");
        assertTrue(headerPage.registerButton.isDisplayed(), "Регистрация");


    }

}
