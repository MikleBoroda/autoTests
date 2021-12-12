package ui_redmine;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import redmine.model.project.Project;
import redmine.model.role.Permissions;
import redmine.model.role.Role;
import redmine.model.user.User;
import redmine.ui.BrowserUtils;
import ui_test.BaseUITest;

import java.util.Collections;
import java.util.List;

import static org.testng.Assert.*;


public class VisibilityOfProjectsUser extends BaseUITest {
    private User userNotAdmin;
    private Role role;
    private Project project1;
    private Project project2;
    private Project project3;

    @BeforeMethod(description =
            "1. Заведен пользователь в системе.\n" +
                    "2. Пользователь подтвержден администратором и не заблокирован\n" +
                    "3. В системе заведена Роль пользователя с правами на просмотр задач\n" +
                    "4. В системе заведен публичный проект (№ 1)\n" +
                    "5. В системе заведен приватный проект (№ 2)\n" +
                    "6. В системе заведен приватный проект (№ 3)\n" +
                    "7. У пользователя нет доступа к проектам №1, №2\n" +
                    "8. У пользователя есть доступ к проекту №3 c ролью из п.3 предусловия")
    public void prepareFixtures() {

        List<Permissions> permissions = Collections.singletonList(Permissions.VIEW_ISSUES);
        role = new Role() {{
            setPermissionsList(permissions);
        }}.create();

        project1 = new Project() {{
            setIsPublic(true);
        }}.create();

        project2 = new Project() {{
            setIsPublic(false);
        }}.create();

        project3 = new Project() {{
            setIsPublic(false);
        }};


        userNotAdmin = new User() {{
            addProjectAndRoles(project3, Collections.singletonList(role));
        }}.create();

        project3.addUserAndRoles(userNotAdmin, Collections.singletonList(role));
        project3.create();


        openBrowser();
        headerPage.loginButton.click();
        loginPage.login(userNotAdmin);
    }

    @Test(description = "5. Видимость проектов. Пользователь")
    public void VisibilityOfProjectsTest() {

        // Отображается домашняя страница
        assertEquals(headerPage.homePage.getText(), "Домашняя страница");

        //На главной странице нажать "Проекты"
        headerPage.projects.click();

        //Отображается страница "Проекты"
        assertEquals(projectsPage.projectsTitle.getText(), "Проекты");

        //Отображается проект из п.4 предусловия
        assertTrue(BrowserUtils.isElementCurrentlyDisplayed(project1.getName()));

        //Не отображается проект из п.5 предусловия
        assertFalse(BrowserUtils.isElementCurrentlyDisplayed(project2.getName()));

        // Отображается проект из п.6 предусловия
        assertTrue(BrowserUtils.isElementCurrentlyDisplayed(project3.getName()));

    }
}
