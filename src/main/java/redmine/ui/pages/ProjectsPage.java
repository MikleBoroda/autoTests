package redmine.ui.pages;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class ProjectsPage extends Page {

    @FindBy(xpath = "//div[@id='main-menu']//a[@class='projects']")
    public WebElement projectsSelect; //вкладка проекты

    @FindBy(xpath = "//div[@id='content']//h2[text() ='Проекты']")
    public WebElement projectsTitle;


}
