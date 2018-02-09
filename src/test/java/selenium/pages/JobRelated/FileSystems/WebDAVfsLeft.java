package selenium.pages.JobRelated.FileSystems;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import selenium.Elements.CheckBox;
import selenium.Elements.InputField;
import selenium.webtestbase.BasePageClass;

public class WebDAVfsLeft extends BasePageClass {

    @FindBy(xpath = ".//*[@id='div-folder-manual1']//input[@name='f1']")
    InputField fsPathInputField;

    @FindBy(xpath = ".//*[@id='txt_username-left']//input[@name='userid1']")
    InputField fsUserNameInputField;

    @FindBy(xpath = ".//*[@id='txt_password-left']//input[@name='password1']")
    InputField fsPasswordInputField;

    @FindBy(id = "security_mode1")
    CheckBox secureModeCheckBox;

    @FindBy(name = "wininet1")
    CheckBox winInetCheckBox;

    @FindBy(name = "bad-certs1")
    CheckBox dontCheckSSLcertCheckBox;

    @FindBy(xpath = ".//*[@id='webdav-left']//input[@name='pk1']")
    InputField certificatePathInputField;

    @FindBy(name = "useproxy1")
    CheckBox connectViaProxy;

    public WebDAVfsLeft(){
        super();
    }

    public WebDAVfsLeft setConnectoidConfig(String path, String userName, String pass){
        fsPathInputField.inputText(path);
        fsUserNameInputField.inputText(userName);
        fsPasswordInputField.inputText(pass);
        return this;
    }

    public WebDAVfsLeft setCertificatePath(String path){
        certificatePathInputField.inputText(path);
        return this;
    }

    public CheckBox getSecureModeCheckBox() {
        return secureModeCheckBox;
    }

    public CheckBox getWinInetCheckBox() {
        return winInetCheckBox;
    }

    public CheckBox getDontCheckSSLcertCheckBox() {
        return dontCheckSSLcertCheckBox;
    }

    public CheckBox getConnectViaProxy() {
        return connectViaProxy;
    }
}
