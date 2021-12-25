package ui_redmine;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import redmine.model.project.Project;
import redmine.model.user.User;
import redmine.ui.BrowserUtils;

import static redmine.allure.asserts.AllureAssert.*;
import static redmine.allure.asserts.AllureAssert.click;

public class ProjectVisibilityPrivate extends BaseUITest {
    private User admin;
    private Project projectNotTied;

    @BeforeMethod(description = "1. Заведен пользователь в системе с правами администратора\n" +
                                "2. Существует приватный проект, не привязанный к пользователю")
    public void prepareFixtures() {
        admin = new User() {{
            setIsAdmin(true);
        }}.create();

        projectNotTied = new Project() {{
            setIsPublic(false);
        }}.create();

        openBrowser();
    }

    @Test(description = "Видимость проекта. Приватный проект. Администратор")
    public void privateProjectTest() {
        click(headerPage.loginButton, "\"Войти\"");
        loginPage.login(admin);

        //1. Отображается домашняя страница
        assertEquals(headerPage.homePage.getText(),
                "Домашняя страница",
                "Отображается домашняя страница");

        //2. На главной странице нажать "Проекты"
        click(headerPage.projects, "Проекты");

        //Отображается страница "Проекты"
        assertEquals(projectsPage.projectsTitle.getText(),
                "Проекты",
                "Отображается страница \"Проекты\"");

        //На странице отображается проект из предусловия
        assertEquals(projectNotTied.getName(),
                BrowserUtils.checkProject(projectNotTied.getName()).getText(),
                "На странице отображается проект из предусловия");

    }
}
