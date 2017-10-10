package selenium.pages.JobRelated.FileSystems;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import selenium.Elements.CheckBox;
import selenium.Elements.InputField;
import selenium.Helpers.BasePageClass;

public class FTPfsRight extends BasePageClass {

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

    @FindBy(xpath = ".//*[@id='cb_utf_8_filenames-right']/div/label/span/span")
    CheckBox utf8fileNamesCheckBox;

    @FindBy(xpath = ".//*[@id='ftp-right']/div/div[1]/div[2]/div/label/span/span")
    CheckBox connectViaProxyCheckBox;

    @FindBy(xpath = ".//*[@id='ftp-right']/div/div[1]/div[3]/div/label/span/span")
    CheckBox activeFTPmodeCheckBox;

    @FindBy(xpath = ".//*[@id='ftp-right']/div/div[1]/div[4]/div/label/span/span")
    CheckBox useMLSD_MLSTcommandsCheckBox;

    @FindBy(xpath = ".//*[@id='ftp-right']/div/div[1]/div[5]/div/label/span/span")
    CheckBox useLISTcommandCheckBox;

    @FindBy(xpath = ".//*[@id='ftp-right']/div/div[2]/div[1]/div/label/span/span")
    CheckBox dontCheckSSLCheckBox;

    @FindBy(xpath = ".//*[@id='ftp-right']/div/div[2]/div[2]/div/label/span/span")
    CheckBox useMDTMCheckBox;

    @FindBy(xpath = ".//*[@id='ftp-right']/div/div[2]/div[3]/div/label/span/span")
    CheckBox implicitFTPScheckBox;

    @FindBy(xpath = ".//*[@id='ftp-right']/div/div[2]/div[4]/div/label/span/span")
    CheckBox renameCheckBox;

    public FTPfsRight(){
        super();
    }

    public FTPfsRight setConnectoidConfig(String path, String userName, String pass, boolean secureMode, boolean utf8,
                                         boolean connectViaProxy, boolean activeFTP, boolean MLSD_MLST, boolean useLISTcmd,
                                         boolean dontCheckSSL, boolean MDTM, boolean implicitFTPS, boolean rename){
        fsPathInputField.inputText(path);
        advanced.click();
        fsUserNameInputField.inputText(userName);
        fsPasswordInputField.inputText(pass);
        secureModeCheckBox.setCheckbox(secureMode);
        utf8fileNamesCheckBox.setCheckbox(utf8);
        connectViaProxyCheckBox.setCheckbox(connectViaProxy);
        activeFTPmodeCheckBox.setCheckbox(activeFTP);
        useMLSD_MLSTcommandsCheckBox.setCheckbox(MLSD_MLST);
        useLISTcommandCheckBox.setCheckbox(useLISTcmd);
        dontCheckSSLCheckBox.setCheckbox(dontCheckSSL);
        useMDTMCheckBox.setCheckbox(MDTM);
        implicitFTPScheckBox.setCheckbox(implicitFTPS);
        renameCheckBox.setCheckbox(rename);
        return this;
    }
}
