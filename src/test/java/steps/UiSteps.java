package steps;

import cucumber.api.java.ru.Если;
import cucumber.api.java.ru.Затем;
import cucumber.api.java.ru.И;
import org.openqa.selenium.WebElement;
import redmine.allure.asserts.AllureAssert;
import redmine.context.Context;
import redmine.cucmber.PageObjectHelper;
import redmine.model.project.Project;
import redmine.model.user.User;
import redmine.ui.BrowserUtils;
import redmine.ui.pages.HeaderPage;
import redmine.ui.pages.LoginPage;
import redmine.ui.pages.Page;
import redmine.ui.pages.UserTablePage;
import redmine.utils.CompareUtils;
import redmine.utils.StringUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static redmine.allure.asserts.AllureAssert.*;
import static redmine.cucmber.PageObjectHelper.*;
import static redmine.cucmber.PageObjectHelper.findElements;
import static redmine.ui.BrowserUtils.*;
import static redmine.ui.BrowserUtils.isElementCurrentlyDisplayed;
import static redmine.ui.pages.Page.getPage;
import static redmine.utils.CompareUtils.*;
import static redmine.utils.CompareUtils.assertListSortedByDateDesc;

public class UiSteps {

    @Если("Нажать кнопку Войти")
    public void clickOnLoginButton() {
        getPage(HeaderPage.class).loginButton.click();
    }

    @Если("На странице {string} нажать на элемент {string}")
    public void clickOnElementOnPage(String pageName, String elementName) {
        findElement(pageName, elementName).click();
    }

    @Если("На странице {string} отображается элемент {string}")
    public void assertDisplayedElement(String pageName, String elementName) {
        assertTrue(isElementCurrentlyDisplayed(findElement(pageName, elementName)));
    }

    @Если("На странице {string} не отображается элемент {string}")
    public void assertNotDisplayedElement(String pageName, String elementName) {
        assertFalse(isElementCurrentlyDisplayed(findElement(pageName, elementName)));
    }

    @И("Войти в систему под пользователем {string}")
    public void loginAsUser(String userStashId) {
        User user = Context.getStash().get(userStashId, User.class);
        getPage(LoginPage.class).login(user);
    }

    @И("На странице Проекты отображается проект {string}")
    public void assertDisplayedProject(String projectStash) {
        Project project = Context.getStash().get(projectStash, Project.class);
        assertTrue(isElementCurrentlyDisplayed(project.getName()));
    }

    @И("На странице Проекты не отображается проект {string}")
    public void assertNotDisplayedProject(String projectStash) {
        Project project = Context.getStash().get(projectStash, Project.class);
        assertFalse(isElementCurrentlyDisplayed(project.getName()));
    }

    @И("На странице {string} в поле {string} ввести текст {string}")
    public void sendKeysToElementOnPage(String pageName, String elementName, String charSequence) {
        findElement(pageName, elementName).sendKeys(charSequence);

    }

    @И("На странице {string} тексты элементов {string} отсортированы по дате по убыванию")
    public void assertElementsTextsSortedByDateDesc(String pageName, String elementsName) {
        List<WebElement> dates = findElements(pageName, elementsName);
        List<String> creationDatesByDesc = getElementsText(dates);
        assertListSortedByDateDesc(creationDatesByDesc);
    }

    @И("На странице Пользователи в таблице нажать на атрибут {string}")
    public void clickOnAttribute(String attribute) {
        UserTablePage userTablePage = getPage(UserTablePage.class);
        click(userTablePage.button(attribute));
    }

    @И("Таблица пользователей остротирована по атрибуту {string} по алфавиту {string}")
    public void sortByAttribute(String attribute, String sortType) {
        List<WebElement> attributeContent = findElements("Пользователи", attribute);
        List<String> attributeText = getElementsText(attributeContent);
        Comparator<String> comparator = getComparator(sortType);
        List<String> copyValuesAttribute = new ArrayList<>(attributeText);
        copyValuesAttribute.sort(comparator);
        assertEquals(attributeText, copyValuesAttribute);
    }

    @И("Таблица с пользователями не отсортирована по атрибуту {string}")
    public void notSortedAttribute(String attribute) {
        List<WebElement> attributeContent = findElements("Пользователи", attribute);
        List<String> attributeText = getElementsText(attributeContent);
        assertSortedFalse(checkSortedList(attributeText));
    }

    @Затем("На странице {string} заполнить поле {string}")
    public void fillInTheField(String pageName, String field) {
        WebElement webElement = PageObjectHelper.findElement(pageName, field);
        switch (field){
            case ("Email"):
                 webElement.sendKeys(StringUtils.randomEmail());
                 break;
            default: webElement.sendKeys(StringUtils.randomEnglishString(7));
        }

    }
}
