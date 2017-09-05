package selenium.pages.entities.JobEntityObjects;

import org.openqa.selenium.support.FindBy;
import selenium.Elements.*;
import selenium.pages.entities.Job;
import selenium.webtestsbase.BasePageClass;

public class SpecialOptions extends BasePageClass {

    @FindBy(xpath = ".//*[@id='runners-select-spop-frm']/div/div/div[2]/div[3]/div[1]/div[1]/label")
    CheckBox deleteStateFilesCheckBox;

    @FindBy(xpath = ".//*[@id='runners-select-spop-frm']/div/div/div[2]/div[3]/div[1]/div[2]/label")
    CheckBox deleteRecycledCheckBox;

    @FindBy(xpath = ".//*[@id='runners-select-spop-frm']/div/div/div[2]/div[3]/div[1]/div[3]/label")
    CheckBox cleanupRecycledCheckBox;

    @FindBy(xpath = ".//*[@id='runners-select-spop-frm']/div/div/div[2]/div[3]/div[1]/div[4]/div/div[1]/label")
    CheckBox deleteGSdataCheckBox;

    @FindBy(id = "select-delete-gs-data")
    Selection deleteGSdataSelection;

    @FindBy(className = "form-control input-sm\" placeholder=")
    InputField filterInputField;

    @FindBy(id = "tbl-select-runners-spop")
    Table table;

    @FindBy(xpath = ".//*[@id='runners-select-spop-frm']/div/div/div[3]/button[2]")
    Button saveBtn;

    @FindBy(xpath = ".//*[@id='runners-select-spop-frm']/div/div/div[3]/button[1]")
    Button cancelBtn;

    public SpecialOptions(){
        super();
    }

    public SpecialOptions setDeleteStateFilesCheckBoxTovalue(boolean value){
        deleteStateFilesCheckBox.setCheckbox(value);
        return this;
    }

    public SpecialOptions setDeleteRecycledCheckBoxToValue(boolean value){
        deleteRecycledCheckBox.setCheckbox(value);
        return this;
    }

    public SpecialOptions setCleanupRecycledCheckBoxToValue(boolean value){
        cleanupRecycledCheckBox.setCheckbox(value);
        return this;
    }

    public SpecialOptions setDeleteGSdataCheckBoxToValue(boolean value){
        deleteGSdataCheckBox.setCheckbox(value);
        return this;
    }

    public SpecialOptions setDeleteGSdataSelectionTovalue(String value){
        deleteGSdataSelection.selectByVisibleText(value);
        return this;
    }

    public SpecialOptions applyFilter(String filterRequest){
        filterInputField.inputText(filterRequest);
        return this;
    }

    public SpecialOptions selectRunnerInTable(String runnerName){
        table.selectElementCheckboxInTable(runnerName);
        return this;
    }

    public Job saveChanges(){
        saveBtn.click();
        return new Job();
    }
}
