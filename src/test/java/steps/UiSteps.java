package steps;

import cucumber.api.java.ru.Если;
import cucumber.api.java.ru.И;
import org.openqa.selenium.WebElement;
import redmine.allure.asserts.AllureAssert;
import redmine.context.Context;
import redmine.cucmber.PageObjectHelper;
import redmine.model.project.Project;
import redmine.ui.BrowserUtils;
import redmine.ui.pages.HeaderPage;
import redmine.ui.pages.Page;

import java.util.List;

import static redmine.ui.BrowserUtils.isElementCurrentlyDisplayed;
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

    @Если("На странице {string} отображается элемент {string}")
    public void assertDisplayedElement(String pageName, String elementName) {
        AllureAssert.assertTrue(
                isElementCurrentlyDisplayed(PageObjectHelper.findElement(pageName, elementName)));
    }

    @Если("На странице {string} не отображается элемент {string}")
    public void assertNotDisplayedElement(String pageName, String elementName) {
        AllureAssert.assertFalse(
                isElementCurrentlyDisplayed(PageObjectHelper.findElement(pageName, elementName)));
    }

    @И("На странице Проекты отображается проект {string}")
    public void assertProjectFromListProjects(String projectStash) {
        Project project = Context.getStash().get(projectStash, Project.class);
        AllureAssert.assertTrue(isElementCurrentlyDisplayed(project.getName()));
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
