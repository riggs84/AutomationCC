package selenium.pages.JobRelated;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import selenium.Elements.Button;
import selenium.Elements.CheckBox;
import selenium.Elements.InputField;
import selenium.Elements.Selection;
import selenium.pages.JobRelated.FileSystems.MyComputerFSright;
import selenium.webtestbase.BasePageClass;

public class RightFolderTab extends BasePageClass {

    @FindBy(xpath = ".//*[@id='tab-right-folder']//label/span[@class='toggle']")
    Button useGSaccountBtn;

    @FindBy(id = "cbFileSystem2")
    Selection fileSystemSelect;

    // What makes file differ container
    @FindBy(xpath = ".//*[@id='panel-right-differ-options-container']/div[1]")
    WebElement whatMakesFilesDiffer;

    // Folder options container
    @FindBy(xpath = ".//*[@id='panel-right-folder-options-container']/div[1]")
    WebElement folderOptions;

    @FindBy(xpath = ".//input[@name='compare-ext-attrs2']")
    CheckBox compareExtendedAttrMacOnlyCheckBox;

    @FindBy(xpath = ".//input[@name='compare-acl2']")
    CheckBox compareACLCheckBox;

    @FindBy(xpath = ".//input[@name='compare-owner2']")
    CheckBox compareOwnerCheckBox;

    @FindBy(xpath = ".//input[@name='compare-attrs2']")
    CheckBox compareAttrPermissionsOnMacCheckbox;

    @FindBy(xpath = ".//input[@name='compare-checksum2']")
    CheckBox compareChecksumsCheckBox;

    public RightFolderTab(){
        super();
    }

    public RightFolderTab selectFileSystem(String fsName){
        fileSystemSelect.selectByVisibleText(fsName);
        return this;
    }

    public RightFolderTab clickWhatMakesFilesDifferPanel(){
        whatMakesFilesDiffer.click();
        return this;
    }

    public RightFolderTab clickFolderOptionsPanel(){
        folderOptions.click();
        return this;
    }

}
