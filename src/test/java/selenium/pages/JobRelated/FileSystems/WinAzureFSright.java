package selenium.pages.JobRelated.FileSystems;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import selenium.Elements.CheckBox;
import selenium.Elements.InputField;
import selenium.webtestsbase.BasePageClass;

public class WinAzureFSright extends BasePageClass {

    @FindBy(xpath = ".//*[@id='div-folder-manual2']//input[@name='f2']")
    InputField fsPathInputField;

    @FindBy(xpath = ".//*[@id='txt_username-right']//input[@name='userid2']")
    InputField fsUserNameInputField;

    @FindBy(xpath = ".//*[@id='txt_password-right']//input[@name='password2']")
    InputField fsPasswordInputField;

    @FindBy(xpath = ".//*[@id='cb_security_mode-right']/div/label/span/span")
    CheckBox secureModeCheckBox;

    @FindBy(xpath = ".//*[@id='panel-right-advanced-container']/div[@data-target='#panel-right-advanced']")
    WebElement advanced;

    @FindBy(xpath = ".//*[@id='windowsazure-right']/div/div[1]/div/label/span/span")
    CheckBox winInetbasedCheckBox;

    @FindBy(xpath = ".//*[@id='windowsazure-right']/div/div[2]/div/label/span/span")
    CheckBox connectViaProxyCheckBox;

    public WinAzureFSright(){
        super();
    }

    public WinAzureFSright setConnectoidConfig(String path, String userName, String pass, boolean secureMode,
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
