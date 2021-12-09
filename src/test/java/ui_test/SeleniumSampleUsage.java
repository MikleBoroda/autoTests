package ui_test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

public class SeleniumSampleUsage {

    @Test
    public void openGooglePage() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/chromedriver");
        WebDriver driver = new ChromeDriver();
        driver.get("https://google.com");

        Thread.sleep(5000);// останавливает текущий поток на 5 секунд

        WebElement element = driver.findElement(By.name("q"));
        element.sendKeys("I-teco");
        element.submit();
        Thread.sleep(5000);
        driver.quit();

    }
}
