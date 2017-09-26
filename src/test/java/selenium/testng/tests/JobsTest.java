package selenium.testng.tests;

import io.qameta.allure.Description;
import org.testng.Assert;
import org.testng.annotations.*;
import selenium.pages.JobRelated.JobEditForm;
import selenium.pages.JobsPage;
import selenium.pages.LoginPage;
import selenium.pages.entities.Job;
import selenium.webtestsbase.DriverFactory;
import selenium.webtestsbase.RunnerMock;

public class JobsTest {

    LoginPage loginPage;
    JobsPage jobPage;
    RunnerMock runner;

    public JobsTest(){
        this.loginPage = new LoginPage();
        this.jobPage = new JobsPage();
        this.runner = new RunnerMock();
    }

    @DataProvider(name = "invalid Job Names")
    public static Object[][] invalidJobNames() {
        return new Object[][]{
                {"123\""},
                {"123*"},
                {"123\'"},
                {"123%"},
        };
    }

    @DataProvider(name = "SideFSselection")
    public static Object[][] sideFSselection() {
        return new Object[][]{
                {"My Computer", "file:///", false},
                {"GoodSync Connect", "gstp://", true},
                {"Windows Shares", "smb://", true},
                {"Media Devices MTP", "mtp://", false},
                {"FTP", "ftp://", true},
                {"SFTP", "sftp://", true},
                {"WebDAV", "http://", true},
                {"Amazon S3", "s3://", true},
                {"Windows Azure", "azure://", true},
                {"One File", "onefile://", true},
                {"WinMobile", "wm://", false},
                {"Backblaze B2", "backblaze://", true},
        };
    }

    @DataProvider(name = "jobs column names")
    public static Object[][] jobsColumnNames() {
        return new Object[][]{
                {"Job Name"},
                {"Users"},
                {"User Groups"},
                {"Computers"},
                {"Computer Groups"},
                {"Description"}
        };
    }

    @BeforeClass
    public void beforeClass(){
        loginPage.loginAs("viktor.iurkov@yandex.ru", "123456");
        runner.sendNewUserQuery("163", "vasyan", "Pekas", "Win", "blabla"
                , "0", "10.5.5.3");
    }

    @Test
    public void newCreatedJobIsPresentInTableTest(){
        jobPage.openPage();
        JobEditForm jobForm = jobPage.createNewJob();
        jobForm.setJobNameAndDescr("testJob", "")
                .selectFSonLeftSideByName("My Computer")
                .setLeftSideConnectoidLocalFS("C://folder", false, false, false);
        jobForm.clickRightFolderLink()
                .selectFSonRightSideByName("My Computer")
                .setRightSideConnectoidLocalFS("D://folder1", false, false, false);
        jobForm.saveJob();
        try {
            Assert.assertTrue(jobPage.isJobPresentInTable("testJob"));
            jobPage.deleteJob("testJob");
        } catch (AssertionError er){
            jobPage.makeScreenShot("newCreatedJobIsPresentInTable");
            jobPage.deleteJob("testJob");
            throw new AssertionError(er.getMessage());
        }
    }

    @Test
    public void createdNewJobMustHaveNameTest(){
        jobPage.openPage();
        JobEditForm jobForm = jobPage.createNewJob();
        jobForm.setJobNameAndDescr("", "")
                .saveJob();
        try {
            Assert.assertTrue(jobForm.isTextPresent("This field is required."));
        } catch (AssertionError er){
            jobPage.makeScreenShot("createdNewJobMustHaveName");
            throw new AssertionError(er.getMessage());
        }
    }

    @Test
    public void newCreatedJobNameMustBeLonger4CharsTest(){
        jobPage.openPage();
        JobEditForm jobForm = jobPage.createNewJob();
        jobForm.setJobNameAndDescr("444", "")
                .saveJob();
        try {
            Assert.assertTrue(jobForm.isTextPresent("Please enter at least 4 characters."));
        } catch (AssertionError er){
            jobForm.makeScreenShot("newCreatedJobNameMustBeLonger4Chars");
            throw new AssertionError(er.getMessage());
        }
    }

