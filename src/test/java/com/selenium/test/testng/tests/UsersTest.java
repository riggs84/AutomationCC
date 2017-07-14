package com.selenium.test.testng.tests;

import com.selenium.test.pages.LoginPage;
import com.selenium.test.pages.UsersPage;
import com.selenium.test.webtestsbase.DriverFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Created by Victor on 13.07.2017.
 */
public class UsersTest {
    LoginPage lp;
    UsersPage usersPage;

    @BeforeClass
    public void beforeClass(){
        DriverFactory.setBrowser("FIREFOX");
        lp = new LoginPage()
            .loginAs("viktor.iurkov@yandex.ru", "123456");
        usersPage = new UsersPage();
    }

    @Test
    public void userOSnameCanNotBeEmptyTest(){
        usersPage.createNewUser("", "viktor","");
        Assert.assertTrue(usersPage.isTextPresent("This field is required"));
    }

    @Test
    public void userNameCanNotBeEmptyTest(){
        usersPage.createNewUser("viktor", "", "");
        Assert.assertTrue(usersPage.isTextPresent("This field is required"));
    }

    @Test // TODO data provider
    public void OSnameCanNotContainSymbolTest(String osName, String name, String email){
        usersPage.createNewUser(osName, name, email);
        Assert.assertTrue(usersPage.isTextPresent("Bad User OS Name."));
    }

    @Test // TODO data provider
    public void fullNameCanNotContainSymbolsTest(String osName, String name, String email){
        usersPage.createNewUser(osName, name, email);
        Assert.assertTrue(usersPage.isTextPresent("Bad User Full Name."));
    }

    @Test
    public void userOSnameMustBeUniqueTest(){
        usersPage.createNewUser("viktor", "viktor", "");
        Assert.assertTrue(usersPage.checkElementPresentInTable("viktor"));
        usersPage.createNewUser("viktor", "viktor", "");
        Assert.assertTrue(usersPage.isTextPresent("Bad User OS Name:'viktor'"));
    }

    @Test // TODO data provider
    public void crtNewUserWithValidData(String osName, String name, String email){
        usersPage.createNewUser(osName, name, email);
        Assert.assertTrue(usersPage.checkElementPresentInTable(osName));
    }
    /* make assertion for create new user inside of function. it will allow us to verify user creation
    in structural tests
     */
    @Test
    public void applyFilterTest(){
        usersPage.applyFilter("vik");
        Assert.assertTrue(usersPage.checkElementPresentInTable(""));
        // filter should be asserted with countable elements in table
    }

    @AfterClass
    public void afterClass(){
        DriverFactory.browserClose();
    }
}
