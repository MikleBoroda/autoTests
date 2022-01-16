package steps;

import cucumber.api.java.ru.Если;
import redmine.ui.pages.HeaderPage;
import redmine.ui.pages.Page;

public class UiSteps {

    @Если("Нажать кнопку Войти")
    public void clickOnLoginButton() {
        Page.getPage(HeaderPage.class).loginButton.click();
    }
}
