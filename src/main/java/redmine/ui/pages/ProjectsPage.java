package redmine.ui.pages;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import redmine.cucmber.ElementName;
import redmine.cucmber.PageName;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
@PageName("Проекты")
public class ProjectsPage extends Page {

    @ElementName("Проекты")
    @FindBy(xpath = "//div[@id='main-menu']//a[text() ='Проекты']")
    public WebElement projectsSelect; //вкладка проекты

    @ElementName("Проекты")
    @FindBy(xpath = "//div[@id='main']//h2[text() ='Проекты']")
    public WebElement projectsTitle; //ЗАГОЛОВОК ПРОЕКТЫ


}
