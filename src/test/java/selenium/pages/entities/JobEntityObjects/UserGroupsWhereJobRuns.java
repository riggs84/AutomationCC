package selenium.pages.entities.JobEntityObjects;

import org.openqa.selenium.support.FindBy;
import selenium.Elements.Button;
import selenium.Elements.InputField;
import selenium.Elements.Table;
import selenium.webtestsbase.BasePageClass;

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
}
