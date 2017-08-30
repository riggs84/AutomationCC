package selenium.pages.entities;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import selenium.Elements.Button;
import selenium.Elements.Selection;
import selenium.webtestsbase.BasePageClass;

public class Admin extends BasePageClass {
    @FindBy(xpath = ".//*[@id='btn-edit-group-admin']")
    Button editAdministratorBtn;

    @FindBy(xpath = ".//*[@id='page-content-wrapper']//tbody//tr//span[@id='span-admin-id']")
    WebElement id;

    @FindBy(xpath = ".//*[@id='page-content-wrapper']//tbody//tr//span[@id='span-admin-name']")
    WebElement adminName;

    @FindBy(xpath = ".//*[@id='page-content-wrapper']//tbody//tr//span[@id='span-admin-email']")
    WebElement adminEmail;

    @FindBy(xpath = ".//*[@id='page-content-wrapper']//tbody//tr//span[@id='span-email-confirmed']")
    WebElement emilConfirmed;

    @FindBy(xpath = ".//*[@id='page-content-wrapper']//tbody//tr//span[@id='span-role']")
    WebElement role;

    @FindBy(xpath = ".//*[@id='admin-edit']//fieldset//select[@name='role']")
    Selection roleChangeSelect;

    @FindBy(xpath = ".//*[@id='admin-edit']//button[1]")
    Button cancelAdminEditBtn;

    @FindBy(xpath = ".//*[@id='admin-edit']//button[2]")
    Button saveAdminEditBtn;

    public Admin(String attribute) {
        super();
        setPageUrl(attribute);
    }

    public String getFieldValue(String fieldName){
        switch (fieldName.toUpperCase()){
            case "ID":
                return id.getText();
            case "ADMINISTRATOR NAME":
                return adminName.getText();
            case "ADMINISTRATOR EMAIL":
                return adminEmail.getText();
            case "EMAIL CONFIRMED":
                return emilConfirmed.getText();
            case "ROLE":
                return role.getText();
        }
        return null;
    }
}
