package selenium.testng;

import io.qameta.allure.Description;
import org.testng.Assert;
import org.testng.annotations.*;
import selenium.Helpers.SQLhelper;
import selenium.Listeners.ScreenshotListener;
import selenium.pages.ComputerGroupsPage;
import selenium.pages.LoginPage;

@Listeners({ScreenshotListener.class})
public class ComputerGroupTest extends SetupClass {
    LoginPage loginPage;
    ComputerGroupsPage computerGroupsPage;

    public ComputerGroupTest(){
        this.loginPage = new LoginPage();
        this.computerGroupsPage = new ComputerGroupsPage();
    }

    @BeforeClass
    public void beforeClass(){
        SQLhelper.cleanAndRecreateDataBase();
        loginPage.loginAs("viktor.iurkov@yandex.ru", "123456");
    }

    @AfterMethod
    public void afterMethod(){
        SQLhelper.dropComputerGroupsTable();
    }

    @DataProvider(name = "valid name")
    public static Object[][] validName(){
        return new Object[][]{
                {"1111"}, // 4
                {"11111"}, // 5
                {"12345qwerty.#1234567890asdfghASDFGTER76"}, // 39
                {"12345qwerty.#1234567890asdfghASDFGTER76z"}, // 40
                {"1234 1234"}, // space in the middle
                {"12345@!$-=+,."}, // valid symbol combination
        };
    }

    @DataProvider(name = "invalid name")
    public static Object[][] invalidName(){
        return new Object[][]{
                {" 1111"}, // space at start
                {"1111 "}, // space at end
                {" 1111 "}, // space at end and start
                {"*1111"}, // * at start
                {"1111*"}, // * at end
                {"11*11"}, // * in the middle
                {"11**11"}, // * multiply
                {"'1234"}, // ' at start
                {"1234'"}, // ' at end
                {"12'34"}, // ' in the middle
                {"12''34"}, // ' multiply
                {"\"1234"}, // " at start
                {"1234\""}, // " at end
                {"12\"\"34"}, // " multiply
                {"%1234"}, // % at start
                {"1234%"}, // % at end
                {"12%34"}, // % in the middle
                {"12%%34"}, // % multiply
                {"\\1234"}, // \ at start
                {"1234\\"}, // \ at end
                {"12\\34"}, // \ in the middle
                {"12\\3\\34"}, // \ multiply
        };
    }

    @Description("The test checks that computer group can be created with valid data")
    @Test(dataProvider = "valid name")
    public void computerGroupCanBeCreatedValidDataTest(String computerGroupName){
        computerGroupsPage.openPage();
        computerGroupsPage.createNewComputerGroup(computerGroupName);
        Assert.assertTrue(computerGroupsPage.checkElementPresentInTable(computerGroupName),
                "Computer group with name: " + computerGroupName + " not found in table");
    }

    @Description("The test checks that computer group can not be created with name length 41")
    @Test
    public void computerGroupCanNotBeCreatedWithName41LengthTest(){
        computerGroupsPage.openPage();
        computerGroupsPage.createNewComputerGroup("12345qwerty.#1234567890asdfghASDFGTER76z2");
        Assert.assertTrue(computerGroupsPage.isTextPresent("Please enter no more than 40 characters."),
                "The warning message 'Please enter no more than 40 characters.' not found");
    }

    @Description("The test checks that group name length can not be shorter than 4 chars")
    @Test
    public void computerGroupNameLengthCanNotBe3charsTest(){
        computerGroupsPage.openPage();
        computerGroupsPage.createNewComputerGroup("123");
        Assert.assertTrue(computerGroupsPage.isTextPresent("Please enter at least 4 characters."),
                "The warning message 'Please enter at least 4 characters.' not found");
    }

    @Description("The test checks that incorrect symbols can be in group name")
    @Test(dataProvider = "invalid name")
    public void computerGroupNameCanNotContainForbiddenSymbolsTest(String computerGroupName){
        computerGroupsPage.openPage();
        computerGroupsPage.createNewComputerGroup(computerGroupName);
        Assert.assertTrue(computerGroupsPage.isTextPresent("Invalid characters! Symbols *, %, \", ', \\ and spaces at start and end position are not allowed."),
                "The warning text 'Invalid characters! Symbols *, %, \", ', \\ and spaces at start and end position are not allowed.' is not found");
    }

    @Description("The test checks that field can not be empty")
    @Test
    public void computerGroupNameCanNotBeEmptyTest(){
        computerGroupsPage.openPage();
        computerGroupsPage.createNewComputerGroup("");
        Assert.assertTrue(computerGroupsPage.isTextPresent("This field is required."),
                "Warning message 'This field is required.' not found");
    }

    @Description("The test checks that computer group name must be unique")
    @Test
    public void computerGroupNameMustBeUniqueTest(){
        computerGroupsPage.openPage();
        SQLhelper.createComputerGroup("123456", 1);
        computerGroupsPage.createNewComputerGroup("123456");
        Assert.assertTrue(computerGroupsPage.isTextPresent(" Bad Group Name: '123456', Group with same Group Name already exists."),
                "Warning message ' Bad Group Name: '123456', Group with same Group Name already exists.' not found");
    }

    @Description("The test checks that computer group can be deactivated")
    @Test
    public void computerGroupCanBeDeactivatedTest(){
        computerGroupsPage.openPage();
        SQLhelper.createComputerGroup("123456", 1);
        computerGroupsPage.deactivateComputerGroup("123456");
        Assert.assertTrue(computerGroupsPage.checkElementPresentInTable("123456"),
                "group '123456' deactivated and should not be visible");
    }

    @Description("The test checks that deactivated group can be activated")
    @Test
    public void computerGroupCanBeActivatedTest(){
        computerGroupsPage.openPage();
        SQLhelper.createComputerGroup("123456", 0);
        computerGroupsPage.showInactive();
        Assert.assertTrue(computerGroupsPage.checkElementPresentInTable("123456"),
                "show all inactive doesn't show group 123456 up");
        computerGroupsPage.activateComputerGroup("123456");
        computerGroupsPage.showInactive();
        Assert.assertTrue(computerGroupsPage.checkElementPresentInTable("123456"),
                "Activated group '123456' is not shown in table with show inactive = false");
    }

    @Description("The test checks that filter can show related entries")
    @Test
    public void computerGroupFilterTest(){
        computerGroupsPage.openPage();
        SQLhelper.createComputerGroup("123456", 1);
        SQLhelper.createComputerGroup("qwerty", 1);
        computerGroupsPage.applyFilter("123");
        Assert.assertTrue(computerGroupsPage.checkElementPresentInTable("123456"),
                "Group '123456' not found in table");
    }



}
