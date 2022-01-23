package redmine.ui.pages;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import redmine.cucmber.ElementName;
import redmine.cucmber.PageName;
import redmine.ui.BrowserManager;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
@PageName("Пользователи")
public class UserTablePage extends Page {


    @ElementName("Даты создания")
    @FindBy(xpath = "//table[@class='list users']/tbody//td[@class='created_on']")
    public List<WebElement> creationDates; // дата создания

    @ElementName("Создано")
    @FindBy(xpath = "//th[@title='Сортировать по \"Создано\"']/a")
    public WebElement creationDateButton;

    @FindBy(xpath = "//table[@class='list users']/tbody//td[@class='username']")
    public List<WebElement> userName; //логин пользователей

    @FindBy(xpath = "//table[@class='list users']/tbody//td[@class='firstname']")
    public List<WebElement> firstName; //имя пользователя

    @FindBy(xpath = "//table[@class='list users']/tbody//td[@class='lastname']")
    public List<WebElement> lastName; // фамилия пользователя

    @FindBy(xpath = "//div[@id='content']//table[@class='list users']")
    public WebElement tableUsers; // таблица пользователей

    @FindBy(xpath = "//div[@id='content']//a[@class='icon icon-add']")
    public WebElement addUserButton;

    public WebElement button(String text) {
        return BrowserManager.getBrowser().getDriver().findElement(By.xpath("//table[@class='list users']/thead//th[.='" + text + "']"));
    }
}
