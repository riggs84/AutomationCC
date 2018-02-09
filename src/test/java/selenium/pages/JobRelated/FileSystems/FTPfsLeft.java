package selenium.pages.JobRelated.FileSystems;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import selenium.Elements.CheckBox;
import selenium.Elements.InputField;
import selenium.webtestbase.BasePageClass;

public class FTPfsLeft extends BasePageClass {

    @FindBy(xpath = ".//*[@id='div-folder-manual1']//input[@name='f1']")
    InputField fsPathInputField;

    @FindBy(xpath = ".//*[@id='txt_username-left']//input[@name='userid1']")
    InputField fsUserNameInputField;

    @FindBy(xpath = ".//*[@id='txt_password-left']//input[@name='password1']")
    InputField fsPasswordInputField;

    @FindBy(id = "security_mode1")
    CheckBox secureModeCheckBox;

    @FindBy(name = "utf8-1")
    CheckBox utf8fileNamesCheckBox;

    @FindBy(xpath = ".//*[@name='useproxy1']")
    CheckBox connectViaProxyCheckBox;

    @FindBy(name = "active1")
    CheckBox activeFTPmodeCheckBox;

    @FindBy(name = "mlsd1")
    CheckBox useMLSD_MLSTcommandsCheckBox;

    @FindBy(name = "listla1")
    CheckBox useLISTcommandCheckBox;

    @FindBy(name = "bad-certs1")
    CheckBox dontCheckSSLCheckBox;

    @FindBy(name = "mdtm1")
    CheckBox useMDTMCheckBox;

    @FindBy(name = "implicit1")
    CheckBox implicitFTPScheckBox;

    @FindBy(name = "move-level-only1")
    CheckBox renameCheckBox;

    @FindBy(name = "tls-sess-reuse1")
    CheckBox requireTLScheckbox;

    public FTPfsLeft(){
        super();
    }

    public FTPfsLeft setConnectoidConfig(String path, String userName, String pass){
        fsPathInputField.inputText(path);
        fsUserNameInputField.inputText(userName);
        fsPasswordInputField.inputText(pass);
        return this;
    }

    public CheckBox getSecureModeCheckBox() {
        return secureModeCheckBox;
    }

    public CheckBox getUtf8fileNamesCheckBox() {
        return utf8fileNamesCheckBox;
    }

    public CheckBox getConnectViaProxyCheckBox() {
        return connectViaProxyCheckBox;
    }

    public CheckBox getActiveFTPmodeCheckBox() {
        return activeFTPmodeCheckBox;
    }

    public CheckBox getUseMLSD_MLSTcommandsCheckBox() {
        return useMLSD_MLSTcommandsCheckBox;
    }

    public CheckBox getUseLISTcommandCheckBox() {
        return useLISTcommandCheckBox;
    }

    public CheckBox getDontCheckSSLCheckBox() {
        return dontCheckSSLCheckBox;
    }

    public CheckBox getUseMDTMCheckBox() {
        return useMDTMCheckBox;
    }

    public CheckBox getImplicitFTPScheckBox() {
        return implicitFTPScheckBox;
    }

    public CheckBox getRenameCheckBox() {
        return renameCheckBox;
    }

    public CheckBox getRequireTLScheckbox() {
        return requireTLScheckbox;
    }
}
