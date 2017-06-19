package com.selenium.test.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by MartinRiggs on 6/19/2017.
 */
public class LoginPage {
    private static final String PAGE_URL = "https://control.goodsync.com/ui/user-login";
    private WebDriver driver;

    @FindBy(id = "userid")
    private WebElement emailField;

    @FindBy (name = "password")
    private WebElement passwordField;

    @FindBy(name = "login")
    private WebElement submitButton;

    public LoginPage(WebDriver driver) throws Exception {
        this.driver = driver;
        PageFactory.initElements(this.driver, LoginPage.class);
        /*check that right page is loaded */
        if (!verifyPageTitle()) {
            throw new Exception("Wrong page is loaded on initiation");
        }
    }

    public LoginPage typeEmail(String email)
    {
        emailField.sendKeys(email);
        return this;
    }
    public LoginPage typePassword(String password)
    {
        passwordField.sendKeys(password);
        return this;
    }
    public DashboardPage submitBtn()
    {
        submitButton.click();
        return new DashboardPage(driver);
    }
    public DasboardPage loginAs(String email, String password)
    {
        typeEmail(email);
        typePassword(password);
        return submitBtn();
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
