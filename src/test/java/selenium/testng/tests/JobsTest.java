package selenium.testng.tests;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import selenium.pages.JobRelated.JobEditForm;
import selenium.pages.JobsPage;
import selenium.pages.LoginPage;
import selenium.webtestsbase.DriverFactory;

public class JobsTest {

    LoginPage loginPage;
    JobsPage jobPage;

    public JobsTest(){
        this.loginPage = new LoginPage();
        this.jobPage = new JobsPage();
    }

    @BeforeClass
    public void beforeClass(){
        loginPage.loginAs("viktor.iurkov@yandex.ru", "123456");
    }

    @Test
    public void crtNewJobTest(){
        jobPage.openPage();
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
