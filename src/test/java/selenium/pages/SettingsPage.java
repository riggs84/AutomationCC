package selenium.pages;

import org.openqa.selenium.support.FindBy;
import selenium.Elements.Button;
import selenium.Elements.InputField;
import selenium.pages.SettingsPageRelated.EditCompanySettings;
import selenium.webtestbase.BasePageClass;

public class SettingsPage extends BasePageClass {

    @FindBy(id = "btn-edit-settings")
    Button editCompanySettingsBtn;

    @FindBy(id = "btn-edit-local-settings")
    Button editPersonalSettingsBtn;

    @FindBy(id = "btn-activate-license")
    Button activateLicenseBtn;

    public SettingsPage(){
        super();
        setPageUrl("/ui/settings");
    }

    public EditCompanySettings clickEditCompanySettings() {
        editCompanySettingsBtn.click();
        waitForModalWindowOpen();
        return new EditCompanySettings();
    }
}
