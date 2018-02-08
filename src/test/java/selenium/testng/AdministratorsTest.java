package selenium.testng;


import io.qameta.allure.Description;
import selenium.Listeners.ScreenshotListener;
import selenium.pages.AdministratorsPage;
import selenium.pages.LoginPage;
import selenium.pages.entities.Admin;
import org.testng.Assert;
import org.testng.annotations.*;
import selenium.Helpers.SQLhelper;

//import selenium.pages.entities.Admin;

/**
 * Created by Victor on 29.06.2017.
 */
@Listeners({ScreenshotListener.class})
public class AdministratorsTest extends SetupClass {
    AdministratorsPage adminPage;
    LoginPage loginPage;

    public AdministratorsTest(){
        this.loginPage = new LoginPage();
        this.adminPage = new AdministratorsPage();
    }

    @BeforeClass
    public void beforeClass(){
        SQLhelper.cleanAndRecreateDataBase();
        loginPage.loginAs("viktor.iurkov@yandex.ru", "123456");
    }

    @DataProvider(name = "table rows")
    public static Object[][] tableRows()
    {
        return new Object[][]{
                {"Name"},
                {"Email"},
                /*{"User Groups"},
                {"Computer groups"},
                {"Email confirmed"},
                {"Creation date"}*/
        };
    }

    @DataProvider(name = "valid new admin credentials")
    public static Object[][] newAdminValidCredentials()
    {
        return new Object[][]{
                //{"Company", "petya", "1@mail.ru", "123456", "123456"},
                {"123456789012345678901234567890123456789012345678901234567890",
                "yurkov@siber.com", "123456", "123456"}, // name 60 chars
               // {"Group", "12345678901234567890123456789012345678901234567890123456789",
                //"yurkov+1@siber.com.ru", "123456", "123456"}, //name 59 chars
                //{"Company", "victor", "yurkov@1siber123.com", "123456", "123456"},
                //{"Company", "azaza", "yurkov+1@siber-boss.com", "123456", "123456"},
                {"viktor", "yurkov+12@siber.com", "123456", "123456"}
        };
    }

    @DataProvider(name = "wrong email credentials")
    public static Object[][] wrongEmailCredentials()
    {
        return new Object[][]
                {
                        {"name1", "yurkov@@siber.com", "123456", "123456"},
                        {"name1", "@siber.com", "123456", "123456"},
                        {"name1", " yurkov@siber.com", "123456", "123456"},
                        {"name1", "yurkov @siber.com", "123456", "123456"},
                        {"name1", "yurkov@siber.com.", "123456", "123456"},
                        //{"Company", "viktor", "-.%/*&?@mail.ru", "123456", "123456"},
                        //TODO re enable this as Volodya make the error checking to one standard message - not working now
                };
    }

    @DataProvider(name = "invalid name")
    public static Object[][] invalidName(){
        return new Object[][]{
                {"%viktor", "yurkov+5@siber.com", "123456", "123456"},
                {"vik%tor", "yurkov+5@siber.com", "123456", "123456"},
                {"viktor%", "yurkov+5@siber.com", "123456", "123456"},
                {"*viktor*", "yurkov+5@siber.com", "123456", "123456"},
                {"vik*tor", "yurkov+5@siber.com", "123456", "123456"},
        };
    }

    @AfterMethod
    public void afterMethod(){
        SQLhelper.dropAdminTable();
    }

    @Description("The test is checking new admin creation with valid credentials")
    @Test(dataProvider = "valid new admin credentials")
    public void createNewAdminTest(String name, String email, String tempPass, String reTempPass)
    {
        adminPage.openPage();
        adminPage.createNewAdministrator(name, email, tempPass, reTempPass);
        try {
            Assert.assertTrue(adminPage.hasElementsInTable(email), "New created admin not found in table");
        } catch(AssertionError er){
            //adminPage.deleteAdmin(email);
            throw new AssertionError(er.getMessage() + " with test data: "
            + name + " " + email);
        }
        //adminPage.deleteAdmin(email);
        //check goes by email because email is unique value and can not be repeatedly used for new admin
    }

