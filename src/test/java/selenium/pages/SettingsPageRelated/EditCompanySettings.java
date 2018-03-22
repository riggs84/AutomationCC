package selenium.pages.SettingsPageRelated;

import io.qameta.allure.Step;
import org.openqa.selenium.support.FindBy;
import selenium.Elements.Button;
import selenium.Elements.InputField;
import selenium.Elements.Table;
import selenium.pages.SettingsPage;
import selenium.webtestbase.BasePageClass;

public class EditCompanySettings extends BasePageClass {
    @FindBy(xpath = ".//*[@id='company-settings-edit']//input[@name='only_confirmed_emails']/../span")
    Button allowAdminLoginWithConfirmedEmailsBtn;

    @FindBy(name = "idle-hours")
    InputField autoLogOutIdleHoursField;

    @FindBy(xpath = ".//*[@id='company-settings-edit']//input[@name='use_company_pin']/../span")
    Button requirePinBtn;

    @FindBy(id = "txt-company-pin")
    InputField requirePinInputField;

    @FindBy(xpath = ".//*[@id='company-settings-edit']//input[@name='otp_authorization']/../span")
    Button useOTPBtn;

    @FindBy(xpath = "//table")
    Table emailNotConfirmedAdminTable;

    @FindBy(xpath = ".//*[@id='company-settings-edit']//div[@class='modal-footer']/button[1]")
    Button cancelBtn;

    @FindBy(xpath = ".//*[@id='company-settings-edit']//div[@class='modal-footer']/button[2]")
    Button saveBtn;

    @Step("Click Allow Administrator Login Only With Confirmed Email")
    public EditCompanySettings clickAllowAdminsLoginWithConfirmedEmail(){
        allowAdminLoginWithConfirmedEmailsBtn.click();
        return this;
    }

    @Step("Set pin code")
    public EditCompanySettings setPinCode(String _pinCode){
        requirePinInputField.inputText(_pinCode);
        return this;
    }

    @Step("Set Auto Logout after idle hours {_hours}")
    public EditCompanySettings setAutoLogOutIdleHours(String _hours){
        autoLogOutIdleHoursField.inputText(_hours);
        return this;
    }

    @Step("Click Require PIN code on Runner registration")
    public EditCompanySettings clickRequirePinCodeOnRunnerRegistration(){
        requirePinBtn.click();
        return this;
    }

    @Step("Click Use One Time Password Authorization")
    public EditCompanySettings clickUseOneTimePassword(){
        useOTPBtn.click();
        return this;
    }

    public boolean isTableNotConfirmedEmailsContainElement(String _elementName){
        return emailNotConfirmedAdminTable.tableContainsElements(_elementName);
    }

    @Step("Click Cancel button ")
    public SettingsPage clickCancelBtn(){
        cancelBtn.click();
        return new SettingsPage();
    }

    @Step("Click Save button")
    public SettingsPage clickSaveBtn(){
        saveBtn.click();
        waitForPageLoad();
        return new SettingsPage();
    }
}
