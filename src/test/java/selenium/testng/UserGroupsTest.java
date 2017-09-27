package selenium.testng;

import io.qameta.allure.Description;
import org.testng.Assert;
import org.testng.annotations.*;
import selenium.pages.LoginPage;
import selenium.pages.UserGroupsPage;
import selenium.webtestsbase.DriverFactory;

public class UserGroupsTest {

    LoginPage loginPage;
    UserGroupsPage userGroupsPage;

    public UserGroupsTest(){
        this.loginPage = new LoginPage();
        this.userGroupsPage = new UserGroupsPage();
    }

    @DataProvider(name = "table rows")
    public static Object[][] tableRows(){
        return new Object[][]{
                {"Group Name"},
                {"Group OS Name"},
                {"Users Count"},
                {"Jobs Count"},
                {"Creation Date"},
        };
    }

    @DataProvider(name = "non valid group names")
    public static Object[][] invalidNames(){
        return new Object[][]{
                {"%123", ""},
                {"%123%", ""},
                {"12%3", ""},
                {"*123*", ""},
                {"12*34", ""},
        };
    }

    @BeforeClass
    public void beforeClass(){
        loginPage.loginAs("viktor.iurkov@yandex.ru", "123456");
    }

    @Description("The test is checking that new group can be created")
    @Test //TODO add data provider
    public void newUserGroupCreationTest(String userGroupName, String userGroupOSname){
        userGroupsPage.openPage();
        userGroupsPage.createNewUserGroup(userGroupName, userGroupOSname);
        try {
            Assert.assertTrue(userGroupsPage.checkElementPresentInTable(userGroupName), "group creation failed");
        } catch(AssertionError er) {
            userGroupsPage.deleteGroup(userGroupName);
            throw new AssertionError(er.getMessage() + " on data: " + userGroupName + " " + userGroupOSname);
        }
        userGroupsPage.deleteGroup(userGroupName);
    }

    @Description("The test is checking that user can apply filter to groups table")
    @Test
    public void applyFilterTest(){
        userGroupsPage.openPage();
        userGroupsPage.createNewUserGroup("Group", "Windows");
        userGroupsPage.createNewUserGroup("ABC", "Windows");
        userGroupsPage.applyFilter("Group");
        try {
            Assert.assertTrue(userGroupsPage.checkElementPresentInTable("Group"));
            Assert.assertEquals(userGroupsPage.countAllElementsInTable(), 1);
        } catch(AssertionError er) {
            userGroupsPage.deleteAllGroups();
            throw new AssertionError(er.getMessage());
        }
        userGroupsPage.deleteAllGroups();
    }

    @Description("The test checks that user group name can not be empty value")
    @Test //TODO think how to make it reliable if it fails
    public void crtNewUserGroupNameCanNotBeEmptyValue(){
        userGroupsPage.openPage();
        userGroupsPage.createNewUserGroup("", "123");
        Assert.assertTrue(userGroupsPage.isTextPresent("This field is required."));
        userGroupsPage.newUserGroupCreationCancelling();
    }

    @Description("The test checks that user group name must be longer than 1 char")
    @Test
    public void userGroupNameMustBeLongerThanOneChar(){
        userGroupsPage.openPage();
        userGroupsPage.createNewUserGroup("1", "");
        Assert.assertTrue(userGroupsPage.isTextPresent("Please enter at least 2 characters."));
        userGroupsPage.newUserGroupCreationCancelling();
    }

    @Description("The test checks that user group name must be unique value")
    @Test
    public void userGroupNameMustBeUniqueValue(){
        userGroupsPage.openPage();
        userGroupsPage.createNewUserGroup("MacOS", "");
        try{
            Assert.assertTrue(userGroupsPage.checkElementPresentInTable("MacOS"));
            userGroupsPage.createNewUserGroup("MacOS", "");
            Assert.assertTrue(userGroupsPage.isTextPresent("Bad Group Name: 'MacOS', Group with same Group Name already exists."),
                    "Warning message that group name isn't unique is absent");
        } catch(AssertionError er) {
            userGroupsPage.openPage();
            userGroupsPage.deleteAllGroups();
            throw new AssertionError(er.getMessage());
        }
        userGroupsPage.newUserGroupCreationCancelling();
        userGroupsPage.deleteAllGroups();
    }

    @Description("The test checks that sorting by column name is working correctly")
    @Test(dataProvider = "table rows")
    public void sortingTableByColumnNameTest(String columnName){
        userGroupsPage.createNewUserGroup("AAAA", "aaaaaaa");
        userGroupsPage.createNewUserGroup("ccccc", "cccccc");
        userGroupsPage.sortTableBy(columnName);
        try {
            Assert.assertTrue(userGroupsPage.isSortedAscendant(columnName), "sorting order is not ascendant");
            userGroupsPage.sortTableBy(columnName);
            Assert.assertTrue(userGroupsPage.isSortedDescendant(columnName), "sorting order is not descendant");
        } catch(AssertionError er) {
            userGroupsPage.deleteAllGroups();
            throw new AssertionError(er.getMessage() + "by column name: " + columnName);
        }
        userGroupsPage.deleteAllGroups();
    }

    @Description("The test checks that user group can be deleted")
    @Test
    public void userGroupDeletionTest(){
        userGroupsPage.openPage();
        userGroupsPage.createNewUserGroup("Group", "Windows");
        userGroupsPage.deleteGroup("Group");
        try {
            Assert.assertFalse(userGroupsPage.checkElementPresentInTable("Group"));
        } catch(AssertionError er) {
           userGroupsPage.deleteAllGroups();
           throw new AssertionError(er.getMessage());
        }
    }

    @Description("The test checks that group name can not contain * and % symbols")
    @Test(dataProvider = "non valid group names")
    public void userGroupNameCanNotContainSpecialSymbols(String name, String OSname){
        userGroupsPage.openPage();
        userGroupsPage.createNewUserGroup(name, OSname);
        Assert.assertTrue(userGroupsPage.isTextPresent("Bad Group Name. It contains invalid characters, please correct!"));
    }

    @Description("The test checks that all groups can be selected and deleted at one time")
    @Test
    public void userGroupDeleteAllTest(){
        userGroupsPage.openPage();
        userGroupsPage.createNewUserGroup("Groups", "linux");
        userGroupsPage.createNewUserGroup("Groups", "MacOS");
        userGroupsPage.deleteAllGroups();
        try {
            Assert.assertEquals(userGroupsPage.countElementsInTableByName("Groups"), 0);
        } catch(AssertionError er) {
            throw new AssertionError(er.getMessage());
        }
    }

    @Description("The test checks that user can deactivate and activate back selected group")
    @Test
    public void userGroupDeactivationAndActivationTest(){
        userGroupsPage.openPage();
        userGroupsPage.createNewUserGroup("Group", "Win98");
        userGroupsPage.deactivateGroup("Group");
        try {
            Assert.assertFalse(userGroupsPage.checkElementPresentInTable("Group"));
            userGroupsPage.showInactive();
            userGroupsPage.activateGroup("Group");
            userGroupsPage.showInactive();
            Assert.assertTrue(userGroupsPage.checkElementPresentInTable("Group"));
        } catch(AssertionError er) {
            userGroupsPage.deleteAllGroups();
            throw new AssertionError(er.getMessage());
        }
        userGroupsPage.deleteAllGroups();
    }


    @AfterSuite
    public void afterSuite(){
        DriverFactory.getInstance().browserClose();
    }
}
