package com.selenium.test.pages;

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
    private WebElement emailField;

    @FindBy (name = "password")
    private WebElement passwordField;

    @FindBy(name = "login")
    private WebElement submitButton;

    public LoginPage()
    {
        super();
        setPageUrl("https://control.goodsync.com/ui/user-login");
    }

    @Override
    protected String getXpathTableLocation(String elementName) {
        return null;
    }

    @Override
    public WebElement getWebElementByName(String name) {
        return null;
    }

    @Override
    public void sortBy(String tableName) {

    }

    public LoginPage typeEmail(String email)
    {
        setElementText(emailField, email);
        return this;
    }

    public LoginPage typePassword(String password)
    {
        setElementText(passwordField, password);
        return this;
    }

    public LoginPage loginAs(String email, String password)
    {
        openPage();
        typeEmail(email);
        typePassword(password);
        clickOnElement(submitButton);
        return this;
        // in test validate that some text is present by calling isTextPresent() wrapped by assert function
    }


}
