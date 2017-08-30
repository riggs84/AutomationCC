package selenium.pages.JobRelated;

import org.openqa.selenium.support.FindBy;
import selenium.Elements.CheckBox;
import selenium.Elements.InputField;
import selenium.Elements.Selection;
import selenium.webtestsbase.BasePageClass;

public class AutoTab extends BasePageClass {

    @FindBy(xpath = ".//*[@id='tab-auto']//label[contains(text(),'Sync On File Change (*). Delay')]")
    CheckBox syncOnFileChangeCheckBox;

    @FindBy(xpath = ".//*[@id='tab-auto']//label[contains(text(),'On Folders Connect')]")
    CheckBox onFolderConnectCheckBox;

    @FindBy(xpath = ".//*[@id='tab-auto']//label[contains(text(),'Periodically. Every')]")
    CheckBox periodicallyCheckBox;

    @FindBy(xpath = ".//*[@id='tab-auto']//label[contains(text(),'On Schedule')]")
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

    @FindBy(xpath = ".//*[@id='tab-auto']//label[contains(text(),'Conflic Resolution: Rename Losing File, not Delete')]")
    CheckBox renameLosingFileNotDelCheckBox;


    public AutoTab(){
        super();
    }
}
