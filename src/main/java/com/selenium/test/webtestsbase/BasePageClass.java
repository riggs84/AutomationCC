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
    private WebDriverWait wait;

    public void setPageUrl(String text)
    {
        this.PAGE_URL = text;
    }
    public BasePageClass()
    {
        wait = new WebDriverWait(DriverFactory.getDriver(),5);
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
        element.clear();
        element.sendKeys(text);
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
