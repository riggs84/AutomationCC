package selenium.pages.ProgramOptionsRelated;

import org.openqa.selenium.support.FindBy;
import selenium.Elements.CheckBox;
import selenium.Elements.InputField;
import selenium.Elements.Selection;
import selenium.webtestsbase.BasePageClass;

public class ConnectionTab extends BasePageClass {

    @FindBy(id = "use-proxy")
    Selection proxySelection;

    @FindBy(xpath = ".//*[@id='smtp-settings']//input[@name='smtp-host']")
    InputField smtpServerAddrInputField;

    @FindBy(xpath = ".//*[@id='smtp-settings']//input[@name='smtp-port']")
    InputField smtpServerPortInputField;

    @FindBy(xpath = ".//*[@id='smtp-settings']//input[@name='smtp-userid']")
    InputField smtpUserIdInputField;

    @FindBy(xpath = ".//*[@id='smtp-settings']//input[@name='smtp-passwd']")
    InputField passwordInputField;

    @FindBy(xpath = ".//*[@id='smtp-settings']//input[@name='smtp-sender']")
    InputField sendersEmailInputField;

    @FindBy(xpath = ".//*[@id='smtp-settings']/div[5]/div/label")
    CheckBox secureSmtpOverSSLCheckBox;

    @FindBy(xpath = ".//*[@id='smtp-settings']/div[6]/div/label")
    CheckBox secureSmtpSTARTTLSCheckBox;

    public ConnectionTab(){
        super();
    }

    public ConnectionTab setProxySelectionFieldToValue(String proxySelect){
        proxySelection.selectByVisibleText(proxySelect);
        return this;
    }

    public ConnectionTab setSmtpAddressFieldToValue(String serverAddr){
        smtpServerAddrInputField.inputText(serverAddr);
        return this;
    }

    public ConnectionTab setSmtpServerPortFieldToValue(String portNum){
        smtpServerPortInputField.inputText(portNum);
        return this;
    }

    public ConnectionTab setSmtpUserIdFieldToValue(String userId){
        smtpUserIdInputField.inputText(userId);
        return this;
    }

    public ConnectionTab setSmtpPasswordFieldToValue(String pass){
        passwordInputField.inputText(pass);
        return this;
    }

    public ConnectionTab setSenderEmailFieldToValue(String email){
        sendersEmailInputField.inputText(email);
        return this;
    }

    public ConnectionTab setSecureSmtpOverSSLtoValue(boolean value){
        secureSmtpOverSSLCheckBox.setCheckbox(value);
        return this;
    }

    public ConnectionTab setSecureSmtpSTARTTLtoValue(boolean value){
        secureSmtpSTARTTLSCheckBox.setCheckbox(value);
        return this;
    }
}
