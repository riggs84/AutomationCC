package selenium.testng.tests;

import io.qameta.allure.Description;
import org.testng.Assert;
import org.testng.annotations.*;
import selenium.pages.JobRelated.JobEditForm;
import selenium.pages.JobsPage;
import selenium.pages.LoginPage;
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
