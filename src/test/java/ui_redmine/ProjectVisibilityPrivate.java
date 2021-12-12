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

    @BeforeMethod(description =
            "1. Заведен пользователь в системе с правами администратора\n" +
                    "2. Существует приватный проект, не привязанный к пользователю")
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
        //1. Отображается домашняя страница
        assertEquals(headerPage.homePage.getText(), "Домашняя страница");

        //2. На главной странице нажать "Проекты"
        headerPage.projects.click();

        //Отображается страница "Проекты"
        assertEquals(projectsPage.projectsTitle.getText(), "Проекты");

        //На странице отображается проект из предусловия
        assertEquals(projectNotTied.getName(), BrowserUtils.checkProject(projectNotTied.getName()).getText());


    }
}
