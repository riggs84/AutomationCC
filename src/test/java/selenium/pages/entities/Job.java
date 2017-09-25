package selenium.pages.entities;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import selenium.Elements.Button;
import selenium.Elements.InputField;
import selenium.Elements.Link;
import selenium.Elements.Table;
import selenium.pages.JobRelated.JobEditForm;
import selenium.pages.entities.JobEntityObjects.*;
import selenium.webtestsbase.BasePageClass;
import selenium.webtestsbase.DriverFactory;

public class Job extends BasePageClass {
    //TODO i didn't implement computer groups table because i don't know is so crucial
    @FindBy(xpath = ".//*[@id='btn-edit-job']")
    Button editJobBtn;

    @FindBy(xpath = ".//*[@id='btn-job-clone']")
    Button cloneJobBtn;

    @FindBy(xpath = ".//*[@id='btn-send-spop']")
    Button specialOpsBtn;

    @FindBy(xpath = ".//*[@id='btn-job-run']")
    Button runJobBtn;

    @FindBy(xpath = ".//*[@id='a-show-job-cl']")
    Link commandLine;

    @FindBy(id = "tbl-jobruns-current")
    Table jobRunsInProgressTable;

    @FindBy(id = "tbl-jobruns")
    Table jobRunsHistoryTable;

    @FindBy(id = "tbl-job-users")
    Table jobUsersTable;

    @FindBy(id = "tbl-job-ugroups")
    Table jobUserGroupsTable;

    @FindBy(id = "tbl-job-computers")
    Table jobComputersTable;

    @FindBy(id = "tbl-job-cgroups")
    Table jobComputerGroups;

    @FindBy(id = "btn-select-users")
    Button editJobUsersListBtn;

    @FindBy(id = "btn-select-ugroups")
    Button editJobUserGroupsListBtn;

    @FindBy(id = "btn-select-computers")
    Button editJobComputerListBtn;

    @FindBy(id = "btn-select-cgroups")
    Button editJobComputerGroupsListBtn;

    @FindBy(xpath = "//div/form[@class='bootbox-form']/input")
    InputField newCloneJobnameInputField;

    @FindBy(xpath = "//body/div[12]/div/div/div[3]/button[2]")
    Button confirmCloneNewJobBtn;

    public Job(){
        super();
    }

    public JobEditForm clickEditJobButton(){
        editJobBtn.click();
        return new JobEditForm();
    }

    public UsersWhereJobRuns editUsersWhereJobRuns(){
        editJobUsersListBtn.click();
        return new UsersWhereJobRuns();
    }

    public UserGroupsWhereJobRuns editUserGroupsWhereJobRuns(){
        editJobUserGroupsListBtn.click();
        return new UserGroupsWhereJobRuns();
    }

    public ComputersWhereJobRuns editComputersWhereJobRuns(){
        editJobComputerListBtn.click();
        return new ComputersWhereJobRuns();
    }

    public ComputerGroupsWhereJobRuns editComputerGroupsWhereJobRuns(){
        editJobComputerGroupsListBtn.click();
        return new ComputerGroupsWhereJobRuns();
    }

    public String getColumnValueByNameFromRunsHistory(String entry, String column){
        return jobRunsHistoryTable.getCellValueBy(entry, column);
    }

    public void clickOnLinkInJobRunsHistoryTable(String linkName){
        jobRunsHistoryTable.clickOnTheLinkBy(linkName, linkName);
    }

    public void clickOnUserNameInUsersWhereJobRuns(String linkName){
        jobUsersTable.clickOnTheLinkBy(linkName, linkName);
    }

    public void clickOnUserGroupNameInUserGroupsWhereJobRuns(String linkName){
        jobUserGroupsTable.clickOnTheLinkBy(linkName, linkName);
    }

    public void clickOnComputerNameInComputersWhereJobRuns(String linkName){
        jobComputersTable.clickOnTheLinkBy(linkName, linkName);
    }

    public String getCommandLineString(){
        //TODO refactor it for more speed solution
        commandLine.clickLink();
        WebElement element = DriverFactory.getInstance().getDriver().findElement(By.xpath("html/body/div[12]/div/div/div[2]"));
        return element.getText();
    }

    public Job cloneJob(String newJobName){
        cloneJobBtn.click();
        newCloneJobnameInputField.inputText(newJobName);
        confirmCloneNewJobBtn.click();
        waitForJSload();
        return new Job();
    }

    public RunJob clickRunJobButton(){
        runJobBtn.click();
        return new RunJob();
    }

    public SpecialOptions clickSpecialOptionsButton(){
        specialOpsBtn.click();
        return new SpecialOptions();
    }

}
