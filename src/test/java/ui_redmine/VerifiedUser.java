package ui_redmine;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import redmine.model.user.Status;
import redmine.model.user.User;
import redmine.ui.BrowserUtils;

import static redmine.allure.asserts.AllureAssert.*;
import static redmine.allure.asserts.AllureAssert.click;

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

    }

    @Test(description = "Авторизация подтвержденным пользователем")
    public void verifiedUserTest() {

        click(headerPage.loginButton, "\"Войти\"");
        loginPage.login(user);
        assertEquals(headerPage.homePageTitle.getText(), "Домашняя страница");
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
        assertFalse(BrowserUtils.isElementCurrentlyDisplayed(headerPage.administration),"Администрирование");
        assertFalse(BrowserUtils.isElementCurrentlyDisplayed(headerPage.loginButton),"Войти");
        assertFalse(BrowserUtils.isElementCurrentlyDisplayed(headerPage.registerButton),"Регистрация");

       // 5. Отображается элемент "Поиск"
        assertTrue(headerPage.quickSearch.isDisplayed(),"Поиск");

    }

}
