package redmine.ui;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import redmine.property.Property;

import java.util.List;

import org.openqa.selenium.NoSuchElementException;

import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class BrowserUtils {
    public static List<String> getElementsText(List<WebElement> elements) {
        return elements.stream().map(el -> el.getText()).collect(Collectors.toList());
    }

    public static boolean isElementCurrentlyDisplayed(WebElement element) {
        try {
            BrowserManager.getBrowser().getDriver().manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
            return element.isDisplayed();
        } catch (NoSuchElementException exception) {
            return false;
        } finally {
            BrowserManager.getBrowser().getDriver().manage().timeouts().implicitlyWait(Property.getIntegerProperty("element.timeout"), TimeUnit.SECONDS);
        }
    }

    public static WebElement checkProject(String projectName) {

        WebElement webElement = BrowserManager.getBrowser().getDriver().findElement(By.xpath("//div[@id='projects-index']//a[text()='" + projectName + "']"));
        return webElement;
    }

    public static boolean isElementCurrentlyDisplayed(String projectName) {
        try {
            BrowserManager.getBrowser().getDriver().manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
            WebElement webElement = BrowserManager.getBrowser().getDriver().findElement(By.xpath("//div[@id='projects-index']//a[text()='" + projectName + "']"));
            return webElement.isDisplayed();
        } catch (NoSuchElementException exception) {
            return false;
        } finally {
            BrowserManager.getBrowser().getDriver().manage().timeouts().implicitlyWait(Property.getIntegerProperty("element.timeout"), TimeUnit.SECONDS);
        }
    }
}
