package redmine.ui.pages;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class HeaderPage extends Page {

    @FindBy(xpath = "//div[@id='content']/h2")
    public WebElement homePageTitle; //Домашняя страница

    @FindBy(xpath = "//div[@id='top-menu']//div[@id ='loggedas']")
    public WebElement enteredAs; //Войти как

    @FindBy(xpath = "//div[@id='top-menu']//a[contains(@class, 'user active')]")
    public WebElement activeUser; // отображение активного юзера после "Вошли как "

    @FindBy(xpath = "//div[@id='top-menu']//a[contains(@class, 'home')]")
    public WebElement homePage; //Домашняя страница

    @FindBy(xpath = "//div[@id='top-menu']//a[contains(@class, 'my-page')]")
    public WebElement myPage; //Моя страница

    @FindBy(xpath = "//div[@id='top-menu']//a[contains(@class, 'projects')]")
    public WebElement projects; //Проекты

    @FindBy(xpath = "//div[@id='top-menu']//a[@class='administration']")
    public WebElement administration; //Админстрирование

    @FindBy(xpath = "//div[@id='top-menu']//a[contains(@class, 'help')]")
    public WebElement help; //Помощь


    @FindBy(xpath = "//div[@id='account']//a[@class='login']")
    public WebElement loginButton; //войти

    @FindBy(xpath = "//div[@id='account']//a[@class='my-account']")
    public WebElement myAccount; // Моя учетная запись

    @FindBy(xpath = "//div[@id='top-menu']//a[contains(@class, 'logout')]")
    public WebElement logOut; //Кнопка "Выйти"

    @FindBy(xpath = "//div[@id='account']//a[@class='register']")
    public WebElement registerButton;

    @FindBy(xpath = "//div[@id='quick-search']//input[@id='q']")
    public WebElement quickSearch; //поле поиска


}
