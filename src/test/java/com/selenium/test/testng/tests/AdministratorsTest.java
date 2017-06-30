package com.selenium.test.testng.tests;

import com.selenium.test.pages.AdministratorsPage;
import com.selenium.test.pages.LoginPage;
import com.selenium.test.webtestsbase.DriverFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Created by Victor on 29.06.2017.
 */
public class AdministratorsTest {
    AdministratorsPage adminPage;
    LoginPage lp;

    @BeforeClass
    public void beforeClass()
    {
        DriverFactory.setBrowser("FIREFOX");
        this.lp = new LoginPage()
                .loginAs("viktor.iurkov@yandex.ru", "123456"); // TODO i don't like this solution
        this.adminPage = new AdministratorsPage();
    }

    @Test
    public void applyFilterTest()
    {
        adminPage.openPage();
        adminPage.applyFilter("sdv");
        Assert.assertEquals(adminPage.isElementsPresentInTable("sdv"),
                adminPage.isElementsNotEqualPresentInTable("sdv"));
    }

    @AfterClass
    public void afterClass()
    {
        DriverFactory.browserClose();
    }
}