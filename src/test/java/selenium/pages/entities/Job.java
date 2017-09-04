package selenium.pages.entities;

import org.openqa.selenium.support.FindBy;
import selenium.Elements.Button;
import selenium.Elements.Link;
import selenium.Elements.Table;
import selenium.webtestsbase.BasePageClass;

public class Job extends BasePageClass {

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

    public Job(){
        super();
    }
}
