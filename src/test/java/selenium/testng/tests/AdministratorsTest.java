package selenium.testng.tests;

import io.qameta.allure.Description;
import selenium.pages.AdministratorsPage;
import selenium.pages.LoginPage;
import selenium.webtestsbase.DriverFactory;
import org.testng.Assert;
import org.testng.annotations.*;

/**
 * Created by Victor on 29.06.2017.
 */
public class AdministratorsTest {
    AdministratorsPage adminPage;
    LoginPage loginPage;

    //@BeforeClass
    public AdministratorsTest()
    {
        DriverFactory.setBrowser("CHROME");
        this.loginPage = new LoginPage()
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

    @Description("The test checks that sorting by clicking column name is correct DESC and ASC order both")
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

    @Description("The test checks that 'name' field for create new admin form must be non empty")
    @Test
    public void crtNewAdminNameFieldCanNotBeEmptyTest()
    {
        adminPage.createNewAdministrator("Company", "", "yurkov@siber.com",
                "123456", "123456");
        Assert.assertTrue(adminPage.isTextPresent("This field is required."),
                "Warning message 'field is required' is not present");
        //adminPage.clickOnElement(adminPage.getWebElementByName("Cancel"));
    }

    @Description("The test checks that new admin name can be at least 5 char long")
    @Test
    public void crtNewAdminNameShouldBeAtLeast5charLongTest()
    {
        adminPage.createNewAdministrator("Company", "1234", "yurkov@siber.com",
                "123456", "123456");
        Assert.assertTrue(adminPage.isTextPresent("Please enter at least 5 characters."),
                "Warning message 'Please enter at least 5 char' is missing");
        //adminPage.clickOnElement(adminPage.getWebElementByName("Cancel"));
    }

    @Description("The test checks that admin name can not be longer than 60 chars")
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

    @Description("The test checks that email must be valid")
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

    @Description("The test checks that admins password must be at least 6 chars")
    @Test
    public void crtNewAdminPasswordCanNotBeShort()
    {
        adminPage.createNewAdministrator("Company", "Viktor",
                "yurkov@siber.com", "123", "123");
        Assert.assertTrue(adminPage.isTextPresent("Please enter at least 6 characters."),
                "The Warn message that pass is too short is not present");
        //adminPage.clickOnElement(adminPage.getWebElementByName("Cancel"));
    }

    @Description("The test checks that new admin password can not be empty")
    @Test
    public void crtNewAdminPasswordMustBeNotEmpty()
    {
        adminPage.createNewAdministrator("Company", "Viktor",
                "yurkov@siber.com", "", "123456");
        Assert.assertTrue(adminPage.isTextPresent("This field is required."),
                "Warning message 'field is required' is not present");
        //adminPage.clickOnElement(adminPage.getWebElementByName("Cancel"));
    }

    @Description("The test checks that password entered is equal in both fields: password and re-enter password")
    @Test
    public void crtNewAdminPasswordFieldsShouldBeEqual()
    {
        adminPage.createNewAdministrator("Company", "Viktor",
                "yurkov@siber.com", "1234567", "123456");
        Assert.assertTrue(adminPage.isTextPresent("Please enter the same value again."),
                "Warn message that passwords are not equal is missing");
        //adminPage.clickOnElement(adminPage.getWebElementByName("Cancel"));
    }

    @Description("The test checks that email address must be unique and not registered yet")
    @Test
    public void alreadyRegisteredEmailCannotBeUsedTest()
    {
        adminPage.createNewAdministrator("Company", "Name2", "viktor.iurkov+1@yandex.ru",
                "123456", "123456");
        Assert.assertTrue(adminPage.hasElementsInTable("Name2"));
        adminPage.createNewAdministrator("Company", "Name2", "viktor.iurkov+1@yandex.ru",
                "123456", "123456");
        Assert.assertTrue(adminPage.isTextPresent("Administrator with same Email already exists."),
                "already registered user check failed");
        adminPage.deleteAll();
    }

    @Description("The test checks that admin can be deactivated")
    @Test
    public void deactivateAdminTest()
    {//TODO re write with func returning active status of element in table
        adminPage.createNewAdministrator("Company", "Viktor1", "yurkov@siber.com",
                "123456", "123456");
        adminPage.deactivateAdmin("yurkov@siber.com");
        Assert.assertFalse(adminPage.hasElementsInTable("yurkov@siber.com"), "deactivation failed");
        adminPage.showInactive();
        Assert.assertTrue(adminPage.hasElementsInTable("yurkov@siber.com"));
        adminPage.activateAdmin("yurkov@siber.com");
        adminPage.showInactive();
        Assert.assertTrue(adminPage.hasElementsInTable("yurkov@siber.com"));
        adminPage.deleteAdmin("yurkov@siber.com");
    }

    /*@Test //test is done in deactivateAdminTest()
    public void showInactiveBtnActivateTest()
    {
        adminPage.showInactive();
        Assert.assertTrue(adminPage.isTextPresent("yurkov@siber.com"), "inactive element is not present");
    }*/

    /*@Test // test is done in deactivateAdminTest()
    public void showInactiveBtnDeactivateTest()
    {
        adminPage.showInactive();
        Assert.assertFalse(adminPage.isTextPresent("yurkov@siber.com"), "show inactive is hide non active admins");
    }*/

    /*@Test //test is done in deactivateAdminTest()
    public void adminActivationTest()
    {
        adminPage.deactivateORactivateAdmin("yurkov@siber.com");
        adminPage.showInactive(); //disable btn
        Assert.assertTrue(adminPage.isTextPresent("yurkov@siber"), "activation of admin failed");
    }*/

    @Description("The test checks that administrator can be deleted")
    @Test
    public void adminDeletionTest()
    {
        adminPage.createNewAdministrator("Company", "viktor1", "yurkov@siber.com",
                "123456", "123456");
        adminPage.deleteAdmin("yurkov@siber.com");
        Assert.assertFalse(adminPage.hasElementsInTable("yurkov@siber.com"));
    }

    @Description("The test checks that applying filter works wright")
    @Test
    public void applyFilterTest() //should be run last in test order
    {
        adminPage.createNewAdministrator("Company","viktrrr", "viktror.iurkov+1@yandex.ru", "123456", "123456");
        adminPage.applyFilter("vikt");
        Assert.assertEquals(2, adminPage.countElementsInTable("vikt"));
        //Assert.assertTrue(adminPage.hasElementsInTable("viktor"), "Element is not present in table");
        Assert.assertFalse(adminPage
                .hasOtherElementsInTableExcept("vikt"), "Other elements are present in table"); // TODO takes 4-5 seconds to run!!!!!!
    }

    @Description("The test checks that user is able to delete all admins by selecting all and then delete them")
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