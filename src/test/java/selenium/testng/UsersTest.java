package selenium.testng;

import io.qameta.allure.Description;
import org.testng.annotations.*;
import selenium.Listeners.ScreenshotListener;
import selenium.pages.LoginPage;
import selenium.pages.UsersPage;
import org.testng.Assert;
import selenium.Helpers.SQLhelper;

/**
 * Created by Victor on 13.07.2017.
 */
@Listeners({ScreenshotListener.class})
public class UsersTest extends SetupClass {
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

    @AfterMethod
    public void afterMethod(){
        SQLhelper.cleanAndRecreateDataBase();
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

    /* make assertion for create new user inside of function. it will allow us to verify user creation
    in structural tests
     */
    @Description("The test checks that applying filter displays correct results")
    @Test
    public void applyFilterTest(){
        SQLhelper.createUser("MacOS", "Elena", "mail@mail.ru");
        SQLhelper.createUser("Windows", "viktor", "mail@mail.ru");
        usersPage.openPage();
        /*usersPage.createNewUser("MacOS", "Elena", "mail@mail.ru");
        usersPage.createNewUser("Windows", "viktor", "mail@mail.com");*/
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

    @Description("The test checks that sorting by column name {columnName} works correct")
    @Test(dataProvider = "rows") //TODO rewrite it as one test with bunch of assertions
    public void sortingTableByColumnNameTest(String columnName){
        SQLhelper.createUser("aaaaaaa", "aaaaaaa", "aaaaaaa@mail.ru");
        SQLhelper.createUser("ccccccc", "ccccccc", "ccccccc@mail.ru");
        usersPage.openPage();
        /*usersPage.createNewUser("aaaaaaa", "aaaaaaaa", "aaaaaaaa@mail.ru");
        usersPage.createNewUser("ccccccc", "cccccc", "cccccccc@mail.ru");*/
        if(!columnName.equals("User OS Name")){
            usersPage.sortTableBy(columnName);
        }
        Assert.assertTrue(usersPage.isSortedAscendant(columnName), "Column " + columnName + "not in descendant order");
        usersPage.sortTableBy(columnName);
        Assert.assertTrue(usersPage.isSortedDescendant(columnName), "Column " + columnName + "not in ascendant order");
        //usersPage.deleteAllusers();
    }

    @Description("The test checks that user can be deleted")
    @Test
    public void userDeletionTest(){
        SQLhelper.createUser("someName", "someFullName", "");
        usersPage.openPage();
        //usersPage.createNewUser("someName", "someFullName", "");
        try {
            usersPage.deleteUser("someName");
            Assert.assertFalse(usersPage.checkElementPresentInTable("someFullName"));
        } catch(AssertionError er) {
            throw new AssertionError(er.getMessage());
        }

    }

    @Description("The test checks that user can select all in table and delete them")
    @Test
    public void userDeletionAllTest(){
        SQLhelper.createUser("testName", "fullName", "");
        SQLhelper.createUser("nameTest", "nameFull", "");
        usersPage.openPage();
        /*usersPage.createNewUser("testName", "fullName", "");
        usersPage.createNewUser("nameTest", "nameFull", "");*/
        usersPage.deleteAllusers();
        //Assert.assertEquals(usersPage.countAllElementsInTable(), 1);
        Assert.assertTrue(usersPage.checkElementPresentInTable("Empty"));
    }

    @Description("The test checks that user can be deactivated and activated then")
    @Test
    public void deactivateUserTest(){
        SQLhelper.createUser("users", "user1", "");
        usersPage.openPage();
        //usersPage.createNewUser("users", "user1", "");
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
