package selenium.pages.entities.JobEntityObjects;

import org.openqa.selenium.support.FindBy;
import selenium.Elements.Button;
import selenium.Elements.InputField;
import selenium.Elements.Table;
import selenium.webtestsbase.BasePageClass;

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
}
