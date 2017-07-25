package com.selenium.test.webtestsbase;

import com.gargoylesoftware.htmlunit.javascript.background.JavaScriptExecutor;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by MartinRiggs on 6/19/2017.
 */
public class BasePageClass {

    private String PAGE_URL;
    //private WebDriverWait wait;
    public void setPageUrl(String text)
    {
        this.PAGE_URL = text;
    }

    public BasePageClass()
    {
        //wait = new WebDriverWait(DriverFactory.getDriver(), 5);
        //PageFactory.initElements(DriverFactory.getDriver(), this);
        PageFactory.initElements(new CustomFieldDecorator(DriverFactory.getDriver()), this);
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

    public void setElementText(WebElement elementName, String text)
    {
        elementName.click();
        elementName.clear();
        elementName.sendKeys(text);
    }
    public void logOut()
    {
        DriverFactory.getDriver().get("https://control.goodsync.com/ui/user-logout");
    }

    /*public void clickOnElement(WebElement element)
    {
        element.click();
    }*/

    public String getPageUrl()
    {
        return PAGE_URL;
    }

    public void waitForJSload() {
        JavascriptExecutor jsExec = (JavascriptExecutor) DriverFactory.getDriver();
        while(!((boolean)(jsExec.executeScript("return jQuery.active == 0"))))
        {
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {

            }
        }
    }







}
