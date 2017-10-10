package selenium.pages.entities.JobEntityObjects;

import org.openqa.selenium.support.FindBy;
import selenium.Elements.Button;
import selenium.Elements.InputField;
import selenium.Elements.Table;
import selenium.pages.entities.Job;
import selenium.Helpers.BasePageClass;

public class ComputerGroupsWhereJobRuns extends BasePageClass {

    @FindBy(xpath = ".//*[@id='tbl-select-cgroups_filter']//input[@class='form-control input-sm']")
    InputField filterInputField;

    @FindBy(id = "tbl-select-cgroups")
    Table table;

    @FindBy(xpath = ".//*[@id='cgroups-select-frm']//div[@class='modal-footer']//button[1]")
    Button cancelBtn;

    @FindBy(xpath = ".//*[@id='cgroups-select-frm']//div[@class='modal-footer']//button[2]")
    Button saveBtn;

    public ComputerGroupsWhereJobRuns(){
        super();
    }

    public ComputerGroupsWhereJobRuns applyFilter(String filterRequest){
        filterInputField.inputText(filterRequest);
        return this;
    }

    public ComputerGroupsWhereJobRuns selectComputerGroupInTable(String compGroup){
        table.selectElementCheckboxInTable(compGroup);
        return this;
    }

    public Job saveChanges(){
        saveBtn.click();
        return new Job();
    }
}
