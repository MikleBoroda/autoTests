package steps;


import cucumber.api.java.bg.То;
import org.testng.Assert;
import redmine.ui.pages.HeaderPage;
import redmine.ui.pages.Page;


public class AssertionSteps {

    @То("Текст элемента моей учетной записи - {string}")
    public void assertMyAccountText(String expectedText) {
        String actualText = Page.getPage(HeaderPage.class).myAccount.getText();
        Assert.assertEquals(actualText, expectedText, "Проверка текста элемента моей учетной записи");
    }


}
