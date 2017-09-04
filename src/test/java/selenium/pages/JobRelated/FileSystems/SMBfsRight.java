package selenium.pages.JobRelated.FileSystems;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import selenium.Elements.CheckBox;
import selenium.Elements.InputField;
import selenium.webtestsbase.BasePageClass;

public class SMBfsRight extends BasePageClass {

    @FindBy(xpath = ".//*[@id='div-folder-manual2']//input[@name='f2']")
    InputField fsPathInputField;

    @FindBy(xpath = ".//*[@id='txt_username-right']//input[@name='userid2']")
    InputField fsUserNameInputField;

    @FindBy(xpath = ".//*[@id='txt_password-right']//input[@name='password2']")
    InputField fsPasswordInputField;

    @FindBy(xpath = ".//*[@id='panel-right-advanced-container']/div[@data-target='#panel-right-advanced']")
    WebElement advanced;

    @FindBy(xpath = ".//*[@id='windows-shares-right']/div/div/div/label/span/span")
    CheckBox fatSystemCheckBox;

    public SMBfsRight(){
        super();
    }

    public SMBfsRight setConnectoidConfig(String path, String userName, String password, boolean fatSystem){
        fsPathInputField.inputText(path);
        advanced.click();
        fsUserNameInputField.inputText(userName);
        fsPathInputField.inputText(password);
        fatSystemCheckBox.setCheckbox(fatSystem);
        return this;
    }
}
