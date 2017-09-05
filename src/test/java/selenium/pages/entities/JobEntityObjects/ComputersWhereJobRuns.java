package selenium.pages.entities.JobEntityObjects;

import org.openqa.selenium.support.FindBy;
import selenium.Elements.Button;
import selenium.Elements.InputField;
import selenium.Elements.Table;
import selenium.pages.entities.Job;
import selenium.webtestsbase.BasePageClass;

public class ComputersWhereJobRuns extends BasePageClass {

    @FindBy(xpath = ".//*[@id='tbl-select-computers_filter']//input[@class='form-control input-sm']")
    InputField filterInputField;

    @FindBy(id = "tbl-select-computers")
    Table table;

    @FindBy(xpath = ".//*[@id='computers-select-frm']//div[@class='modal-footer']//button[1]")
    Button cancelBtn;

    @FindBy(xpath = ".//*[@id='computers-select-frm']//div[@class='modal-footer']//button[2]")
    Button saveBtn;

    public ComputersWhereJobRuns(){
        super();
    }

    public ComputersWhereJobRuns applyFilter(String filterRequest){
        filterInputField.inputText(filterRequest);
        return this;
    }

    public ComputersWhereJobRuns selectComputerInTable(String name){
        table.selectElementCheckboxInTable(name);
        return this;
    }

    public Job saveChanges(){
        saveBtn.click();
        return new Job();
    }
}
