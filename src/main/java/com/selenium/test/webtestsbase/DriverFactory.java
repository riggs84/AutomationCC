package com.selenium.test.webtestsbase;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;

/**
 * Created by Victor on 21.06.2017.
 */
public class DriverFactory {

    private static WebDriver driver;
    private static long IMPLICIT_WAIT_TIMEOUT = 5;

    public static WebDriver getDriver()
    {
        return driver;
    }

    public static void setBrowser(String browserName)
    {
        switch(browserName.toUpperCase())
        {
            // driver = new browserType(desiredcapabilities)
            case "CHROME":
                break;
            case "IE":
                break;
            case "FIREFOX":
                driver = new FirefoxDriver(BrowserSettings.getSettings("firefox"));
                break;
            default:
                break;
        }
        driver.manage().timeouts().implicitlyWait(IMPLICIT_WAIT_TIMEOUT, TimeUnit.SECONDS);
    }

    public static void browserClose()
    {
        if (driver != null)
        {
            driver = null;
            driver.quit();
        }
    }

}
