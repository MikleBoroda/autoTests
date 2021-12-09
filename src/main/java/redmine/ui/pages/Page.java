package redmine.ui.pages;

import lombok.SneakyThrows;
import org.openqa.selenium.support.PageFactory;
import redmine.ui.BrowserManager;

public abstract class Page {

   @SneakyThrows
    public static <T extends Page> T getPage(Class<T> clazz) {
        T page = clazz.getDeclaredConstructor().newInstance();
      PageFactory.initElements(BrowserManager.getBrowser().getDriver(), page);
        return page;
    }

}
