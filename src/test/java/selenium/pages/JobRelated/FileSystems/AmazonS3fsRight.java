package selenium.pages.JobRelated.FileSystems;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import selenium.Elements.CheckBox;
import selenium.Elements.InputField;
import selenium.webtestbase.BasePageClass;

public class AmazonS3fsRight extends BasePageClass {

    @FindBy(xpath = ".//*[@id='div-folder-manual2']//input[@name='f2']")
    InputField fsPathInputField;

    @FindBy(xpath = ".//*[@id='txt_username-right']//input[@name='userid2']")
    InputField fsAWSaccessKeyInputField;

    @FindBy(xpath = ".//*[@id='txt_password-right']//input[@name='password2']")
    InputField fsAWSsecreteKeyInputField;

    @FindBy(xpath = ".//*[@id='cb_security_mode-right']")
    CheckBox secureModeCheckBox;

    @FindBy(xpath = ".//*[@id='amazons3-right']//input[@name='useproxy2']")
    CheckBox connectViaProxyCheckBox;

    @FindBy(xpath = ".//*[@id='amazons3-right']//input[@name='reduced-redundancy2']")
    CheckBox redundancyCheckBox;

    @FindBy(xpath = ".//*[@id='amazons3-right']//input[@name='server-encrypt2']")
    CheckBox serverSideEncrCheckBox;

    @FindBy(xpath = ".//*[@id='amazons3-right']//input[@name='us-gov-cloud2']")
    CheckBox usGovCloudCheckBox;

    @FindBy(xpath = ".//*[@id='amazons3-right']//input[@name='hostbased2']")
    CheckBox hostBasedAddressingCheckBox;

    @FindBy(xpath = ".//*[@id='amazons3-right']//input[@name='infrequent-access2']")
    CheckBox infrequentAccessStndrtStorageCheckBox;

    @FindBy(xpath = ".//*[@id='amazons3-right']//input[@name='aws4-auth2']")
    CheckBox useV4authCheckBox;

    @FindBy(xpath = ".//*[@id='amazons3-right']//input[@name='accesspolicy2']")
    InputField accessCtrlInputField;

    @FindBy(xpath = ".//*[@id='panel-right-advanced-container']/div[1]")
    WebElement fileSystemSpecificPanel;

    public AmazonS3fsRight(){
        super();
    }

    public InputField getFsPathInputField() {
        return fsPathInputField;
    }

    public AmazonS3fsRight clickFileSystemSpecificPanel(){
        fileSystemSpecificPanel.click();
        return new AmazonS3fsRight();
    }

    public AmazonS3fsRight setAwsPath(String path){
        fsPathInputField.setText(path);
        return this;
    }

    public AmazonS3fsRight setAwsAccessKeyId(String str){
        fsAWSaccessKeyInputField.inputText(str);
        return this;
    }

    public AmazonS3fsRight setAwsSecretAccessKey(String str){
        fsAWSsecreteKeyInputField.inputText(str);
        return this;
    }

    public AmazonS3fsRight setSecureModeCheckBox(boolean val){
        secureModeCheckBox.setCheckbox(val);
        return this;
    }

    public AmazonS3fsRight setConnectViaProxyCheckbox(boolean bool){
        connectViaProxyCheckBox.setCheckbox(bool);
        return this;
    }

    public AmazonS3fsRight setReduceRedundancyCheckbox(boolean bool){
        redundancyCheckBox.setCheckbox(bool);
        return this;
    }

    public AmazonS3fsRight setServerSideEncryptCheckbox(boolean bool){
        serverSideEncrCheckBox.setCheckbox(bool);
        return this;
    }

    public AmazonS3fsRight setUsGovermntCloudCheckbox(boolean bool){
        usGovCloudCheckBox.setCheckbox(bool);
        return this;
    }

    public AmazonS3fsRight setHostBasedAddressingCheckbox(boolean bool){
        hostBasedAddressingCheckBox.setCheckbox(bool);
        return this;
    }

    public AmazonS3fsRight setInfreqAccessStandartStorageCheckbox(boolean bool){
        infrequentAccessStndrtStorageCheckBox.setCheckbox(bool);
        return this;
    }

    public AmazonS3fsRight setUseV4authorizationCheckbox(boolean bool){
        useV4authCheckBox.setCheckbox(bool);
        return this;
    }

    public AmazonS3fsRight setAccessCtrlInputFieldToValue(String val){
        accessCtrlInputField.inputText(val);
        return this;
    }
}
