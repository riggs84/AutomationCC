package selenium.pages.JobRelated;

import org.openqa.selenium.support.FindBy;
import selenium.Elements.CheckBox;
import selenium.Elements.Selection;
import selenium.webtestbase.BasePageClass;

public class AdvancedTab extends BasePageClass {

    @FindBy(xpath = ".//input[@name='copy-create-time']") ///div/div[1]/div/label/span/span")
    CheckBox copyFileCreateTimeCheckBox;

    @FindBy(xpath = ".//input[@name='copy-attrs']") //"/div/div[2]/div/label/span/span")
    CheckBox copyAttrCheckBox;

    @FindBy(xpath = ".//input[@name='copy-acl']")//div/div[3]/div/label/span/span")
    CheckBox copyACLAttrCheckBox;

    @FindBy(xpath = ".//input[@name='compare-acl']")//div/div[4]/div/label/span/span")
    CheckBox detectACLOwnerChangesCheckBox;

    @FindBy(xpath = ".//input[@name='copy-owner']")//div/div[5]/div/label/span/span")
    CheckBox copyOwnerCheckBox;

    @FindBy(xpath = ".//input[@name='move-mode']")//div/div[6]/div/label/span/span")
    CheckBox moveModeDelSourceCheckBox;

    @FindBy(xpath = ".//input[@name='delete-empty-folders-on-move']")//div/div[7]/div/label/span/span")
    CheckBox moveModeDelEmptyCheckBox;

    @FindBy(xpath = ".//input[@name='copy-locked']")//div/div[8]/div/label/span/span")
    CheckBox copyLockedUsingVSSCheckBox;

    @FindBy(xpath = ".//input[@name='uber-unlocked']")//div/div[9]/div/label/span/span")
    CheckBox uberUnlockedModeCheckBox;

    @FindBy(xpath = ".//input[@name='retry-sync-if-file-changes']")//div/div[10]/div/label/span/span")
    CheckBox retrySyncIfSourceChangesCheckBox;

    @FindBy(xpath = ".//input[@name='compare-md5']")//div/div[11]/div/label/span/span")
    CheckBox compareCheckSumsCheckBox;

    @FindBy(xpath = ".//input[@name='detect-moves']")//div/div[12]/div/label/span/span")
    CheckBox detectMovesAndRenamesCheckBox;

    @FindBy(xpath = ".//input[@name='est-req-space']")//div/div[13]/div/label/span/span")
    CheckBox estimateSpaceCheckBox;

    @FindBy(xpath = ".//input[@name='copy-original-fs-names']")//div/div[15]/div/label/span/span")
    CheckBox copyOriginalFSnamesCheckBox;

    @FindBy(xpath = ".//input[@name='copy-ext-attrs']")//div/div[16]/div/label/span/span")
    CheckBox copyExtendedAttrCheckBox;

    @FindBy(xpath = ".//input[@name='compare-ext-attrs']")//div/div[17]/div/label/span/span")
    CheckBox compareExtendedAttrCheckBox;

    @FindBy(xpath = ".//*[@id='tab-advanced']//select[@name='links']")
    Selection symbLinksAndJunctionsSelect;

    public AdvancedTab(){
        super();
    }

    public CheckBox getCopyFileCreateTimeCheckBox() {
        return copyFileCreateTimeCheckBox;
    }

    public CheckBox getCopyAttrCheckBox() {
        return copyAttrCheckBox;
    }

    public CheckBox getCopyACLAttrCheckBox() {
        return copyACLAttrCheckBox;
    }

    public CheckBox getDetectACLOwnerChangesCheckBox() {
        return detectACLOwnerChangesCheckBox;
    }

    public CheckBox getCopyOwnerCheckBox() {
        return copyOwnerCheckBox;
    }

    public CheckBox getMoveModeDelSourceCheckBox() {
        return moveModeDelSourceCheckBox;
    }

    public CheckBox getMoveModeDelEmptyCheckBox() {
        return moveModeDelEmptyCheckBox;
    }

    public CheckBox getCopyLockedUsingVSSCheckBox() {
        return copyLockedUsingVSSCheckBox;
    }

