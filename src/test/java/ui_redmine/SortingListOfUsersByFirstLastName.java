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


    @BeforeMethod
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

    @Test
    public void sortedListFirstLastNameTest() {

        assertEquals(headerPage.homePage.getText(), "Домашняя страница");

        headerPage.administration.click();
        assertEquals(administrationPage.administrationTitle.getText(), "Администрирование");

        administrationPage.users.click();
        assertTrue(userTablePage.tableUsers.isDisplayed());

        List<String> firstNameAsc = BrowserUtils.getElementsText(userTablePage.firstName);
        List<String> lastNameAsc = BrowserUtils.getElementsText(userTablePage.lastName);
        assertFalse(checkSortedList(firstNameAsc));
        assertFalse(checkSortedList(lastNameAsc));


        userTablePage.button("Фамилия").click();

        List<String> lastNameAsc2 = BrowserUtils.getElementsText(userTablePage.lastName);
        List<String> firstNameAsc2 = BrowserUtils.getElementsText(userTablePage.firstName);
        assertListSortedByLastNameAsc(lastNameAsc2);
        assertFalse(checkSortedList(firstNameAsc2));


        userTablePage.button("Фамилия").click();

        List<String> lastNameDesc = BrowserUtils.getElementsText(userTablePage.lastName);
        List<String> firstNameDes = BrowserUtils.getElementsText(userTablePage.firstName);
        assertListSortedByLastNameDesc(lastNameDesc);
        assertFalse(checkSortedList(firstNameDes));

        userTablePage.button("Имя").click();

        List<String> firstNameAsc3 = BrowserUtils.getElementsText(userTablePage.firstName);
        List<String> lastNameAsc3 = BrowserUtils.getElementsText(userTablePage.lastName);
        assertListSortedByFirstNameAsc(firstNameAsc3);
        assertFalse(checkSortedList(lastNameAsc3));


        userTablePage.button("Имя").click();

        List<String> firstNameDesc2 = BrowserUtils.getElementsText(userTablePage.firstName);
        List<String> lastNameDesc2 = BrowserUtils.getElementsText(userTablePage.lastName);
        assertListSortedByFirstNameDesc(firstNameDesc2);
        assertFalse(checkSortedList(lastNameDesc2));





    }

}
