package db_requests_tests;

import org.testng.annotations.Test;
import redmine.model.project.Project;

public class ProjectRequestTest {
    @Test
    public void createProject(){
        Project project = new Project();
        project.create();
        System.out.println(project.getName());
    }
}
