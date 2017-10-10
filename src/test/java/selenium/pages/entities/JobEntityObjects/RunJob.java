package selenium.pages.entities.JobEntityObjects;

import org.openqa.selenium.support.FindBy;
import selenium.Elements.Button;
import selenium.Elements.InputField;
import selenium.Elements.Table;
import selenium.pages.entities.Job;
import selenium.webtestbase.BasePageClass;

public class RunJob extends BasePageClass {

    @FindBy(xpath = ".//*[@id='tbl-select-runners_filter']//input[@class='form-control input-sm']")
    InputField filterInputField;

    @FindBy(id = "tbl-select-runners")
    Table table;

    @FindBy(xpath = ".//*[@id='runners-select-frm']/div/div/div[3]/button[2]")
    Button saveBtn;

    @FindBy(xpath = ".//*[@id='runners-select-frm']/div/div/div[3]/button[1]")
    Button cancelBtn;

    public RunJob(){
        super();
    }

    public RunJob applyFilter(String filterRequest){
        filterInputField.inputText(filterRequest);
        return this;
    }

    public RunJob selectRunnerInTableByName(String runnerName){
        table.selectElementCheckboxInTable(runnerName);
        return this;
    }

    public Job saveChanges(){
        saveBtn.click();
        return new Job();
    }

}
