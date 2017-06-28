package com.selenium.test.testng.tests;

import com.selenium.test.pages.LoginPage;
import com.selenium.test.webtestsbase.DriverFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Created by Victor on 28.06.2017.
 */
public class LoginTest {
    LoginPage loginPage;

    @BeforeClass
    public void beforeClass()
    {
        DriverFactory.setBrowser("FIREFOX");
        this.loginPage = new LoginPage();
    }

    @Test
    public void loginTestRegisteredUser()
    {
        loginPage.openPage();
        loginPage.loginAs("viktor.iurkov@yandex.ru", "123456");
        Assert.assertTrue(loginPage.isTextPresent("_qwerty"));
        loginPage.logOut(); //for cookie clean up
    }

    @Test
    public void loginTestNonRegistredUser()
    {
        loginPage.loginAs("viktor.iurkov@yandex.ru", "1234567");
        Assert.assertTrue(loginPage.isTextPresent("Incorrect UserId or Password"));
    }

    @Test
    public void loginTestNoPasswordEntered()
    {
        loginPage.openPage();
        loginPage.loginAs("viktor.iurkov@yandex.ru","");
        Assert.assertTrue(loginPage.isTextPresent("This field is required."));
    }

    @Test
    public void loginTestNoEmailEntered()
    {
        loginPage.openPage();
        loginPage.loginAs("", "123456");
        Assert.assertTrue(loginPage.isTextPresent("This field is required."));
    }

}
