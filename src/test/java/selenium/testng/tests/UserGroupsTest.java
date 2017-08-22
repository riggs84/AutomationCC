package selenium.testng.tests;

import io.qameta.allure.Description;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
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

    @Description("The test checks that sorting by column name is working correctly")
    @Test //TODO add dataprovider
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
