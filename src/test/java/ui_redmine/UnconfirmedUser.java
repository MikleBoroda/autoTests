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

    @BeforeMethod
    public void prepareFixtures() {
        user = new User() {{
            setStatus(Status.UNACCEPTED);
        }}.create();
        openBrowser();
        headerPage.loginButton.click();
        loginPage.login(user);


    }

    @Test
    public void unconfirmedUserTest() {
        WebElement flashError = browser.getDriver().findElement(By.xpath("//div[@id='content']//div[@id='flash_error']"));

        assertEquals(headerPage.homePage.getText(), "Домашняя страница");
        assertEquals(flashError.getText(), "Ваша учётная запись создана и ожидает подтверждения администратора.");
        assertFalse(BrowserUtils.isElementCurrentlyDisplayed(headerPage.myPage));
        assertTrue(headerPage.loginButton.isDisplayed());
        assertTrue(headerPage.registerButton.isDisplayed());


    }


}
