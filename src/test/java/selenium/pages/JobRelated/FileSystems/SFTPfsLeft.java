package selenium.pages.JobRelated.FileSystems;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import selenium.Elements.CheckBox;
import selenium.Elements.InputField;
import selenium.webtestbase.BasePageClass;

public class SFTPfsLeft extends BasePageClass {

    @FindBy(xpath = ".//*[@id='div-folder-manual1']//input[@name='f1']")
    InputField fsPathInputField;

    @FindBy(xpath = ".//*[@id='txt_username-left']//input[@name='userid1']")
    InputField fsUserNameInputField;

    @FindBy(xpath = ".//*[@id='txt_password-left']//input[@name='password1']")
    InputField fsPasswordInputField;

    @FindBy(name = "utf8-1")
    CheckBox useUTF8CheckBox;

    @FindBy(xpath = ".//*[@id='sftp-left']//input[@name='pk1']")
    InputField privateKeyInputField;

    public SFTPfsLeft(){
        super();
    }

    public SFTPfsLeft setConnectoidConfig(String path, String userName, String password){
        fsPathInputField.inputText(path);
        fsUserNameInputField.inputText(userName);
        fsPasswordInputField.inputText(password);
        return this;
    }

    public CheckBox getUseUTF8CheckBox() {
        return useUTF8CheckBox;
    }

    public SFTPfsLeft setPrivateKeyPath(String path){
        privateKeyInputField.inputText(path);
        return this;
    }
}