    @Description("The test checks that sorting by clicking column name is correct DESC and ASC order both")
    @Test(dataProvider = "table rows")
    public void sortingTableFieldsTest(String fieldName)
    {
        SQLhelper.createAdministrator("yurkov+14@siber.com", "aaaaa", "123456", true);
        SQLhelper.createAdministrator("yurkov+13@siber.com", "ccccc", "123456", true);
        adminPage.openPage();
        if (!fieldName.equals("Name")){
            adminPage.sortBy(fieldName);
        }
            try {
                Assert.assertTrue(adminPage.isSortedAscendant(fieldName), "The table is not sorted Ascendant order");
                adminPage.sortBy(fieldName);
                Assert.assertTrue(adminPage.isSortedDescendant(fieldName), "The table is not sorted Descendant order");
            } catch (AssertionError er) {
                /*adminPage.deleteAdmin("yurkov+3@siber.com");
                adminPage.deleteAdmin("yurkov+4@siber.com");*/
                throw new AssertionError(er.getMessage() + " on " + fieldName + " field");
        }
        //adminPage.deleteAll(); //TODO  enable this once bug is fixed
        /*adminPage.deleteAdmin("yurkov+3@siber.com");
        adminPage.deleteAdmin("yurkov+4@siber.com");*/
    }

    @Description("The test checks that 'name' field for create new admin form must be non empty")
    @Test
    public void crtNewAdminNameFieldCanNotBeEmptyTest()
    {
        adminPage.openPage();
        adminPage.createNewAdministrator("", "yurkov@siber.com",
                "123456", "123456");
        Assert.assertTrue(adminPage.isTextPresent("This field is required."),
                "Warning message 'field is required' is not present");
    }

    @Description("The test checks that new admin name can be at least 4 char long")
    @Test
    public void crtNewAdminNameShouldBeAtLeast4charLongTest()
    {
        adminPage.openPage();
        adminPage.createNewAdministrator("123", "yurkov@siber.com", "123456",
                "123456");
        Assert.assertTrue(adminPage.isTextPresent("Please enter at least 4 characters."),
                "Warning message 'Please enter at least 4 char' is missing");
    }

    @Description("The test checks that admin name can not be longer than 60 chars")
    @Test // TODO data provider 60 and 61 char name long check
    public void crtNewAdminNameShouldBeNotMoreThan60charTest() //the test is checking that whole field can > 60 char
    {
        adminPage.openPage();
        adminPage.createNewAdministrator("1111111111111111111111111111111111111111111111111111111111111",
                "yurkov@siber.com", "123456",
                "123456");
        Assert.assertTrue(adminPage.isTextPresent("Please enter no more than 60 characters."),
                "Warning message that name must be shorter is not present");
    }

    @Description("The test checks that email must be valid")
    @Test(dataProvider = "wrong email credentials")
    public void crtNewAdminWrongEmailCredTest(String name, String email, String pass1, String pass2)
    {
        //Test uses wrong email creds to check if system validate them
        adminPage.openPage();
        adminPage.createNewAdministrator(name, email, pass1, pass2);
        Assert.assertTrue(adminPage.isTextPresent("Please enter a valid email address."),
                "No warn message that email is not valid");
    }

    @Description("The test checks that administrator name can not contain special symbols")
    @Test(dataProvider = "invalid name")
    public void crtNewAdminNameCanNotContainSpecialSymbols(String name, String email, String pass, String pass2){
        adminPage.openPage();
        adminPage.createNewAdministrator(name, email, pass, pass2);
        Assert.assertTrue(adminPage.isTextPresent("Invalid characters! Symbols *, %, \", ', \\ and spaces at start and end position are not allowed."),
                "no warning message that name is incorrect");
    }

    @Description("The test checks that admins password must be at least 6 chars")
    @Test
    public void crtNewAdminPasswordCanNotBeShort()
    {
        adminPage.openPage();
        adminPage.createNewAdministrator("Viktor", "yurkov@siber.com",
                "123", "123");
        Assert.assertTrue(adminPage.isTextPresent("Please enter at least 6 characters."),
                "The Warn message that pass is too short is not present");
    }

    @Description("The test checks that create new admin can be canceled by clicking cancel btn in from window")
    @Test
    public void crtNewAdminCancellingTest(){
        adminPage.openPage();
        adminPage.cancelingAdminCreation("qwerty5", "yurkov+5@siber.com", "123456", "123456");
        Assert.assertFalse(adminPage.hasElementsInTable("yurkov+5@siber.com"));
    }

    @Description("The test checks that new admin password can not be empty")
    @Test
    public void crtNewAdminPasswordMustBeNotEmpty()
    {
        adminPage.openPage();
        adminPage.createNewAdministrator("Viktor",
                "yurkov@siber.com", "", "123456");
        Assert.assertTrue(adminPage.isTextPresent("This field is required."),
                "Warning message 'field is required' is not present");
    }

    @Description("The test checks that password entered is equal in both fields: password and re-enter password")
    @Test
    public void crtNewAdminPasswordFieldsShouldBeEqual()
    {
        adminPage.openPage();
        adminPage.createNewAdministrator("Viktor",
                "yurkov@siber.com", "1234567", "123456");
        Assert.assertTrue(adminPage.isTextPresent("Please enter the same value again."),
                "Warn message that passwords are not equal is missing");
    }

