package db_requests_tests;

import org.testng.annotations.Test;
import redmine.model.role.Role;

public class RoleRequestsTest {
    @Test
    public void createRole(){
        Role role = new Role();
        role.create();
        System.out.println(role.getId());
    }
}
