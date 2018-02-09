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

    @FindBy(id = "security_mode1")
    CheckBox secureModeCheckBox;

    @FindBy(name="useproxy1")
    CheckBox connectViaProxyCheckBox;

    @FindBy(name="reduced-redundancy1")
    CheckBox redundancyCheckBox;

    @FindBy(name="server-encrypt1")
    CheckBox serverSideEncrCheckBox;

    @FindBy(name="us-gov-cloud1")
    CheckBox usGovCloudCheckBox;

    @FindBy(name="hostbased1")
    CheckBox hostBasedAddressingCheckBox;

    @FindBy(name="infrequent-access1")
    CheckBox infrequentAccessStndrtStorageCheckBox;

    @FindBy(name="aws4-auth1")
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

    public AmazonS3fsLeft setAccessCtrlInputFieldToValue(String val){
        accessCtrlInputField.inputText(val);
        return this;
    }

    public CheckBox getSecureModeCheckBox() {
        return secureModeCheckBox;
    }

    public CheckBox getConnectViaProxyCheckBox() {
        return connectViaProxyCheckBox;
    }

    public CheckBox getRedundancyCheckBox() {
        return redundancyCheckBox;
    }

    public CheckBox getServerSideEncrCheckBox() {
        return serverSideEncrCheckBox;
    }

    public CheckBox getUsGovCloudCheckBox() {
        return usGovCloudCheckBox;
    }

    public CheckBox getHostBasedAddressingCheckBox() {
        return hostBasedAddressingCheckBox;
    }

    public CheckBox getInfrequentAccessStndrtStorageCheckBox() {
        return infrequentAccessStndrtStorageCheckBox;
    }

    public CheckBox getUseV4authCheckBox() {
        return useV4authCheckBox;
    }
}
