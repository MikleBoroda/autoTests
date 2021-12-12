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

    @BeforeMethod(description = "Заведен пользователь в системе с правами администратора")
    public void prepareFixtures() {

        User admin = new User() {{
            setIsAdmin(true);
            setStatus(Status.ACTIVE);
        }}.create();

        openBrowser();
        headerPage.loginButton.click();
        loginPage.login(admin);
    }


    @Test(description = "8. Администрирование. Создание пользователя.")
    public void createUserTest() {

        assertEquals(headerPage.homePage.getText(), "Домашняя страница", "1. Отображается домашняя страница");

        // 2. На главной странице нажать "Администрирование"
        headerPage.administration.click();
        assertEquals(administrationPage.administrationTitle.getText(), "Администрирование");

        //3. Нажать "Новый пользователь"
        administrationPage.users.click();
        assertEquals(userTablePage.addUserButton.getText(), "Новый пользователь");

        //3. Нажать "Новый пользователь"
        userTablePage.addUserButton.click();
        assertEquals(newUsersPage.newUsersTitle.getText(), "Пользователи » Новый пользователь", "Отображается страница \"Пользователи >> Новый пользователь\"");

        //4. Заполнить поля "Пользователь", "Имя", "Фамилия", "Email" корректными значениями
        newUsersPage.inputUserLogin.sendKeys("Mixa" + StringUtils.randomString("qazwsxedcedcrfvtgbyhnujmiklop", 3));
        String login = newUsersPage.inputUserLogin.getAttribute("value");
        newUsersPage.inputFirstName.sendKeys("Mixail" + StringUtils.randomString("qazwsxedcedcrfvtgbyhnujmiklop", 3));
        newUsersPage.inputLastName.sendKeys("Kuznetsov" + StringUtils.randomString("qazwsxedcedcrfvtgbyhnujmiklop", 3));
        newUsersPage.inputEmail.sendKeys(StringUtils.randomEmail());

        //5. Установить чекбокс "Создание пароля"
        newUsersPage.checkBoxGeneratePass.click();

        //6. Нажать кнопку Создать
        newUsersPage.createButton.click();
        assertEquals(newUsersPage.userWillBeCreated.getText(), "Пользователь " + login + " создан.");

        //запрос в БД для проверки логина
        User user = new UserRequests().read(login);
        assertEquals(user.getLogin(), login);

    }

}
