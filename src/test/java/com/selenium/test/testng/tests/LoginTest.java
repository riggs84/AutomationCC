package com.selenium.test.testng.tests;

import com.selenium.test.pages.LoginPage;
import com.selenium.test.webtestsbase.DriverFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Created by Victor on 28.06.2017.
 */
public class LoginTest {
    LoginPage loginPage;

    @DataProvider (name = "nonValidEmailCredentials")
    public Object[][] nonValidEmailCredentials()
    {
        return new Object [][]{
                {"viktor.iurkov@ yandex.ru", "123456"},
                {},
                {},
        };
    }
    @DataProvider (name = "registeredUsers")
    public Object [][] registeredUsers()
    {
        // email, password, userName
        return new Object [][]
                {
                        {"viktor.iurkov@yandex.ru", "123456", "_qwerty"},
                        {},
                };
    }
    @DataProvider (name = "nonRegisteredUsers")
    public Object [][] nonRegisteredUsers()
    {
        return new Object[][]
                {
                        {},
                        {},

                };
    }

    @BeforeClass
    public void beforeClass()
    {
        DriverFactory.setBrowser("FIREFOX");
        this.loginPage = new LoginPage();
    }

    @Test (dataProvider = "registeredUsers")
    public void loginTestRegisteredUser(String email, String password, String userName)
    {
        //loginPage.openPage();
        loginPage.loginAs(email, password);
        Assert.assertTrue(loginPage.isTextPresent(userName));
        loginPage.logOut(); //for cookie clean up
    }

    @Test (dataProvider = "nonRegisteredUsers")
    public void loginTestNonRegistredUser(String email, String password)
    {
        loginPage.loginAs(email, password);
        Assert.assertTrue(loginPage.isTextPresent("Incorrect UserId or Password"));
    }

    @Test
    public void loginTestNoPasswordEntered()
    {
        //loginPage.openPage();
        loginPage.loginAs("viktor.iurkov@yandex.ru","");
        Assert.assertTrue(loginPage.isTextPresent("This field is required."));
    }

    @Test
    public void loginTestNoEmailEntered()
    {
        //loginPage.openPage();
        loginPage.loginAs("", "123456");
        Assert.assertTrue(loginPage.isTextPresent("This field is required."));
    }

    @Test (dataProvider = "nonValidEmailCredentials")
    public void LoginTestInvalidEmailCredentials (String email, String password)
    {
        //loginPage.openPage();
        loginPage.loginAs(email, password);
        Assert.assertTrue(loginPage.isTextPresent("Please enter a valid email address."));
    }

    @AfterClass
    public void afterClass()
    {
        DriverFactory.browserClose();
    }


}