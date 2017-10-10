package selenium.pages.JobRelated.FileSystems;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import selenium.Elements.CheckBox;
import selenium.Elements.InputField;
import selenium.webtestbase.BasePageClass;

public class GSTPconnectFSleft extends BasePageClass {

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

    @FindBy(xpath = ".//*[@id='gs-connect-left']/div/div[1]/div/label/span/span")
    CheckBox connectViaProxyCheckBox;

    @FindBy(xpath = ".//*[@title='Connect to this server via Proxy server that is specified and enabled in Tools -> Program Options -> Connection']/div/label/span/span")
    CheckBox doNotCheckSSLCertCheckBox;

    @FindBy(xpath = ".//*[@id='gs-connect-left']//input[@name='pk1']")
    InputField certificatePathInputField;

    @FindBy(xpath = ".//*[@id='gs-connect-left']//input[@name='encrypt-password1']")
    InputField encryptionPasswordInputField;


    public GSTPconnectFSleft(){
        super();
    }

    public GSTPconnectFSleft setConnectoidConfig(String path, boolean secureMode, boolean connectViaProxy, boolean dontCheckSSL, String certPath, String encrPassword){
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
