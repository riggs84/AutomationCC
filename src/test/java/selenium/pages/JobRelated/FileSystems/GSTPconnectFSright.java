package selenium.pages.JobRelated.FileSystems;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import selenium.Elements.CheckBox;
import selenium.Elements.InputField;
import selenium.webtestbase.BasePageClass;

public class GSTPconnectFSright extends BasePageClass {

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

    @FindBy(xpath = ".//*[@id='gs-connect-right']/div/div[1]/div/label/span/span")
    CheckBox connectViaProxyCheckBox;

    @FindBy(xpath = ".//*[@title='Connect to this server via Proxy server that is specified and enabled in Tools -> Program Options -> Connection']/div/label/span/span")
    CheckBox doNotCheckSSLCertCheckBox;

    @FindBy(xpath = ".//*[@id='gs-connect-right']//input[@name='pk2']")
    InputField certificatePathInputField;

    @FindBy(xpath = ".//*[@id='gs-connect-right']//input[@name='encrypt-password2']")
    InputField encryptionPasswordInputField;

    public GSTPconnectFSright(){
        super();
    }

    public GSTPconnectFSright setConnectoidConfig(String path, boolean secureMode, boolean connectViaProxy, boolean dontCheckSSL, String certPath, String encrPassword){
        fsPathInputField.inputText(path);
        advanced.click();
        connectViaProxyCheckBox.setCheckbox(connectViaProxy);
        secureModeCheckBox.setCheckbox(secureMode);
        doNotCheckSSLCertCheckBox.setCheckbox(dontCheckSSL);
        certificatePathInputField.inputText(certPath);
        encryptionPasswordInputField.inputText(encrPassword);
        return this;
    }
}
