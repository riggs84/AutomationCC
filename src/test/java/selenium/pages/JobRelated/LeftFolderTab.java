package selenium.pages.JobRelated;

import jdk.internal.util.xml.impl.Input;
import org.openqa.selenium.support.FindBy;
import selenium.Elements.Button;
import selenium.Elements.CheckBox;
import selenium.Elements.InputField;
import selenium.Elements.Selection;
import selenium.webtestsbase.BasePageClass;

public class LeftFolderTab extends BasePageClass {

    @FindBy(xpath = ".//*[@id='tab-left-folder']//label/span[@class='toggle']")
    Button useGSaccountBtn;

    @FindBy(id = "cbFileSystem1")
    Selection fileSystemSelect;

    @FindBy(xpath = ".//*[@id='div-folder-manual1']//input[@name='f1']")
    InputField pathInputField;

    @FindBy(xpath = ".//*[@id='panel-left-folder-options']//label[contains(text(),'Safe Copy using temporary files')/span/span]")
    CheckBox safeCopyUsingTempFiles;

    @FindBy(xpath = ".//*[@id='panel-left-folder-options']//label[contains(text(),'No _gsdata_ folder here')/span/span]")
    CheckBox noGSDATAfolderHereCheckBox;

    @FindBy(xpath = ".//*[@id='panel-left-folder-options']//label[contains(text(),'Do not List Folders during analyze')/span/span]")
    CheckBox notListFoldersDuringAnalyzeCheckBox;

    @FindBy(xpath = ".//*[@id='panel-left-folder-options']//label[contains(text(),'Encrypt File Bodies')/span/span]")
    CheckBox encryptFileBodiesCheckBox;

    @FindBy(xpath = ".//*[@id='panel-left-folder-options']//label[contains(text(),'Encrypt File Names')/span/span]")
    CheckBox encryptFilenamesCheckBox;

    @FindBy(xpath = ".//*[@id='panel-left-folder-options']//input[@name='encrypt-password1']")
    InputField encryptPasswordInputField;

    Connectoids connectoids;

    public LeftFolderTab(){
        super();
    }
}
