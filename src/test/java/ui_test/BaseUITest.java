package ui_test;

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


    protected void openBrowser() {
        browser = BrowserManager.getBrowser();
        headerPage = getPage(HeaderPage.class);
        loginPage = getPage(LoginPage.class);
        administrationPage = getPage(AdministrationPage.class);
        userTablePage = getPage(UserTablePage.class);
        projectsPage = getPage(ProjectsPage.class);


    }

    @AfterMethod
    public void tearDown() {
        BrowserManager.removeBrowser();
    }

}
