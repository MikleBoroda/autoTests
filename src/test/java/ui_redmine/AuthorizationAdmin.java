package ui_redmine;


import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import redmine.model.user.User;
import redmine.ui.BrowserUtils;
import ui_test.BaseUITest;

import static org.testng.Assert.*;

public class AuthorizationAdmin extends BaseUITest {
    private User admin;

    @BeforeMethod

    public void prepareFixtures() {

        admin = new User() {{
            setIsAdmin(true);
        }}.create();


        openBrowser(); //открыли браузер
        headerPage.loginButton.click(); // нажали войти
        loginPage.login(admin); //залогинились пользователем

    }

    @Test

    public void testAdministrationPage() {
        assertEquals(headerPage.homePageTitle.getText(), "Домашняя страница");
        assertEquals(headerPage.enteredAs.getText() + headerPage.activeUser.getText(),
                headerPage.enteredAs.getText() + admin.getLogin());
        assertEquals(headerPage.homePage.getText(), "Домашняя страница");
        assertEquals(headerPage.myPage.getText(), "Моя страница");
        assertEquals(headerPage.projects.getText(), "Проекты");
        assertEquals(headerPage.help.getText(), "Помощь");
        assertEquals(headerPage.myAccount.getText(), "Моя учётная запись");
        assertEquals(headerPage.logOut.getText(), "Выйти");
        assertFalse(BrowserUtils.isElementCurrentlyDisplayed(headerPage.loginButton));
        assertFalse(BrowserUtils.isElementCurrentlyDisplayed(headerPage.registerButton));
        assertTrue(headerPage.quickSearch.isDisplayed());


    }

}
