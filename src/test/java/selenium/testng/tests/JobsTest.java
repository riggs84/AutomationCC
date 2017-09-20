package selenium.testng.tests;

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
            Assert.assertTrue(jobForm.isTextPresent(" Bad Job Name. It contains invalid characters, please correct!"));
        } catch (AssertionError er){
            jobForm.makeScreenShot("jobNameCanNotContainCharacters");
            throw new AssertionError(er.getMessage() + " on input data: " + name);
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
