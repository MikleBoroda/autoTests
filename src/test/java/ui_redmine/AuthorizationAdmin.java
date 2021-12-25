package ui_redmine;


import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import redmine.model.user.User;
import redmine.ui.BrowserUtils;

import static redmine.allure.asserts.AllureAssert.*;


public class AuthorizationAdmin extends BaseUITest {
    private User admin;

    @BeforeMethod(description = "Заведен пользователь в системе с правами администратора")

    public void prepareFixtures() {

        admin = new User() {{
            setIsAdmin(true);
        }}.create();

        openBrowser();


    }

    @Test(description = "Авторизация администратором")

    public void testAdministrationPage() {

        click(headerPage.loginButton, "\"Войти\"");
        loginPage.login(admin);

        assertEquals(headerPage.homePageTitle.getText(),
                "Домашняя страница",
                "Отображается домашняя страница");

        //2. Отображается "Вошли как <логин пользователя>"
        assertEquals(headerPage.enteredAs.getText(),
                "Вошли как " + admin.getLogin(),
                "Отображается \"Вошли как <логин пользователя>\"");

        //3. В заголовке страницы отображаются элементы: "Домашняя страница", "Моя страница", "Проекты", "Помощь", "Моя учётная запись", "Выйти"
        assertEquals(headerPage.homePage.getText(),
                "Домашняя страница",
                "Текст элемента \"Домашняя страница\"");

        assertEquals(headerPage.myPage.getText(),
                "Моя страница",
                "Текст элемента \"Моя страница\"");

        assertEquals(headerPage.projects.getText(),
                "Проекты",
                "Текст элемента \"Проекты\"");

        assertEquals(headerPage.help.getText(),
                "Помощь",
                "Текст элемента \"Помощь\"");

        assertEquals(headerPage.myAccount.getText(),
                "Моя учётная запись",
                "Текст элемента \"Моя учетная запись\"");

        assertEquals(headerPage.logOut.getText(),
                "Выйти",
                "Текст элемента \"Выйти\"");

        //4. В заголовке страницы не отображаются элементы "Войти", "Регистрация"
        assertFalse(BrowserUtils.isElementCurrentlyDisplayed(headerPage.loginButton), "Войти");
        assertFalse(BrowserUtils.isElementCurrentlyDisplayed(headerPage.registerButton), "Регистрация");

        //5. Отображается элемент "Поиск"
        assertTrue(headerPage.quickSearch.isDisplayed(), "Поиск");


    }

}
