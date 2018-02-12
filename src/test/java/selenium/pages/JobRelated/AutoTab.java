package selenium.pages.JobRelated;

import org.openqa.selenium.support.FindBy;
import selenium.Elements.CheckBox;
import selenium.Elements.InputField;
import selenium.Elements.Selection;
import selenium.webtestbase.BasePageClass;

public class AutoTab extends BasePageClass {

    @FindBy(xpath = ".//input[@name='on-file-change']")
    CheckBox syncOnFileChangeCheckBox;

    @FindBy(xpath = ".//input[@name='on-folder-connect']")
    CheckBox onFolderConnectCheckBox;

    @FindBy(xpath = ".//input[@name='on-timer']")
    CheckBox periodicallyCheckBox;

    @FindBy(xpath = ".//input[@name='on-schedule']")
    CheckBox onScheduleCheckBox;

    @FindBy(xpath = ".//*[@id='tab-auto']//input[@name='onfilechange-delay']")
    InputField onFileChangeDelayInputField;

    @FindBy(xpath = ".//*[@id='tab-auto']//input[@name='timer-period']")
    InputField periodicallyTimerInputField;

    @FindBy(xpath = ".//input[@name='cb-limit-changes']")
    CheckBox notSyncIfChangesMoreThatCheckBox;

    @FindBy(xpath = ".//*[@id='tab-auto']//input[@name='limit-changes']")
    InputField notSyncChangesInputField;

    @FindBy(xpath = ".//input[@name='wait-for-locks']")
    CheckBox waitForLockToClearCheckBox;

    @FindBy(xpath = ".//*[@id='tab-auto']//input[@name='wait-for-locks-minutes']")
    InputField waitForLocksInputField;

    @FindBy(xpath = ".//*[@id='tab-auto']//select[@name='autoresolve']")
    Selection autoResolveConflictsSelect;

    @FindBy(xpath = ".//input[@name='rename-losing-file']")
    CheckBox renameLosingFileNotDelCheckBox;

    @FindBy(xpath = ".//*[@id='container-crud']//table/tbody//input[@name='schedule-min']")
    InputField scheduleMinutesInputField;

    @FindBy(xpath = ".//*[@id='container-crud']//table/tbody//input[@name='schedule-dow']")
    InputField scheduleDayOfWeekInputField;

    @FindBy(xpath = ".//*[@id='container-crud']//table/tbody//input[@name='schedule-day']")
    InputField scheduleDayOfMonthInputField;

    @FindBy(xpath = ".//*[@id='container-crud']//table/tbody//input[@name='schedule-month']")
    InputField scheduleMonthInputField;

    @FindBy(xpath = "//input[@name='schedule-hour']")
    InputField scheduleHourInputField;

    public AutoTab(){
        super();
    }

    public AutoTab setScheduleMinutesToValue(String val){
        scheduleMinutesInputField.inputText(val);
        return this;
    }

    public AutoTab setScheduleHoursToValue(String val){
        scheduleHourInputField.inputText(val);
        return this;
    }

    public AutoTab setScheduleDayOfWeekToValue(String val){
        scheduleDayOfWeekInputField.inputText(val);
        return this;
    }

    public AutoTab setScheduleDayOfMonthToValue(String val){
        scheduleDayOfMonthInputField.inputText(val);
        return this;
    }

    public AutoTab setScheduleMonthToValue(String val){
        scheduleMonthInputField.inputText(val);
        return this;
    }

    public CheckBox getRenameLosingFileNotDelCheckBox() {
        return renameLosingFileNotDelCheckBox;
    }

    public CheckBox getWaitForLockToClearCheckBox() {
        return waitForLockToClearCheckBox;
    }

    public CheckBox getNotSyncIfChangesMoreThatCheckBox() {
        return notSyncIfChangesMoreThatCheckBox;
    }

    public CheckBox getSyncOnFileChangeCheckBox() {
        return syncOnFileChangeCheckBox;
    }

    public CheckBox getPeriodicallyCheckBox() {
        return periodicallyCheckBox;
    }

    public CheckBox getOnFolderConnectCheckBox() {
        return onFolderConnectCheckBox;
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
