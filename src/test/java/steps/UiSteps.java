package steps;

import cucumber.api.java.ru.Если;
import cucumber.api.java.ru.И;
import org.openqa.selenium.WebElement;
import redmine.cucmber.PageObjectHelper;
import redmine.ui.BrowserUtils;
import redmine.ui.pages.HeaderPage;
import redmine.ui.pages.Page;

import java.util.List;

import static redmine.utils.CompareUtils.assertListSortedByDateDesc;

public class UiSteps {

    @Если("Нажать кнопку Войти")
    public void clickOnLoginButton() {
        Page.getPage(HeaderPage.class).loginButton.click();
    }

    @Если("На странице {string} нажать на элемент {string}")
    public void clickOnElementOnPage(String pageName, String elementName) {
        PageObjectHelper.findElement(pageName, elementName).click();
    }

    @И("На странице {string} в поле {string} ввести текст {string}")
    public void sendKeysToElementOnPage(String pageName, String elementName, String charSequence) {
        PageObjectHelper.findElement(pageName, elementName).sendKeys(charSequence);

    }

    @И("На странице {string} тексты элементов {string} отсортированы по дате по убыванию")
    public void assertElementsTextsSortedByDateDesc(String pageName, String elementsName) {
        List<WebElement> dates = PageObjectHelper.findElements(pageName, elementsName);
        List<String> creationDatesByDesc = BrowserUtils.getElementsText(dates);
        assertListSortedByDateDesc(creationDatesByDesc);
    }
}
