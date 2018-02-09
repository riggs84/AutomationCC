package selenium.pages.JobRelated.FileSystems;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import selenium.Elements.CheckBox;
import selenium.Elements.InputField;
import selenium.webtestbase.BasePageClass;

public class WinAzureFSleft extends BasePageClass {

    @FindBy(xpath = ".//*[@id='div-folder-manual1']//input[@name='f1']")
    InputField fsPathInputField;

    @FindBy(xpath = ".//*[@id='txt_username-left']//input[@name='userid1']")
    InputField fsUserNameInputField;

    @FindBy(xpath = ".//*[@id='txt_password-left']//input[@name='password1']")
    InputField fsPasswordInputField;

    @FindBy(id = "security_mode1")
    CheckBox secureModeCheckBox;

    @FindBy(name = "useproxy1")
    CheckBox connectViaProxyCheckBox;

    public WinAzureFSleft(){
        super();
    }

    public WinAzureFSleft setConnectoidConfig(String path, String userName, String pass){
        fsPathInputField.inputText(path);
        fsUserNameInputField.inputText(userName);
        fsPasswordInputField.inputText(pass);
        return this;
    }

    public CheckBox getSecureModeCheckBox() {
        return secureModeCheckBox;
    }

    public CheckBox getConnectViaProxyCheckBox() {
        return connectViaProxyCheckBox;
    }
}
