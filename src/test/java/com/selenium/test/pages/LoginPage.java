package com.selenium.test.pages;

import com.selenium.test.Elements.Button;
import com.selenium.test.Elements.InputField;
import com.selenium.test.webtestsbase.BasePageClass;
import com.selenium.test.webtestsbase.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

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

    public LoginPage typeEmail(String email)
    {
        emailField.inputText(email);
        return this;
    }

    public LoginPage typePassword(String password)
    {
        passwordField.inputText(password);
        return this;
    }

    public LoginPage loginAs(String email, String password)
    {
        openPage();
        typeEmail(email);
        typePassword(password);
        submitButton.click();
        waitForJSload();
        return this;
        // in test validate that some text is present by calling isTextPresent() wrapped by assert function
    }


}
