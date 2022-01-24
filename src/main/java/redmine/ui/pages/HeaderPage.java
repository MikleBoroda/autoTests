package redmine.ui.pages;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import redmine.cucmber.ElementName;
import redmine.cucmber.PageName;


@NoArgsConstructor(access = AccessLevel.PACKAGE)
@PageName("Заголовок страницы")
public class HeaderPage extends Page {

    @ElementName("Заглавие Домашняя Страница")
    @FindBy(xpath = "//div[@id='content']/h2")
    public WebElement homePageTitle; //Домашняя страница

    @ElementName("Вошли как")
    @FindBy(xpath = "//div[@id ='loggedas']")
    public WebElement enteredAs; //Войти как

    @FindBy(xpath = "//div[@id='top-menu']//a[contains(@class, 'user active')]")
    public WebElement activeUser; // отображение активного юзера после "Вошли как "

    @ElementName("Домашняя страница")
    @FindBy(xpath = "//div[@id='top-menu']//a[contains(@class, 'home')]")
    public WebElement homePage; //Домашняя страница

    @ElementName("Моя страница")
    @FindBy(xpath = "//div[@id='top-menu']//a[contains(@class, 'my-page')]")
    public WebElement myPage; //Моя страница

    @ElementName("Проекты")
    @FindBy(xpath = "//div[@id='top-menu']//a[contains(@class, 'projects')]")
    public WebElement projects; //Проекты

    @ElementName("Администрирование")
    @FindBy(xpath = "//div[@id='top-menu']//a[@class='administration']")
    public WebElement administration; //Админстрирование

    @ElementName("Помощь")
    @FindBy(xpath = "//div[@id='top-menu']//a[contains(@class, 'help')]")
    public WebElement help; //Помощь


    @ElementName("Войти")
    @FindBy(xpath = "//div[@id='account']//a[@class='login']")
    public WebElement loginButton; //войти

    @ElementName("Моя учётная запись")
    @FindBy(xpath = "//div[@id='account']//a[@class='my-account']")
    public WebElement myAccount; // Моя учетная запись

    @ElementName("Выйти")
    @FindBy(xpath = "//div[@id='top-menu']//a[contains(@class, 'logout')]")
    public WebElement logOut; //Кнопка "Выйти"

    @ElementName("Регистрация")
    @FindBy(xpath = "//div[@id='account']//a[@class='register']")
    public WebElement registerButton;

    @ElementName("Поиск")
    @FindBy(xpath = "//div[@id='quick-search']//input[@id='q']")
    public WebElement quickSearch; //поле поиска

    @ElementName("Сообщение")
    @FindBy(xpath = "//div[@id='content']//div[@id='flash_error']")
    public WebElement flashError; //Сообщение об ошибке



}
