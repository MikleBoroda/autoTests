package ui_redmine;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import redmine.model.user.Status;
import redmine.model.user.User;
import redmine.ui.BrowserUtils;
import ui_test.BaseUITest;

import static org.testng.Assert.*;

public class VerifiedUser extends BaseUITest {
    private User user;

    @BeforeMethod
    public void prepareFixtures() {
        user = new User() {{
            setStatus(Status.ACTIVE);
        }}.create();

        openBrowser();
        headerPage.loginButton.click();
        loginPage.login(user);
    }

    @Test
    public void verifiedUserTest() {
        assertEquals(headerPage.homePageTitle.getText(), "Домашняя страница");
        assertEquals(headerPage.enteredAs.getText() + headerPage.activeUser.getText(),
                headerPage.enteredAs.getText() + user.getLogin());
        assertEquals(headerPage.homePage.getText(), "Домашняя страница");
        assertEquals(headerPage.myPage.getText(), "Моя страница");
        assertEquals(headerPage.projects.getText(), "Проекты");
        assertEquals(headerPage.help.getText(), "Помощь");
        assertEquals(headerPage.myAccount.getText(), "Моя учётная запись");
        assertEquals(headerPage.logOut.getText(), "Выйти");
        assertFalse(BrowserUtils.isElementCurrentlyDisplayed(headerPage.loginButton));
        assertFalse(BrowserUtils.isElementCurrentlyDisplayed(headerPage.registerButton));
        assertFalse(BrowserUtils.isElementCurrentlyDisplayed(headerPage.administration));
        assertTrue(headerPage.quickSearch.isDisplayed());

    }

}
