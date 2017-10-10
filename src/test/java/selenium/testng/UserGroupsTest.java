package selenium.testng;

import io.qameta.allure.Description;
import org.testng.Assert;
import org.testng.annotations.*;
import selenium.Listeners.ScreenshotListener;
import selenium.pages.LoginPage;
import selenium.pages.UserGroupsPage;
import selenium.Helpers.SQLhelper;

@Listeners({ScreenshotListener.class})
public class UserGroupsTest extends SetupClass {

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
                {"Users count"},
                {"Jobs count"},
                {"Creation date"},
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

    @DataProvider(name = "valid group names")
    public static Object[][] validNames(){
        return new Object[][]{
                {"myGroup", ""},
        };
    }

    @BeforeClass
    public void beforeClass(){
        //SQLhelper.cleanAndRecreateDataBase();
        loginPage.loginAs("viktor.iurkov@yandex.ru", "123456");
    }

    @AfterMethod
    public void afterMethod(){
        SQLhelper.dropUserGroupsTable();
    }

    @Description("The test is checking that new group can be created")
    @Test(dataProvider = "valid group names")
    public void newUserGroupCreationTest(String userGroupName, String userGroupOSname){
        userGroupsPage.openPage();
        userGroupsPage.createNewUserGroup(userGroupName, userGroupOSname);
        try {
            Assert.assertTrue(userGroupsPage.checkElementPresentInTable(userGroupName), "group creation failed");
        } catch(AssertionError er) {
            //userGroupsPage.deleteGroup(userGroupName);
            throw new AssertionError(er.getMessage() + " on data: " + userGroupName + " " + userGroupOSname);
        }
        //userGroupsPage.deleteGroup(userGroupName);
    }

    @Description("The test is checking that user can apply filter to groups table")
    @Test
    public void applyFilterTest(){
        userGroupsPage.openPage();
        userGroupsPage.createNewUserGroup("Group", "Windows");
        userGroupsPage.createNewUserGroup("ABC", "Windows1");
        userGroupsPage.applyFilter("Group");
        try {
            Assert.assertTrue(userGroupsPage.checkElementPresentInTable("Group"));
            Assert.assertEquals(userGroupsPage.countAllElementsInTable(), 1);
        } catch(AssertionError er) {
            /*userGroupsPage.openPage();
            userGroupsPage.deleteAllGroups();*/
            throw new AssertionError(er.getMessage());
        }
        /*userGroupsPage.openPage();
        userGroupsPage.deleteAllGroups();*/
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
            /*userGroupsPage.openPage();
            userGroupsPage.deleteAllGroups();*/
            throw new AssertionError(er.getMessage());
        }
        /*userGroupsPage.newUserGroupCreationCancelling();
        userGroupsPage.deleteAllGroups();*/
    }

    @Description("The test checks that user group os name must be unique")
    @Test
    public void userGroupOSnameMustBeUnique(){
        userGroupsPage.openPage();
        userGroupsPage.createNewUserGroup("group1", "Windows");
        try {
            Assert.assertTrue(userGroupsPage.checkElementPresentInTable("group1"), "Group creation failed. Group not found");
            userGroupsPage.createNewUserGroup("group2", "Windows");
            Assert.assertTrue(userGroupsPage
                    .isTextPresent("Bad Group OS Name: 'Windows', Group with same Group OS Name already exists."),
                    "Warning message that such already exist is absent");
        } catch(AssertionError er) {
            /*userGroupsPage.openPage();
            userGroupsPage.deleteAllGroups();*/
            throw new AssertionError(er.getMessage());
        }
        /*userGroupsPage.newUserGroupCreationCancelling();
        userGroupsPage.deleteAllGroups();*/
    }

    @Description("The test checks that sorting by column name is working correctly")
    @Test(dataProvider = "table rows")
    public void sortingTableByColumnNameTest(String columnName){
        userGroupsPage.openPage();
        userGroupsPage.createNewUserGroup("AAAA", "aaaaaaa");
        userGroupsPage.createNewUserGroup("ccccc", "cccccc");
        if(!columnName.equals("Group OS Name")){
            userGroupsPage.sortTableBy(columnName);
        }
        try {
            Assert.assertTrue(userGroupsPage.isSortedAscendant(columnName), "sorting order is not ascendant");
            userGroupsPage.sortTableBy(columnName);
            Assert.assertTrue(userGroupsPage.isSortedDescendant(columnName), "sorting order is not descendant");
        } catch(AssertionError er) {
            //userGroupsPage.deleteAllGroups();
            throw new AssertionError(er.getMessage() + "by column name: " + columnName);
        }
        //userGroupsPage.deleteAllGroups();
    }

    @Description("The test checks that user group can be deleted")
    @Test
    public void userGroupDeletionTest(){
        userGroupsPage.openPage();
        userGroupsPage.createNewUserGroup("Group2", "Windows");
        userGroupsPage.deleteGroup("Group2");
        try {
            Assert.assertFalse(userGroupsPage.checkElementPresentInTable("Group2"));
        } catch(AssertionError er) {
           //userGroupsPage.deleteAllGroups();
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
        userGroupsPage.createNewUserGroup("Groups1", "MacOS");
        userGroupsPage.deleteAllGroups();
        try {
            Assert.assertEquals(userGroupsPage.countAllElementsInTable(), 0);
        } catch(AssertionError er) {
            /*userGroupsPage.openPage();
            userGroupsPage.deleteAllGroups();*/
            throw new AssertionError(er.getMessage());
        }
    }

    @Description("The test checks that user can deactivate and activate back selected group")
    @Test
    public void userGroupDeactivationAndActivationTest(){
        userGroupsPage.openPage();
        userGroupsPage.createNewUserGroup("Group1", "Win98");
        userGroupsPage.deactivateGroup("Group1");
        try {
            Assert.assertFalse(userGroupsPage.checkElementPresentInTable("Group1"));
            userGroupsPage.showInactive();
            userGroupsPage.activateGroup("Group1");
            userGroupsPage.showInactive();
            Assert.assertTrue(userGroupsPage.checkElementPresentInTable("Group1"));
        } catch(AssertionError er) {
            //userGroupsPage.deleteAllGroups();
            throw new AssertionError(er.getMessage());
        }
        //userGroupsPage.deleteAllGroups();
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
