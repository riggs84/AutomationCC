package selenium.testng;

import io.qameta.allure.Description;
import org.testng.Assert;
import org.testng.annotations.*;
import selenium.Listeners.ScreenshotListener;
import selenium.pages.ComputersPage;
import selenium.pages.LoginPage;
import selenium.webtestsbase.DriverFactory;
import selenium.webtestsbase.SQLhelper;

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

    @Description("The test checks that user can create computer")
    @Test(dataProvider = "computer names")
    public void crtNewComputerTest(String computerOSname){
        computersPage.openPage();
        computersPage.createNewComputer(computerOSname);
        try {
            Assert.assertTrue(computersPage.checkElementPresentInTable(computerOSname), "Computer creation failed");
        } catch(AssertionError er) {
            //computersPage.deleteComputer(computerOSname);
            throw new AssertionError(er.getMessage());
        }
        //computersPage.deleteComputer(computerOSname);
    }

    @Description("The test checks that filter apply works correctly")
    @Test
    public void filterApplyTest(){
        computersPage.openPage();
        computersPage.createNewComputer("MyComputer");
        computersPage.createNewComputer("NotComputer");
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
        computersPage.openPage();
        computersPage.createNewComputer("SomePC");
        computersPage.createNewComputer("someComputer");
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
        computersPage.openPage();
        computersPage.createNewComputer("Computer1");
        computersPage.createNewComputer("PC2007");
        computersPage.deleteAllComputers();
        try {
            //Assert.assertEquals(computersPage.countAllElementsInTable(), 1);
            Assert.assertTrue(computersPage.checkElementPresentInTable("Empty"));
        } catch(AssertionError er) {
            //computersPage.deleteAllComputers();
            throw new AssertionError(er.getMessage());
        }
    }

    @Description("The test checks that computer name must be unique value")
    @Test
    public void computerNameMustBeUniqueValue(){
        computersPage.openPage();
        computersPage.createNewComputer("MAGGY");
        try {
            computersPage.createNewComputer("MAGGY");
            Assert.assertTrue(computersPage.isTextPresent("Bad Computer OS Name: 'MAGGY', Computer with same Computer OS Name already exists."),
                    "No warning message that name isn't unique value");
        } catch(AssertionError er) {
            /*computersPage.openPage();
            computersPage.deleteAllComputers();*/
            throw new AssertionError(er.getMessage());
        }
        /*computersPage.newComputerCreationCancelling();
        computersPage.deleteAllComputers();*/
    }

    @Description("The test checks that computer can be deactivated and activated back then")
    @Test
    public void computerActivationAndDeactivationTest(){
        computersPage.openPage();
        computersPage.createNewComputer("MyComputer");
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
        computersPage.openPage();
        computersPage.createNewComputer("Win98");
        computersPage.createNewComputer("Basic");
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

    @Description("The test checks that computer OS name can not be empty. Form validation throws error")
    @Test
    public void computerOSnameCanNotBeEmpty(){
        computersPage.openPage();
        computersPage.createNewComputer("");
        try{
            Assert.assertTrue(computersPage.isTextPresent("This field is required."));
        } catch(AssertionError er) {
            //computersPage.openPage();
            throw new AssertionError(er.getMessage());
        }
        //computersPage.newComputerCreationCancelling();
    }

    @Description("The test checks that computer OS name can not be longer than 40 chars")
    @Test
    public void computerOSnameCanBeShorterThan40CharsTest(){
        computersPage.openPage();
        computersPage.createNewComputer("11111111111111111111111111111111111111111");
        try {
            Assert.assertTrue(computersPage.isTextPresent("Please enter no more than 40 characters."), "boundary value test failed");
        } catch(AssertionError er) {
            //computersPage.newComputerCreationCancelling();
            throw new AssertionError(er.getMessage());
        }
        //computersPage.newComputerCreationCancelling();
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
