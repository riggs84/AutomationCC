package selenium.pages.JobRelated;

import org.openqa.selenium.support.FindBy;
import selenium.Elements.CheckBox;
import selenium.Elements.InputField;
import selenium.Elements.Selection;
import selenium.webtestsbase.BasePageClass;

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
}
