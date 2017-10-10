package selenium.pages.JobRelated.FileSystems;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import selenium.Elements.CheckBox;
import selenium.Elements.InputField;
import selenium.Helpers.BasePageClass;

public class WebDAVfsLeft extends BasePageClass {

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

    @FindBy(xpath = ".//*[@id='webdav-left']/div/div[1]/div/label/span/span")
    CheckBox winInetCheckBox;

    @FindBy(xpath = ".//*[@id='webdav-left']/div/div[2]/div/label/span/span")
    CheckBox dontCheckSSLcertCheckBox;

    @FindBy(xpath = ".//*[@id='webdav-left']//input[@name='pk1']")
    InputField certificatePathInputField;

    @FindBy(xpath = ".//*[@id='webdav-left']/div/div[3]/div/label/span/span")
    CheckBox connectViaProxy;

    public WebDAVfsLeft(){
        super();
    }

    public WebDAVfsLeft setConnectoidConfig(String path, String userName, String pass, boolean secureMode,
                                            boolean winInet, boolean dontCheckSSL, boolean connViaProxy,
                                            String certPath){
        fsPathInputField.inputText(path);
        advanced.click();
        fsUserNameInputField.inputText(userName);
        fsPasswordInputField.inputText(pass);
        secureModeCheckBox.setCheckbox(secureMode);
        winInetCheckBox.setCheckbox(winInet);
        dontCheckSSLcertCheckBox.setCheckbox(dontCheckSSL);
        connectViaProxy.setCheckbox(connViaProxy);
        certificatePathInputField.inputText(certPath);
        return this;
    }
}
