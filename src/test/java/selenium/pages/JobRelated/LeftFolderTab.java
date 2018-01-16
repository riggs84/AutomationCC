package selenium.pages.JobRelated;

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

    @FindBy(xpath = ".//*[@id='div-folder-manual1']//input[@name='f1']")
    InputField pathInputField;

    @FindBy(xpath = ".//*[@id='panel-left-folder-options']/div/div/div/div[1]/div/label/span/span")
    CheckBox safeCopyUsingTempFiles;

    @FindBy(xpath = ".//*[@id='panel-left-folder-options']/div/div/div/div[2]/div/label/span/span")
    CheckBox noGSDATAfolderHereCheckBox;

    @FindBy(xpath = ".//*[@id='panel-left-folder-options']/div/div/div/div[3]/div/label/span/span")
    CheckBox notListFoldersDuringAnalyzeCheckBox;

    @FindBy(xpath = ".//*[@id='panel-left-folder-options']/div/div/div/div[4]/div/label/span/span")
    CheckBox encryptFileBodiesCheckBox;

    @FindBy(xpath = ".//*[@id='panel-left-folder-options']/div/div/div/div[5]/div/label/span/span")
    CheckBox encryptFilenamesCheckBox;

    @FindBy(xpath = ".//*[@id='panel-left-folder-options']//input[@name='encrypt-password1']")
    InputField encryptPasswordInputField;

    @FindBy(xpath = ".//*[@id='panel-left-differ-options-container']/div[")

    MyComputerFSleft myComputerFS;


    public LeftFolderTab(){
        super();
        myComputerFS = new MyComputerFSleft();
    }

    public LeftFolderTab selectFileSystem(String fsName){
        fileSystemSelect.selectByVisibleText(fsName);
        return this;
    }

    public LeftFolderTab setLeftSideConnectoidLocalFS(String path, boolean compressNTFS, boolean uncompressNTFS, boolean fatFS){
        myComputerFS.setConnectiodConfig(path, compressNTFS, uncompressNTFS, fatFS);
        return this;
    }

    public String getValueForPathField(){
        return pathInputField.getValue();
    }


}
