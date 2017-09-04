package selenium.pages.JobRelated.FileSystems;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import selenium.Elements.CheckBox;
import selenium.Elements.InputField;
import selenium.webtestsbase.BasePageClass;

public class WinAzureFSleft extends BasePageClass {

    @FindBy(xpath = ".//*[@id='div-folder-manual1']//input[@name='f1']")
    InputField fsPathInputField;

    @FindBy(xpath = ".//*[@id='txt_username-left']//input[@name='userid1']")
    InputField fsUserNameInputField;

    @FindBy(xpath = ".//*[@id='txt_password-left']//input[@name='password1']")
    InputField fsPasswordInputField;

    @FindBy(xpath = ".//*[@id='cb_security_mode-left']/div/label/span/span")
    CheckBox secureModeCheckBox;

    @FindBy(xpath = ".//*[@id='panel-left-advanced-container']/div[@data-target='#panel-left-advanced']")
    WebElement advanced;

    @FindBy(xpath = ".//*[@id='windowsazure-left']/div/div[1]/div/label/span/span")
    CheckBox winInetbasedCheckBox;

    @FindBy(xpath = ".//*[@id='windowsazure-left']/div/div[2]/div/label/span/span")
    CheckBox connectViaProxyCheckBox;

    public WinAzureFSleft(){
        super();
    }

    public WinAzureFSleft setConnectoidConfig(String path, String userName, String pass, boolean secureMode,
                                              boolean winInet, boolean connViaProxy){
        fsPathInputField.inputText(path);
        fsUserNameInputField.inputText(userName);
        fsPasswordInputField.inputText(pass);
        advanced.click();
        secureModeCheckBox.setCheckbox(secureMode);
        winInetbasedCheckBox.setCheckbox(winInet);
        connectViaProxyCheckBox.setCheckbox(connViaProxy);
        return this;
    }
}
