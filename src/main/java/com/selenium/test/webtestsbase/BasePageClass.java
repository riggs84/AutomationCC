package com.selenium.test.webtestsbase;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

/**
 * Created by MartinRiggs on 6/19/2017.
 */
public class BasePageClass {

    private String PAGE_URL;
    private WebDriverWait wait; // TODO do i need wait class?

    public void setPageUrl(String text)
    {
        this.PAGE_URL = text;
    }
    public BasePageClass()
    {
        wait = new WebDriverWait(DriverFactory.getDriver(),5); // TODO do i need this?
        PageFactory.initElements(DriverFactory.getDriver(), this);
    }
    public boolean isTextPresent(String text)
    {
        try{
            boolean b = DriverFactory.getDriver().getPageSource().contains(text);
            return b;
        }
        catch(Exception e){
            return false;
        }
    }
    /* the method should be overriden in every page using wait object for explicit wait*/
    public void openPage()
    {
        DriverFactory.getDriver().get(getPageUrl());
    }
    public void setElementText(WebElement element, String text)
    {
        element.click();
        element.clear();
        element.sendKeys(text);
    }
    public void logOut()
    {
        DriverFactory.getDriver().get("https://control.goodsync.com/ui/user-logout");
    }
    public void clickOnElement(WebElement element)
    {
        element.click();
    }
    public String getPageUrl()
    {
        return PAGE_URL;
    }



}