    public CheckBox getUberUnlockedModeCheckBox() {
        return uberUnlockedModeCheckBox;
    }

    public CheckBox getRetrySyncIfSourceChangesCheckBox() {
        return retrySyncIfSourceChangesCheckBox;
    }

    public CheckBox getCompareCheckSumsCheckBox() {
        return compareCheckSumsCheckBox;
    }

    public CheckBox getDetectMovesAndRenamesCheckBox() {
        return detectMovesAndRenamesCheckBox;
    }

    public CheckBox getEstimateSpaceCheckBox() {
        return estimateSpaceCheckBox;
    }

    public CheckBox getCopyOriginalFSnamesCheckBox() {
        return copyOriginalFSnamesCheckBox;
    }

    public CheckBox getCopyExtendedAttrCheckBox() {
        return copyExtendedAttrCheckBox;
    }

    public CheckBox getCompareExtendedAttrCheckBox() {
        return compareExtendedAttrCheckBox;
    }

    public AdvancedTab setCopyFileCreateTimeCheckBoxToValue(boolean value){
        copyFileCreateTimeCheckBox.setCheckbox(value);
        return this;
    }

    public AdvancedTab setCopyAttrCheckBoxToValue(boolean value){
        copyAttrCheckBox.setCheckbox(value);
        return this;
    }

    public AdvancedTab setCopyAclCheckBoxToValue(boolean value){
        copyACLAttrCheckBox.setCheckbox(value);
        return this;
    }

    public AdvancedTab setDetectAclOrOwnerChangeCheckBoxToValue(boolean value){
        detectACLOwnerChangesCheckBox.setCheckbox(value);
        return this;
    }

    public AdvancedTab setCopyOwnerCheckBoxToValue(boolean value){
        copyOwnerCheckBox.setCheckbox(value);
        return this;
    }

    public AdvancedTab setMoveModeDeleteSourceAfterCopyCheckBoxToValue(boolean value){
        moveModeDelSourceCheckBox.setCheckbox(value);
        return this;
    }

    public AdvancedTab setMoveModeDeleteEmptyFoldersCheckBoxToValue(boolean value){
        moveModeDelEmptyCheckBox.setCheckbox(value);
        return this;
    }

    public AdvancedTab setCopyLockedUsingVSSCheckBoxToValue(boolean value){
        copyLockedUsingVSSCheckBox.setCheckbox(value);
        return this;
    }

    public AdvancedTab setUberUnlockedModeCheckBoxToValue(boolean value){
        uberUnlockedModeCheckBox.setCheckbox(value);
        return this;
    }

    public AdvancedTab setRetrySyncIfSourceChangeCheckBoxToValue(boolean value){
        retrySyncIfSourceChangesCheckBox.setCheckbox(value);
        return this;
    }

    public AdvancedTab setCompareCheckSumCheckBoxToValue(boolean value){
        compareCheckSumsCheckBox.setCheckbox(value);
        return this;
    }

    public AdvancedTab setDetectMoveAndRenameCheckBoxToValue(boolean value){
        detectMovesAndRenamesCheckBox.setCheckbox(value);
        return this;
    }

    public AdvancedTab setEstimateSpaceCheckBoxToValue(boolean value){
        estimateSpaceCheckBox.setCheckbox(value);
        return this;
    }

    public AdvancedTab setCopyOriginalFSnamesCheckBoxToValue(boolean value){
        copyOriginalFSnamesCheckBox.setCheckbox(value);
        return this;
    }

    public AdvancedTab setCopyExtendedAttrCheckBoxToValue(boolean value){
        copyExtendedAttrCheckBox.setCheckbox(value);
        return this;
    }

    public AdvancedTab setCompareExtendedAttrCheckBoxToValue(boolean value){
        compareExtendedAttrCheckBox.setCheckbox(value);
        return this;
    }

    public AdvancedTab setSymbolicLinkAndJunctionSelectionToValue(String nameFromList){
        symbLinksAndJunctionsSelect.selectByVisibleText(nameFromList);
        return this;
    }
}
