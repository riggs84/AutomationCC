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

    @FindBy(xpath = ".//*[@id='tab-general']/div/div[2]/div/label/span/span")
    CheckBox propagateDeletionsCheckBox;

    @FindBy(xpath = ".//*[@id='tab-general']/div/div[3]/div/label/span/span")
    CheckBox crtLeftOrRightFolderIfNotFoundCheckBox;

    @FindBy(xpath = ".//*[@id='tab-general']/div/div[4]/div[1]/div/div/div/label/span/span")
    CheckBox saveDelOrReplacedFilesLastVerOnlyCheckBox;

    @FindBy(xpath = ".//*[@id='tab-general']/div/div[4]/div[2]/div[1]/div/label/span/span")
    CheckBox clnSavedFolderAfterThisManyDaysCheckBox;

    @FindBy(xpath = ".//*[@id='tab-general']//div[@class='inline']//input[@name='days-prev-version']")
    InputField clnSavedFolderAfterThisManyDaysInputField;

    @FindBy(xpath = ".//*[@id='tab-general']/div/div[4]/div[3]/div/div/div/label/span/span")
    CheckBox saveDelOrReplacedFilesMultiplyVerCheckBox;

    @FindBy(xpath = ".//*[@id='tab-general']/div/div[4]/div[4]/div/div[1]/div/label/span/span")
    CheckBox clnHistoryFolderAfterThisManyDaysCheckBox;

    @FindBy(xpath = ".//*[@id='tab-general']//div[@class='inline']//input[@name='days-past-version']")
    InputField clnHistoryFolderAfterThisManyDaysInputField;

    @FindBy(xpath = ".//*[@id='tab-general']//div[@class='inline']//input[@name='reconnect-secs']")
    InputField totalSecForReconnnectAttemptInputField;

    @FindBy(xpath = ".//*[@id='tab-general']/div/div[4]/div[6]/div[1]/div/label/span/span")
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
        return this;
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
