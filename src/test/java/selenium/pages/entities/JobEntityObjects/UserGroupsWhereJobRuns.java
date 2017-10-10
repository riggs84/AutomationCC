package selenium.pages.entities.JobEntityObjects;

import org.openqa.selenium.support.FindBy;
import selenium.Elements.Button;
import selenium.Elements.InputField;
import selenium.Elements.Table;
import selenium.pages.entities.Job;
import selenium.Helpers.BasePageClass;

public class UserGroupsWhereJobRuns extends BasePageClass {

    @FindBy(xpath = ".//*[@id='tbl-select-ugroups_filter']//div[@id='tbl-select-ugroups_filter']//input")
    InputField filterInputField;

    @FindBy(id = "tbl-select-ugroups")
    Table table;

    @FindBy(xpath = ".//*[@id='ugroups-select-frm']//div[@class='modal-footer']//button[1]")
    Button cancelBtn;

    @FindBy(xpath = ".//*[@id='ugroups-select-frm']//div[@class='modal-footer']//button[2]")
    Button saveBtn;

    public UserGroupsWhereJobRuns(){
        super();
    }

    public UserGroupsWhereJobRuns applyFilter(String request){
        filterInputField.inputText(request);
        return this;
    }

    public UserGroupsWhereJobRuns selectUserGroupInTable(String name){
        table.selectElementCheckboxInTable(name);
        return this;
    }

    public Job saveChanges(){
        saveBtn.click();
        return new Job();
    }
}
