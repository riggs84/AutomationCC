package selenium.pages.JobRelated;

import org.openqa.selenium.support.FindBy;
import selenium.Elements.CheckBox;
import selenium.Elements.InputField;
import selenium.Elements.Selection;
import selenium.webtestbase.BasePageClass;

public class GeneralTab extends BasePageClass {

    @FindBy(id = "cb-job-direction")
    Selection jobTypeAndDirectionSelect;

    public CheckBox getPropagateDeletionsCheckBox() {
        return propagateDeletionsCheckBox;
    }

    @FindBy(xpath = ".//*[@name='deletions']")//(xpath = ".//*[@id='tab-general']/div/div[2]/div/label/span/span")
    CheckBox propagateDeletionsCheckBox;

    public CheckBox getCrtLeftOrRightFolderIfNotFoundCheckBox() {
        return crtLeftOrRightFolderIfNotFoundCheckBox;
    }

    @FindBy(xpath = ".//*[@name='create-if-not-found']")
    CheckBox crtLeftOrRightFolderIfNotFoundCheckBox;

    public CheckBox getSaveDelOrReplacedFilesLastVerOnlyCheckBox() {
        return saveDelOrReplacedFilesLastVerOnlyCheckBox;
    }

    @FindBy(xpath = ".//*[@name='save-prev-version']")
    CheckBox saveDelOrReplacedFilesLastVerOnlyCheckBox;

    public CheckBox getClnSavedFolderAfterThisManyDaysCheckBox() {
        return clnSavedFolderAfterThisManyDaysCheckBox;
    }

    @FindBy(xpath = ".//*[@name='cleanup-prev-version']")
    CheckBox clnSavedFolderAfterThisManyDaysCheckBox;

    @FindBy(xpath = ".//*[@id='tab-general']//div[@class='inline']//input[@name='days-prev-version']")
    InputField clnSavedFolderAfterThisManyDaysInputField;

    public CheckBox getSaveDelOrReplacedFilesMultiplyVerCheckBox() {
        return saveDelOrReplacedFilesMultiplyVerCheckBox;
    }

    @FindBy(xpath = ".//*[@name='save-past-versions']")
    CheckBox saveDelOrReplacedFilesMultiplyVerCheckBox;

    @FindBy(xpath = ".//*[@name='cleanup-past-version']")
    CheckBox clnHistoryFolderAfterThisManyDaysCheckBox;

    @FindBy(xpath = ".//*[@id='tab-general']//div[@class='inline']//input[@name='days-past-version']")
    InputField clnHistoryFolderAfterThisManyDaysInputField;

    @FindBy(xpath = ".//*[@id='tab-general']//div[@class='inline']//input[@name='reconnect-secs']")
    InputField totalSecForReconnnectAttemptInputField;

    @FindBy(xpath = ".//*[@name='run-parallel-threads']")
    CheckBox runParallelThreadInSyncCheckBox;

    @FindBy(xpath = ".//*[@id='tab-general']//div[@class='inline']//input[@name='worker-threads']")
    InputField numberOfThreadsRunInParallelInputField;

    @FindBy(xpath = ".//*[@id='tab-general']//input[@name='speed-limit']")
    InputField speedLimitDownInputField;

    @FindBy(xpath = ".//*[@id='tab-general']//input[@name='up-speed-limit']")
    InputField speedLimitUpInputField;

    public GeneralTab(){
        super();
    }

    public GeneralTab setJobType(String nameFromList){
        jobTypeAndDirectionSelect.selectByVisibleText(nameFromList);
        return new GeneralTab();
    }

    public GeneralTab setPropagateDeletionsCheckBoxToValue(boolean value){
        propagateDeletionsCheckBox.setCheckbox(value);
        return this;
    }

    public GeneralTab setCreateFolderIfNotFoundCheckBoxToValue(boolean value){
        crtLeftOrRightFolderIfNotFoundCheckBox.setCheckbox(value);
        return this;
    }

    public GeneralTab setSaveDelOrReplacedLastVerOnlyCheckBoxToValue(boolean value){
        saveDelOrReplacedFilesLastVerOnlyCheckBox.setCheckbox(value);
        return this;
    }

    public GeneralTab setCleanSavedFolderAfterManyDaysCheckBoxToValue(boolean value){
        clnSavedFolderAfterThisManyDaysCheckBox.setCheckbox(value);
        return this;
    }

    public GeneralTab setCleanSavedFolderAfterManyDaysFieldToValue(String days){
        clnSavedFolderAfterThisManyDaysInputField.inputText(days);
        return this;
    }

    public GeneralTab setSaveDelOrReplacedFilesMultiVerCheckBoxToValue(boolean value){
        saveDelOrReplacedFilesMultiplyVerCheckBox.setCheckbox(value);
        return this;
    }

    public GeneralTab setCleanHistoryFolderAfteManyDaysCheckBoxTovalue(boolean value){
        clnHistoryFolderAfterThisManyDaysCheckBox.setCheckbox(value);
        return this;
    }

    public GeneralTab setCleanHistoryFolderAfterDaysInputFieldToValue(String days){
        clnHistoryFolderAfterThisManyDaysInputField.inputText(days);
        return this;
    }

    public GeneralTab setTotalSecondsToReconnectAttemptInputFieldToValue(String seconds){
        totalSecForReconnnectAttemptInputField.inputText(seconds);
        return this;
    }

    public GeneralTab setRunParallelThreadsCheckBoxToValue(boolean value){
        runParallelThreadInSyncCheckBox.setCheckbox(value);
        return this;
    }

    public GeneralTab setNumberOfThreadsToRunInParallelInputFieldToValue(String number){
        numberOfThreadsRunInParallelInputField.inputText(number);
        return this;
    }

    public GeneralTab setSpeedLimitDownToValue(String speed){
        speedLimitDownInputField.inputText(speed);
        return this;
    }

    public GeneralTab setSpeedLimitUpToValue(String speed){
        speedLimitUpInputField.inputText(speed);
        return this;
    }

    public GeneralTab setPropagateDelCheckBox(boolean val){
        propagateDeletionsCheckBox.setCheckbox(val);
        return this;
    }



}
