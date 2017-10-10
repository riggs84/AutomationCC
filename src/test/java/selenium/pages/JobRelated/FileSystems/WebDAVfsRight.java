package selenium.pages.JobRelated.FileSystems;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import selenium.Elements.CheckBox;
import selenium.Elements.InputField;
import selenium.Helpers.BasePageClass;

public class WebDAVfsRight extends BasePageClass {

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

    @FindBy(xpath = ".//*[@id='webdav-right']/div/div[1]/div/label/span/span")
    CheckBox winInetCheckBox;

    @FindBy(xpath = ".//*[@id='webdav-right']/div/div[2]/div/label/span/span")
    CheckBox dontCheckSSLcertCheckBox;

    @FindBy(xpath = ".//*[@id='webdav-right']//input[@name='pk2']")
    InputField certificatePathInputField;

    @FindBy(xpath = ".//*[@id='webdav-right']/div/div[3]/div/label/span/span")
    CheckBox connectViaProxy;

    public WebDAVfsRight(){
        super();
    }

    public WebDAVfsRight setConnectoidConfig(String path, String userName, String pass, boolean secureMode,
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
