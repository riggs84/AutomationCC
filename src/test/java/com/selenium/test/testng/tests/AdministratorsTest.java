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
    public void applyFilterTest() //should be run last in test order
    {
        adminPage.openPage();
        adminPage.applyFilter("sdv");
        Assert.assertTrue(adminPage.hasElementsInTable("sdv"), "Element is not present in table");
        Assert.assertFalse(adminPage
                .hasOtherElementsInTableExcept("sdv"), "Other elements are present in table"); // TODO takes 4-5 seconds to run!!!!!!
    }

    @Test //TODO add data provider
    public void sortingTableFieldsTest(String fieldName)
    {
        /* the test checks ASC and DESC order abilities
        By default first click on table head element leads to ASC order. Second click to DESC order
         */
         adminPage.sortBy(fieldName);
         Assert.assertTrue(adminPage.isSortedAscendant(fieldName), "The table is not sorted Ascendent order");
         adminPage.sortBy(fieldName);
         Assert.assertTrue(adminPage.isSortedDescendant(fieldName), "The table is not sorted Descendant order");
    }

    @Test //TODO data provider
    public void createNewAdminTest(String role, String name, String email, String tempPass, String reTempPass)
    {
        adminPage.createNewAdministrator(role, name, email, tempPass, reTempPass);
        Assert.assertTrue(adminPage.hasElementsInTable(email), "New created admin not found in table");
        //check goes by email because email is unique value and can not be repeatedly used for new admin
    }

    @Test
    public void crtNewAdminNameFieldCanNotBeEmptyTest()
    {
        adminPage.createNewAdministrator("Company", "", "yurkov@siber.com",
                "123456", "123456");
        Assert.assertTrue(adminPage.isTextPresent("This field is required."),
                "Warning message 'field is required' is not present");
        adminPage.clickOnElement(adminPage.getWebElementByName("Cancel"));
    }

    @Test
    public void crtNewAdminNameShouldBeAtLeast5charLongTest()
    {
        adminPage.createNewAdministrator("Company", "1234", "yurkov@siber.com",
                "123456", "123456");
        Assert.assertTrue(adminPage.isTextPresent("Please enter at least 5 characters."),
                "Warning message 'Please enter at least 5 char' is missing");
        adminPage.clickOnElement(adminPage.getWebElementByName("Cancel"));
    }

    @Test // TODO data provider 60 and 61 char name long check
    public void crtNewAdminNameShouldBeNotMoreThan60charTest() //the test is checking that whole field can be <=60 char
    {
        adminPage.createNewAdministrator("Company",
                "1111111111111111111111111111111111111111111111111111111111111", "yurkov@siber.com",
                "123456", "123456");
        Assert.assertTrue(adminPage.isTextPresent("Please enter no more than 60 characters."),
                "Warning message that name must be shorter is not present");
        adminPage.clickOnElement(adminPage.getWebElementByName("Cancel"));
    }

    @Test //TODO data provider for one @ and @@ and more symbols
    public void crtNewAdminEmailMustHaveOnlyOne_AT_symbolTest()
    {
        adminPage.createNewAdministrator("Company", "viktor", "yurkov@@siber.com",
                "123456", "123456");
        Assert.assertTrue(adminPage.isTextPresent("Please enter a valid email address."),
                "No warn message that email is not valid");
        adminPage.clickOnElement(adminPage.getWebElementByName("Cancel"));
    }

    @Test
    public void crtNewAdminEmailNameMustBeNonEmptyTest()
    {
        adminPage.createNewAdministrator("Company", "Viktor", "@siber.com",
                "123456", "123456");
        Assert.assertTrue(adminPage.isTextPresent("Please enter a valid email address."),
                "No warn message that email is not valid" );
        adminPage.clickOnElement(adminPage.getWebElementByName("Cancel"));
    }

    @Test // TODO provider data begin space bar and end space bar + other symbols in domain name
    public void crtNewAdminEmailNameShouldNotHaveSpaceAtBeginAndEndTest()
    {
        adminPage.createNewAdministrator("Company", "Viktor", " yurkov@siber.com",
                "123456", "123456");
        Assert.assertTrue(adminPage.isTextPresent("Please enter a valid email address."),
                "Warn message that 'email is not valid' is not present");
        adminPage.clickOnElement(adminPage.getWebElementByName("Cancel"));
    }

    @Test
    public void crtNewAdminPasswordCanNotBeShort()
    {
        adminPage.createNewAdministrator("Company", "Viktor",
                "yurkov@siber.com", "123", "123");
        Assert.assertTrue(adminPage.isTextPresent("Please enter at least 6 characters."),
                "The Warn message that pass is too short is not present");
        adminPage.clickOnElement(adminPage.getWebElementByName("Cancel"));
    }

    @Test
    public void crtNewAdminPasswordMustBeNotEmpty()
    {
        adminPage.createNewAdministrator("Company", "Viktor",
                "yurkov@siber.com", "", "123456");
        Assert.assertTrue(adminPage.isTextPresent("This field is required."),
                "Warning message 'field is required' is not present");
        adminPage.clickOnElement(adminPage.getWebElementByName("Cancel"));
    }

    @Test
    public void crtNewAdminPasswordFieldsShouldBeEqual()
    {
        adminPage.createNewAdministrator("Company", "Viktor",
                "yurkov@siber.com", "1234567", "123456");
        Assert.assertTrue(adminPage.isTextPresent("Please enter the same value again."),
                "Warn message that passwords are not equal is missing");
        adminPage.clickOnElement(adminPage.getWebElementByName("Cancel"));
    }

    @Test
    public void test()
    {
        adminPage.openPage();
        adminPage.selectElementInTable("aaaa@aaaa");
    }

    @AfterClass
    public void afterClass()
    {
        DriverFactory.browserClose();
    }
}
