package selenium.testng;

import io.qameta.allure.Description;
import org.testng.Assert;
import org.testng.annotations.*;
import selenium.Listeners.ScreenshotListener;
import selenium.pages.ComputersPage;
import selenium.pages.LoginPage;
import selenium.Helpers.SQLhelper;

@Listeners({ScreenshotListener.class})
public class ComputersTest extends SetupClass {

    ComputersPage computersPage;
    LoginPage loginPage;

    public ComputersTest(){
        this.loginPage = new LoginPage();
        this.computersPage = new ComputersPage();
    }

    @DataProvider(name = "table rows")
    public static Object[][] tableRows(){
        return new Object [][]{
                {"Computer OS Name"},
                {"OS Info"},
                {"Computer Groups"},
                {"Jobs Count"},
                {"Last Job Run"},
                {"Last Job Run Error"},
                {"Version"},
        };
    }

    @DataProvider(name = "computer names")
    public static Object[][] names(){
        return new Object[][]{
                {"Maggy"},
        };
    }

    @BeforeClass
    public void beforeClass(){
        //SQLhelper.cleanAndRecreateDataBase();
        loginPage.loginAs("viktor.iurkov@yandex.ru", "123456");
    }

    @AfterMethod
    public void afterMethod(){
        SQLhelper.dropComputersTable();
    }

    @Description("The test checks that filter apply works correctly")
    @Test
    public void filterApplyTest(){
        SQLhelper.createComputer("MyComputer");
        SQLhelper.createComputer("NotComputer");
        computersPage.openPage();
        /*computersPage.createNewComputer("MyComputer");
        computersPage.createNewComputer("NotComputer");*/
        computersPage.applyFilter("MyCom");
        try {
            Assert.assertTrue(computersPage.checkElementPresentInTable("MyComputer"));
            Assert.assertEquals(computersPage.countAllElementsInTable(), 1);
        } catch(AssertionError er) {
            /*computersPage.openPage();
            computersPage.deleteAllComputers();*/
            throw new AssertionError(er.getMessage());
        }
        /*computersPage.openPage();
        computersPage.deleteAllComputers();*/
    }

    @Description("The test checks that user can delete computer selecting it in table")
    @Test
    public void computerDeletionTest(){
        SQLhelper.createComputer("SomePC");
        SQLhelper.createComputer("someComputer");
        computersPage.openPage();
        /*computersPage.createNewComputer("SomePC");
        computersPage.createNewComputer("someComputer");*/
        computersPage.deleteComputer("SomePC");
        try {
            Assert.assertFalse(computersPage.checkElementPresentInTable("SomePC"));
            Assert.assertEquals(computersPage.countAllElementsInTable(), 1);
        }catch(AssertionError er) {
            //computersPage.deleteAllComputers();
            throw new AssertionError(er.getMessage());
        }
        //computersPage.deleteAllComputers();
    }

    @Description("The test checks that user can select all computers and delete them on one time event")
    @Test
    public void deleteAllComputersTest(){
        SQLhelper.createComputer("Computer1");
        SQLhelper.createComputer("PC2007");
        computersPage.openPage();
        /*computersPage.createNewComputer("Computer1");
        computersPage.createNewComputer("PC2007");*/
        computersPage.deleteAllComputers();
        try {
            //Assert.assertEquals(computersPage.countAllElementsInTable(), 1);
            Assert.assertTrue(computersPage.checkElementPresentInTable("Empty"));
        } catch(AssertionError er) {
            //computersPage.deleteAllComputers();
            throw new AssertionError(er.getMessage());
        }
    }

    @Description("The test checks that computer can be deactivated and activated back then")
    @Test
    public void computerActivationAndDeactivationTest(){
        SQLhelper.createComputer("MyComputer");
        computersPage.openPage();
        //computersPage.createNewComputer("MyComputer");
        computersPage.deactivateComputer("MyComputer");
        try{
            Assert.assertFalse(computersPage.checkElementPresentInTable("MyComputer"));
            computersPage.showInactive();
            computersPage.activateComputer("MyComputer");
            computersPage.showInactive();
            Assert.assertTrue(computersPage.checkElementPresentInTable("MyComputer"));
        } catch(AssertionError er) {
            //computersPage.deleteAllComputers();
            throw new AssertionError(er.getMessage());
        }
        //computersPage.deleteAllComputers();
    }

    @Description("The test checks that sorting by column name works correctly")
    @Test(dataProvider = "table rows")
    public void sortingTableByColumnNameTest(String columnName){
        SQLhelper.createComputer("Win98");
        SQLhelper.createComputer("Basic");
        computersPage.openPage();
        /*computersPage.createNewComputer("Win98");
        computersPage.createNewComputer("Basic");*/
        computersPage.sortTableBy(columnName);
        try {
            Assert.assertTrue(computersPage.isSortedAscendant(columnName), "the table is not sorted ascendant");
            computersPage.sortTableBy(columnName);
            Assert.assertTrue(computersPage.isSortedDescendant(columnName), "the table is not sorted descendant");
        } catch(AssertionError er) {
            //computersPage.deleteAllComputers();
            throw new AssertionError(er.getMessage());
        }
        //computersPage.deleteAllComputers();
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
