package selenium.pages.JobRelated.FileSystems;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import selenium.Elements.CheckBox;
import selenium.Elements.InputField;
import selenium.webtestbase.BasePageClass;

public class AmazonS3fsLeft extends BasePageClass {

    @FindBy(xpath = ".//*[@id='div-folder-manual1']//input[@name='f1']")
    InputField fsPathInputField;

    @FindBy(xpath = ".//*[@id='txt_username-left']//input[@name='userid1']")
    InputField fsAWSaccessKeyInputField;

    @FindBy(xpath = ".//*[@id='txt_password-left']//input[@name='password1']")
    InputField fsAWSsecreteKeyInputField;

    @FindBy(xpath = ".//*[@id='cb_security_mode-left']")
    CheckBox secureModeCheckBox;

    @FindBy(xpath = ".//*[@id='amazons3-left']//input[@name='useproxy1']")
    CheckBox connectViaProxyCheckBox;

    @FindBy(xpath = ".//*[@id='amazons3-left']//input[@name='reduced-redundancy1']")
    CheckBox redundancyCheckBox;

    @FindBy(xpath = ".//*[@id='amazons3-left']//input[@name='server-encrypt1']")
    CheckBox serverSideEncrCheckBox;

    @FindBy(xpath = ".//*[@id='amazons3-left']//input[@name='us-gov-cloud1']")
    CheckBox usGovCloudCheckBox;

    @FindBy(xpath = ".//*[@id='amazons3-left']//input[@name='hostbased1']")
    CheckBox hostBasedAddressingCheckBox;

    @FindBy(xpath = ".//*[@id='amazons3-left']//input[@name='infrequent-access1']")
    CheckBox infrequentAccessStndrtStorageCheckBox;

    @FindBy(xpath = ".//*[@id='amazons3-left']//input[@name='aws4-auth1']")
    CheckBox useV4authCheckBox;

    @FindBy(xpath = ".//*[@id='amazons3-left']//input[@name='accesspolicy1']")
    InputField accessCtrlInputField;

    @FindBy(xpath = ".//*[@id='panel-left-advanced-container']/div[1]")
    WebElement fileSystemSpecificPanel;

    public AmazonS3fsLeft(){
        super();
    }

    public InputField getFsPathInputField() {
        return fsPathInputField;
    }

    public AmazonS3fsLeft clickFileSystemSpecificPanel(){
        fileSystemSpecificPanel.click();
        return new AmazonS3fsLeft();
    }

    public AmazonS3fsLeft setAwsPath(String path){
        fsPathInputField.setText(path);
        return this;
    }

    public AmazonS3fsLeft setAwsAccessKeyId(String str){
        fsAWSaccessKeyInputField.inputText(str);
        return this;
    }

    public AmazonS3fsLeft setAwsSecretAccessKey(String str){
        fsAWSsecreteKeyInputField.inputText(str);
        return this;
    }

    public AmazonS3fsLeft setSecureModeCheckBox(boolean val){
        secureModeCheckBox.setCheckbox(val);
        return this;
    }

    public AmazonS3fsLeft setConnectViaProxyCheckbox(boolean bool){
        connectViaProxyCheckBox.setCheckbox(bool);
        return this;
    }

    public AmazonS3fsLeft setReduceRedundancyCheckbox(boolean bool){
        redundancyCheckBox.setCheckbox(bool);
        return this;
    }

    public AmazonS3fsLeft setServerSideEncryptCheckbox(boolean bool){
        serverSideEncrCheckBox.setCheckbox(bool);
        return this;
    }

    public AmazonS3fsLeft setUsGovermntCloudCheckbox(boolean bool){
        usGovCloudCheckBox.setCheckbox(bool);
        return this;
    }

    public AmazonS3fsLeft setHostBasedAddressingCheckbox(boolean bool){
        hostBasedAddressingCheckBox.setCheckbox(bool);
        return this;
    }

    public AmazonS3fsLeft setInfreqAccessStandartStorageCheckbox(boolean bool){
        infrequentAccessStndrtStorageCheckBox.setCheckbox(bool);
        return this;
    }

    public AmazonS3fsLeft setUseV4authorizationCheckbox(boolean bool){
        useV4authCheckBox.setCheckbox(bool);
        return this;
    }

    public AmazonS3fsLeft setAccessCtrlInputFieldToValue(String val){
        accessCtrlInputField.inputText(val);
        return this;
    }
}
