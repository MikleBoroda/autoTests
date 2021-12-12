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

public class SortingListOfUsersByFirstLastName extends BaseUITest {


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

        User user3 = new User() {{
            setStatus(Status.ACTIVE);
        }}.create();

        User user4 = new User() {{
            setStatus(Status.ACTIVE);
        }}.create();

        openBrowser();
        headerPage.loginButton.click();
        loginPage.login(admin);

    }

    @Test(description = " Администрирование. Сортировка списка пользователей по имени и фамилии")
    public void sortedListFirstLastNameTest() {

        //Отображается домашняя страница
        assertEquals(headerPage.homePage.getText(), "Домашняя страница");

        // На главной странице нажать "Администрирование"
        headerPage.administration.click();
        // Отображается страница "Администрирование"
        assertEquals(administrationPage.administrationTitle.getText(), "Администрирование");

        //Выбрать из списка меню "Пользователи"
        administrationPage.users.click();
        //Отображается таблица с пользователями
        assertTrue(userTablePage.tableUsers.isDisplayed());

        List<String> firstNameAsc = BrowserUtils.getElementsText(userTablePage.firstName);
        List<String> lastNameAsc = BrowserUtils.getElementsText(userTablePage.lastName);
        //Таблица с пользователями не отсортирована по имени
        assertFalse(checkSortedList(firstNameAsc));
        // Таблица с пользователями не отсортирована по фамилии
        assertFalse(checkSortedList(lastNameAsc));


        //В шапке таблицы нажать на "Фамилия"
        userTablePage.button("Фамилия").click();
        List<String> lastNameAsc2 = BrowserUtils.getElementsText(userTablePage.lastName);
        List<String> firstNameAsc2 = BrowserUtils.getElementsText(userTablePage.firstName);

        //Таблица с пользователями отсортирована по фамилии по возрастанию (не учитывается регистр)
        assertListSortedByLastNameAsc(lastNameAsc2);
        // Таблица с пользователями не отсортирована по имени
        assertFalse(checkSortedList(firstNameAsc2));

        //В шапке таблицы нажать на "Фамилия"
        userTablePage.button("Фамилия").click();
        List<String> lastNameDesc = BrowserUtils.getElementsText(userTablePage.lastName);
        List<String> firstNameDes = BrowserUtils.getElementsText(userTablePage.firstName);

        //Таблица с пользователями отсортирована по фамилии по убыванию (не учитывается регистр)
        assertListSortedByLastNameDesc(lastNameDesc);
        //Таблица с пользователями не отсортирована по имени
        assertFalse(checkSortedList(firstNameDes));

        //Таблица с пользователями не отсортирована по имени
        userTablePage.button("Имя").click();
        List<String> firstNameAsc3 = BrowserUtils.getElementsText(userTablePage.firstName);
        List<String> lastNameAsc3 = BrowserUtils.getElementsText(userTablePage.lastName);

        //Таблица с пользователями отсортирована по имени по возрастанию (не учитывается регистр)
        assertListSortedByFirstNameAsc(firstNameAsc3);
        //Таблица с пользователями не отсортирована по фамилии
        assertFalse(checkSortedList(lastNameAsc3));


        //В шапке таблицы нажать на "Имя"
        userTablePage.button("Имя").click();

        List<String> firstNameDesc2 = BrowserUtils.getElementsText(userTablePage.firstName);
        List<String> lastNameDesc2 = BrowserUtils.getElementsText(userTablePage.lastName);

        //Таблица с пользователями отсортирована по имени по убыванию (не учитывается регистр)
        assertListSortedByFirstNameDesc(firstNameDesc2);
        //В шапке таблицы нажать на "Имя"
        assertFalse(checkSortedList(lastNameDesc2));


    }

}
