package redmine.ui.pages;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import redmine.cucmber.ElementName;
import redmine.cucmber.PageName;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
@PageName("Новый пользователь")
public class NewUsersPage extends Page {
    @ElementName("Новый пользователь")
    @FindBy(xpath = "//div[@id='content']/h2[contains(a,'Пользователи') and text()=' » Новый пользователь']")
    public WebElement newUsersTitle;// Описание формы страницы "Пользователи"

    @ElementName("Пользователь")
    @FindBy(xpath = "//div[@id='user_form']//input[@id='user_login']")
    public WebElement inputUserLogin; //поле для ввода "Пользователь"(логин)

    @ElementName("Имя")
    @FindBy(xpath = "//div[@id='user_form']//input[@id='user_firstname']")
    public WebElement inputFirstName; // поле для ввода "Имя" пользователя

    @ElementName("Фамилия")
    @FindBy(xpath = "//div[@id='user_form']//input[@id='user_lastname']")
    public WebElement inputLastName; // поле для ввода "Фамилия" пользователя

    @ElementName("Email")
    @FindBy(xpath = "//div[@id='user_form']//input[@id='user_mail']")
    public WebElement inputEmail; // поле для ввода "Email" пользователя

    @ElementName("Создание пароля")
    @FindBy(xpath = "//div[@id='user_form']//input[@id='user_generate_password']")
    public WebElement checkBoxGeneratePass;

    @ElementName("Создать")
    @FindBy(xpath = "//div[@id='content']//form[@class='new_user']/p/input[@value ='Создать']")
    public WebElement createButton;

    @ElementName("Сообщение")
    @FindBy(xpath = "//div[@id='flash_notice']")
    public WebElement userWillBeCreated;


}
