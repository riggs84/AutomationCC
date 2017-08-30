package selenium.pages.JobRelated;

import org.openqa.selenium.support.FindBy;
import selenium.Elements.CheckBox;
import selenium.Elements.Selection;
import selenium.webtestsbase.BasePageClass;

public class AdvancedTab extends BasePageClass {

    @FindBy(xpath = ".//*[@id='tab-advanced']/div/div[1]/div/label/span/span")
    CheckBox copyFileCreateTimeCheckBox;

    @FindBy(xpath = ".//*[@id='tab-advanced']/div/div[2]/div/label/span/span")
    CheckBox copyAttrCheckBox;

    @FindBy(xpath = ".//*[@id='tab-advanced']/div/div[3]/div/label/span/span")
    CheckBox copyACLAttrCheckBox;

    @FindBy(xpath = ".//*[@id='tab-advanced']/div/div[4]/div/label/span/span")
    CheckBox detectACLOwnerChangesCheckBox;

    @FindBy(xpath = ".//*[@id='tab-advanced']/div/div[5]/div/label/span/span")
    CheckBox copeOwnerCheckBox;

    @FindBy(xpath = ".//*[@id='tab-advanced']/div/div[6]/div/label/span/span")
    CheckBox moveModeDelSourceCheckBox;

    @FindBy(xpath = ".//*[@id='tab-advanced']/div/div[7]/div/label/span/span")
    CheckBox moveModeDelEmptyCheckBox;

    @FindBy(xpath = ".//*[@id='tab-advanced']/div/div[8]/div/label/span/span")
    CheckBox copyLockedUsingVSSCheckBox;

    @FindBy(xpath = ".//*[@id='tab-advanced']/div/div[9]/div/label/span/span")
    CheckBox uberUnlockedModeCheckBox;

    @FindBy(xpath = ".//*[@id='tab-advanced']/div/div[10]/div/label/span/span")
    CheckBox retrySyncIfSourceChangesCheckBox;

    @FindBy(xpath = ".//*[@id='tab-advanced']/div/div[11]/div/label/span/span")
    CheckBox compareCheckSumsCheckBox;

    @FindBy(xpath = ".//*[@id='tab-advanced']/div/div[12]/div/label/span/span")
    CheckBox detectMovesAndRenamesCheckBox;

    @FindBy(xpath = ".//*[@id='tab-advanced']/div/div[13]/div/label/span/span")
    CheckBox estimateSpaceCheckBox;

    @FindBy(xpath = ".//*[@id='tab-advanced']/div/div[15]/div/label/span/span")
    CheckBox copyOriginalFSnamesCheckBox;

    @FindBy(xpath = ".//*[@id='tab-advanced']/div/div[16]/div/label/span/span")
    CheckBox copyExtendedAttrCheckBox;

    @FindBy(xpath = ".//*[@id='tab-advanced']/div/div[17]/div/label/span/span")
    CheckBox compareExtendedAttrCheckBox;

    @FindBy(xpath = ".//*[@id='tab-advanced']//select[@name='links']")
    Selection symbLinksAndJunctionsSelect;


    public AdvancedTab(){
        super();
    }
}
