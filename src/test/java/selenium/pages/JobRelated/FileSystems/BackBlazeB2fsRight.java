package selenium.pages.JobRelated.FileSystems;

import org.openqa.selenium.support.FindBy;
import selenium.Elements.InputField;
import selenium.webtestbase.BasePageClass;

public class BackBlazeB2fsRight extends BasePageClass {

    @FindBy(xpath = ".//*[@id='div-folder-manual2']//input[@name='f2']")
    InputField fsPathInputField;

    @FindBy(xpath = ".//*[@id='txt_username-right']//input[@name='userid2']")
    InputField fsUserNameInputField;

    @FindBy(xpath = ".//*[@id='txt_password-right']//input[@name='password2']")
    InputField fsPasswordInputField;

    public BackBlazeB2fsRight(){
        super();
    }

    public BackBlazeB2fsRight setConnectoidConfig(String path, String userName, String pass){
        fsPathInputField.inputText(path);
        fsUserNameInputField.inputText(userName);
        fsPasswordInputField.inputText(pass);
        return this;
    }
}
