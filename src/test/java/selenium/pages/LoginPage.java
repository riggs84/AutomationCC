package selenium.pages;

import io.qameta.allure.Step;
import selenium.Elements.Button;
import selenium.Elements.InputField;
import selenium.webtestsbase.BasePageClass;
import org.openqa.selenium.support.FindBy;

/**
 * Created by MartinRiggs on 6/19/2017.
 */
public class LoginPage extends BasePageClass {

    @FindBy(id = "userid")
    InputField emailField;

    @FindBy (name = "password")
    InputField passwordField;

    @FindBy(name = "login")
    Button submitButton;

    public LoginPage()
    {
        super();
        setPageUrl("https://control.goodsync.com/ui/user-login");
    }

    private LoginPage typeEmail(String email)
    {
        emailField.inputText(email);
        return this;
    }

    private LoginPage typePassword(String password)
    {
        passwordField.inputText(password);
        return this;
    }

    @Step("Login by entering email and password")
    public LoginPage loginAs(String email, String password)
    {
        openPage();
        typeEmail(email);
        typePassword(password);
        submitButton.click();
        return this;
        // in test validate that some text is present by calling isTextPresent() wrapped by assert function
    }


}