package selenium.pages.JobRelated;

import org.openqa.selenium.support.FindBy;
import selenium.Elements.CheckBox;
import selenium.Elements.InputField;
import selenium.Elements.Selection;
import selenium.webtestsbase.BasePageClass;

public class GeneralTab extends BasePageClass {

    @FindBy(id = "cb-job-direction")
    Selection jobTypeAndDirectionSelect;

    @FindBy(xpath = ".//*[@id='tab-general']//label[contains(text(),'Propagate Deletions')]//span[@class='check']")
    CheckBox propagateDeletionsCheckBox;

    @FindBy(xpath = ".//*[@id='tab-general']//label[contains(text(),'Create left/right Sync folders if they are not found ')]//span[@class='check']")
    CheckBox crtLeftOrRightFolderIfNotFoundCheckBox;

    @FindBy(xpath = ".//*[@id='tab-general']//label[contains(text(),'Save deleted/replaced files, last version only')]//span[@class='check']")
    CheckBox saveDelOrReplacedFilesLastVerOnlyCheckBox;

    @FindBy(xpath = ".//*[@id='tab-general']//label[contains(text(),'Cleanup _saved_ folder after this many days')]//span[@class='check']")
    CheckBox clnSavedFolderAfterThisManyDaysCheckBox;

    @FindBy(xpath = ".//*[@id='tab-general']//div[@class='inline']//input[@name='days-prev-version']")
    InputField clnSavedFolderAfterThisManyDaysInputField;

    @FindBy(xpath = ".//*[@id='tab-general']//label[contains(text(),'Save deleted/replaced files, multiple versions')]//span[@class='check']")
    CheckBox saveDelOrReplacedFilesMultiplyVerCheckBox;

    @FindBy(xpath = ".//*[@id='tab-general']//label[contains(text(),'Cleanup _history_ folder after this may days')]//span[@class='check']")
    CheckBox clnHistoryFolderAfterThisManyDaysCheckBox;

    @FindBy(xpath = ".//*[@id='tab-general']//div[@class='inline']//input[@name='days-past-version']")
    InputField clnHistoryFolderAfterThisManyDaysInputField;

    @FindBy(xpath = ".//*[@id='tab-general']//div[@class='inline']//input[@name='reconnect-secs']")
    InputField totalSecForReconnnectAttemptInputField;

    @FindBy(xpath = ".//*[@id='tab-general']//label[contains(text(),'Run Parallel Threads in Sync, this many')]//span[@class='check']")
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

}
