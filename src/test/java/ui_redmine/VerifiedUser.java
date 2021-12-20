package ui_redmine;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import redmine.model.user.Status;
import redmine.model.user.User;
import redmine.ui.BrowserUtils;

import static org.testng.Assert.*;

public class VerifiedUser extends BaseUITest {
    private User user;

    @BeforeMethod(description =
            "1. Заведен пользователь в системе.\n" +
                    "2. Пользователь подтвержден администратором и не заблокирован")
    public void prepareFixtures() {
        user = new User() {{
            setStatus(Status.ACTIVE);
        }}.create();

        openBrowser();
        headerPage.loginButton.click();
        loginPage.login(user);
    }

    @Test(description = "2. Авторизация подтвержденным пользователем")
    public void verifiedUserTest() {
        //Отображается домашняя страница
        assertEquals(headerPage.homePageTitle.getText(), "Домашняя страница");

        // Отображается "Вошли как <логин пользователя>"
        assertEquals(headerPage.enteredAs.getText(), "Вошли как " + user.getLogin());

        //3. В заголовке страницы отображаются элементы: "Домашняя страница", "Моя страница", "Проекты", "Помощь",
        // "Моя учётная запись", "Выйти"
        assertEquals(headerPage.homePage.getText(), "Домашняя страница");
        assertEquals(headerPage.myPage.getText(), "Моя страница");
        assertEquals(headerPage.projects.getText(), "Проекты");
        assertEquals(headerPage.help.getText(), "Помощь");
        assertEquals(headerPage.myAccount.getText(), "Моя учётная запись");
        assertEquals(headerPage.logOut.getText(), "Выйти");

        //4. В заголовке страницы не отображаются элементы "Администрирование", "Войти", "Регистрация"
        assertFalse(BrowserUtils.isElementCurrentlyDisplayed(headerPage.administration));
        assertFalse(BrowserUtils.isElementCurrentlyDisplayed(headerPage.loginButton));
        assertFalse(BrowserUtils.isElementCurrentlyDisplayed(headerPage.registerButton));

       // 5. Отображается элемент "Поиск"
        assertTrue(headerPage.quickSearch.isDisplayed());

    }

}
