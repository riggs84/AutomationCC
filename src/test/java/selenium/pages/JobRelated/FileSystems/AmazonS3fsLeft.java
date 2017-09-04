package selenium.pages.JobRelated.FileSystems;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import selenium.Elements.CheckBox;
import selenium.Elements.InputField;
import selenium.webtestsbase.BasePageClass;

public class AmazonS3fsLeft extends BasePageClass {

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

    @FindBy(xpath = ".//*[@id='amazons3-left']/div/div[1]/div[1]/div/label/span/span")
    CheckBox connectViaProxyCheckBox;

    @FindBy(xpath = ".//*[@id='amazons3-left']/div/div[1]/div[2]/div/label/span/span")
    CheckBox redandancyCheckBox;

    @FindBy(xpath = ".//*[@id='amazons3-left']/div/div[1]/div[3]/div/label/span/span")
    CheckBox serverSideEncrCheckBox;

    @FindBy(xpath = ".//*[@id='amazons3-left']/div/div[1]/div[4]/div/label/span/span")
    CheckBox usGovCloudCheckBox;

    @FindBy(xpath = ".//*[@id='amazons3-left']/div/div[2]/div[1]/div/label/span/span")
    CheckBox hostBasedAddressingCheckBox;

    @FindBy(xpath = ".//*[@id='amazons3-left']/div/div[2]/div[2]/div/label/span/span")
    CheckBox infrequentAccessStndrtStorageCheckBox;

    @FindBy(xpath = ".//*[@id='amazons3-left']/div/div[2]/div[3]/div/label/span/span")
    CheckBox useV4authCheckBox;

    @FindBy(xpath = ".//*[@id='amazons3-left']//input[@name='accesspolicy1']")
    InputField acceessCtrlInputField;

    public AmazonS3fsLeft(){
        super();
    }

    public AmazonS3fsLeft setConnectoidConfig(String path, String userName, String pass, boolean secureMode, boolean connectViaProxy,
                                              boolean redundancy, boolean serverSideEncr, boolean usGovCld,
                                              boolean hostBasedAddr, boolean infreqAccess, boolean useV4, String accCtrl){
        fsPathInputField.inputText(path);
        advanced.click();
        fsUserNameInputField.inputText(userName);
        fsPasswordInputField.inputText(pass);
        secureModeCheckBox.setCheckbox(secureMode);
        connectViaProxyCheckBox.setCheckbox(connectViaProxy);
        redandancyCheckBox.setCheckbox(redundancy);
        serverSideEncrCheckBox.setCheckbox(serverSideEncr);
        usGovCloudCheckBox.setCheckbox(usGovCld);
        hostBasedAddressingCheckBox.setCheckbox(hostBasedAddr);
        infrequentAccessStndrtStorageCheckBox.setCheckbox(infreqAccess);
        useV4authCheckBox.setCheckbox(useV4);
        acceessCtrlInputField.inputText(accCtrl);
        return this;
    }
}
