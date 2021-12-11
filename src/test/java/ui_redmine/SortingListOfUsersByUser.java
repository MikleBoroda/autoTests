package ui_redmine;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import redmine.model.user.Status;
import redmine.model.user.User;
import redmine.ui.BrowserUtils;
import ui_test.BaseUITest;

import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static redmine.utils.CompareUtils.*;

public class SortingListOfUsersByUser extends BaseUITest {
    private User admin;
    private User user1;
    private User user2;


    @BeforeMethod
    public void prepareFixtures() {

        admin = new User() {{
            setIsAdmin(true);
        }}.create();

        user1 = new User() {{
            setStatus(Status.ACTIVE);
        }}.create();

        user2 = new User() {{
            setStatus(Status.ACTIVE);
        }}.create();

        openBrowser();
        headerPage.loginButton.click();
        loginPage.login(admin);

    }

    @Test
    public void sortingListOfUsersByUserTest() {
        assertEquals(headerPage.homePage.getText(), "Домашняя страница");

        headerPage.administration.click();
        assertEquals(administrationPage.administrationTitle.getText(), "Администрирование");

        administrationPage.users.click();
        assertTrue(userTablePage.tableUsers.isDisplayed());



        List<String> userNameAsc = BrowserUtils.getElementsText(userTablePage.userName);
        assertListSortedByUserNameAsc(userNameAsc);

        userTablePage.button("Пользователь").click();

        List<String> userNameDesc = BrowserUtils.getElementsText(userTablePage.userName);
        assertListSortedByUserNameDesc(userNameDesc);





    }


}
