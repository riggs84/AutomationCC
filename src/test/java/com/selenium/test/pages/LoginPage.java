package com.selenium.test.pages;

import com.selenium.test.webtestsbase.BasePageClass;
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

    public LoginPage(WebDriver driver)
    {
        super(driver);
        PageFactory.initElements(driver, LoginPage.class);
        setPageUrl("https://control.goodsync.com/ui/user-login");
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
    /* depreciated since 20/06/17
    public DashboardPage submitBtn()
    {
        submitButton.click();
        return new DashboardPage(driver);
    }
    */
    public LoginPage loginAs(String email, String password)
    {
        typeEmail(email);
        typePassword(password);
        clickOnElement(submitButton);
        return this;
        // in test validate that some text is present by calling isTextPresent() wrapped by assert function
    }
    public String getPageTitle()
    {
        return driver.getTitle();
    }
    public boolean verifyPageTitle()
    {
        return getPageTitle().contains("JobServer Enterprise");
    }

}
