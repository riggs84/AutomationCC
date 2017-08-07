package selenium.testng.tests;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import selenium.pages.LoginPage;
import selenium.pages.UsersPage;
import selenium.webtestsbase.DriverFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * Created by Victor on 13.07.2017.
 */
public class UsersTest {
    LoginPage lp;
    UsersPage usersPage;

    //@BeforeClass
    public UsersTest(){
        DriverFactory.setBrowser("FIREFOX");
        this.lp = new LoginPage()
            .loginAs("viktor.iurkov@yandex.ru", "123456");
        this.usersPage = new UsersPage();
    }

    @BeforeTest
    public void beforeTest(){
        usersPage.openPage();
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
    @Test // TODO data provider
    public void OSnameCanNotContainSymbolTest(String osName, String name, String email){
        usersPage.createNewUser(osName, name, email);
        Assert.assertTrue(usersPage.isTextPresent("Bad User OS Name."));
    }

    @Description("The test checks that full name can not contain non valid symbols")
    @Test // TODO data provider
    public void fullNameCanNotContainSymbolsTest(String osName, String name, String email){
        usersPage.createNewUser(osName, name, email);
        Assert.assertTrue(usersPage.isTextPresent("Bad User Full Name."));
    }

    @Description("The test checks that user OS name must be unique value")
    @Test
    public void userOSnameMustBeUniqueTest(){
        usersPage.createNewUser("viktor", "viktor", "");
        Assert.assertTrue(usersPage.checkElementPresentInTable("viktor"));
        usersPage.createNewUser("viktor", "viktor", "");
        Assert.assertTrue(usersPage.isTextPresent("Bad User OS Name:'viktor'"));
        // TODO delete user after
    }

    @Description("The test checks that user can be created with valid data. Boundary value technique is used")
    @Test // TODO data provider
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
        usersPage.createNewUser("Windows", "viktor", "mail@mail.com");
        usersPage.applyFilter("vik");
        Assert.assertEquals(1,usersPage.countElementsInTableByName("vik"));
        // filter should be asserted with countable elements in table
    }

    @Description("The test checks that sorting by column name works correct")
    @Test
    public void sortingTableByColumnNameTest(String columnName){
        usersPage.createNewUser("aaaaaaa", "aaaaaaaa", "aaaaaaaa@mail.ru");
        usersPage.createNewUser("ccccccc", "cccccc", "cccccccc@mail.ru");
        usersPage.sortTableBy(columnName);
        Assert.assertTrue(usersPage.isSortedDescendant(columnName));
        usersPage.sortTableBy(columnName);
        Assert.assertTrue(usersPage.isSortedAscendant(columnName));
        usersPage.deleteAllusers();
    }

    @Description("The test checks that user can be deleted")
    @Test
    public void userDeletionTest(){
        usersPage.createNewUser("someName", "someFullName", "");
        usersPage.deleteUser("someName");
        Assert.assertFalse(usersPage.checkElementPresentInTable("someName"));
    }

    @AfterClass
    public void afterClass(){
        DriverFactory.browserClose();
    }
}
