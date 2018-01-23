package selenium.testng;

import io.qameta.allure.Description;
import org.testng.Assert;
import org.testng.annotations.*;
import selenium.Listeners.ScreenshotListener;
import selenium.pages.JobRelated.AdvancedTab;
import selenium.pages.JobRelated.AutoTab;
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
        SQLhelper.cleanAndRecreateDataBase();
    }

    @Test
    public void newCreatedJobIsPresentInTableTest(){
        jobPage.openPage();
        JobEditForm jobForm = jobPage.createNewJob();
        jobForm.setJobNameAndDescr("testJob", "")
                .selectFSonLeftSideByName("My Computer");
        jobForm.clickRightFolderLink()
                .selectFSonRightSideByName("My Computer");
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
            Assert.assertTrue(jobPage.isTextPresent("Invalid characters! Symbols *, %, \", ', \\ and spaces at start and end position are not allowed."));
        } catch (AssertionError er){
            throw new AssertionError(er.getMessage() + " on input data: " + name);
        }
    }

    /*@Description("Description field can not contains spec chars")
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
    }*/

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
            /*jobForm.clickFormCancelButton()
                    .deleteJob("myJobName");*/
        } catch (AssertionError er) {
            throw new AssertionError(er.getMessage());
        }
    }

    // TODO this test must use File system pges for acces to theirs elements data
    /*@Description("Checks that left side FS can be changed and proper fields are present")
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
    }*/

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
            throw new AssertionError(er.getMessage());
        }
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
        runner.sendNewUserQuery(SQLhelper.getCompanyId(), "vasyan", "Pekas", "2", "blabla"
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
        runner.sendNewUserQuery(SQLhelper.getCompanyId(), "viktor", "PC", "2",
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
        runner.sendNewUserQuery(SQLhelper.getCompanyId(), "viktor", "PC", "2",
                "Test", "0", "");
        SQLhelper.setRunnerBooleanFlags(1,1, "viktor");
        jobPage.openPage();
        JobEditForm jobForm = jobPage.createNewJob();
        GeneralTab general = jobForm.setJobNameAndDescr("testName", "")
                .clickGeneralTabLink();
        general.setJobType("Backup Left to Right (1-way)");
        jobForm.saveJob();
        SQLhelper.assignJobToComputer("testName", "PC");
        runner.sendGetJobsQuery("0", "", runner.getFromCredsByKey("jobrunnerid"));
        Assert.assertEquals(runner.getJobOptionsValueByName("testName", "dir"), "ltor");
    }

    @Description("The test check that assigned to user group job is received by runner")
    @Test
    public void jobCanBeAssignedToUserGroupTest(){
        runner.sendNewUserQuery(SQLhelper.getCompanyId(), "viktor", "PC", "2",
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
        runner.sendGetJobsQuery("0", "", runner.getFromCredsByKey("jobrunnerid"));
        Assert.assertEquals(runner.getJobOptionsValueByName("testName", "dir"), "ltor");
    }

    @Description("The test checks that job can be assigned to computer group and job is received by runner")
    @Test
    public void jobCanBeAssignedToComputerGroupTest(){
        runner.sendNewUserQuery(SQLhelper.getCompanyId(), "viktor", "PC", "2",
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
    public void jobDirectionCanBeChangedRtoLVisualCheckTest(){
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
        runner.sendNewUserQuery(SQLhelper.getCompanyId(), "viktor", "PC", "2",
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
        runner.sendNewUserQuery(SQLhelper.getCompanyId(), "viktor", "PC", "2",
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

    @Description("The test checks that propagate deletions checkbox can not be disabled for 2 way job visual check")
    @Test
    public void propagatedDelCanNotBeDisabledFor2wayJobVisuallyCheckTest(){
        jobPage.openPage();
        JobEditForm jobForm = jobPage.createNewJob();
        Assert.assertTrue(
                jobForm.clickGeneralTabLink()
                        .setPropagateDelCheckBox(false)
                .getPropagateDeletionsCheckBox()
                .selectCheckBox()
                .isSelected());
    }

    @Description("The test checks that propagate deletions checkbox can be off for one way job visual check")
    @Test
    public void propagatedDelCanBeDisabledFor1wayJobVisuallyCheckTest(){
        jobPage.openPage();
        JobEditForm jobForm = jobPage.createNewJob();
        Assert.assertFalse(jobForm.clickGeneralTabLink()
                .setJobType("Backup Right to Left (1-way)")
                .getPropagateDeletionsCheckBox()
                .setCheckbox(false)
                .isSelected());
    }

    @Description("The test checks that propagated del can not be off for 2way job with runnner mock check")
    @Test
    public void propagateDelCanNotBeDisabledFor2wayJobTest(){
        runner.sendNewUserQuery(SQLhelper.getCompanyId(), "viktor", "PC", "2",
                "Test", "0", "");
        SQLhelper.setRunnerBooleanFlags(1,1, "viktor");
        jobPage.openPage();
        JobEditForm jobForm = jobPage.createNewJob();
        jobForm.setJobNameAndDescr("testName", "")
                .clickGeneralTabLink()
                .setPropagateDeletionsCheckBoxToValue(false);
        jobForm.saveJob();
        SQLhelper.assignJobToUser("testName", "viktor");
        runner.sendGetJobsQuery("0", "", runner.getFromCredsByKey("jobrunnerid"));
        Assert.assertEquals(runner.getJobOptionsValueByName("testName", "deletions"), null);
    }

    @Description("The test checks that propagation deletions can be disabled for 1way job with runner mock check")
    @Test
    public void propagatedDelCanBeDisabledFor1wayJobTest(){
        runner.sendNewUserQuery(SQLhelper.getCompanyId(), "viktor", "PC", "2",
                "Test", "0", "");
        SQLhelper.setRunnerBooleanFlags(1,1, "viktor");
        jobPage.openPage();
        JobEditForm jobForm = jobPage.createNewJob();
        GeneralTab general = jobForm.setJobNameAndDescr("testName", "")
                .clickGeneralTabLink()
                .setJobType("Backup Right to Left (1-way)")
                .setPropagateDelCheckBox(false);
        jobForm.saveJob();
        SQLhelper.assignJobToUser("testName", "viktor");
        runner.sendGetJobsQuery("0", "", runner.getFromCredsByKey("jobrunnerid"));
        Assert.assertEquals(runner.getJobOptionsValueByName("testName", "deletions"), "no");
    }

    @Description("The test checks that create if not found can be selected and checked visually")
    @Test
    public void createIfNotFoundCanBeSelectedVisualCheckTest(){
        jobPage.openPage();
        JobEditForm jobForm = jobPage.createNewJob();
        Assert.assertTrue(jobForm.setJobNameAndDescr("NameTest", "")
                .clickGeneralTabLink()
                .getCrtLeftOrRightFolderIfNotFoundCheckBox()
                .setCheckbox(true)
                .isSelected());
    }

    @Description("The test checks that create if not found can be selected and confirmed by runner mock Object")
    @Test
    public void createIfNotFoundCanBeSelectedTest(){
        runner.sendNewUserQuery(SQLhelper.getCompanyId(), "viktor", "PC", "2",
                "Test", "0", "");
        SQLhelper.setRunnerBooleanFlags(1,1, "viktor");
        jobPage.openPage();
        JobEditForm jobForm = jobPage.createNewJob();
        jobForm.setJobNameAndDescr("testName", "")
                .clickGeneralTabLink()
                .getCrtLeftOrRightFolderIfNotFoundCheckBox()
                .setCheckbox(true);
        jobForm.saveJob();
        SQLhelper.assignJobToUser("testName", "viktor");
        runner.sendGetJobsQuery("0", "", runner.getFromCredsByKey("jobrunnerid"));
        Assert.assertEquals(runner.getJobOptionsValueByName("testName", "create-if-not-found"), "yes");
    }

    @Description("The test checks that save delete/replaced files, last ver only checkbox can be deselected")
    @Test
    public void deleteReplaceFilesCheckBoxCanBeDeselectedVisualCheckTest(){
        jobPage.openPage();
        JobEditForm jobForm = jobPage.createNewJob();
        Assert.assertFalse(jobForm.clickGeneralTabLink()
                .getSaveDelOrReplacedFilesLastVerOnlyCheckBox()
                .setCheckbox(false)
                .isSelected());
    }

    @Description("The test checks that save delete/replaced files, last ver only can be deselected and received by runner mock")
    @Test
    public void deleteReplaceFilesLastVerCanBeDeselectedTest(){
        runner.sendNewUserQuery(SQLhelper.getCompanyId(), "viktor", "PC", "2",
                "Test", "0", "");
        SQLhelper.setRunnerBooleanFlags(1,1, "viktor");
        jobPage.openPage();
        JobEditForm jobForm = jobPage.createNewJob();
        jobForm.setJobNameAndDescr("testName", "")
                .clickGeneralTabLink()
                .getSaveDelOrReplacedFilesLastVerOnlyCheckBox()
                .setCheckbox(false);
        jobForm.saveJob();
        SQLhelper.assignJobToUser("testName", "viktor");
        runner.sendGetJobsQuery("0", "", runner.getFromCredsByKey("jobrunnerid"));
        Assert.assertEquals(runner.getJobOptionsValueByName("testName", "save-prev-version"), "no");
    }

    @Description("The test checks that clean up saved folder checkbox can be deactivated with visual check")
    @Test
    public void cleanUpSavedFolderCanBeDeselevtedVisualCheckTest(){
        jobPage.openPage();
        JobEditForm jobForm = jobPage.createNewJob();
        Assert.assertFalse(jobForm.clickGeneralTabLink()
                .getClnSavedFolderAfterThisManyDaysCheckBox()
                .setCheckbox(false)
                .isSelected());
    }

    @Description("The test checks that clean up saved folder checkbox can be deactivated and this option is received by runner mock object")
    @Test
    public void cleanUpSavedFolderCanBeDeselectedTest(){
        runner.sendNewUserQuery(SQLhelper.getCompanyId(), "viktor", "PC", "2",
                "Test", "0", "");
        SQLhelper.setRunnerBooleanFlags(1,1, "viktor");
        jobPage.openPage();
        JobEditForm jobForm = jobPage.createNewJob();
        jobForm.setJobNameAndDescr("testName", "")
                .clickGeneralTabLink()
                .setCleanSavedFolderAfterManyDaysCheckBoxToValue(false);
        jobForm.saveJob();
        SQLhelper.assignJobToUser("testName", "viktor");
        runner.sendGetJobsQuery("0", "", runner.getFromCredsByKey("jobrunnerid"));
        Assert.assertEquals(runner.getJobOptionsValueByName("testName", "cleanup-prev-version"), "no");
    }

    @Description("The test checks that clean up saved folder after that many days value '0' can be set and passed to runner")
    @Test
    public void cleanUpSavedFolderAfterThatDayZeroTest(){
        runner.sendNewUserQuery(SQLhelper.getCompanyId(), "viktor", "PC", "2",
                "Test", "0", "");
        SQLhelper.setRunnerBooleanFlags(1,1, "viktor");
        jobPage.openPage();
        JobEditForm jobForm = jobPage.createNewJob();
        jobForm.setJobNameAndDescr("testName", "")
                .clickGeneralTabLink()
                .setCleanSavedFolderAfterManyDaysFieldToValue("0");
        jobForm.saveJob();
        SQLhelper.assignJobToUser("testName", "viktor");
        runner.sendGetJobsQuery("0", "", runner.getFromCredsByKey("jobrunnerid"));
        Assert.assertEquals(runner.getJobOptionsValueByName("testName", "days-prev-version"), "0");
    }

    @Description("The test checks that clean up saved folder after that many days value '1000' can be set and passed to runner")
    @Test
    public void cleanUpSavedFolderAfterThatDay1000Test(){
        runner.sendNewUserQuery(SQLhelper.getCompanyId(), "viktor", "PC", "2",
                "Test", "0", "");
        SQLhelper.setRunnerBooleanFlags(1,1, "viktor");
        jobPage.openPage();
        JobEditForm jobForm = jobPage.createNewJob();
        jobForm.setJobNameAndDescr("testName", "")
                .clickGeneralTabLink()
                .setCleanSavedFolderAfterManyDaysFieldToValue("1000");
        jobForm.saveJob();
        SQLhelper.assignJobToUser("testName", "viktor");
        runner.sendGetJobsQuery("0", "", runner.getFromCredsByKey("jobrunnerid"));
        Assert.assertEquals(runner.getJobOptionsValueByName("testName", "days-prev-version"), "1000");
    }

    @Description("The test checks that clean up saved folder after that many days throw error message if value more than 1000")
    @Test
    public void cleanUpSavedFolderAfterThatDay1001Test(){
        jobPage.openPage();
        JobEditForm jobForm = jobPage.createNewJob();
        jobForm.setJobNameAndDescr("testName", "")
                .clickGeneralTabLink()
                .setCleanSavedFolderAfterManyDaysFieldToValue("1001");
        jobForm.saveJob();
        Assert.assertTrue(jobForm.isTextPresent("Please enter a value between 0 and 1000."));
    }

    @Description("The test checks that clean up saved folder after that many days throw errors if value lower than 0")
    @Test
    public void cleanUpSavedFolderAfterDayNegativeValueTest(){
        jobPage.openPage();
        JobEditForm jobForm = jobPage.createNewJob();
        jobForm.setJobNameAndDescr("testName", "")
                .clickGeneralTabLink()
                .setCleanSavedFolderAfterManyDaysFieldToValue("-1");
        jobForm.saveJob();
        Assert.assertTrue(jobForm.isTextPresent("Please enter a value greater than or equal to 0."));
    }

    @Description("The test checks that clean up saved folder after that many days can not be empty")
    @Test
    public void cleanUpSavedFolderAfterDayEmptyTest(){
        jobPage.openPage();
        JobEditForm jobForm = jobPage.createNewJob();
        jobForm.setJobNameAndDescr("testName", "")
                .clickGeneralTabLink()
                .setCleanSavedFolderAfterManyDaysFieldToValue(" ");
        jobForm.saveJob();
        Assert.assertTrue(jobForm.isTextPresent("This field is required."));
    }

    @Description("The test checks that clean up saved folder after many days can take only digits")
    @Test
    public void cleanUpSavedFolderAfterDayNonDigitTest(){
        jobPage.openPage();
        JobEditForm jobForm = jobPage.createNewJob();
        jobForm.setJobNameAndDescr("testName", "")
                .clickGeneralTabLink()
                .setCleanSavedFolderAfterManyDaysFieldToValue("one");
        jobForm.saveJob();
        Assert.assertTrue(jobForm.isTextPresent("Please enter a valid number."));
    }

    @Description("The test checks that save deleted/replaced files multiply versions checkbox can be selected with visual check")
    @Test
    public void saveDeleteReplacedFilesMultiplyVerCheckboxCanBeSelectedVisuallyTest(){
        jobPage.openPage();
        JobEditForm jobForm = jobPage.createNewJob();
        Assert.assertTrue(jobForm.clickGeneralTabLink()
                .getSaveDelOrReplacedFilesMultiplyVerCheckBox()
                .setCheckbox(true)
                .isSelected());
    }

    @Description("The test checks that save deleted/replaced files multiply ver checkbox can be set and received by runner mock object")
    @Test
    public void saveDeleteReplacedFilesMultiplyVerCheckBoxTest(){
        runner.sendNewUserQuery(SQLhelper.getCompanyId(), "viktor", "PC", "2",
                "Test", "0", "");
        SQLhelper.setRunnerBooleanFlags(1,1, "viktor");
        jobPage.openPage();
        JobEditForm jobForm = jobPage.createNewJob();
        jobForm.setJobNameAndDescr("testName", "")
                .clickGeneralTabLink()
                .setSaveDelOrReplacedFilesMultiVerCheckBoxToValue(true);
        jobForm.saveJob();
        SQLhelper.assignJobToUser("testName", "viktor");
        runner.sendGetJobsQuery("0", "", runner.getFromCredsByKey("jobrunnerid"));
        Assert.assertEquals(runner.getJobOptionsValueByName("testName", "save-past-versions"), "yes");
    }

    @Description("The test checks that if save del/repl files multiply is selected save del/rep files last ver must be deselected by auto")
    @Test
    public void saveDeleteReplacedFilesMultiplySelectedOtherDeselectedVisualCheckTest(){
        jobPage.openPage();
        JobEditForm jobForm = jobPage.createNewJob();
        GeneralTab general = jobForm.clickGeneralTabLink()
                .setSaveDelOrReplacedFilesMultiVerCheckBoxToValue(true);
        Assert.assertFalse(general.getSaveDelOrReplacedFilesLastVerOnlyCheckBox()
                .isSelected());
    }

    @Description("The test checks that if save del/repl files multiply is selected save del/rep files last ver must be deselected by auto and received by runner")
    @Test
    public void saveDeleteReplacedFilesMultiplySelectedOtherDeselectedTest(){
        runner.sendNewUserQuery(SQLhelper.getCompanyId(), "viktor", "PC", "2",
                "Test", "0", "");
        SQLhelper.setRunnerBooleanFlags(1,1, "viktor");
        jobPage.openPage();
        JobEditForm jobForm = jobPage.createNewJob();
        jobForm.setJobNameAndDescr("testName", "")
                .clickGeneralTabLink()
                .setSaveDelOrReplacedFilesMultiVerCheckBoxToValue(true);
        jobForm.saveJob();
        SQLhelper.assignJobToUser("testName", "viktor");
        runner.sendGetJobsQuery("0", "", runner.getFromCredsByKey("jobrunnerid"));
        Assert.assertEquals(runner.getJobOptionsValueByName("testName", "save-past-versions"), "yes");
        Assert.assertEquals(runner.getJobOptionsValueByName("testName", "save-prev-version"), "no");
    }

    @Description("The test checks that clean up history folder checkbox can be selected and confirmed via runner mock object")
    @Test
    public void cleanUpHistoryFolderCheckboxCanBeSelectedTest(){
        runner.sendNewUserQuery(SQLhelper.getCompanyId(), "viktor", "PC", "2",
                "Test", "0", "");
        SQLhelper.setRunnerBooleanFlags(1,1, "viktor");
        jobPage.openPage();
        JobEditForm jobForm = jobPage.createNewJob();
        jobForm.setJobNameAndDescr("testName", "")
                .clickGeneralTabLink()
                .setCleanHistoryFolderAfteManyDaysCheckBoxTovalue(true);
        jobForm.saveJob();
        SQLhelper.assignJobToUser("testName", "viktor");
        runner.sendGetJobsQuery("0", "", runner.getFromCredsByKey("jobrunnerid"));
        Assert.assertEquals(runner.getJobOptionsValueByName("testName", "cleanup-past-versions"), "yes");
    }

    @Description("The test checks that clean up history folder can be set to '0' days and check by runner mock object")
    @Test
    public void cleanUpHistoryFolderSetToZeroTest(){
        runner.sendNewUserQuery(SQLhelper.getCompanyId(), "viktor", "PC", "2",
                "Test", "0", "");
        SQLhelper.setRunnerBooleanFlags(1,1, "viktor");
        jobPage.openPage();
        JobEditForm jobForm = jobPage.createNewJob();
        GeneralTab general = jobForm.setJobNameAndDescr("testName", "")
                .clickGeneralTabLink()
                .setCleanHistoryFolderAfteManyDaysCheckBoxTovalue(true);
        general.setCleanHistoryFolderAfterDaysInputFieldToValue("0");
        jobForm.saveJob();
        SQLhelper.assignJobToUser("testName", "viktor");
        runner.sendGetJobsQuery("0", "", runner.getFromCredsByKey("jobrunnerid"));
        Assert.assertEquals(runner.getJobOptionsValueByName("testName", "cleanup-past-versions"), "yes");
        Assert.assertEquals(runner.getJobOptionsValueByName("testName", "days-past-versions"), "0");
    }

    @Description("The test checks that clean up history folder can be set to 1000 days and received by runner mock object")
    @Test
    public void cleanUpHistoryFolderSetTo1000Test(){
        runner.sendNewUserQuery(SQLhelper.getCompanyId(), "viktor", "PC", "2",
                "Test", "0", "");
        SQLhelper.setRunnerBooleanFlags(1,1, "viktor");
        jobPage.openPage();
        JobEditForm jobForm = jobPage.createNewJob();
        GeneralTab general = jobForm.setJobNameAndDescr("testName", "")
                .clickGeneralTabLink()
                .setCleanHistoryFolderAfteManyDaysCheckBoxTovalue(true);
        general.setCleanHistoryFolderAfterDaysInputFieldToValue("1000");
        jobForm.saveJob();
        SQLhelper.assignJobToUser("testName", "viktor");
        runner.sendGetJobsQuery("0", "", runner.getFromCredsByKey("jobrunnerid"));
        Assert.assertEquals(runner.getJobOptionsValueByName("testName", "cleanup-past-versions"), "yes");
        Assert.assertEquals(runner.getJobOptionsValueByName("testName", "days-past-versions"), "1000");
    }

    @Description("The test checks that clean up history folder set days can not be set to 1001 days")
    @Test
    public void cleanUpHistoryFolderSetTo1001Test(){
        jobPage.openPage();
        JobEditForm jobForm = jobPage.createNewJob();
        GeneralTab general = jobForm.setJobNameAndDescr("testName", "")
                .clickGeneralTabLink()
                .setCleanHistoryFolderAfteManyDaysCheckBoxTovalue(true);
        general.setCleanHistoryFolderAfterDaysInputFieldToValue("1001");
        jobForm.saveJob();
        Assert.assertTrue(jobForm.isTextPresent("Please enter a value between 0 and 1000."));
    }

    @Description("The test checks that clean up history folder set days can not be set to negative value like -1")
    @Test
    public void cleanUpHistoryFolderSetToNegativeTest(){
        jobPage.openPage();
        JobEditForm jobForm = jobPage.createNewJob();
        GeneralTab general = jobForm.setJobNameAndDescr("testName", "")
                .clickGeneralTabLink()
                .setCleanHistoryFolderAfteManyDaysCheckBoxTovalue(true);
        general.setCleanHistoryFolderAfterDaysInputFieldToValue("-1");
        jobForm.saveJob();
        Assert.assertTrue(jobForm.isTextPresent("Please enter a value greater than or equal to 0."));
    }

    @Description("The test checks that clean up history folder set days can not be empty")
    @Test
    public void cleanUpHistoryFolderSetToEmptyTest(){
        jobPage.openPage();
        JobEditForm jobForm = jobPage.createNewJob();
        GeneralTab general = jobForm.setJobNameAndDescr("testName", "")
                .clickGeneralTabLink()
                .setCleanHistoryFolderAfteManyDaysCheckBoxTovalue(true);
        general.setCleanHistoryFolderAfterDaysInputFieldToValue(" ");
        jobForm.saveJob();
        Assert.assertTrue(jobForm.isTextPresent("This field is required."));
    }

    @Description("The test checks that clean up history folder can not be set days to non digit value")
    @Test
    public void cleanUpHistoryFolderSetToNonDigitTest(){
        jobPage.openPage();
        JobEditForm jobForm = jobPage.createNewJob();
        GeneralTab general = jobForm.setJobNameAndDescr("testName", "")
                .clickGeneralTabLink()
                .setCleanHistoryFolderAfteManyDaysCheckBoxTovalue(true);
        general.setCleanHistoryFolderAfterDaysInputFieldToValue("one");
        jobForm.saveJob();
        Assert.assertTrue(jobForm.isTextPresent("Please enter a valid number."));
    }

    @Description("The test checks that total seconds to reconnect attempt can be set to '0' seconds")
    @Test
    public void totalSecondsToReconnectCanBeSetToZeroTest(){
        runner.sendNewUserQuery(SQLhelper.getCompanyId(), "viktor", "PC", "2",
                "Test", "0", "");
        SQLhelper.setRunnerBooleanFlags(1,1, "viktor");
        jobPage.openPage();
        JobEditForm jobForm = jobPage.createNewJob();
        GeneralTab general = jobForm.setJobNameAndDescr("testName", "")
                .clickGeneralTabLink()
                .setTotalSecondsToReconnectAttemptInputFieldToValue("0");
        jobForm.saveJob();
        SQLhelper.assignJobToUser("testName", "viktor");
        runner.sendGetJobsQuery("0", "", runner.getFromCredsByKey("jobrunnerid"));
        Assert.assertEquals(runner.getJobOptionsValueByName("testName", "reconnect-secs"), "0");
    }

    @Description("The test checks that total seconds to reconnect attempt can be set to 30000")
    @Test
    public void totalSecondsToReconnectCanBeSetTo30000Test(){
        runner.sendNewUserQuery(SQLhelper.getCompanyId(), "viktor", "PC", "2",
                "Test", "0", "");
        SQLhelper.setRunnerBooleanFlags(1,1, "viktor");
        jobPage.openPage();
        JobEditForm jobForm = jobPage.createNewJob();
        GeneralTab general = jobForm.setJobNameAndDescr("testName", "")
                .clickGeneralTabLink()
                .setTotalSecondsToReconnectAttemptInputFieldToValue("30000");
        jobForm.saveJob();
        SQLhelper.assignJobToUser("testName", "viktor");
        runner.sendGetJobsQuery("0", "", runner.getFromCredsByKey("jobrunnerid"));
        Assert.assertEquals(runner.getJobOptionsValueByName("testName", "reconnect-secs"), "30000");
    }

    @Description("The test checks that total seconds to reconnect attempt can not be set to negative value")
    @Test
    public void totalSecondsToReconnectCanBeSetToNegativeTest(){
        jobPage.openPage();
        JobEditForm jobForm = jobPage.createNewJob();
        jobForm.clickGeneralTabLink()
                .setTotalSecondsToReconnectAttemptInputFieldToValue("-1");
        jobForm.saveJob();
        Assert.assertTrue(jobForm.isTextPresent("Please enter a value greater than or equal to 0."));
    }

    @Description("The test checks that total seconds to reconnect attempt can not be set to value more than 30000")
    @Test
    public void totalSecondsToReconnectCanBeSetTo30001Test(){
        jobPage.openPage();
        JobEditForm jobForm = jobPage.createNewJob();
        jobForm.clickGeneralTabLink()
                .setTotalSecondsToReconnectAttemptInputFieldToValue("30001");
        jobForm.saveJob();
        Assert.assertTrue(jobForm.isTextPresent("Please enter a value between 0 and 30000."));
    }

    @Description("The test checks that total seconds to reconnect attempt can not be empty")
    @Test
    public void totalSecondsToReconnectCanBeSetToEmptyTest(){
        jobPage.openPage();
        JobEditForm jobForm = jobPage.createNewJob();
        jobForm.clickGeneralTabLink()
                .setTotalSecondsToReconnectAttemptInputFieldToValue(" ");
        jobForm.saveJob();
        Assert.assertTrue(jobForm.isTextPresent("This field is required."));
    }

    @Description("The test checks that total seconds to reconnect attempt can not be set to non digit value")
    @Test
    public void totalSecondsToReconnectCanBeSetToNonDigitTest(){
        jobPage.openPage();
        JobEditForm jobForm = jobPage.createNewJob();
        jobForm.setJobNameAndDescr("TestName", "");
        GeneralTab general = jobForm.clickGeneralTabLink()
                .setTotalSecondsToReconnectAttemptInputFieldToValue("one");
        jobForm.saveJob();
        Assert.assertTrue(general.isTextPresent("Please enter a valid number."));
    }

    @Description("The test checks that run parallel threads checkbox can be selected with visual check")
    @Test
    public void runParallelThreadsCanBeSelectedVisualCheckTest(){
        jobPage.openPage();
        JobEditForm jobForm = jobPage.createNewJob();
        Assert.assertTrue(jobForm.clickGeneralTabLink()
                .getRunParallelThreadInSyncCheckBox()
                .setCheckbox(true)
                .isSelected());
    }

    @Description("The test checks that run parallel threads can be selected and runner received that setting via runner mock object")
    @Test
    public void runParallelThreadsCanBeSelectedTest(){
        runner.sendNewUserQuery(SQLhelper.getCompanyId(), "viktor", "PC", "2",
                "Test", "0", "");
        SQLhelper.setRunnerBooleanFlags(1,1, "viktor");
        jobPage.openPage();
        JobEditForm jobForm = jobPage.createNewJob();
        GeneralTab general = jobForm.setJobNameAndDescr("testName", "")
                .clickGeneralTabLink()
                .setRunParallelThreadsCheckBoxToValue(true);
        jobForm.saveJob();
        SQLhelper.assignJobToUser("testName", "viktor");
        runner.sendGetJobsQuery("0", "", runner.getFromCredsByKey("jobrunnerid"));
        Assert.assertEquals(runner.getJobOptionsValueByName("testName", "run-parallel-threads"), "yes");
    }

    @Description("The test checks that run parallel threads can not be set to negative value")
    @Test
    public void runParallelThreadsCanNotBeSetToNegativeValue(){
        jobPage.openPage();
        JobEditForm jobForm = jobPage.createNewJob();
        jobForm.clickGeneralTabLink()
                .setRunParallelThreadsCheckBoxToValue(true)
                .setNumberOfThreadsToRunInParallelInputFieldToValue("-1");
        jobForm.saveJob();
        Assert.assertTrue(jobForm.isTextPresent("Please enter a value greater than or equal to 0."),
                "warning message 'Please enter a value greater than or equal to 0.' was not found ");
    }

    @Description("The test checks that run parallel threads can be set to zero")
    @Test
    public void runParallelThreadsCanBeSetToZeroTest(){
        runner.sendNewUserQuery(SQLhelper.getCompanyId(), "viktor", "PC", "2",
                "Test", "0", "");
        SQLhelper.setRunnerBooleanFlags(1,1, "viktor");
        jobPage.openPage();
        JobEditForm jobForm = jobPage.createNewJob();
        GeneralTab general = jobForm.setJobNameAndDescr("testName", "")
                .clickGeneralTabLink()
                .setRunParallelThreadsCheckBoxToValue(true)
                .setNumberOfThreadsToRunInParallelInputFieldToValue("0");
        jobForm.saveJob();
        SQLhelper.assignJobToUser("testName", "viktor");
        runner.sendGetJobsQuery("0", "", runner.getFromCredsByKey("jobrunnerid"));
        Assert.assertEquals(runner.getJobOptionsValueByName("testName", "run-parallel-threads"), "yes",
                "option run parallel threads not equal 'yes'");
    }

    @Description("The test checks that run parallel threads can be set to 99")
    @Test
    public void runParallelThreadsCanBeSetTo99Test(){
        runner.sendNewUserQuery(SQLhelper.getCompanyId(), "viktor", "PC", "2",
                "Test", "0", "");
        SQLhelper.setRunnerBooleanFlags(1,1, "viktor");
        jobPage.openPage();
        JobEditForm jobForm = jobPage.createNewJob();
        GeneralTab general = jobForm.setJobNameAndDescr("testName", "")
                .clickGeneralTabLink()
                .setRunParallelThreadsCheckBoxToValue(true)
                .setNumberOfThreadsToRunInParallelInputFieldToValue("99");
        jobForm.saveJob();
        SQLhelper.assignJobToUser("testName", "viktor");
        runner.sendGetJobsQuery("0", "", runner.getFromCredsByKey("jobrunnerid"));
        Assert.assertEquals(runner.getJobOptionsValueByName("testName", "run-parallel-threads"), "yes",
                "option run parallel thread not equal to 'yes'");
        Assert.assertEquals(runner.getJobOptionsValueByName("testName", "worker-threads"), "99",
                "option worker threads is not equal 99");
    }

    @Description("The test checks that run parallel threads can not be set to value more that 100")
    @Test
    public void runParallelThreadsCanNotBeSetTo51Test(){
        jobPage.openPage();
        JobEditForm jobForm = jobPage.createNewJob();
        jobForm.clickGeneralTabLink()
                .setRunParallelThreadsCheckBoxToValue(true)
                .setNumberOfThreadsToRunInParallelInputFieldToValue("100");
        jobForm.saveJob();
        Assert.assertTrue(jobForm.isTextPresent("Please enter a value between 0 and 99."),
                "message with warning 'Please enter a value between 0 and 99.' not found");
    }

    @Description("The tests checks that run parallel threads can not be set to non digit value")
    @Test
    public void runParallelThreadsCanNotBeSetToNonDigitValueTest(){
        jobPage.openPage();
        JobEditForm jobForm = jobPage.createNewJob();
        jobForm.clickGeneralTabLink()
                .setRunParallelThreadsCheckBoxToValue(true)
                .setNumberOfThreadsToRunInParallelInputFieldToValue("one");
        jobForm.saveJob();
        Assert.assertTrue(jobForm.isTextPresent("Please enter a valid number."),
                "warning message 'Please enter a valid number.' not found ");
    }

    @Description("The test checks that run parallel threads can not be empty value")
    @Test
    public void runParallelThreadsCanNotBeEmptyTest(){
        jobPage.openPage();
        JobEditForm jobForm = jobPage.createNewJob();
        jobForm.clickGeneralTabLink()
                .setRunParallelThreadsCheckBoxToValue(true)
                .setNumberOfThreadsToRunInParallelInputFieldToValue(" ");
        jobForm.saveJob();
        Assert.assertTrue(jobForm.isTextPresent("This field is required."),
                "warning message 'This field is required.'");
    }

    @Description("The test checks that sync on file change delay can not be set to negative value like -1")
    @Test
    public void syncOnFileChangeDelayCanNotBeSetToNegativeValue(){
        jobPage.openPage();
        JobEditForm jobForm = jobPage.createNewJob();
        jobForm.clickAutoTabLink()
                .setSyncOnFileChangeCheckBox(true)
                .setOFCdelayFieldToValue("-1");
        jobForm.saveJob();
        Assert.assertTrue(jobForm.isTextPresent("Please enter a value greater than or equal to 0."),
                "warning message 'Please enter a value greater than or equal to 0.' is not found");
    }

    @Description("The test checks that sync on file change delay can be set to 999 value")
    @Test
    public void syncOnFileChangeDelayCanBeSetTo999ValueTest(){
        runner.sendNewUserQuery(SQLhelper.getCompanyId(), "viktor", "PC", "2",
                "Test", "0", "");
        SQLhelper.setRunnerBooleanFlags(1,1, "viktor");
        jobPage.openPage();
        JobEditForm jobForm = jobPage.createNewJob();
        AutoTab autoTab = jobForm.setJobNameAndDescr("testName", "")
                .clickAutoTabLink()
                .setSyncOnFileChangeCheckBox(true)
                .setOFCdelayFieldToValue("999");
        jobForm.saveJob();
        SQLhelper.assignJobToUser("testName", "viktor");
        runner.sendGetJobsQuery("0", "", runner.getFromCredsByKey("jobrunnerid"));
        Assert.assertTrue(jobPage.isJobPresentInTable("testName"));
        Assert.assertEquals(runner.getJobOptionsValueByName("testName", "onfilechange-delay"), "999",
                "option on file change delay is not equal to 999");
    }

    @Description("The test checks that sync on file change delay can not be set to value more than 999, like 1000 and more")
    @Test
    public void syncOnFileChangeDelayCanNotBeSetTo1000Test(){
        jobPage.openPage();
        JobEditForm jobForm = jobPage.createNewJob();
        jobForm.clickAutoTabLink()
                .setSyncOnFileChangeCheckBox(true)
                .setOFCdelayFieldToValue("1000");
        jobForm.saveJob();
        Assert.assertTrue(jobForm.isTextPresent("Please enter a value between 0 and 999."),
                "warning message 'Please enter a value between 0 and 999.' not found");
    }

    @Description("The test checks that sync on file change delay can not be set to non digit value")
    @Test
    public void syncOnFileChangeDelayCanNotBeSetToNonDigitValueTest(){
        jobPage.openPage();
        JobEditForm jobForm = jobPage.createNewJob();
        jobForm.clickAutoTabLink()
                .setSyncOnFileChangeCheckBox(true)
                .setOFCdelayFieldToValue("eee");
        jobForm.saveJob();
        Assert.assertTrue(jobForm.isTextPresent("Please enter a valid number."),
                "warning message 'Please enter a valid number.' not found");
    }

    @Description("The test checks that on folder connect checkbox is can be selected")
    @Test
    public void syncOnFolderConnectCanBeSelectedTest(){
        jobPage.openPage();
        JobEditForm jobForm = jobPage.createNewJob();
        Assert.assertTrue(jobForm.clickAutoTabLink()
                .getOnFolderConnectCheckBox()
                .setCheckbox(true)
                .isSelected(), "sync on folder connect checkbox is not selected");
    }

    @Description("The test checks that sync on folder connect can be set and obtain by runner mock object")
    @Test
    public void syncOnFolderConnectCanBeSetTest(){
        runner.sendNewUserQuery(SQLhelper.getCompanyId(), "viktor", "PC", "2",
                "Test", "0", "");
        SQLhelper.setRunnerBooleanFlags(1,1, "viktor");
        jobPage.openPage();
        JobEditForm jobForm = jobPage.createNewJob();
        AutoTab autoTab = jobForm.setJobNameAndDescr("testName", "")
                .clickAutoTabLink()
                .setOnFolderConnectCheckBox(true);
        jobForm.saveJob();
        SQLhelper.assignJobToUser("testName", "viktor");
        runner.sendGetJobsQuery("0", "", runner.getFromCredsByKey("jobrunnerid"));
        Assert.assertEquals(runner.getJobOptionsValueByName("testName", "on-folder-connect"), "sync",
                "option on folder connect is not equal to 'sync'");
    }

    @Description("The test checks that periodically checkbox can be selected")
    @Test
    public void periodicallyCheckBoxCanBeSelectedTest(){
        jobPage.openPage();
        JobEditForm jobForm = jobPage.createNewJob();
        Assert.assertTrue(jobForm.clickAutoTabLink()
                .getPeriodicallyCheckBox()
                .setCheckbox(true)
                .isSelected(), "periodically checkbox is not selected");
    }

    @Description("The test checks that periodically checkbox can be set and option obtained by runner mock object")
    @Test
    public void periodicallyCheckBoxCanBeSelectedAndPassedToRunnerTest(){
        runner.sendNewUserQuery(SQLhelper.getCompanyId(), "viktor", "PC", "2",
                "Test", "0", "");
        SQLhelper.setRunnerBooleanFlags(1,1, "viktor");
        jobPage.openPage();
        JobEditForm jobForm = jobPage.createNewJob();
        AutoTab autoTab = jobForm.setJobNameAndDescr("testName", "")
                .clickAutoTabLink()
                .setPeriodicallyCheckBox(true)
                .setPeriodicallyTimerFieldToValue("5");
        jobForm.saveJob();
        SQLhelper.assignJobToUser("testName", "viktor");
        runner.sendGetJobsQuery("0", "", runner.getFromCredsByKey("jobrunnerid"));
        Assert.assertEquals(runner.getJobOptionsValueByName("testName", "on-timer"), "sync",
                "option on timer is not equal to 'sync'");
        Assert.assertEquals(runner.getJobOptionsValueByName("testName", "timer-period"), "5",
                "value for periodically timer is not equal to 5");
    }

    @Description("The test checks that periodically time can not be set to zero minutes")
    @Test
    public void periodicallyTimeCanBeSetToZeroTest(){
        jobPage.openPage();
        JobEditForm jobForm = jobPage.createNewJob();
        AutoTab autoTab = jobForm.setJobNameAndDescr("testName", "")
                .clickAutoTabLink()
                .setPeriodicallyCheckBox(true)
                .setPeriodicallyTimerFieldToValue("0");
        jobForm.saveJob();
        Assert.assertTrue(jobForm.isTextPresent("Please enter a value between 1 and 2147483647."),
                "Validation error message Please enter a value between 1 and 2147483647. not found");
        /*Assert.assertTrue(jobPage.isJobPresentInTable("testName"),
                "job 'testName' is not found in jobs table");*/

    }

    @Description("The test checks that periodically timer can be set max valid value 2147483647")
    @Test
    public void periodicallyTimeCanBeSetToMaxValueTest(){
        runner.sendNewUserQuery(SQLhelper.getCompanyId(), "viktor", "PC", "2",
                "Test", "0", "");
        SQLhelper.setRunnerBooleanFlags(1,1, "viktor");
        jobPage.openPage();
        JobEditForm jobForm = jobPage.createNewJob();
        AutoTab autoTab = jobForm.setJobNameAndDescr("testName", "")
                .clickAutoTabLink()
                .setPeriodicallyCheckBox(true)
                .setPeriodicallyTimerFieldToValue("2147483647");
        jobForm.saveJob();
        SQLhelper.assignJobToUser("testName", "viktor");
        runner.sendGetJobsQuery("0", "", runner.getFromCredsByKey("jobrunnerid"));
        Assert.assertTrue(jobPage.isJobPresentInTable("testName"), "job 'testName' is not found in table");
        Assert.assertEquals(runner.getJobOptionsValueByName("testName", "timer-period"), "2147483647",
                "option timer period is not equal to '2147483647'");
    }

    @Description("The test checks that periodically timer can not be empty")
    @Test
    public void periodicallyTimeCanNotBeEmptyTest(){
        jobPage.openPage();
        JobEditForm jobForm = jobPage.createNewJob();
        jobForm.clickAutoTabLink()
                .setPeriodicallyCheckBox(true)
                .setPeriodicallyTimerFieldToValue("");
        jobForm.saveJob();
        Assert.assertTrue(jobForm.isTextPresent("This field is required."),
                "warning message 'This field is required.' is not found");
    }

    @Description("The test checks that periodically time can not be non digit value")
    @Test
    public void periodicallyTimeCanNotBeNonDigitValueTest(){
        jobPage.openPage();
        JobEditForm jobForm = jobPage.createNewJob();
        jobForm.clickAutoTabLink()
                .setPeriodicallyCheckBox(true)
                .setPeriodicallyTimerFieldToValue("eee");
        jobForm.saveJob();
        Assert.assertTrue(jobForm.isTextPresent("Please enter a valid number."),
                "warning message 'Please enter a valid number.' is not found");
    }

    @Description("The test checks that do not sync if changes checkbox can be selected")
    @Test
    public void doNotSyncIfChangesCanBeSelectedTest(){
        jobPage.openPage();
        JobEditForm jobForm = jobPage.createNewJob();
        Assert.assertTrue(jobForm.clickAutoTabLink()
                .getNotSyncIfChangesMoreThatCheckBox()
                .setCheckbox(true)
                .isSelected(), "do not sync if changes checkbox is not selected");
    }

    @Description("The test checks that do not sync if changes lower than can be set 100%")
    @Test
    public void doNotSyncIfChangeCanBeSetTo100percTest(){
        runner.sendNewUserQuery(SQLhelper.getCompanyId(), "viktor", "PC", "2",
                "Test", "0", "");
        jobPage.openPage();
        JobEditForm jobForm = jobPage.createNewJob();
        AutoTab autoTab = jobForm.setJobNameAndDescr("testName", "")
                .clickAutoTabLink()
                .setNotSyncIfChangesMoreThanCheckBox(true)
                .setNotSyncChangesFieldToValue("100");
        jobForm.saveJob();
        SQLhelper.setRunnerBooleanFlags(1, 1, "viktor");
        SQLhelper.assignJobToUser("testName", "viktor");
        runner.sendGetJobsQuery("0", "", runner.getFromCredsByKey("jobrunnerid"));
        Assert.assertTrue(jobPage.isJobPresentInTable("testName"), "job 'testname' is not found in table");
        Assert.assertEquals(runner.getJobOptionsValueByName("testName", "limit-changes"), "100",
                "option limit changes not equal to 100");
    }

    @Description("The test checks that do not sync if changes can not be set to 101%")
    @Test
    public void doNotSyncIfChangeCanNotBeSetTo101percTest(){
        jobPage.openPage();
        JobEditForm jobForm = jobPage.createNewJob();
        jobForm.clickAutoTabLink()
                .setNotSyncIfChangesMoreThanCheckBox(true)
                .setNotSyncChangesFieldToValue("101");
        jobForm.saveJob();
        Assert.assertTrue(jobForm.isTextPresent("Please enter a value between 0 and 100."),
                "warning message 'Please enter a value between 0 and 100.' is not found");
    }

    @Description("The test checks that do not sync if changes can not be set to -1%")
    @Test
    public void doNotSyncIfChangesCanNotBeNegativeValueTest(){
        jobPage.openPage();
        JobEditForm jobForm = jobPage.createNewJob();
        jobForm.clickAutoTabLink()
                .setNotSyncIfChangesMoreThanCheckBox(true)
                .setNotSyncChangesFieldToValue("-1");
        jobForm.saveJob();
        Assert.assertTrue(jobForm.isTextPresent("Please enter a value greater than or equal to 0."),
                "warning message 'Please enter a value greater than or equal to 0.' is not found");
    }

    @Description("The test checks that do not sync if changes can not be set to non digit value")
    @Test
    public void doNotSyncIfChangesCanNotBeSetToNonDigitValueTest(){
        jobPage.openPage();
        JobEditForm jobForm = jobPage.createNewJob();
        jobForm.clickAutoTabLink()
                .setNotSyncIfChangesMoreThanCheckBox(true)
                .setNotSyncChangesFieldToValue("eee");
        jobForm.saveJob();
        Assert.assertTrue(jobForm.isTextPresent("Please enter a valid number."),
                "warning message 'Please enter a valid number.' is not found");
    }

    @Description("The test checks that do not sync if changes by default is equal to 50%")
    @Test
    public void doNotSyncIfChangeDefaultValue50percTest(){
        runner.sendNewUserQuery(SQLhelper.getCompanyId(), "viktor", "PC", "2",
                "Test", "0", "");
        jobPage.openPage();
        JobEditForm jobForm = jobPage.createNewJob();
        AutoTab autoTab = jobForm.setJobNameAndDescr("testName", "")
                .clickAutoTabLink()
                .setNotSyncIfChangesMoreThanCheckBox(true);
        jobForm.saveJob();
        SQLhelper.setRunnerBooleanFlags(1, 1, "viktor");
        SQLhelper.assignJobToUser("testName", "viktor");
        runner.sendGetJobsQuery("0", "", runner.getFromCredsByKey("jobrunnerid"));
        Assert.assertEquals(runner.getJobOptionsValueByName("testName", "limit-changes"), "50",
                "option limit changes is not set to 50");
    }

    @Description("The test checks that wait for locks to clear is enabled by default")
    @Test
    public void waitForLocksToClearEnabledByDefaultTest(){
        jobPage.openPage();
        JobEditForm jobForm = jobPage.createNewJob();
        Assert.assertTrue(jobForm.clickAutoTabLink()
                .getWaitForLockToClearCheckBox()
                .isSelected(), "wait for locks to clear checkbox is not selected");
    }

    @Description("The test checks that wait for locks to clear can be set to max valid value 2147483647")
    @Test
    public void waitForLocksToClearCanBeSetToMaxValidValueTest(){
        runner.sendNewUserQuery(SQLhelper.getCompanyId(), "viktor", "PC", "2",
                "Test", "0", "");
        jobPage.openPage();
        JobEditForm jobForm = jobPage.createNewJob();
        AutoTab autoTab = jobForm.setJobNameAndDescr("testName", "")
                .clickAutoTabLink()
                .setWaitForLockToClearCheckBox(true)
                .setWaitForLocksFieldToValue("2147483647");
        jobForm.saveJob();
        SQLhelper.setRunnerBooleanFlags(1, 1, "viktor");
        SQLhelper.assignJobToUser("testName", "viktor");
        runner.sendGetJobsQuery("0", "", runner.getFromCredsByKey("jobrunnerid"));
        Assert.assertTrue(jobPage.isJobPresentInTable("testName"));
        Assert.assertEquals(runner.getJobOptionsValueByName("testName", "wait-for-locks-minutes"), "2147483647",
                "option wait for locks minutes is not equal to 2147483647");
    }

    @Description("The test checks that wait for locks to clear can not be set to -1")
    @Test
    public void waitForLocksToClearCanNotBeSetToNegativeValueTest(){
        jobPage.openPage();
        JobEditForm jobForm = jobPage.createNewJob();
        jobForm.clickAutoTabLink()
                .setWaitForLockToClearCheckBox(true)
                .setWaitForLocksFieldToValue("-1");
        jobForm.saveJob();
        Assert.assertTrue(jobForm.isTextPresent("Please enter a value greater than or equal to 0."),
                "warning message 'Please enter a value greater than or equal to 0.' is not found");
    }

    @Description("The test checks that wait for locks to clear can not accept non digit values")
    @Test
    public void waitForLocksToClearCanNotAcceptNonDigitValuesTest(){
        jobPage.openPage();
        JobEditForm jobForm = jobPage.createNewJob();
        jobForm.clickAutoTabLink()
                .setWaitForLockToClearCheckBox(true)
                .setWaitForLocksFieldToValue("eee");
        jobForm.saveJob();
        Assert.assertTrue(jobForm.isTextPresent("Please enter a valid number."),
                "warning message 'Please enter a valid number.' is not found");
    }

    @Description("The test checks that Conflict resolution checkbox can be selected")
    @Test
    public void conflictResolutionRenameNotDeleteCheckboxCanBeSelectedTest(){
        jobPage.openPage();
        JobEditForm jobForm = jobPage.createNewJob();
        Assert.assertTrue(jobForm.clickAutoTabLink()
                .getRenameLosingFileNotDelCheckBox()
                .setCheckbox(true)
                .isSelected(), "conflict resolution rename not del checkbox is not selected");
    }

    @Description("The test checks that Conflict resolution rename not delete can be received by runner mock object")
    @Test
    public void conflictResolutionRenameNotDeleteCanBeReceivedByRunnerTest(){
        runner.sendNewUserQuery(SQLhelper.getCompanyId(), "viktor", "PC", "2",
                "Test", "0", "");
        jobPage.openPage();
        JobEditForm jobForm = jobPage.createNewJob();
        AutoTab autoTab = jobForm.setJobNameAndDescr("testName", "")
                .clickAutoTabLink()
                .setRenameLosingFileNotDeleteCheckBox(true);
        jobForm.saveJob();
        SQLhelper.setRunnerBooleanFlags(1, 1, "viktor");
        SQLhelper.assignJobToUser("testName", "viktor");
        runner.sendGetJobsQuery("0", "", runner.getFromCredsByKey("jobrunnerid"));
        Assert.assertTrue(jobPage.isJobPresentInTable("testName"));
        Assert.assertEquals(runner.getJobOptionsValueByName("testName", "rename-losing-file"), "yes",
                "option rename losing file is not equal to yes");
    }

    @Description("The test checks that copy file creation time is ON and received by runner mock object")
    @Test
    public void copyFileCreationTimeIsOnTest(){
        runner.sendNewUserQuery(SQLhelper.getCompanyId(), "viktor", "PC", "2",
                "Test", "0", "");
        jobPage.openPage();
        JobEditForm jobForm = jobPage.createNewJob();
        AdvancedTab advancedTab = jobForm.setJobNameAndDescr("testName", "")
                .clickAdvancedTabLink()
                .clickSyncPanel()
                .setCopyFileCreateTimeCheckBoxToValue(true);
        jobForm.saveJob();
        SQLhelper.setRunnerBooleanFlags(1, 1, "viktor");
        SQLhelper.assignJobToUser("testName", "viktor");
        runner.sendGetJobsQuery("0", "", runner.getFromCredsByKey("jobrunnerid"));
        Assert.assertEquals(runner.getJobOptionsValueByName("testName", "copy-create-time"), "yes",
                "option copy create time is not equal to yes");
    }

    @Description("The test checks that copy attributes checkbox is On by default")
    @Test
    public void copyAttributesCheckboxIsOnByDefaultTest(){
        jobPage.openPage();
        JobEditForm jobForm = jobPage.createNewJob();
        Assert.assertTrue(jobForm.clickAdvancedTabLink()
                .getCopyAttrCheckBox()
                .isSelected(), "copy attributes checkbox is not selected");
    }

    @Description("The test checks that copy attributes can be set to OFF and received by runner mock object")
    @Test
    public void copyAttributesCheckboxCanBeSetToOffTest(){
        runner.sendNewUserQuery(SQLhelper.getCompanyId(), "viktor", "PC", "2",
                "Test", "0", "");
        jobPage.openPage();
        JobEditForm jobForm = jobPage.createNewJob();
        AdvancedTab advancedTab = jobForm.setJobNameAndDescr("testName", "")
                .clickAdvancedTabLink()
                .clickSyncPanel()
                .setCopyAttrCheckBoxToValue(false);
        jobForm.saveJob();
        SQLhelper.setRunnerBooleanFlags(1, 1, "viktor");
        SQLhelper.assignJobToUser("testName", "viktor");
        runner.sendGetJobsQuery("0", "", runner.getFromCredsByKey("jobrunnerid"));
        Assert.assertTrue(jobPage.isJobPresentInTable("testName"));
        Assert.assertEquals(runner.getJobOptionsValueByName("testName", "copy-attrs"), "no",
                "option copy attributes is not equal to no");
    }

    @Description("The test checks that copy ACL security attrs can be selected and received by runner mock object")
    @Test
    public void aclSecurityAttrCheckboxCanBeReceivedByRunnerTest(){
        runner.sendNewUserQuery(SQLhelper.getCompanyId(), "viktor", "PC", "2",
                "Test", "0", "");
        jobPage.openPage();
        JobEditForm jobForm = jobPage.createNewJob();
        AdvancedTab advancedTab = jobForm.setJobNameAndDescr("testName", "")
                .clickAdvancedTabLink()
                .clickSyncPanel()
                .setCopyAclCheckBoxToValue(true);
        jobForm.saveJob();
        SQLhelper.setRunnerBooleanFlags(1, 1, "viktor");
        SQLhelper.assignJobToUser("testName", "viktor");
        runner.sendGetJobsQuery("0", "", runner.getFromCredsByKey("jobrunnerid"));
        Assert.assertTrue(jobPage.isJobPresentInTable("testName"), "job 'testName' is not found in table");
        Assert.assertEquals(runner.getJobOptionsValueByName("testName", "copy-acl"), "yes",
                "option copy acl is not equal to yes");
    }

    @Description("The test checks that max time to run can be selected and set with default value equal to zero by runner object")
    @Test
    public void maxTimeToRunIsSetToZeroByDefaultOnActivationTest(){
        runner.sendNewUserQuery(SQLhelper.getCompanyId(), "viktor", "PC", "2",
                "Test", "0", "");
        jobPage.openPage();
        JobEditForm jobForm = jobPage.createNewJob();
        jobForm.setJobNameAndDescr("testName", "")
                .clickGeneralTabLink()
                .setMaxTimeToRunCheckBox(true);
        jobForm.saveJob();
        SQLhelper.setRunnerBooleanFlags(1, 1, "viktor");
        SQLhelper.assignJobToUser("testName", "viktor");
        runner.sendGetJobsQuery("0", "", runner.getFromCredsByKey("jobrunnerid"));
        Assert.assertEquals(runner.getJobOptionsValueByName("testName", "max-time-to-run"), "0",
                "option max time to run not equal to default value '0'");
    }

    @Description("The test checks that max time to run can be selected and set to 100 and confirmed by runner object")
    @Test
    public void maxTimeToRunCanBeSetToValue100Test(){
        runner.sendNewUserQuery(SQLhelper.getCompanyId(), "viktor", "PC", "2",
                "Test", "0", "");
        jobPage.openPage();
        JobEditForm jobForm = jobPage.createNewJob();
        jobForm.setJobNameAndDescr("testName", "")
                .clickGeneralTabLink()
                .setMaxTimeToRunCheckBox(true)
                .setMaxTimeToRunToValue("100");
        jobForm.saveJob();
        SQLhelper.setRunnerBooleanFlags(1, 1, "viktor");
        SQLhelper.assignJobToUser("testName", "viktor");
        runner.sendGetJobsQuery("0", "", runner.getFromCredsByKey("jobrunnerid"));
        Assert.assertEquals(runner.getJobOptionsValueByName("testName", "max-time-to-run"), "100",
                "option max time to run not equal to default value '100'");
    }

    @Description("The test checks that max time to run can be set only to digit value")
    @Test
    public void maxTimeToRunCanNotAcceptNonDigitValueTest(){
        jobPage.openPage();
        JobEditForm jobForm = jobPage.createNewJob();
        jobForm.setJobNameAndDescr("testName", "")
                .clickGeneralTabLink()
                .setMaxTimeToRunCheckBox(true)
                .setMaxTimeToRunToValue("eeeeee");
        jobForm.saveJob();
        Assert.assertTrue(jobForm.isTextPresent("Please enter a valid number."),
                "Warning message 'Please enter a valid number.' not found");
    }

    @Description("The test checks that max time to run checkbox can be selected and re-opening saves changes")
    @Test
    public void maxTimeToRunCanBeSavedOnReOpenTest(){
        jobPage.openPage();
        JobEditForm jobForm = jobPage.createNewJob();
        jobForm.setJobNameAndDescr("testName", "")
                .clickGeneralTabLink()
                .setMaxTimeToRunCheckBox(true);
        jobForm.saveJob();
        Assert.assertTrue(jobPage.clickOnTheJobNameInTable("testName")
                .clickEditJobButton()
                .clickGeneralTabLink()
                .getMaxTimeToRunCheckBox()
                .isSelected(), "check box max time to run not selected");
    }

    @Description("The test checks that on file change can be selected and default value '0' is not set and passed to runner object")
    @Test
    public void onFileChangeDefaultValueTest(){
        runner.sendNewUserQuery(SQLhelper.getCompanyId(), "viktor", "PC", "2",
                "Test", "0", "");
        jobPage.openPage();
        JobEditForm jobForm = jobPage.createNewJob();
        jobForm.setJobNameAndDescr("testName", "")
                .clickAutoTabLink()
                .setSyncOnFileChangeCheckBox(true);
        jobForm.saveJob();
        SQLhelper.setRunnerBooleanFlags(1, 1, "viktor");
        SQLhelper.assignJobToUser("testName", "viktor");
        runner.sendGetJobsQuery("0", "", runner.getFromCredsByKey("jobrunnerid"));
        Assert.assertEquals(runner.getJobOptionsValueByName("testName", "on-file-change"), "sync",
                "option on file change is not equal to 'sync'");
        Assert.assertEquals(runner.getJobOptionsValueByName("testName", "onfilechange-delay"), null,
                "option on file change delay is present in command line keys");
    }

    @Description("The test checks that on file change delay input field value can not be empty")
    @Test
    public void onFileChangeDelayCanNotBeEmptyTest(){
        jobPage.openPage();
        JobEditForm jobForm = jobPage.createNewJob();
        jobForm.setJobNameAndDescr("testName", "")
                .clickAutoTabLink()
                .setSyncOnFileChangeCheckBox(true)
                .setOFCdelayFieldToValue("");
        jobForm.saveJob();
        Assert.assertTrue(jobForm.isTextPresent("This field is required."),
                "Warning message 'This field is required.' is not found");
    }

    @Description("The test checks that run max parallel threads by default is equal to 5")
    @Test
    public void maxParallelThreadsEqualToValue5byDefaultTest(){
        runner.sendNewUserQuery(SQLhelper.getCompanyId(), "viktor", "PC", "2",
                "Test", "0", "");
        jobPage.openPage();
        JobEditForm jobForm = jobPage.createNewJob();
        jobForm.setJobNameAndDescr("testName", "")
                .clickGeneralTabLink()
                .setRunParallelThreadsCheckBoxToValue(true);
        jobForm.saveJob();
        SQLhelper.setRunnerBooleanFlags(1, 1, "viktor");
        SQLhelper.assignJobToUser("testName", "viktor");
        runner.sendGetJobsQuery("0", "", runner.getFromCredsByKey("jobrunnerid"));
        Assert.assertEquals(runner.getJobOptionsValueByName("testName", "worker-threads"), "5",
                "parallel threads by default is not equal to value 5");
    }

    @Description("The test checks that float value like 10.6 will be cut to integer and will be saved as 10")
    @Test
    public void onFileChangeDelayCanOnlyBeSavedAsIntegerValueTest(){
        runner.sendNewUserQuery(SQLhelper.getCompanyId(), "viktor", "PC", "2",
                "Test", "0", "");
        jobPage.openPage();
        JobEditForm jobForm = jobPage.createNewJob();
        jobForm.setJobNameAndDescr("testName", "")
                .clickAutoTabLink()
                .setSyncOnFileChangeCheckBox(true)
                .setOFCdelayFieldToValue("10.6");
        jobForm.saveJob();
        SQLhelper.setRunnerBooleanFlags(1, 1, "viktor");
        SQLhelper.assignJobToUser("testName", "viktor");
        runner.sendGetJobsQuery("0", "", runner.getFromCredsByKey("jobrunnerid"));
        Assert.assertEquals(runner.getJobOptionsValueByName("testName", "on-file-change"), "sync",
                "option on file change is not equal to 'sync'");
        Assert.assertEquals(runner.getJobOptionsValueByName("testName", "onfilechange-delay"), "10",
                "option on file change delay is not equal to '10' in command line keys");
    }

    @Description("The test checks that if user selected OFC so on folder connect can not be selected")
    @Test
    public void onFileChangeSelectionDisableOnFolderConnectTest(){
        jobPage.openPage();
        JobEditForm jobForm = jobPage.createNewJob();
        Assert.assertFalse(jobForm.setJobNameAndDescr("testName", "")
                .clickAutoTabLink()
                .setSyncOnFileChangeCheckBox(true)
                .setOnFolderConnectCheckBox(true)
                .getOnFolderConnectCheckBox()
                .isSelected());
    }

    @Description("The test checks that if on folder connect was selected selection OFC will disable on folder connect")
    @Test
    public void onFileChangeSelectionResetOnFolderConnectTest(){
        jobPage.openPage();
        JobEditForm jobForm = jobPage.createNewJob();
        Assert.assertFalse(jobForm.setJobNameAndDescr("testName", "")
                .clickAutoTabLink()
                .setOnFolderConnectCheckBox(true)
                .setSyncOnFileChangeCheckBox(true)
                .getOnFolderConnectCheckBox()
                .isSelected());
    }

    @Description("The tests checks that max time to run can not be empty value")
    @Test
    public void maxTimeToRunCanNotBeEmptyValueTest(){
        jobPage.openPage();
        JobEditForm jobForm = jobPage.createNewJob();
        jobForm.setJobNameAndDescr("testName", "")
                .clickGeneralTabLink()
                .setMaxTimeToRunCheckBox(true)
                .setMaxTimeToRunToValue("");
        jobForm.saveJob();
        Assert.assertTrue(jobForm.isTextPresent("This field is required."),
                "Warning message 'This field is required.' not found");
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
