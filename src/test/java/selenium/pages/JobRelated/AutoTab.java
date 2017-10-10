package selenium.pages.JobRelated;

import org.openqa.selenium.support.FindBy;
import selenium.Elements.CheckBox;
import selenium.Elements.InputField;
import selenium.Elements.Selection;
import selenium.Helpers.BasePageClass;

public class AutoTab extends BasePageClass {

    @FindBy(xpath = ".//*[@id='tab-auto']/div/div[1]/div[1]/div/label/span/span")
    CheckBox syncOnFileChangeCheckBox;

    @FindBy(xpath = ".//*[@id='tab-auto']/div/div[2]/div/div/div/label/span/span")
    CheckBox onFolderConnectCheckBox;

    @FindBy(xpath = ".//*[@id='tab-auto']/div/div[3]/div/div[1]/div/label/span/span")
    CheckBox periodicallyCheckBox;

    @FindBy(xpath = ".//*[@id='tab-auto']/div/div[4]/div/label/span/span")
    CheckBox onScheduleCheckBox;

    @FindBy(xpath = ".//*[@id='tab-auto']//input[@name='onfilechange-delay']")
    InputField onFileChangeDelayInputField;

    @FindBy(xpath = ".//*[@id='tab-auto']//input[@name='timer-period']")
    InputField periodicallyTimerInputField;

    @FindBy(xpath = ".//*[@id='tab-auto']//input[@name='cb-limit-changes']/span")
    CheckBox notSyncIfChangesMoreThatCheckBox;

    @FindBy(xpath = ".//*[@id='tab-auto']//input[@name='limit-changes']")
    InputField notSyncChangesInputField;

    @FindBy(xpath = ".//*[@id='tab-auto']//label[contains(text(),'Wait for Locks to clear')]")
    CheckBox waitForLockToClearCheckBox;

    @FindBy(xpath = ".//*[@id='tab-auto']//input[@name='wait-for-locks-minutes']")
    InputField waitForLocksInputField;

    @FindBy(xpath = ".//*[@id='tab-auto']//select[@name='autoresolve']")
    Selection autoResolveConflictsSelect;

    @FindBy(xpath = ".//*[@id='tab-auto']/div/div[7]/div/label/span/span")
    CheckBox renameLosingFileNotDelCheckBox;

    public AutoTab(){
        super();
    }

    public AutoTab setSyncOnFileChangeCheckBox(boolean value){
        syncOnFileChangeCheckBox.setCheckbox(value);
        return this;
    }

    public AutoTab setOnFolderConnectCheckBox(boolean value){
        onFolderConnectCheckBox.setCheckbox(value);
        return this;
    }

    public AutoTab setPeriodicallyCheckBox(boolean value){
        periodicallyCheckBox.setCheckbox(value);
        return this;
    }

    public AutoTab setOnScheduleCheckBox(boolean value){
        onScheduleCheckBox.setCheckbox(value);
        return this;
    }

    public AutoTab setWaitForLockToClearCheckBox(boolean value){
        waitForLockToClearCheckBox.setCheckbox(value);
        return this;
    }

    public AutoTab setNotSyncIfChangesMoreThanCheckBox(boolean value){
        notSyncIfChangesMoreThatCheckBox.setCheckbox(value);
        return this;
    }

    public AutoTab setRenameLosingFileNotDeleteCheckBox(boolean value){
        renameLosingFileNotDelCheckBox.setCheckbox(value);
        return this;
    }

    public AutoTab setAutoResolveConflicts(String nameFromList){
        autoResolveConflictsSelect.selectByVisibleText(nameFromList);
        return this;
    }

    public AutoTab setOFCdelayFieldToValue(String seconds){
        onFileChangeDelayInputField.inputText(seconds);
        return this;
    }

    public AutoTab setPeriodicallyTimerFieldToValue(String minutes){
        periodicallyTimerInputField.inputText(minutes);
        return this;
    }

    public AutoTab setNotSyncChangesFieldToValue(String percentage){
        notSyncChangesInputField.inputText(percentage);
        return this;
    }

    public AutoTab setWaitForLocksFieldToValue(String minutes){
        waitForLocksInputField.inputText(minutes);
        return this;
    }
}
