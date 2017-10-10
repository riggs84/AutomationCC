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

    @FindBy(xpath = ".//*[@id='panel-left-advanced-container']/div[@data-target='#panel-left-advanced']")
    WebElement advanced;

    @FindBy(xpath = ".//*[@id='cb_utf_8_filenames-left']/div/label/span/span")
    CheckBox useUTF8CheckBox;

    public SFTPfsLeft(){
        super();
    }

    public SFTPfsLeft setConnectoidConfig(String path, String userName, String password, boolean utf8){
        fsPathInputField.inputText(path);
        advanced.click();
        fsUserNameInputField.inputText(userName);
        fsPasswordInputField.inputText(password);
        useUTF8CheckBox.setCheckbox(utf8);
        return this;
    }
}
