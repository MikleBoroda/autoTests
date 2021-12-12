package redmine.ui.pages;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class NewUsersPage extends Page {

    @FindBy(xpath = "//div[@id='content']/h2[contains(a,'Пользователи') and text()=' » Новый пользователь']")
    public WebElement newUsersTitle;// Описание формы страницы "Пользователи"

    @FindBy(xpath = "//div[@id='user_form']//input[@id='user_login']")
    public WebElement inputUserLogin; //поле для ввода "Пользователь"(логин)

    @FindBy(xpath = "//div[@id='user_form']//input[@id='user_firstname']")
    public WebElement inputFirstName; // поле для ввода "Имя" пользователя

    @FindBy(xpath = "//div[@id='user_form']//input[@id='user_lastname']")
    public WebElement inputLastName; // поле для ввода "Фамилия" пользователя

    @FindBy(xpath = "//div[@id='user_form']//input[@id='user_mail']")
    public WebElement inputEmail; // поле для ввода "Email" пользователя

    @FindBy(xpath = "//div[@id='user_form']//input[@id='user_generate_password']")
    public WebElement checkBoxGeneratePass;

    @FindBy(xpath = "//div[@id='content']//form[@class='new_user']/p/input[@value ='Создать']")
    public WebElement createButton;

    @FindBy(xpath = "//div[@id='flash_notice']")
    public WebElement userWillBeCreated;


}
