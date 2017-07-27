package com.selenium.test.testng.tests;

import com.selenium.test.pages.AdministratorsPage;
import com.selenium.test.pages.LoginPage;
import com.selenium.test.webtestsbase.DriverFactory;
import org.testng.Assert;
import org.testng.annotations.*;

/**
 * Created by Victor on 29.06.2017.
 */
public class AdministratorsTest {
    AdministratorsPage adminPage;
    LoginPage lp;

    //@BeforeClass
    public AdministratorsTest()
    {
        DriverFactory.setBrowser("FIREFOX");
        this.lp = new LoginPage()
                .loginAs("viktor.iurkov@yandex.ru", "123456"); // TODO i don't like this solution
        this.adminPage = new AdministratorsPage();
    }

    @BeforeTest
    public void beforeTest(){
        adminPage.openPage();
    }

    @DataProvider(name = "table rows")
    public static Object[][] tableRows()
    {
        return new Object[][]{
                {"Role"},
                {"Name"},
                {"Email"},
                {"User Groups"},
                {"Computer groups"},
                {"Email confirmed"},
                {"Creation date"}
        };
    }

    @DataProvider(name = "valid new admin credentials")
    public static Object[][] newAdminValidCredentials()
    {
        return new Object[][]{
                {"Company", "petya", "1@mail.ru", "123456", "123456"},
                {"Group", "123456789012345678901234567890123456789012345678901234567890",
                "yurkov@siber", "123456", "123456"}, // name 60 chars
                {"Company", "viktor", "-.%/*&?@mail.ru", "123456", "123456"},
                {"Group", "12345678901234567890123456789012345678901234567890123456789",
                "yurkov@siber.com.ru", "123456", "123456"}, //name 59 chars
                {"Company", "victor", "yurkov@1siber123.com", "123456", "123456"},
                {"Company", "azaza", "yurkov1@siber-boss.com", "123456", "123456"},
                {"Company", "viktor", "yurkov@siber.com", "123456", "123456"}
        };
    }

    @DataProvider(name = "wrong email credentials")
    public static Object[][] wrongEmailCredentials()
    {
        return new Object[][]
                {
                        {"Company", "name1", "yurkov@@siber.com", "123456", "123456"},
                        {"Company", "name1", "@siber.com", "123456", "123456"},
                        {"Company", "name1", " yurkov@siber.com", "123456", "123456"},
                        {"Company", "name1", "yurkov @siber.com", "123456", "123456"},
                        {"Company", "name1", "yurkov@siber.com.", "123456", "123456"},
                };
    }

    @Test(dataProvider = "valid new admin credentials")
    public void createNewAdminTest(String role, String name, String email, String tempPass, String reTempPass)
    {
        adminPage.createNewAdministrator(role, name, email, tempPass, reTempPass);
        Assert.assertTrue(adminPage.hasElementsInTable(email), "New created admin not found in table");
        adminPage.deleteAdmin(name);
        //check goes by email because email is unique value and can not be repeatedly used for new admin
    }

    @Test(dataProvider = "table rows")
    public void sortingTableFieldsTest(String fieldName)
    {
        /* the test checks ASC and DESC order abilities
        By default first click on table head element leads to ASC order. Second click to DESC order
         */
        adminPage.createNewAdministrator("Company", "aaaaa", "aaaaa@mail.ru", "123456", "123456");
        adminPage.createNewAdministrator("Group", "cccc", "ccccc@mail.ru", "123456", "123456");
        adminPage.sortBy(fieldName);
        Assert.assertTrue(adminPage.isSortedAscendant(fieldName), "The table is not sorted Ascendant order");
        adminPage.sortBy(fieldName);
        Assert.assertTrue(adminPage.isSortedDescendant(fieldName), "The table is not sorted Descendant order");
        adminPage.deleteAll();
    }

    @Test
    public void crtNewAdminNameFieldCanNotBeEmptyTest()
    {
        adminPage.createNewAdministrator("Company", "", "yurkov@siber.com",
                "123456", "123456");
        Assert.assertTrue(adminPage.isTextPresent("This field is required."),
                "Warning message 'field is required' is not present");
        //adminPage.clickOnElement(adminPage.getWebElementByName("Cancel"));
    }

    @Test
    public void crtNewAdminNameShouldBeAtLeast5charLongTest()
    {
        adminPage.createNewAdministrator("Company", "1234", "yurkov@siber.com",
                "123456", "123456");
        Assert.assertTrue(adminPage.isTextPresent("Please enter at least 5 characters."),
                "Warning message 'Please enter at least 5 char' is missing");
        //adminPage.clickOnElement(adminPage.getWebElementByName("Cancel"));
    }

