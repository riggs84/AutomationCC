package selenium.pages.JobRelated;

import org.openqa.selenium.support.FindBy;
import selenium.Elements.CheckBox;
import selenium.Elements.InputField;
import selenium.Elements.Selection;
import selenium.webtestsbase.BasePageClass;

public class GeneralTab extends BasePageClass {

    @FindBy(id = "cb-job-direction")
    Selection jobTypeAndDirectionSelect;

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

}
