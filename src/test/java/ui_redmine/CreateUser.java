package ui_redmine;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import redmine.db.reaquests.UserRequests;
import redmine.model.user.Status;
import redmine.model.user.User;
import redmine.utils.StringUtils;

import static redmine.allure.asserts.AllureAssert.*;
import static redmine.allure.asserts.AllureAssert.click;

public class CreateUser extends BaseUITest {
    private User admin;

    @BeforeMethod(description = "Заведен пользователь в системе с правами администратора")
    public void prepareFixtures() {

        admin = new User() {{
            setIsAdmin(true);
            setStatus(Status.ACTIVE);
        }}.create();

        openBrowser();

    }


    @Test(description = "Администрирование. Создание пользователя.")
    public void createUserTest() {

        click(headerPage.loginButton, "\"Войти\"");
        loginPage.login(admin);

        assertEquals(headerPage.homePage.getText(),
                "Домашняя страница",
                "Отображается домашняя страница");

        // 2. На главной странице нажать "Администрирование"
        click(headerPage.administration, "Администрирование");
        assertEquals(administrationPage.administrationTitle.getText(),
                "Администрирование",
                "Отображается страница \"Администрирование\"");

        //3. Нажать на форме "Администрирование"  "пользователи" и проверить кнопку
        click(administrationPage.users, "Пользователи");
        assertTrue(userTablePage.addUserButton.isDisplayed(), "Новый пользователь");


        click(userTablePage.addUserButton, "Новый пользователь");
        assertEquals(newUsersPage.newUsersTitle.getText(),
                "Пользователи » Новый пользователь",
                "Отображается страница \"Пользователи >> Новый пользователь\"");

        //4. Заполнить поля "Пользователь", "Имя", "Фамилия", "Email" корректными значениями
        newUsersPage.inputUserLogin.sendKeys("Mixa" + StringUtils.randomString("qazwsxedcedcrfvtgbyhnujmiklop", 3));
        String login = newUsersPage.inputUserLogin.getAttribute("value");
        newUsersPage.inputFirstName.sendKeys("Mixail" + StringUtils.randomString("qazwsxedcedcrfvtgbyhnujmiklop", 3));
        newUsersPage.inputLastName.sendKeys("Kuznetsov" + StringUtils.randomString("qazwsxedcedcrfvtgbyhnujmiklop", 3));
        newUsersPage.inputEmail.sendKeys(StringUtils.randomEmail());

        //5. Установить чекбокс "Создание пароля"
        click(newUsersPage.checkBoxGeneratePass, "чекбокс \"Создание пароля\"");

        //6. Нажать кнопку "Создать"
        click(newUsersPage.createButton, "кнопка \"Создать\"");
        assertEquals(newUsersPage.userWillBeCreated.getText(),
                "Пользователь " + login + " создан.",
                "Пользователь <логин> создан.");

        //запрос в БД для проверки логина
        User user = new UserRequests().read(login);
        assertEquals(user.getLogin(), login,"Проверка записи в БД с логином " + login);

    }

}
