package ui_redmine;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import redmine.db.reaquests.UserRequests;
import redmine.model.user.Status;
import redmine.model.user.User;
import redmine.utils.StringUtils;
import ui_test.BaseUITest;

import static org.testng.Assert.assertEquals;

public class CreateUser extends BaseUITest {

    @BeforeMethod
    public void prepareFixtures() {

        User admin = new User() {{
            setIsAdmin(true);
            setStatus(Status.ACTIVE);
        }}.create();

        openBrowser();
        headerPage.loginButton.click();
        loginPage.login(admin);
    }

    @Test
    public void createUserTest() {

        assertEquals(headerPage.homePage.getText(), "Домашняя страница");
        headerPage.administration.click();
        assertEquals(administrationPage.administrationTitle.getText(), "Администрирование");
        administrationPage.users.click();
        assertEquals(userTablePage.addUserButton.getText(), "Новый пользователь");
        userTablePage.addUserButton.click();
        assertEquals(newUsersPage.newUsersTitle.getText(), "Пользователи » Новый пользователь");

        newUsersPage.inputUserLogin.sendKeys("Mixa" + StringUtils.randomString("qazwsxedcedcrfvtgbyhnujmiklop", 3));
        String login = newUsersPage.inputUserLogin.getAttribute("value");
        newUsersPage.inputFirstName.sendKeys("Mixail" + StringUtils.randomString("qazwsxedcedcrfvtgbyhnujmiklop", 3));
        newUsersPage.inputLastName.sendKeys("Kuznetsov" + StringUtils.randomString("qazwsxedcedcrfvtgbyhnujmiklop", 3));
        newUsersPage.inputEmail.sendKeys(StringUtils.randomEmail());
        newUsersPage.checkBoxGeneratePass.click();
        newUsersPage.createButton.click();

        User user = new UserRequests().read(login);

        assertEquals(user.getLogin(), login);

    }

}
