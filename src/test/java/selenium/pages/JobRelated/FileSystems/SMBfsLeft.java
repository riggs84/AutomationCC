package selenium.pages.JobRelated.FileSystems;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import selenium.Elements.CheckBox;
import selenium.Elements.InputField;
import selenium.Helpers.BasePageClass;

public class SMBfsLeft extends BasePageClass{

    @FindBy(xpath = ".//*[@id='div-folder-manual1']//input[@name='f1']")
    InputField fsPathInputField;

    @FindBy(xpath = ".//*[@id='txt_username-left']//input[@name='userid1']")
    InputField fsUserNameInputField;

    @FindBy(xpath = ".//*[@id='txt_password-left']//input[@name='password1']")
    InputField fsPasswordInputField;

    @FindBy(xpath = ".//*[@id='panel-left-advanced-container']/div[@data-target='#panel-left-advanced']")
    WebElement advanced;

    @FindBy(xpath = ".//*[@id='windows-shares-left']/div/div/div/label/span/span")
    CheckBox fatSystemCheckBox;

    public SMBfsLeft(){
        super();
    }

    public SMBfsLeft setConnectoidConfig(String path, String userName, String password, boolean fatSystem){
        fsPathInputField.inputText(path);
        advanced.click();
        fsUserNameInputField.inputText(userName);
        fsPasswordInputField.inputText(password);
        fatSystemCheckBox.setCheckbox(fatSystem);
        return this;
    }
}