    @Test(dataProvider = "invalid Job Names")
    public void jobNameCanNotContainCharactersTest(String name){
        jobPage.openPage();
        JobEditForm jobForm = jobPage.createNewJob();
        jobForm.setJobNameAndDescr(name, "")
                .saveJob();
        try {
            Assert.assertTrue(jobPage.isTextPresent(" Bad Job Name. It contains invalid characters, please correct!"));
        } catch (AssertionError er){
            jobForm.makeScreenShot("jobNameCanNotContainCharacters");
            throw new AssertionError(er.getMessage() + " on input data: " + name);
        }
    }

    @Description("Description field can not contains spec chars")
    @Test//TODO add data provider for description with invalid chars
    public void newJobCrtDescriptionFieldCanNotContainsSpecialCharsTest(String descr){
        jobPage.openPage();
        JobEditForm jobForm = jobPage.createNewJob();
        jobForm.setJobNameAndDescr("123456", descr)
                .saveJob();
        try {
            Assert.assertTrue(jobPage.isTextPresent(" Bad Description. It contains invalid characters, please correct!"));
        } catch (AssertionError er){
            jobPage.makeScreenShot("DescriptionFieldCanNotContainSpecChars");
            throw new AssertionError(er.getMessage() + " on data: " + descr);
        }
    }

    @Description("Job name must be unique")
    @Test
    public void jobNameMustBeUniqueValueTest(){
        jobPage.openPage();
        JobEditForm jobForm = jobPage.createNewJob();
        jobForm.setJobNameAndDescr("myJobName", "test job")
                .saveJob();
        jobForm = jobPage.createNewJob();
        jobForm.setJobNameAndDescr("myJobName", "")
                .saveJob();
        try {
            Assert.assertTrue(jobPage.isTextPresent(" Bad Job Name: 'myJobName', Job with same Job Name already exists."));
            jobForm.clickFormCancelButton()
                    .deleteJob("myJobName");
        } catch (AssertionError er) {
            jobPage.makeScreenShot("jobNameMustBeUniqueValue");
            jobPage.openPage();
            jobPage.deleteJob("myJobName");
            throw new AssertionError(er.getMessage());
        }
    }

    @Description("Checks that left side FS can be changed and proper fields are present")
    @Test(dataProvider = "SideFSselection")
    public void leftSideFSchangeTest(String fsName, String fsProtocol, boolean containsUserAndPass){
        jobPage.openPage();
        JobEditForm jobForm = jobPage.createNewJob();
        String str = jobForm.clickLeftFolderLink()
                .selectFSonLeftSideByName(fsName)
                .getValueForPathField();
        try {
            Assert.assertEquals(str, fsProtocol);
            Assert.assertTrue(jobForm.isTextPresent(fsName));
            if (containsUserAndPass){
                Assert.assertTrue(jobForm.isTextPresent("User Name"));
                Assert.assertTrue(jobForm.isTextPresent("Password"));
            }
        } catch (AssertionError er){
            jobPage.makeScreenShot("leftSideFSchange");
            throw new AssertionError(er.getMessage() + "on data: " + fsName);
        }
    }

    @Description("Test checks that job can be cloned")
    @Test
    public void jobMayBeClonedTest(){
        jobPage.openPage();
        JobEditForm jobForm = jobPage.createNewJob();
        jobForm.setJobNameAndDescr("jobNameTest", "")
                .saveJob();
        try {
            Assert.assertTrue(jobPage.isJobPresentInTable("jobNameTest"));
        } catch (AssertionError er){
            jobPage.makeScreenShot("jobMayBeClonedTest");
            throw new AssertionError(er.getMessage() + "created job is not present in table");
        }
        Job jobNameTest = jobPage.clickOnTheJobNameInTable("jobNameTest");
        jobNameTest.cloneJob("jobNameTestClone");
        jobPage.openPage();
        try {
            Assert.assertTrue(jobPage.isJobPresentInTable("jobNameTestClone"));
        } catch (AssertionError er) {
            jobPage.makeScreenShot("jobMayBeCloned");
            jobPage.deleteJob("jobNameTest");
            throw new AssertionError(er.getMessage());
        }
        jobPage.deleteJob("jobNameTest");
        jobPage.deleteJob("jobNameTestClone");
    }

