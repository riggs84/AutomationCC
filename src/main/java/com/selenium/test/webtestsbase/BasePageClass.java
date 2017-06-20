package com.selenium.test.webtestsbase;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

/**
 * Created by MartinRiggs on 6/19/2017.
 */
public class BasePageClass {

    protected WebDriver driver;
    private String PAGE_URL;

    public void setPageUrl(String text)
    {
        this.PAGE_URL = text;
    }
    public BasePageClass(WebDriver driver)
    {
        this.driver = driver;
    }
    public boolean isTextPresent(String text)
    {
        try{
            boolean b = driver.getPageSource().contains(text);
            return b;
        }
        catch(Exception e){
            return false;
        }
    }
    public void openPage()
    {
        driver.get(getPageUrl());
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
