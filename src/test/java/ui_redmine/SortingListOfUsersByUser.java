package ui_redmine;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import redmine.model.user.Status;
import redmine.model.user.User;
import redmine.ui.BrowserUtils;

import java.util.List;

import static redmine.allure.asserts.AllureAssert.*;
import static redmine.allure.asserts.AllureAssert.click;
import static redmine.utils.CompareUtils.*;

public class SortingListOfUsersByUser extends BaseUITest {
    private User admin;

    @BeforeMethod(description =
            "1. Заведен пользователь в системе с правами администратора\n" +
                    "2. Заведено несколько пользователей в системе")
    public void prepareFixtures() {

        admin = new User() {{
            setIsAdmin(true);
        }}.create();

        User user1 = new User() {{
            setStatus(Status.ACTIVE);
        }}.create();

        User user2 = new User() {{
            setStatus(Status.ACTIVE);
        }}.create();

        openBrowser();


    }

    @Test(description = "Администрирование. Сортировка списка пользователей по пользователю")
    public void sortingListOfUsersByUserTest() {

        click(headerPage.loginButton, "\"Войти\"");
        loginPage.login(admin);
        assertEquals(headerPage.homePage.getText(), "Домашняя страница");

        click(headerPage.administration, "Администрирование");
        assertEquals(administrationPage.administrationTitle.getText(), "Администрирование",
                "Отображается страница \"Администрирование\"");

        click(administrationPage.users,"\"Пользователи\"");
        assertTrue(userTablePage.tableUsers.isDisplayed(),"таблица \"Пользователи\"");

        List<String> userNameAsc = BrowserUtils.getElementsText(userTablePage.userName);
        assertListSortedByUserNameAsc(userNameAsc);

        click(userTablePage.button("Пользователь"),"Пользователь");
        List<String> userNameDesc = BrowserUtils.getElementsText(userTablePage.userName);
        assertListSortedByUserNameDesc(userNameDesc);

    }


}
