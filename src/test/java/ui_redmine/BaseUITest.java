package ui_redmine;

import io.qameta.allure.Step;
import org.testng.annotations.AfterMethod;
import redmine.ui.Browser;
import redmine.ui.BrowserManager;
import redmine.ui.pages.*;

import static redmine.ui.pages.Page.getPage;

public class BaseUITest {

    protected Browser browser;
    protected HeaderPage headerPage;
    protected LoginPage loginPage;
    protected AdministrationPage administrationPage;
    protected UserTablePage userTablePage;
    protected ProjectsPage projectsPage;
    protected NewUsersPage newUsersPage;


    @Step("открываю браузер на главной странице")
    protected void openBrowser() {
        browser = BrowserManager.getBrowser();
        headerPage = getPage(HeaderPage.class);
        loginPage = getPage(LoginPage.class);
        administrationPage = getPage(AdministrationPage.class);
        userTablePage = getPage(UserTablePage.class);
        projectsPage = getPage(ProjectsPage.class);
        newUsersPage = getPage(NewUsersPage.class);


    }

    @AfterMethod(description = "закрытие браузера")
    public void tearDown() {
        browser.takeScreenshot();
        BrowserManager.removeBrowser();
    }

}
