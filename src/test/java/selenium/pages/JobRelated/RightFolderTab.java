package selenium.pages.JobRelated;

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

    @FindBy(xpath = ".//*[@id='div-folder-manual1']//input[@name='f1']")
    InputField pathInputField;

    @FindBy(xpath = ".//*[@id='panel-right-folder-options']/div/div/div/div[1]/div/label/span/span")
    CheckBox safeCopyUsingTempFiles;

    @FindBy(xpath = ".//*[@id='panel-right-folder-options']/div/div/div/div[2]/div/label/span/span")
    CheckBox noGSDATAfolderHereCheckBox;

    @FindBy(xpath = ".//*[@id='panel-right-folder-options']/div/div/div/div[3]/div/label/span/span")
    CheckBox notListFoldersDuringAnalyzeCheckBox;

    @FindBy(xpath = ".//*[@id='panel-right-folder-options']/div/div/div/div[4]/div/label/span/span")
    CheckBox encryptFileBodiesCheckBox;

    @FindBy(xpath = ".//*[@id='panel-right-folder-options']/div/div/div/div[5]/div/label/span/span")
    CheckBox encryptFilenamesCheckBox;

    @FindBy(xpath = ".//*[@id='panel-right-folder-options']//input[@name='encrypt-password1']")
    InputField encryptPasswordInputField;

    public RightFolderTab(){
        super();
    }

    public RightFolderTab selectFileSystem(String fsName){
        fileSystemSelect.selectByVisibleText(fsName);
        return this;
    }

}
