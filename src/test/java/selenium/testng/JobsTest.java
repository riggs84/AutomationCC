package selenium.testng;

import io.qameta.allure.Description;
import org.testng.Assert;
import org.testng.annotations.*;
import selenium.Listeners.ScreenshotListener;
import selenium.pages.JobRelated.GeneralTab;
import selenium.pages.JobRelated.JobEditForm;
import selenium.pages.JobsPage;
import selenium.pages.LoginPage;
import selenium.pages.entities.Job;
import selenium.pages.entities.JobEntityObjects.ComputersWhereJobRuns;
import selenium.pages.entities.JobEntityObjects.UsersWhereJobRuns;
import selenium.webtestbase.RunnerMock;
import selenium.Helpers.SQLhelper;
import sun.java2d.loops.FillRect;

@Listeners({ScreenshotListener.class})
public class JobsTest extends SetupClass {

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
               // {"My Computer", "file:///", false},
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
        //SQLhelper.cleanAndRecreateDataBase();
        loginPage.loginAs("viktor.iurkov@yandex.ru", "123456");
    }

    @AfterMethod
    public void afterMethod(){
        SQLhelper.dropJobsTable();
        SQLhelper.dropUsersTable();
        SQLhelper.dropJobsRunnersTable();
        SQLhelper.dropComputersTable();
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
            //jobPage.deleteJob("testJob");
        } catch (AssertionError er){
            //jobPage.deleteJob("testJob");
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
            /*jobPage.openPage();
            jobPage.deleteJob("myJobName");*/
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
            throw new AssertionError(er.getMessage() + "created job is not present in table");
        }
        Job jobNameTest = jobPage.clickOnTheJobNameInTable("jobNameTest");
        jobNameTest.cloneJob("jobNameTestClone");
        jobPage.openPage();
        try {
            Assert.assertTrue(jobPage.isJobPresentInTable("jobNameTestClone"));
        } catch (AssertionError er) {
            /*jobPage.makeScreenShot("jobMayBeCloned");
            jobPage.deleteJob("jobNameTest");*/
            throw new AssertionError(er.getMessage());
        }
        /*jobPage.deleteJob("jobNameTest");
        jobPage.deleteJob("jobNameTestClone");*/
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
            Job job = jobPage.clickOnTheJobNameInTable("jobForInactiveTest");
            Assert.assertTrue(job.isTextPresent("Job Inactive."), "Job Inactive phrase is not found");
        } catch(AssertionError er) {
            //jobPage.deleteAllJobs();
            throw new AssertionError(er.getMessage());
        }
    }

    @Description("Jobs can be sorted by clicking column name")
    @Test(dataProvider = "jobs column names")
    public void jobsCanBeSortedAscOrDescOrderTest(String columnName){
        jobPage.openPage();
        JobEditForm jobForm = jobPage.createNewJob();
        jobForm.setJobNameAndDescr("aaaa", "")
                .saveJob();
        jobForm = jobPage.createNewJob();
        jobForm.setJobNameAndDescr("cccc", "")
                .saveJob();
        jobPage.sortBy(columnName);
        try {
            Assert.assertTrue(jobPage.isSortedAscendant(columnName));
            jobPage.sortBy(columnName);
            Assert.assertTrue(jobPage.isSortedDescendant(columnName));
        } catch (AssertionError er){
            //jobPage.deleteAllJobs();
            throw new AssertionError(er.getMessage() + "job is not sorted ASC/DESC by: " + columnName);
        }
        //jobPage.deleteAllJobs();
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
            throw new AssertionError(er.getMessage());
        }
        //jobPage.deleteJob("testName");
    }

    @Description("job can be deleted by selection")
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
            //jobPage.deleteAllJobs();
            throw new AssertionError(er.getMessage());
        }
    }

    @Description("Test checks that job->General tab job direction can be changed from sync to LtoR one-way")
    @Test
    public void jobDirectionCanBeChangedToLtoRtest(){
        runner.sendNewUserQuery("1", "vasyan", "Pekas", "2", "blabla"
                , "0", "10.5.5.3");
        jobPage.openPage();
        JobEditForm jobForm = jobPage.createNewJob();
        GeneralTab general = jobForm.setJobNameAndDescr("LtoR", "")
                .clickGeneralTabLink();
        general.setJobType("Backup Left to Right (1-way)");
        jobForm.saveJob();
        SQLhelper.setRunnerBooleanFlags(1, 1, "vasyan");
        SQLhelper.assignJobToUser("LtoR", "vasyan");
        runner.sendGetJobsQuery("0", "", runner.getFromCredsByKey("jobrunnerid"));
        Assert.assertEquals(runner.getJobOptionsValueByName("LtoR", "dir"), "ltor");
    }

    @Description("The test checks that job can be assigned to User and this job is received by runner")
    @Test
    public void jobCanBeAssignedToUserTest(){
        runner.sendNewUserQuery("1", "viktor", "PC", "2",
                "Test", "0", "");
        SQLhelper.setRunnerBooleanFlags(1,1, "viktor");
        jobPage.openPage();
        JobEditForm jobForm = jobPage.createNewJob();
        GeneralTab general = jobForm.setJobNameAndDescr("testName", "")
                .clickGeneralTabLink();
        general.setJobType("Backup Left to Right (1-way)");
        jobForm.saveJob();
        Job job = jobPage.clickOnTheJobNameInTable("testName");
        UsersWhereJobRuns usersToRunJob = job.editUsersWhereJobRuns();
        usersToRunJob.selectUserInTable("viktor").saveChanges();
        runner.sendGetJobsQuery("0", "", runner.getFromCredsByKey("jobrunnerid"));
        Assert.assertEquals(runner.getJobOptionsValueByName("testName", "dir"), "ltor");
    }

    @Description("The test checks that job can be assigned to a user and this change is saved on Web page")
    @Test
    public void jobCanBeAssignedToUserVisualCheckTest(){
        SQLhelper.createUser("viktro", "viktro yurkov", "");
        SQLhelper.setRunnerBooleanFlags(1,1, "viktro");
        jobPage.openPage();
        JobEditForm jobForm = jobPage.createNewJob();
        jobForm.setJobNameAndDescr("testJobName", "")
                .saveJob();
        Job job = jobPage.clickOnTheJobNameInTable("testJobName")
                .editUsersWhereJobRuns().selectUserInTable("viktro")
                .saveChanges();
        Assert.assertTrue(job.isUserInUsersToRunJobTable("viktro"),
                "user name is not visible in table for users to run job");
    }

    @Description("The test checks that job can be assigned to Users group with visual assertion on Web page")
    @Test
    public void jobCanBeAssignedToUserGroupVisualCheckTest(){
        SQLhelper.createUserGroup("testGroup", "Win");
        jobPage.openPage();
        JobEditForm jobForm = jobPage.createNewJob();
        jobForm.setJobNameAndDescr("TestJobName", "")
                .saveJob();
        Job job = jobPage.clickOnTheJobNameInTable("TestJobName")
                .editUserGroupsWhereJobRuns().selectUserGroupInTable("testGroup")
                .saveChanges();
        Assert.assertTrue(job.isUserGroupInUserGroupToRunJobTable("testGroup"));
    }

    @Description("The test checks that job can be assigned to Computer with visual assertion on Web Page")
    @Test
    public void jobCanBeAssignedToComputerVisualCheckTest(){
        SQLhelper.createComputer("MyPC");
        jobPage.openPage();
        JobEditForm jobForm = jobPage.createNewJob();
        jobForm.setJobNameAndDescr("TestJob", "")
                .saveJob();
        Job job = jobPage.clickOnTheJobNameInTable("TestJob")
                .editComputersWhereJobRuns().selectComputerInTable("MyPC")
                .saveChanges();
        Assert.assertTrue(job.isComputerInComputersToRunJobTable("MyPC"));
    }

    @Description("The test checks that job can be assigned to Computer Group with visual assertion on Web page")
    @Test
    public void jobCanBeAssignedToComputerGroupVisualCheckTest(){
        SQLhelper.createComputerGroup("TestGroup", 1);
        jobPage.openPage();
        JobEditForm jobForm = jobPage.createNewJob();
        jobForm.setJobNameAndDescr("TestJob", "")
                .saveJob();
        Job job = jobPage.clickOnTheJobNameInTable("TestJob")
                .editComputerGroupsWhereJobRuns().selectComputerGroupInTable("TestGroup")
                .saveChanges();
        Assert.assertTrue(job.isComputerGroupInComputerGroupToRunJobTable("TestGroup"));
    }

    @Description("Test checks that job assigned to computer is received by runner")
    @Test
    public void jobCanBeAssignedToComputerTest(){
        runner.sendNewUserQuery("1", "viktor", "PC", "2",
                "Test", "0", "");
        SQLhelper.setRunnerBooleanFlags(1,1, "viktor");
        jobPage.openPage();
        JobEditForm jobForm = jobPage.createNewJob();
        GeneralTab general = jobForm.setJobNameAndDescr("testName", "")
                .clickGeneralTabLink();
        general.setJobType("Backup Left to Right (1-way)");
        jobForm.saveJob();
        SQLhelper.assignJobToComputer("testName", "PC");
        /*Job job = jobPage.clickOnTheJobNameInTable("testName");
        ComputersWhereJobRuns usersToRunJob = job.editComputersWhereJobRuns();
        usersToRunJob.selectComputerInTable("PC").saveChanges();*/
        runner.sendGetJobsQuery("0", "", runner.getFromCredsByKey("jobrunnerid"));
        Assert.assertEquals(runner.getJobOptionsValueByName("testName", "dir"), "ltor");
    }

    @Description("The test check that assigned to user group job is received by runner")
    @Test
    public void jobCanBeAssignedToUserGroupTest(){
        runner.sendNewUserQuery("1", "viktor", "PC", "2",
                "Test", "0", "");
        SQLhelper.setRunnerBooleanFlags(1,1, "viktor");
        jobPage.openPage();
        JobEditForm jobForm = jobPage.createNewJob();
        GeneralTab general = jobForm.setJobNameAndDescr("testName", "")
                .clickGeneralTabLink();
        general.setJobType("Backup Left to Right (1-way)");
        jobForm.saveJob();
        SQLhelper.createUserGroup("TestGroup", "TestName");
        SQLhelper.addUserToUsersGroup("viktor", "TestGroup");
        SQLhelper.assignJobToUserGroup("testName", "TestGroup");
        /*Job job = jobPage.clickOnTheJobNameInTable("testName");
        ComputersWhereJobRuns usersToRunJob = job.editComputersWhereJobRuns();
        usersToRunJob.selectComputerInTable("PC").saveChanges();*/
        runner.sendGetJobsQuery("0", "", runner.getFromCredsByKey("jobrunnerid"));
        Assert.assertEquals(runner.getJobOptionsValueByName("testName", "dir"), "ltor");
    }

    @Description("The test checks that job can be assigned to computer group and job is received by runner")
    @Test
    public void jobCanBeAssignedToComputerGroupTest(){
        runner.sendNewUserQuery("1", "viktor", "PC", "2",
                "Test", "0", "");
        SQLhelper.setRunnerBooleanFlags(1,1, "viktor");
        jobPage.openPage();
        JobEditForm jobForm = jobPage.createNewJob();
        GeneralTab general = jobForm.setJobNameAndDescr("testName", "")
                .clickGeneralTabLink();
        general.setJobType("Backup Left to Right (1-way)");
        jobForm.saveJob();
        SQLhelper.createComputerGroup("TestGroup", 1);
        SQLhelper.assignJobToComputerGroup("testName", "TestGroup");
        SQLhelper.addComputerToComputerGroup("TestGroup", "PC");
        /*Job job = jobPage.clickOnTheJobNameInTable("testName");
        ComputersWhereJobRuns usersToRunJob = job.editComputersWhereJobRuns();
        usersToRunJob.selectComputerInTable("PC").saveChanges();*/
        runner.sendGetJobsQuery("0", "", runner.getFromCredsByKey("jobrunnerid"));
        Assert.assertEquals(runner.getJobOptionsValueByName("testName", "dir"), "ltor");
    }

    @Description("The test checks that job direction can be changed from 2 way to left to right and confirmed visually")
    @Test
    public void jobDirectionCanBeChangedLtoRVisualCheckTest(){
        jobPage.openPage();
        JobEditForm jobForm = jobPage.createNewJob();
        GeneralTab  general = jobForm.setJobNameAndDescr("TestName", "")
                .clickGeneralTabLink();
        Assert.assertTrue(general.isTextPresent("Synchronize 2-Way"));
        general.setJobType("Backup Left to Right (1-way)");
        Assert.assertTrue(general.isTextPresent("Backup Left to Right (1-way)"));
    }

    @Description("The test checks that job direction can be chamged from 2 way to right to left and confirmed visually")
    @Test
    public void jobDirectionCanBeChanhedRtoLVisualCheckTest(){
        jobPage.openPage();
        JobEditForm jobForm = jobPage.createNewJob();
        GeneralTab  general = jobForm.setJobNameAndDescr("TestName", "")
                .clickGeneralTabLink();
        Assert.assertTrue(general.isTextPresent("Synchronize 2-Way"));
        general.setJobType("Backup Right to Left (1-way)");
        Assert.assertTrue(general.isTextPresent("Backup Right to Left (1-way)"));
    }

    @Description("The test checks that job direction can be changed from 2 way to L to R and received by runner")
    @Test
    public void jobDirectionCanBeChangedLtoRTest(){
        runner.sendNewUserQuery("1", "viktor", "PC", "2",
                "Test", "0", "");
        SQLhelper.setRunnerBooleanFlags(1,1, "viktor");
        jobPage.openPage();
        JobEditForm jobForm = jobPage.createNewJob();
        GeneralTab general = jobForm.setJobNameAndDescr("testName", "")
                .clickGeneralTabLink();
        jobForm.saveJob();
        SQLhelper.assignJobToUser("testName", "viktor");
        runner.sendGetJobsQuery("0", "", runner.getFromCredsByKey("jobrunnerid"));
        Assert.assertEquals(runner.getJobOptionsValueByName("testName", "dir"), null);
        jobForm = jobPage.clickOnTheJobNameInTable("testName")
                .clickEditJobButton();
        jobForm.clickGeneralTabLink()
                .setJobType("Backup Left to Right (1-way)");
        jobForm.saveJob();
        runner.sendGetJobsQuery("0", "", runner.getFromCredsByKey("jobrunnerid"));
        Assert.assertEquals(runner.getJobOptionsValueByName("testName", "dir"), "ltor");
    }

    @Description("The test checks that job direction can be changed from 2 way to R to L and received by runner")
    @Test
    public void jobDirectionCanBeChangedToRtoLTest(){
        runner.sendNewUserQuery("1", "viktor", "PC", "2",
                "Test", "0", "");
        SQLhelper.setRunnerBooleanFlags(1,1, "viktor");
        jobPage.openPage();
        JobEditForm jobForm = jobPage.createNewJob();
        GeneralTab general = jobForm.setJobNameAndDescr("testName", "")
                .clickGeneralTabLink();
        jobForm.saveJob();
        SQLhelper.assignJobToUser("testName", "viktor");
        runner.sendGetJobsQuery("0", "", runner.getFromCredsByKey("jobrunnerid"));
        Assert.assertEquals(runner.getJobOptionsValueByName("testName", "dir"), null);
        jobForm = jobPage.clickOnTheJobNameInTable("testName")
                .clickEditJobButton();
        jobForm.clickGeneralTabLink()
                .setJobType("Backup Right to Left (1-way)");
        jobForm.saveJob();
        runner.sendGetJobsQuery("0", "", runner.getFromCredsByKey("jobrunnerid"));
        Assert.assertEquals(runner.getJobOptionsValueByName("testName", "dir"), "rtol");
    }

    @Description("The test checks that propagate deletions checkbox can not be disabled for 2 way job")
    @Test
    public void propagatedDelCanNotBeDisabledFor2wayJobTest(){
        jobPage.openPage();
        JobEditForm jobForm = jobPage.createNewJob();
        Assert.assertTrue(
                jobForm.clickGeneralTabLink()
                .getPropagateDeletionsCheckBox()
                .selectCheckBox()
                .isSelected());
    }

    /*@AfterClass
    public void afterClass(){
        jobPage.logOut();
    }

    @AfterSuite
    public void afterSuite(){
        DriverFactory.getInstance().browserClose();
    }*/
}
