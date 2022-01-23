package redmine.ui.pages;

import io.qameta.allure.Step;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import redmine.cucmber.ElementName;
import redmine.cucmber.PageName;
import redmine.model.user.User;


@NoArgsConstructor(access = AccessLevel.PACKAGE)
@PageName("Страница авторизации")
public class LoginPage extends Page {

    @ElementName("Логин")
    @FindBy(xpath = "//input[@id='username']")
    private WebElement userNameInput;

    @ElementName("Пароль")
    @FindBy(xpath = "//input[@id='password']")
    private WebElement passwordInput;

    @ElementName("Вход")
    @FindBy(xpath = "//input[@id='login-submit']")
    private WebElement signInButton;


    @Step("Вход в систему пользователем с логином {0} и паролем {1}")
    public void login(String login, String password) {
        userNameInput.sendKeys(login);
        passwordInput.sendKeys(password);
        signInButton.click();

    }


    public void login(User user) {
        login(user.getLogin(), user.getPassword());
    }
}
