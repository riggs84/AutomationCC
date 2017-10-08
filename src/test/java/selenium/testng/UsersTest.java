package selenium.testng;

import io.qameta.allure.Description;
import org.testng.annotations.*;
import selenium.Listeners.ScreenshotListener;
import selenium.pages.LoginPage;
import selenium.pages.UsersPage;
import selenium.webtestsbase.DriverFactory;
import org.testng.Assert;
import selenium.webtestsbase.SQLhelper;

/**
 * Created by Victor on 13.07.2017.
 */
@Listeners({ScreenshotListener.class})
public class UsersTest {
    LoginPage loginPage;
    UsersPage usersPage;

    public UsersTest(){
        this.loginPage = new LoginPage();
        this.usersPage = new UsersPage();
    }

    @BeforeClass
    public void beforeClass(){
        //SQLhelper.cleanAndRecreateDataBase();
        loginPage.loginAs("viktor.iurkov@yandex.ru", "123456");
    }

    /*@AfterMethod
    public void afterMethod(){
        SQLhelper.cleanAndRecreateDataBase();
    }*/


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

    @DataProvider(name = "rows")
    public static Object[][] rows(){
        return new Object[][]{
                {"User Full Name"},
                {"User OS Name"},
                {"Email"},
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
        usersPage.openPage();
        usersPage.createNewUser("", "viktor","");
        Assert.assertTrue(usersPage.isTextPresent("This field is required"));
    }

    @Description("The test checks that user full name can not be empty")
    @Test
    public void userNameCanNotBeEmptyTest(){
        usersPage.openPage();
        usersPage.createNewUser("viktor", "", "");
        Assert.assertTrue(usersPage.isTextPresent("This field is required"));
    }

    @Description("The test checks that user OS name can not contain non valid symbols")
    @Test(dataProvider = "nonValidOSnames")
    public void OSnameCanNotContainSymbolTest(String osName, String name, String email){
        usersPage.openPage();
        usersPage.createNewUser(osName, name, email);
        Assert.assertTrue(usersPage.isTextPresent("Bad User OS Name."));
        //usersPage.newUserCreationCancelling();
    }

    @Description("The test checks that full name can not contain non valid symbols")
    @Test(dataProvider = "nonValidFullName") //TODO wrong we need close dialog window
    public void fullNameCanNotContainSymbolsTest(String osName, String name, String email){
        usersPage.openPage();
        usersPage.createNewUser(osName, name, email);
        Assert.assertTrue(usersPage.isTextPresent("Bad User Full Name."));
        //usersPage.newUserCreationCancelling();
    }

    @Description("The test checks that user OS name must be unique value")
    @Test
    public void userOSnameMustBeUniqueTest(){
        usersPage.openPage();
        usersPage.createNewUser("viktor", "viktor", "");
        try {
            Assert.assertTrue(usersPage.checkElementPresentInTable("viktor"));
            usersPage.createNewUser("viktor", "viktor", "");
            Assert.assertTrue(usersPage.isTextPresent("Bad User OS Name: 'viktor', User with same User OS Name already exists."));
        } catch(AssertionError er){
            /*usersPage.openPage();
            usersPage.deleteAllusers();*/
            throw new AssertionError(er.getMessage());
        }
        //usersPage.openPage(); //TODO why is userCreationCancelling not working on home laptop ?
        /*usersPage.newUserCreationCancelling();
        usersPage.deleteAllusers();*/
    }

    @Description("The test checks that user can be created with valid data. Boundary value technique is used")
    @Test(dataProvider = "validCredentials")
    public void crtNewUserWithValidDataTest(String osName, String name, String email){
        usersPage.openPage();
        usersPage.createNewUser(osName, name, email);
        try {
            Assert.assertTrue(usersPage.checkElementPresentInTable(osName), "Creation new user is failed");
        } catch(AssertionError er) {
            //usersPage.deleteUser(name);
            throw new AssertionError(er.getMessage() + " with data: " + osName + " " + name);
        }
        //usersPage.deleteUser(name);
    }
    /* make assertion for create new user inside of function. it will allow us to verify user creation
    in structural tests
     */
    @Description("The test checks that applying filter displays correct results")
    @Test
    public void applyFilterTest(){
        usersPage.openPage();
        usersPage.createNewUser("MacOS", "Elena", "mail@mail.ru");
        usersPage.createNewUser("Windows", "viktor", "mail@mail.com");
        usersPage.applyFilter("vik");
        try {
            Assert.assertEquals(usersPage.countElementsInTableByName("vik"), 1);
        } catch(AssertionError er) {
            /*usersPage.openPage();
            usersPage.deleteAllusers();*/
            throw new AssertionError(er.getMessage());
        }
        /*usersPage.openPage();
        usersPage.deleteAllusers();*/
    }

    @Description("The test checks that sorting by column name works correct")
    @Test(dataProvider = "rows") //TODO rewrite it as one test with bunch of assertions
    public void sortingTableByColumnNameTest(String columnName){
        usersPage.openPage();
        usersPage.createNewUser("aaaaaaa", "aaaaaaaa", "aaaaaaaa@mail.ru");
        usersPage.createNewUser("ccccccc", "cccccc", "cccccccc@mail.ru");
        if(!columnName.equals("User OS Name")){
            usersPage.sortTableBy(columnName);
        }
        Assert.assertTrue(usersPage.isSortedAscendant(columnName), "not in descendant order");
        usersPage.sortTableBy(columnName);
        Assert.assertTrue(usersPage.isSortedDescendant(columnName), "not in ascendant order");
        //usersPage.deleteAllusers();
    }

    @Description("The test checks that user can be deleted")
    @Test
    public void userDeletionTest(){
        usersPage.openPage();
        usersPage.createNewUser("someName", "someFullName", "");
        try {
            usersPage.deleteUser("someName");
            Assert.assertFalse(usersPage.checkElementPresentInTable("someFullName"));
        } catch(AssertionError er) {
            usersPage.makeScreenShot("userDeletionTest");
            throw new AssertionError(er.getMessage());
        }

    }

    @Description("The test checks that user can select all in table and delete them")
    @Test
    public void userDeletionAllTest(){
        usersPage.openPage();
        usersPage.createNewUser("testName", "fullName", "");
        usersPage.createNewUser("nameTest", "nameFull", "");
        usersPage.deleteAllusers();
        Assert.assertEquals(usersPage.countAllElementsInTable(), 1);
        Assert.assertTrue(usersPage.checkElementPresentInTable("Empty"));
    }

    @Description("The test checks that user can be deactivated and activated then")
    @Test
    public void deactivateUserTest(){
        usersPage.openPage();
        usersPage.createNewUser("users", "user1", "");
        usersPage.deactivateUser("users");
        try {
            Assert.assertFalse(usersPage.checkElementPresentInTable("user1"));
            usersPage.showInactive();
            Assert.assertTrue(usersPage.checkElementPresentInTable("users"));
            usersPage.activateUser("users");
            usersPage.showInactive();
            Assert.assertTrue(usersPage.checkElementPresentInTable("users"));
        } catch(AssertionError er) {
            //usersPage.deleteAllusers();
            throw new AssertionError(er.getMessage());
        }
        //usersPage.deleteAllusers();
    }

    /*@AfterClass
    public void afterClass(){
        loginPage.logOut();
    }

    @AfterSuite
    public void afterSuite(){
        DriverFactory.getInstance().browserClose();
    }*/
}
