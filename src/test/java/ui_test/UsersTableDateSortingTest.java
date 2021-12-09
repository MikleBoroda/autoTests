package ui_test;


import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import redmine.model.user.User;
import redmine.ui.BrowserManager;
import redmine.ui.BrowserUtils;


import java.util.List;

import static org.testng.Assert.assertTrue;
import static redmine.utils.CompareUtils.*;

public class UsersTableDateSortingTest extends BaseUITest {


    @BeforeMethod
    public void prepareFixtures() {
        User admin = new User() {{
            setIsAdmin(true);
        }}.create();

        openBrowser(); //открыли браузер
        headerPage.loginButton.click(); // нажали войти
        loginPage.login(admin); //залогинились пользователем
        headerPage.administration.click(); //нажали администрирование
        administrationPage.users.click(); //нажали пользователи


    }

    @Test
    public void testTableDateSortingTest() {
        userTablePage.button("Создано").click();
        List<String> creationDatesByDesc = BrowserUtils.getElementsText(userTablePage.creationDates);
        assertListSortedByDateDesc(creationDatesByDesc);

//        browser.actions()
//                .contextClick(headerPage.loginButton)
//                .doubleClick(headerPage.administration)
//                .build().perform();//build - собираем, perform - выполняем


        userTablePage.button("Создано").click();
        List<String> creationDatesByAsc = BrowserUtils.getElementsText(userTablePage.creationDates);
        assertListSortedByDateAsc(creationDatesByAsc);

    }

}