    @Description("Deactivated job can not be open for editing or other actions")
    @Test
    public void deactivatedJobCanNotBeEditTest(){
        jobPage.openPage();
        JobEditForm jobForm = jobPage.createNewJob();
        jobForm.setJobNameAndDescr("jobForInactiveTest", "")
                .saveJob();
        jobPage.deactivateJob("jobForInactiveTest");
        try {
            Assert.assertFalse(jobPage.isJobPresentInTable("jobForInactiveTest"));
            jobPage.showInactive();
            jobPage.clickOnTheJobNameInTable("jobForInactiveTest");
            Assert.assertTrue(jobPage.isTextPresent("   Job Inactive. "));
        } catch(AssertionError er) {
            jobPage.deleteAllJobs();
            throw new AssertionError(er.getMessage());
        }
    }

    @Description("Jobs can be sorted by clicking column name")
    @Test(dataProvider = "jobs column names")
    public void jobsCanBeSortedAscOrDescOrderTest(String columnName){
        jobPage.openPage(); //TODO not working!!!! Figure solution out!!!!!!!!!!!!!!!!!!!!!!!
        JobEditForm jobForm = jobPage.createNewJob();
        jobForm.setJobNameAndDescr("aaaa", "")
                .saveJob();
        JobEditForm jobForm1 = jobPage.createNewJob();
        jobForm1.setJobNameAndDescr("cccc", "")
                .saveJob();
        jobPage.sortBy(columnName);
        try {
            Assert.assertTrue(jobPage.isSortedAscendant(columnName));
            jobPage.sortBy(columnName);
            Assert.assertTrue(jobPage.isSortedDescendant(columnName));
            jobPage.deleteAllJobs();
        } catch (AssertionError er){
            jobPage.deleteAllJobs();
            throw new AssertionError(er.getMessage() + "job is not sorted ASC/DESC by: " + columnName);
        }
        jobPage.deleteAllJobs();
    }

    @Description("Test That job can be deactivated and activated back")
    @Test
    public void jobCanBeDeactivatedAndActivatedTest(){
        jobPage.openPage();
        JobEditForm jobForm = jobPage.createNewJob();
        jobForm.setJobNameAndDescr("testName", "")
                .saveJob();
        jobPage.deactivateJob("testName");
        try {
            Assert.assertFalse(jobPage.isJobPresentInTable("testName"), "deactivation failed");
            jobPage.showInactive();
            Assert.assertTrue(jobPage.isJobPresentInTable("testName"), "job is not visible after show inactive");
            jobPage.activateJob("testName");
            jobPage.showInactive();
            Assert.assertTrue(jobPage.isJobPresentInTable("testName"), "activation failed");
        } catch(AssertionError er) {
            jobPage.makeScreenShot("jobCanBeActivatedAndDeactivated");
            throw new AssertionError(er.getMessage());
            //TODO clean up
        }
        jobPage.deleteJob("testName");
    }

    @Description("job can be deleted by selectiong")
    @Test
    public void jobCanBeDeletedTest(){
        jobPage.openPage();
        JobEditForm jobForm = jobPage.createNewJob();
        jobForm.setJobNameAndDescr("jobForTest", "")
                .saveJob();
        try {
            Assert.assertTrue(jobPage.isJobPresentInTable("jobForTest"), "job is not created correctly");
            jobPage.deleteJob("jobForTest");
            Assert.assertFalse(jobPage.isJobPresentInTable("jobForTest"), "job deletion failed");
        } catch(AssertionError er) {
            jobPage.deleteAllJobs();
            throw new AssertionError(er.getMessage());
        }
    }

    @Test
    public void crtNewJobTest(){
        jobPage.openPage();
        runner.sendGetJobsQuery("0", "10.5.5.3", runner.getFromCredsByKey("jobrunnerid"));
        //System.out.println("resp body: " + runner.getResponseMessage() + " body: " + runner.getResponseBody());
        JobEditForm jobForm = jobPage.createNewJob();
        jobForm.setJobNameAndDescr("blabla","")
                .clickLeftFolderLink()
                .selectFSonLeftSideByName("My Computer")
                .setLeftSideConnectoidLocalFS("C://folder", true, false, false);
        jobForm.clickRightFolderLink()
                .selectFSonRightSideByName("My Computer")
                .setRightSideConnectoidLocalFS("D://vdfjkvni", true, true, true);
        jobForm.clickAutoTabLink()
                .setPeriodicallyCheckBox(true);
        jobForm.saveJob();
        jobPage.clickOnTheJobNameInTable("blabla");

    }

    @AfterClass
    public void afterClass(){
        jobPage.logOut();
    }

    @AfterSuite
    public void afterSuite(){
        DriverFactory.getInstance().browserClose();
    }
}
