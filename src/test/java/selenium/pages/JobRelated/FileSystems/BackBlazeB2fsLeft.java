package selenium.pages.JobRelated.FileSystems;

import org.openqa.selenium.support.FindBy;
import selenium.Elements.InputField;
import selenium.webtestbase.BasePageClass;

public class BackBlazeB2fsLeft extends BasePageClass {

    @FindBy(xpath = ".//*[@id='div-folder-manual1']//input[@name='f1']")
    InputField fsPathInputField;

    @FindBy(xpath = ".//*[@id='txt_username-left']//input[@name='userid1']")
    InputField fsUserNameInputField;

    @FindBy(xpath = ".//*[@id='txt_password-left']//input[@name='password1']")
    InputField fsPasswordInputField;

    public BackBlazeB2fsLeft(){
        super();
    }

    public BackBlazeB2fsLeft setConnectoidConfig(String path, String userName, String pass){
        fsPathInputField.inputText(path);
        fsUserNameInputField.inputText(userName);
        fsPasswordInputField.inputText(pass);
        return this;
    }
}
