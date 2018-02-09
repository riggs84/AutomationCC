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

    @FindBy(xpath = ".//*[@id='cb_security_mode-left']/div/label")
    CheckBox secureModeCheckBox;

    @FindBy(xpath = ".//*[@id='panel-left-advanced-container']/div[@data-target='#panel-left-advanced']")
    WebElement advanced;

    @FindBy(xpath = ".//*[@id='gs-connect-left']/div/div[1]/div/label")
    CheckBox connectViaProxyCheckBox;

    @FindBy(xpath = ".//*[@id='gs-connect-left']/div/div[2]/div/label")
    CheckBox doNotCheckSSLCertCheckBox;

    @FindBy(xpath = ".//*[@id='gs-connect-left']//input[@name='pk1']")
    InputField certificatePathInputField;

    @FindBy(xpath = ".//*[@id='gs-connect-left']//input[@name='encrypt-password1']")
    InputField encryptionPasswordInputField;


    public GSTPconnectFSleft(){
        super();
    }

    public GSTPconnectFSleft setConnectoidConfig(String path){
        fsPathInputField.inputText(path);
        return this;
    }

    public CheckBox getSecureModeCheckBox() {
        return secureModeCheckBox;
    }

    public CheckBox getConnectViaProxyCheckBox() {
        return connectViaProxyCheckBox;
    }

    public CheckBox getDoNotCheckSSLCertCheckBox() {
        return doNotCheckSSLCertCheckBox;
    }

    public GSTPconnectFSleft setPathToCertificate(String path){
        certificatePathInputField.inputText(path);
        return this;
    }
}
