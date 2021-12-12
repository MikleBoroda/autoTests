package ui_redmine;


import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import redmine.model.user.User;
import redmine.ui.BrowserUtils;
import ui_test.BaseUITest;

import static org.testng.Assert.*;

public class AuthorizationAdmin extends BaseUITest {
    private User admin;

    @BeforeMethod(description = "Заведен пользователь в системе с правами администратора")

    public void prepareFixtures() {

        admin = new User() {{
            setIsAdmin(true);
        }}.create();

        openBrowser(); //открыли браузер
        headerPage.loginButton.click(); // нажали войти
        loginPage.login(admin); //залогинились пользователем

    }

    @Test(description = "1. Авторизация администратором")

    public void testAdministrationPage() {
        //1. Отображается домашняя страница
        assertEquals(headerPage.homePageTitle.getText(), "Домашняя страница");

        //2. Отображается "Вошли как <логин пользователя>"
        assertEquals(headerPage.enteredAs.getText(), "Вошли как " + admin.getLogin());
        //3. В заголовке страницы отображаются элементы: "Домашняя страница", "Моя страница", "Проекты", "Помощь", "Моя учётная запись", "Выйти"
        assertEquals(headerPage.homePage.getText(), "Домашняя страница");
        assertEquals(headerPage.myPage.getText(), "Моя страница");
        assertEquals(headerPage.projects.getText(), "Проекты");
        assertEquals(headerPage.help.getText(), "Помощь");
        assertEquals(headerPage.myAccount.getText(), "Моя учётная запись");
        assertEquals(headerPage.logOut.getText(), "Выйти");

        //4. В заголовке страницы не отображаются элементы "Войти", "Регистрация"
        assertFalse(BrowserUtils.isElementCurrentlyDisplayed(headerPage.loginButton));
        assertFalse(BrowserUtils.isElementCurrentlyDisplayed(headerPage.registerButton));

        //5. Отображается элемент "Поиск"
        assertTrue(headerPage.quickSearch.isDisplayed());


    }

}
