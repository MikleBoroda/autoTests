package redmine.ui.pages;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;



@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class AdministrationPage extends Page {

    @FindBy(xpath = "//div[@id='admin-menu']//a[contains(@class,'projects')]")
    public WebElement projects;

    @FindBy(xpath = "//div[@id='admin-menu']//a[contains(@class,'users')]")
    public WebElement users;




}
