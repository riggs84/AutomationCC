package selenium.pages.JobRelated;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import selenium.Elements.Button;
import selenium.Elements.CheckBox;
import selenium.Elements.InputField;
import selenium.Elements.Selection;
import selenium.pages.JobRelated.FileSystems.MyComputerFSleft;
import selenium.webtestbase.BasePageClass;

public class LeftFolderTab extends BasePageClass {

    @FindBy(xpath = ".//*[@id='tab-left-folder']//label/span[@class='toggle']")
    Button useGSaccountBtn;

    @FindBy(id = "cbFileSystem1")
    Selection fileSystemSelect;

    // What makes file differ container
    @FindBy(xpath = ".//*[@id='panel-left-differ-options-container']/div[1]")
    WebElement whatMakesFilesDiffer;

    // Folder options container
    @FindBy(xpath = ".//*[@id='panel-left-folder-options-container']/div[1]")
    WebElement folderOptions;

    @FindBy(xpath = ".//input[@name='compare-ext-attrs1']")
    CheckBox compareExtendedAttrMacOnlyCheckBox;

    @FindBy(xpath = ".//input[@name='compare-acl1']")
    CheckBox compareACLCheckBox;

    @FindBy(xpath = ".//input[@name='compare-owner1']")
    CheckBox compareOwnerCheckBox;

    @FindBy(xpath = ".//input[@name='compare-attrs1']")
    CheckBox compareAttrPermissionsOnMacCheckbox;

    @FindBy(xpath = ".//input[@name='compare-checksum1']")
    CheckBox compareChecksumsCheckBox;

    public CheckBox getCompareExtendedAttrMacOnlyCheckBox() {
        return compareExtendedAttrMacOnlyCheckBox;
    }

    public CheckBox getCompareACLCheckBox() {
        return compareACLCheckBox;
    }

    public CheckBox getCompareOwnerCheckBox() {
        return compareOwnerCheckBox;
    }

    public CheckBox getCompareAttrPermissionsOnMacCheckbox() {
        return compareAttrPermissionsOnMacCheckbox;
    }

    public CheckBox getCompareChecksumsCheckBox() {
        return compareChecksumsCheckBox;
    }

    public LeftFolderTab(){
        super();
    }

    public LeftFolderTab selectFileSystem(String fsName){
        fileSystemSelect.selectByVisibleText(fsName);
        return this;
    }

    public LeftFolderTab clickWhatMakesFilesDifferPanel(){
        whatMakesFilesDiffer.click();
        return this;
    }

    public LeftFolderTab clickFolderOptionsPanel(){
        folderOptions.click();
        return this;
    }


}
