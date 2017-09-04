package selenium.pages.JobRelated.FileSystems;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import selenium.Elements.CheckBox;
import selenium.Elements.InputField;
import selenium.webtestsbase.BasePageClass;

public class FTPfsLeft extends BasePageClass {

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

    @FindBy(xpath = ".//*[@id='cb_utf_8_filenames-left']/div/label/span/span")
    CheckBox utf8fileNamesCheckBox;

    @FindBy(xpath = ".//*[@id='ftp-left']/div/div[1]/div[2]/div/label/span/span")
    CheckBox connectViaProxyCheckBox;

    @FindBy(xpath = ".//*[@id='ftp-left']/div/div[1]/div[3]/div/label/span/span")
    CheckBox activeFTPmodeCheckBox;

    @FindBy(xpath = ".//*[@id='ftp-left']/div/div[1]/div[4]/div/label/span/span")
    CheckBox useMLSD_MLSTcommandsCheckBox;

    @FindBy(xpath = ".//*[@id='ftp-left']/div/div[1]/div[5]/div/label/span/span")
    CheckBox useLISTcommandCheckBox;

    @FindBy(xpath = ".//*[@id='ftp-left']/div/div[2]/div[1]/div/label/span/span")
    CheckBox dontCheckSSLCheckBox;

    @FindBy(xpath = ".//*[@id='ftp-left']/div/div[2]/div[2]/div/label/span/span")
    CheckBox useMDTMCheckBox;

    @FindBy(xpath = ".//*[@id='ftp-left']/div/div[2]/div[3]/div/label/span/span")
    CheckBox implicitFTPScheckBox;

    @FindBy(xpath = ".//*[@id='ftp-left']/div/div[2]/div[4]/div/label/span/span")
    CheckBox renameCheckBox;

    public FTPfsLeft(){
        super();
    }

    public FTPfsLeft setConnectoidConfig(String path, String userName, String pass, boolean secureMode, boolean utf8,
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
