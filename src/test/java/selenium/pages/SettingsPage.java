package selenium.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import selenium.Elements.*;
import selenium.pages.SettingsPageRelated.EditCompanySettings;
import selenium.webtestbase.BasePageClass;

public class SettingsPage extends BasePageClass {

    @FindBy(id = "btn-edit-settings")
    Button editCompanySettingsBtn;

    @FindBy(id = "btn-edit-local-settings")
    Button editPersonalSettingsBtn;

    @FindBy(id = "btn-activate-license")
    Button activateLicenseBtn;

    @FindBy(xpath = "//table/tbody/tr/td[1]/div[1]/div[2]//table//tr[1]/td[2]/span")
    TextField companyId;

    @FindBy(id = "span-settings-only-confirmed-email")
    TextField allowAdminWithConfirmedEmail;

    @FindBy(id = "span-settings-otp-enabled")
    TextField oneTimePasswordAuth;

    @FindBy(id = "span-settings-idle-time")
    TextField autoLogOutIdleTime;

    @FindBy(id = "span-settings-pin-mask")
    TextField companyPin;

    @FindBy(id = "btn-show-pin")
    Link show;

    @FindBy(id = "span-locale")
    TextField localeOptions;

    @FindBy(id = "span-timezone")
    TextField timeZoneOptions;

    @FindBy(id = "span-no-run-period")
    TextField longTimeNoRunsDashboardDefaultPeriod;

    public SettingsPage(){
        super();
        setPageUrl("/ui/settings");
    }

    @Step("Click Edit Company Settings")
    public EditCompanySettings clickEditCompanySettings() {
        editCompanySettingsBtn.click();
        waitForModalWindowOpen();
        return new EditCompanySettings();
    }

    @Step("Read Company Id")
    public String getCompanyId(){
        return companyId.getValue();
    }

    @Step("Read Allow Administrator Login Only With Confirmed Email")
    public String getAllowAdminWithConfirmedEmail(){
        return allowAdminWithConfirmedEmail.getValue();
    }

    @Step("Read One Time Password Authorization")
    public String getOneTimePasswordAuth(){
        return oneTimePasswordAuth.getValue();
    }

    @Step("Read Auto Logout after Idle")
    public String getAutoLogOutIdle(){
        return autoLogOutIdleTime.getValue();
    }

    @Step("Read Company PIN")
    public String getCompanyPin(){
        return companyPin.getValue();
    }

    @Step("Read Locale Options")
    public String getLocaleOptions(){
        return localeOptions.getValue();
    }

    @Step("Read TimeZone Options")
    public String getTimeZoneOptions(){
        return timeZoneOptions.getValue();
    }

    @Step("Read Dashboard. Long Time No Runs Default Period")
    public String getLongTimeNoRunsDefaultPeriod(){
        return longTimeNoRunsDashboardDefaultPeriod.getValue();
    }

    @Step("Click show/hide pin code")
    public SettingsPage clickSnowOrHidePinCode(){
        show.clickLink();
        return this;
    }



}
