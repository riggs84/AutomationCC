package selenium.pages.JobRelated.FileSystems;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import selenium.Elements.CheckBox;
import selenium.Elements.InputField;
import selenium.webtestbase.BasePageClass;

public class SFTPfsRight extends BasePageClass {

    @FindBy(xpath = ".//*[@id='div-folder-manual2']//input[@name='f2']")
    InputField fsPathInputField;

    @FindBy(xpath = ".//*[@id='txt_username-right']//input[@name='userid2']")
    InputField fsUserNameInputField;

    @FindBy(xpath = ".//*[@id='txt_password-right']//input[@name='password2']")
    InputField fsPasswordInputField;

    @FindBy(xpath = ".//*[@id='panel-right-advanced-container']/div[@data-target='#panel-right-advanced']")
    WebElement advanced;

    @FindBy(xpath = ".//*[@id='cb_utf_8_filenames-right']/div/label/span/span")
    CheckBox useUTF8CheckBox;

    public SFTPfsRight(){
        super();
    }

    public SFTPfsRight setConnectoidConfig(String path, String userName, String password, boolean utf8){
        fsPathInputField.inputText(path);
        advanced.click();
        fsUserNameInputField.inputText(userName);
        fsPasswordInputField.inputText(password);
        useUTF8CheckBox.setCheckbox(utf8);
        return this;
    }
}
