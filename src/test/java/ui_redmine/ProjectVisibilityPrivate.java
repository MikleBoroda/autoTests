package ui_redmine;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import redmine.model.project.Project;
import redmine.model.user.User;
import redmine.ui.BrowserUtils;
import ui_test.BaseUITest;

import static org.testng.Assert.assertEquals;

public class ProjectVisibilityPrivate extends BaseUITest {
    private User admin;
    private Project projectNotTied;

    @BeforeMethod
    public void prepareFixtures() {


        admin = new User() {{
            setIsAdmin(true);

        }}.create();

        projectNotTied = new Project() {{
            setIsPublic(false);
        }}.create();

        openBrowser();
        headerPage.loginButton.click();
        loginPage.login(admin);

    }

    @Test
    public void privateProjectTest() {

        assertEquals(headerPage.homePage.getText(), "Домашняя страница");
        headerPage.projects.click();
        assertEquals(projectsPage.projectsTitle.getText(), "Проекты");
        assertEquals(projectNotTied.getName(), BrowserUtils.checkProject(projectNotTied.getName()).getText());


    }
}
