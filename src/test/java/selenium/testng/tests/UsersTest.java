package selenium.testng.tests;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.testng.annotations.*;
import selenium.pages.LoginPage;
import selenium.pages.UsersPage;
import selenium.webtestsbase.DriverFactory;
import org.testng.Assert;

/**
 * Created by Victor on 13.07.2017.
 */
public class UsersTest {
    LoginPage lp;
    UsersPage usersPage;

    public UsersTest(){
        this.lp = new LoginPage();
        this.usersPage = new UsersPage();
    }

    @BeforeTest
    public void beforeTest(){
        lp.loginAs("viktor.iurkov@yandex.ru", "123456");
    }

    @BeforeMethod
    public void beforeMetod(){
        usersPage.openPage();
    }

    @DataProvider(name = "nonValidOSnames")
    public static Object[][] nonValidOSnames(){
        return new Object [][]{
                {"%", "viktor1", ""},
                {"1%", "viktor1", ""},
                {"1%1", "viktor1", ""},
                {"*", "viktor1", ""},
                {"1**1", "viktor1", ""},
                {"\"", "viktor1", ""},
                {"123\"", "viktor1", ""},
                {"\"123\"", "viktor1", ""},
        };
    }

    //TODO add more input data
    @DataProvider(name = "validCredentials")
    public static Object[][] validCredentials(){
        return new Object [][]{
                {"Windows98", "Gareth Marshal", "CEO@mail.ru"},
        };
    }

    @DataProvider(name = "nonValidFullName")
    public static Object[][] nonValidFullName(){
        return new Object [][]{
                {"viktor1", "%", ""},
                {"viktor1", "1%1", ""},
                {"viktor1", "*", ""},
                {"viktor1", "1**1", ""},
        };
    }

    @Description("The test checks that user OS name can not be empty")
    @Test
    public void userOSnameCanNotBeEmptyTest(){
        usersPage.createNewUser("", "viktor","");
        Assert.assertTrue(usersPage.isTextPresent("This field is required"));
    }

    @Description("The test checks that user full name can not be empty")
    @Test
    public void userNameCanNotBeEmptyTest(){
        usersPage.createNewUser("viktor", "", "");
        Assert.assertTrue(usersPage.isTextPresent("This field is required"));
    }

    @Description("The test checks that user OS name can not contain non valid symbols")
    @Test(dataProvider = "nonValidOSnames")
    public void OSnameCanNotContainSymbolTest(String osName, String name, String email){
        usersPage.createNewUser(osName, name, email);
        Assert.assertTrue(usersPage.isTextPresent("Bad User OS Name."));
    }

    @Description("The test checks that full name can not contain non valid symbols")
    @Test(dataProvider = "nonValidFullName")
    public void fullNameCanNotContainSymbolsTest(String osName, String name, String email){
        usersPage.createNewUser(osName, name, email);
        Assert.assertTrue(usersPage.isTextPresent("Bad User Full Name."));
    }

    @Description("The test checks that user OS name must be unique value")
    @Test
    public void userOSnameMustBeUniqueTest(){
        usersPage.createNewUser("viktor", "viktor", "");
        try {
            Assert.assertTrue(usersPage.checkElementPresentInTable("viktor"));
            usersPage.createNewUser("viktor", "viktor", "");
            Assert.assertTrue(usersPage.isTextPresent("Bad User OS Name:'viktor'"));
        } catch(AssertionError er){
            usersPage.deleteAllusers();
            throw new AssertionError(er.getMessage());
        }
        usersPage.deleteAllusers();
    }

    @Description("The test checks that user can be created with valid data. Boundary value technique is used")
    @Test(dataProvider = "validCredentials")
    public void crtNewUserWithValidDataTest(String osName, String name, String email){
        usersPage.createNewUser(osName, name, email);
        Assert.assertTrue(usersPage.checkElementPresentInTable(osName));
    }
    /* make assertion for create new user inside of function. it will allow us to verify user creation
    in structural tests
     */
    @Description("The test checks that applying filter displays correct results")
    @Test
    public void applyFilterTest(){
        usersPage.createNewUser("MacOS", "Elena", "mail@mail.ru");
        usersPage.createNewUser("Windows", "viktor", "mail@mail.com");
        usersPage.applyFilter("vik");
        try {
            Assert.assertEquals(usersPage.countElementsInTableByName("vik"), 1);
        } catch(AssertionError er) {
            usersPage.deleteAllusers();
            throw new AssertionError(er.getMessage());
        }
        usersPage.deleteAllusers();
    }

    @Description("The test checks that sorting by column name works correct")
    @Test
    public void sortingTableByColumnNameTest(String columnName){
        usersPage.createNewUser("aaaaaaa", "aaaaaaaa", "aaaaaaaa@mail.ru");
        usersPage.createNewUser("ccccccc", "cccccc", "cccccccc@mail.ru");
        usersPage.sortTableBy(columnName);
        try {
            Assert.assertTrue(usersPage.isSortedDescendant(columnName), "not in descendant order");
            usersPage.sortTableBy(columnName);
            Assert.assertTrue(usersPage.isSortedAscendant(columnName), "not in ascendant order");
        } catch(AssertionError er) {
            usersPage.deleteAllusers();
            throw new AssertionError(er.getMessage());
        }
        usersPage.deleteAllusers();
    }

    @Description("The test checks that user can be deleted")
    @Test
    public void userDeletionTest(){
        usersPage.createNewUser("someName", "someFullName", "");
        usersPage.deleteUser("someName");
        Assert.assertFalse(usersPage.checkElementPresentInTable("someName"));
    }

    @Description("The test checks that user can select all in table and delete them")
    @Test
    public void userDeletionAllTest(){
        usersPage.createNewUser("testName", "fullName", "");
        usersPage.createNewUser("nameTest", "nameFull", "");
        usersPage.deleteAllusers();
        Assert.assertEquals(usersPage.countAllElementsInTable(), 1);
        Assert.assertTrue(usersPage.checkElementPresentInTable("Empty"));
    }

    @Description("The test checks that user can be deactivated and activated then")
    @Test
    public void deactivateUserTest(){
        usersPage.createNewUser("users", "user1", "");
        usersPage.deactivateUser("users");
        try {
            Assert.assertFalse(usersPage.checkElementPresentInTable("users"));
            usersPage.showInactive();
            Assert.assertTrue(usersPage.checkElementPresentInTable("users"));
            usersPage.activateUser("users");
            usersPage.showInactive();
            Assert.assertTrue(usersPage.checkElementPresentInTable("users"));
        } catch(AssertionError er) {
            usersPage.deleteAllusers();
            throw new AssertionError(er.getMessage());
        }
        usersPage.deleteAllusers();
    }

    @AfterSuite
    public void afterClass(){
        DriverFactory.getInstance().browserClose();
    }
}
