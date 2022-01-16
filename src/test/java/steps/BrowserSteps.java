package steps;

import cucumber.api.java.bg.И;
import redmine.ui.BrowserManager;

public class BrowserSteps {

    @И("Открыт браузер на главной странице")
    public void openBrowserOnMainPage() {
        BrowserManager.getBrowser();
    }

    @И("Открыт браузер на странице {string}")
    public void openBrowserOnPage(String url){
        BrowserManager.getBrowser(url);
    }
}
