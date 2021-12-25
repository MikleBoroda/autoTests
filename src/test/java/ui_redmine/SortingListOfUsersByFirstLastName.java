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

public class SortingListOfUsersByFirstLastName extends BaseUITest {
    private User admin;

    @BeforeMethod(description = "1. Заведен пользователь в системе с правами администратора\n" +
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

        User user3 = new User() {{
            setStatus(Status.ACTIVE);
        }}.create();

        User user4 = new User() {{
            setStatus(Status.ACTIVE);
        }}.create();

        openBrowser();

    }

    @Test(description = " Администрирование. Сортировка списка пользователей по имени и фамилии")
    public void sortedListFirstLastNameTest() {

        click(headerPage.loginButton, "\"Войти\"");
        loginPage.login(admin);
        assertEquals(headerPage.homePage.getText(), "Домашняя страница");

        click(headerPage.administration, "Администрирование");
        assertEquals(administrationPage.administrationTitle.getText(),
                "Администрирование",
                "Отображается страница \"Администрирование\"");

        click(administrationPage.users, "\"Пользователи\"");
        assertTrue(userTablePage.tableUsers.isDisplayed(), "таблица \"Пользователи\"");

        List<String> firstNameAsc = BrowserUtils.getElementsText(userTablePage.firstName);
        List<String> lastNameAsc = BrowserUtils.getElementsText(userTablePage.lastName);
        assertSortedFalse(checkSortedList(firstNameAsc), "Имя");
        assertSortedFalse(checkSortedList(lastNameAsc), "Фамилия");


        click(userTablePage.button("Фамилия"), "Фамилия");
        List<String> lastNameAsc2 = BrowserUtils.getElementsText(userTablePage.lastName);
        List<String> firstNameAsc2 = BrowserUtils.getElementsText(userTablePage.firstName);
        assertListSortedByLastNameAsc(lastNameAsc2);
        assertSortedFalse(checkSortedList(firstNameAsc2), "Имя");


        click(userTablePage.button("Фамилия"), "Фамилия");
        List<String> lastNameDesc = BrowserUtils.getElementsText(userTablePage.lastName);
        List<String> firstNameDes = BrowserUtils.getElementsText(userTablePage.firstName);
        assertListSortedByLastNameDesc(lastNameDesc);
        assertSortedFalse(checkSortedList(firstNameDes), "Имя");

        click(userTablePage.button("Имя"), "Имя");
        List<String> firstNameAsc3 = BrowserUtils.getElementsText(userTablePage.firstName);
        List<String> lastNameAsc3 = BrowserUtils.getElementsText(userTablePage.lastName);
        assertListSortedByFirstNameAsc(firstNameAsc3);
        assertSortedFalse(checkSortedList(lastNameAsc3), "Фамилия");

        click(userTablePage.button("Имя"), "Имя");
        List<String> firstNameDesc2 = BrowserUtils.getElementsText(userTablePage.firstName);
        List<String> lastNameDesc2 = BrowserUtils.getElementsText(userTablePage.lastName);
        assertListSortedByFirstNameDesc(firstNameDesc2);
        assertSortedFalse(checkSortedList(lastNameDesc2), "Имя");

    }

}
