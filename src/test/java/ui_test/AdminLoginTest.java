package ui_test;

import lombok.SneakyThrows;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import redmine.model.user.User;



public class AdminLoginTest extends BaseUITest {

    private User user;

    @BeforeMethod
    public void prepareFixtures() {
        user = new User() {{
            setIsAdmin(true);
        }}.create();
        openBrowser();

    }

    @Test
    @SneakyThrows
    public void loginAsAdminTest() {

        // browser.getDriver().get(Property.getStringProperty("url")); //решена задача 2
        //   driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);// Решено 3. Ожидание загрузгрузки элемента в течении 10 сек

        //   WebDriverWait wait = new WebDriverWait(driver, 5);//явное ожидание
        //  wait.until(dr -> dr.findElement(By.xpath("//xpath")).isDisplayed());
        //  wait.until(ExpectedConditions.alertIsPresent());// ждем пока непоявится всплывающеее окно


        //4 Локаторы вынести в отдельное место
//        WebElement loginButton = browser.getDriver().findElement(By.xpath("//div[@id='account']//a[@class='login']"));
//        loginButton.click();
//
//        WebElement loginInput = browser.getDriver().findElement(By.xpath("//input[@id='username']"));
//        loginInput.sendKeys(user.getLogin());
//
//        WebElement passwordInput = browser.getDriver().findElement(By.xpath("//input[@id='password']"));
//        passwordInput.sendKeys(user.getPassword());
//
//        WebElement signInButton = browser.getDriver().findElement(By.xpath("//input[@id='login-submit']"));
//        signInButton.click();
//
//
//        WebElement myAccount = browser.getDriver().findElement(By.xpath("//div[@id='account']//a[@class='my-account']"));
//        Assert.assertEquals(myAccount.getText(), "Моя учетная запись");

        headerPage.loginButton.click();
        loginPage.login(user);
        String myAccountText = headerPage.myAccount.getText();
        Assert.assertEquals(myAccountText, "Моя учётная запись");


      //  browser.getDriver().quit();

    }


}