    @Test // TODO data provider 60 and 61 char name long check
    public void crtNewAdminNameShouldBeNotMoreThan60charTest() //the test is checking that whole field can > 60 char
    {
        adminPage.createNewAdministrator("Company",
                "1111111111111111111111111111111111111111111111111111111111111", "yurkov@siber.com",
                "123456", "123456");
        Assert.assertTrue(adminPage.isTextPresent("Please enter no more than 60 characters."),
                "Warning message that name must be shorter is not present");
        //adminPage.clickOnElement(adminPage.getWebElementByName("Cancel"));
    }

    @Test(dataProvider = "wrong email credentials")
    public void crtNewAdminWrongEmailCredTest(String role, String name, String email, String pass1, String pass2)
    {
        //Test uses wrong email creds to check if system validate them
        adminPage.createNewAdministrator(role, name, email, pass1, pass2);
        Assert.assertTrue(adminPage.isTextPresent("Please enter a valid email address."),
                "No warn message that email is not valid");
        //adminPage.clickOnElement(adminPage.getWebElementByName("Cancel"));
    }

    /*@Test //TODO data provider for one @ and @@ and more symbols
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
    }*/

    @Test
    public void crtNewAdminPasswordCanNotBeShort()
    {
        adminPage.createNewAdministrator("Company", "Viktor",
                "yurkov@siber.com", "123", "123");
        Assert.assertTrue(adminPage.isTextPresent("Please enter at least 6 characters."),
                "The Warn message that pass is too short is not present");
        //adminPage.clickOnElement(adminPage.getWebElementByName("Cancel"));
    }

    @Test
    public void crtNewAdminPasswordMustBeNotEmpty()
    {
        adminPage.createNewAdministrator("Company", "Viktor",
                "yurkov@siber.com", "", "123456");
        Assert.assertTrue(adminPage.isTextPresent("This field is required."),
                "Warning message 'field is required' is not present");
        //adminPage.clickOnElement(adminPage.getWebElementByName("Cancel"));
    }

    @Test
    public void crtNewAdminPasswordFieldsShouldBeEqual()
    {
        adminPage.createNewAdministrator("Company", "Viktor",
                "yurkov@siber.com", "1234567", "123456");
        Assert.assertTrue(adminPage.isTextPresent("Please enter the same value again."),
                "Warn message that passwords are not equal is missing");
        //adminPage.clickOnElement(adminPage.getWebElementByName("Cancel"));
    }

    @Test
    public void alreadyRegisteredEmailCannotBeUsedTest()
    {
        adminPage.createNewAdministrator("Company", "Name2", "viktor.iurkov@yandex.ru",
                "123456", "123456");
        Assert.assertTrue(adminPage.isTextPresent("Administrator with same Email already exists."),
                "already registered user check failed");
        //adminPage.clickOnElement(adminPage.getWebElementByName("Cancel"));
    }

    @Test
    public void deactivateAdminTest()
    {
        adminPage.deactivateORactivateAdmin("yurkov@siber.com");
        Assert.assertFalse(adminPage.hasElementsInTable("yurkov@siber.com"), "deactivation failed");
    }

    @Test
    public void showInactiveBtnActivateTest()
    {
        adminPage.showInactive();
        Assert.assertTrue(adminPage.isTextPresent("yurkov@siber.com"), "inactive element is not present");
    }

    @Test
    public void showInactiveBtnDeactivateTest()
    {
        adminPage.showInactive();
        Assert.assertFalse(adminPage.isTextPresent("yurkov@siber.com"), "show inactive is hide non active admins");
    }

    @Test
    public void adminActivationTest()
    {
        adminPage.deactivateORactivateAdmin("yurkov@siber.com");
        adminPage.showInactive(); //disable btn
        Assert.assertTrue(adminPage.isTextPresent("yurkov@siber"), "activation of admin failed");
    }

    @Test
    public void adminDeletionTest()
    {
        adminPage.deleteAdmin("yurkov@siber.com");
        Assert.assertFalse(adminPage.hasElementsInTable("yurkov@siber.com"));
    }

    @Test
    public void applyFilterTest() //should be run last in test order
    {
        adminPage.createNewAdministrator("Company","viktrrr", "abcd@mail.ru", "123456", "123456");
        adminPage.applyFilter("viktor");
        Assert.assertEquals(2, adminPage.countElementsInTable("vikt"));
        //Assert.assertTrue(adminPage.hasElementsInTable("viktor"), "Element is not present in table");
        Assert.assertFalse(adminPage
                .hasOtherElementsInTableExcept("vikt"), "Other elements are present in table"); // TODO takes 4-5 seconds to run!!!!!!
    }

    @Test
    public void deleteAllAdminTest()
    {
        adminPage.deleteAll();
        Assert.assertTrue(adminPage.hasElementsInTable("viktor.iurkov@yandex.ru"));
        Assert.assertFalse(adminPage.hasOtherElementsInTableExcept("viktor.iurkov@yandex.ru"));
    }

    @AfterClass
    public void afterClass()
    {
        DriverFactory.browserClose();
    }
}
