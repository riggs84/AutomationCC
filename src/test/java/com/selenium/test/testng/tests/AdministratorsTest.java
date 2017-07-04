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
    public void crtNewAdminNameFieldCanNotBeEmpty()
    {
        adminPage.createNewAdministrator("Company", "", "qwerty@ert",
                "123456", "123456");
        Assert.assertTrue(adminPage.isTextPresent("This field is required."),
                "Warning message 'field is required' is not present");
        adminPage.clickOnElement(adminPage.getWebElementByName("Cancel"));
    }

    @Test
    public void crtNewAdminNameShouldBeAtLeast5char()
    {
        adminPage.createNewAdministrator("Company", "1234", "qwe@qw",
                "123456", "123456");
        Assert.assertTrue(adminPage.isTextPresent("Please enter at least 5 characters."),
                "Warning message 'Please enter at least 5 char' is missing");
        adminPage.clickOnElement(adminPage.getWebElementByName("Cancel"));
    }

    @AfterClass
    public void afterClass()
    {
        DriverFactory.browserClose();
    }
}
