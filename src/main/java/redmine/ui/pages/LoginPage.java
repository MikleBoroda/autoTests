package redmine.ui.pages;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import redmine.model.user.User;


@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class LoginPage extends Page {

    @FindBy(xpath = "//input[@id='username']")
    private WebElement userNameInput;

    @FindBy(xpath = "//input[@id='password']")
    private WebElement passwordInput;

    @FindBy(xpath = "//input[@id='login-submit']")
    private WebElement signInButton;


    public void login(String login, String password) {
        userNameInput.sendKeys(login);
        passwordInput.sendKeys(password);
        signInButton.click();

    }

    public void login(User user) {

        login(user.getLogin(), user.getPassword());
    }
}