    @Description("The test checks that email address must be unique and not registered yet")
    @Test
    public void alreadyRegisteredEmailCannotBeUsedTest()
    {
        SQLhelper.createAdministrator("viktor.iurkov+1@yandex.ru", "Name2", "123456", true);
        adminPage.openPage();
        /*adminPage.createNewAdministrator("Company", "Name2", "viktor.iurkov+1@yandex.ru",
                "123456", "123456");*/
        try {
            Assert.assertTrue(adminPage.hasElementsInTable("Name2"));
            adminPage.createNewAdministrator("Name2", "viktor.iurkov+1@yandex.ru",
                    "123456", "123456");
            Assert.assertTrue(adminPage.isTextPresent("Administrator with same Email already exists."),
                    "already registered user check failed");
            adminPage.cancelingAdminCreation();
        } catch(AssertionError er){
            //adminPage.openPage();
            //adminPage.deleteAdmin("viktor.iurkov+1@yandex.ru");
            throw new AssertionError(er.getMessage());
        }
        //adminPage.deleteAdmin("viktor.iurkov+1@yandex.ru");
    }

    @Description("The test checks that admin can be deactivated")
    @Test
    public void deactivateAdminTest()
    {//TODO re write with func returning active status of element in table
        SQLhelper.createAdministrator("yurkov+6@siber.com", "Viktor1", "123456", true);
        adminPage.openPage();
        /*adminPage.createNewAdministrator("Company", "Viktor1", "yurkov+6@siber.com",
                "123456", "123456");*/
        adminPage.deactivateAdmin("yurkov+6@siber.com");
        try {
            Assert.assertFalse(adminPage.hasElementsInTable("yurkov+6@siber.com"), "deactivation failed");
            adminPage.showInactive();
            Assert.assertTrue(adminPage.hasElementsInTable("yurkov+6@siber.com"), "show deactivated failed");
            adminPage.activateAdmin("yurkov+6@siber.com");
            adminPage.showInactive();
            Assert.assertTrue(adminPage.hasElementsInTable("yurkov+6@siber.com"), "activation failed");
        } catch(AssertionError er) {
            //adminPage.deleteAdmin("yurkov+6@siber.com");
            throw new AssertionError(er.getMessage() + " for admin");
        }
        //adminPage.deleteAdmin("yurkov+6@siber.com");
    }

    @Description("The test checks that administrator can be deleted")
    @Test
    public void adminDeletionTest()
    {
        SQLhelper.createAdministrator("yurkov+10@siber.com", "viktor1", "123456", true);
        adminPage.openPage();
        adminPage.deleteAdmin("yurkov+10@siber.com");
        Assert.assertFalse(adminPage.hasElementsInTable("yurkov+10@siber.com"));
    }

    @Description("The test checks that applying filter works wright")
    @Test
    public void applyFilterTest()
    {
        SQLhelper.createAdministrator("viktor.iurkov+1@yandex.ru", "viktrrr", "123456", true);
        adminPage.openPage();
        //adminPage.createNewAdministrator("Company","viktrrr", "viktor.iurkov+1@yandex.ru", "123456", "123456");
        adminPage.applyFilter("viktr");
            try {
                Assert.assertEquals(adminPage.countElementsInTable("viktr"), 1);
                /*Assert.assertFalse(adminPage
                        .hasOtherElementsInTableExcept("viktr"), "Other elements are present in table");// TODO takes 4-5 seconds to run!!!!!!*/
            } catch(AssertionError er){
                //adminPage.deleteAdmin("viktor.iurkov+1@yandex.ru");
                throw new AssertionError(er.getMessage());
        }
        //adminPage.deleteAdmin("viktor.iurkov+1@yandex.ru");
    }

    // TODO activate this test then bug will be fixed
    //@Description("The test checks that user is able to delete all admins by selecting all and then delete them")
    //@Test
    /*public void deleteAllAdminTest() //TODO warning may delete all admins!!!!
    {
        adminPage.createNewAdministrator("Company", "qwerty+1", "yurkov+3@siber.com", "123456", "123456");
        adminPage.deleteAll();
        Assert.assertTrue(adminPage.hasElementsInTable("viktor.iurkov@yandex.ru"));
        Assert.assertFalse(adminPage.hasOtherElementsInTableExcept("viktor.iurkov@yandex.ru"));
    }*/

    /*@AfterClass
    public void afterClass(){
        adminPage.logOut();
    }

    @AfterSuite
    public void afterSuite(){
        DriverFactory.getInstance().browserClose();
    }*/
}
