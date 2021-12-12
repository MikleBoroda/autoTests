package ui_redmine;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import redmine.model.user.Status;
import redmine.model.user.User;
import redmine.ui.BrowserUtils;
import ui_test.BaseUITest;

import java.util.List;

import static org.testng.Assert.*;
import static redmine.utils.CompareUtils.*;

public class SortingListOfUsersByUser extends BaseUITest {


    @BeforeMethod(description =
            "1. Заведен пользователь в системе с правами администратора\n" +
                    "2. Заведено несколько пользователей в системе")
    public void prepareFixtures() {

        User admin = new User() {{
            setIsAdmin(true);
        }}.create();

        User user1 = new User() {{
            setStatus(Status.ACTIVE);
        }}.create();

        User user2 = new User() {{
            setStatus(Status.ACTIVE);
        }}.create();

        openBrowser();
        headerPage.loginButton.click();
        loginPage.login(admin);

    }

    @Test(description = "6. Администрирование. Сортировка списка пользователей по пользователю")
    public void sortingListOfUsersByUserTest() {

        //Отображается домашняя страница
        assertEquals(headerPage.homePage.getText(), "Домашняя страница");

        //На главной странице нажать "Администрирование"
        headerPage.administration.click();
        //На главной странице нажать "Администрирование"
        assertEquals(administrationPage.administrationTitle.getText(), "Администрирование");

        //Выбрать из списка меню "Пользователи"
        administrationPage.users.click();
        // Отображается таблица с пользователями
        assertTrue(userTablePage.tableUsers.isDisplayed());


        List<String> userNameAsc = BrowserUtils.getElementsText(userTablePage.userName);

        // Таблица с пользователями отсортирована по логину пользователей по возрастанию
        assertListSortedByUserNameAsc(userNameAsc);

        //В шапке таблицы нажать на "Пользователь"
        userTablePage.button("Пользователь").click();


        List<String> userNameDesc = BrowserUtils.getElementsText(userTablePage.userName);

        //Таблица с пользователями отсортирована по логину пользователей по убыванию
        assertListSortedByUserNameDesc(userNameDesc);

    }


}
