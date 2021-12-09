package ui_redmine;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import redmine.model.project.Project;
import redmine.model.role.Permissions;
import redmine.model.role.Role;
import redmine.model.user.Status;
import redmine.model.user.User;
import ui_test.BaseUITest;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VisibilityOfProjectsUser extends BaseUITest {
    private User userNotAdmin;
    private Role role;
    private Project project1;
    private Project project2;
    private Project project3;

    @BeforeMethod
    public void prepareFixtures() {

        List<Permissions> permissions = Collections.singletonList(Permissions.VIEW_ISSUES);


        role = new Role() {{
            setPermissionsList(permissions);
        }}.create();

        project1 = new Project(){{
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
        Map<User, List<Role>> members = project3.getMembers();
        project3.create();


        openBrowser();
        headerPage.loginButton.click();
        loginPage.login(userNotAdmin);
    }

    @Test
    public void VisibilityOfProjectsTest() {
        headerPage.projects.click();

    }
}