package com.selenium.test.pages;

import com.selenium.test.webtestsbase.BasePageClass;
import com.selenium.test.webtestsbase.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Victor on 29.06.2017.
 */
public class AdministratorsPage extends BasePageClass {

    @FindBy (xpath = "//input[@id='cb-show-inactive' and @type='checkbox']")
    private WebElement showInactiveBtn;

    @FindBy (xpath = "//input[@type='search' and @class='form-control']")
    private WebElement filterField;

    @FindBy (id = "btn-create-new")
    private WebElement createNewAdminBtn;

    @FindBy (id = "btn-activate-checked")
    private WebElement activateBtn;

    @FindBy (id = "btn-deactivate-checked")
    private WebElement deactivateBtn;

    @FindBy (id = "btn-remove-checked")
    private WebElement deleteBtn;

    @FindBy(xpath = "//table/thead/tr/th[1]")
    private WebElement roleTableField;

    @FindBy(xpath = "//table/thead/tr/th[2]")
    private WebElement nameTableField;

    @FindBy(xpath = "//table/thead/tr/th[3]")
    private WebElement emailTableField;

    @FindBy(xpath = "//table/thead/tr/th[4]")
    private WebElement userGroupsTableField;

    @FindBy(xpath = "//table/thead/tr/th[5]")
    private WebElement computerGroupsTableField;

    @FindBy(xpath = "//table/thead/tr/th[6]")
    private WebElement emailConfirmedTableField;

    @FindBy(xpath = "//table/thead/tr/th[7]")
    private WebElement creationDateTableField;

    @FindBy(xpath = "//table/thead/tr/th[8]")
    private WebElement activeTableField;

    @FindBy(xpath = "//tbody")
    private WebElement tableBody;

    @FindBy(xpath = ".//*[@id='admin-edit']/div/div/div[2]/div[2]/div/fieldset/div/div[1]/select")
    private WebElement crtNewAdmRoleField;

    @FindBy(xpath = ".//*[@id='admin-edit']/div/div/div[2]/div[2]/div/fieldset/div/div[2]/input")
    private WebElement crtNewAdmNameField;

    @FindBy(xpath = ".//*[@id='admin-edit']/div/div/div[2]/div[2]/div/fieldset/div/div[3]/input")
    private WebElement crtNewAdmEmailField;

    @FindBy(xpath = ".//*[@id='txt-admin-password']")
    private WebElement crtNewAdmTempPassField;

    @FindBy(xpath = ".//*[@id='password-container']/div[2]/input")
    private WebElement crtNewAdmTempPassReEnterField;

    @FindBy(xpath = ".//*[@id='admin-edit']/div/div/div[3]/button[1]")
    private WebElement crtNewAdmCancelButton;

    @FindBy(xpath = ".//*[@id='admin-edit']/div/div/div[3]/button[2]")
    private WebElement crtNewAdmSaveButton;

    @FindBy(xpath = "//div[4]/div/div/div[3]/button[2]")
    private WebElement deactivationConfirmBtn;

    @FindBy(css = ".btn.btn-default")
    private WebElement deactivationCancelBtn;

    @FindBy(xpath = ".//*[@id='tbl-group-admins']//span/span")
    private WebElement selectAllCheckbox;

    public AdministratorsPage()
    {
        super();
        setPageUrl("https://control.goodsync.com/ui/administrators");
    }

    public WebElement getWebElementByName(String name)
    {
        WebElement temp = null;
        switch(name.toUpperCase())
        {
            case "SAVE":
                temp = crtNewAdmSaveButton;
                break;
            case "CREATE NEW ADMINISTRATOR":
                temp = createNewAdminBtn;
                break;
            case "CANCEL":
                temp = crtNewAdmCancelButton;
                break;
            case "DEACTIVATE":
                temp = deactivateBtn;
                break;
        }
        return temp;
    }

    public void applyFilter(String searchRequest)
    {
        filterField.clear();
        filterField.sendKeys(searchRequest);
    }

    @Override
    public void sortBy(String tableName) {
        switch(tableName.toUpperCase())
        {
            case "ROLE":
                roleTableField.click();
                break;
            case "NAME":
                nameTableField.click();
                break;
            case "EMAIL":
                emailTableField.click();
                break;
            case "USER GROUPS":
                userGroupsTableField.click();
                break;
            case "COMPUTER GROUPS":
                computerGroupsTableField.click();
                break;
            case "EMAIL CONFIRMED":
                emailConfirmedTableField.click();
                break;
            case "CREATION DATE":
                creationDateTableField.click();
                break;
            case "ACTIVATE":
                activeTableField.click();
                break;
            default:
                break;

        }

    }

    public boolean hasElementsInTable(String elementName)
    {
        return tableContainsElements(tableBody,elementName);
    }

    public boolean hasOtherElementsInTableExcept(String elementName)
    {
        return tableContainsElementsExcept(tableBody, elementName);
    }

    public boolean isSortedAscendant(String elementName)
    {
        return checkAscendantOrderInTable(tableBody,elementName);
    }

    public boolean isSortedDescendant(String elementName)
    {
        return checkDescendantOrderInTable(tableBody,elementName);
    }

    @Override
    protected String getXpathTableLocation(String elementName)
    {
        String xpathRequest = null;
        switch(elementName.toUpperCase())
        {
            case "ROLE":
                xpathRequest = "//tbody/tr/td[1]";
                break;
            case "NAME":
                xpathRequest = "//tbody/tr/td[2]/a";
                break;
            case "EMAIL":
                xpathRequest = "//tbody/tr/td[3]";
                break;
            case "USER GROUPS":
                xpathRequest = "//tbody/tr/td[4]";
                break;
            case "COMPUTER GROUPS":
                xpathRequest = "//tbody/tr/td[5]";
                break;
            case "EMAIL CONFIRMED":
                xpathRequest = "//tbody/tr/td[6]/span";
                break;
            case "CREATION DATE":
                xpathRequest = "//tbody/tr/td[7]";
                break;
            case "ACTIVE":
                xpathRequest = "//tbody/tr/td[8]/span";
                break;
            default:
                break;
        }
        return xpathRequest;
    }

    private void fillNewAdminFormUp(String adminRole, String name, String email, String tempPass, String reEnterTempPass)
    {
        Select selection = new Select(crtNewAdmRoleField);
        selection.selectByVisibleText(adminRole);
        setElementText(crtNewAdmNameField, name);
        setElementText(crtNewAdmEmailField, email);
        setElementText(crtNewAdmTempPassField, tempPass);
        setElementText(crtNewAdmTempPassReEnterField, reEnterTempPass);
    }

    public void createNewAdministrator(String role, String name, String email, String pass1, String pass2)
    {
        clickOnElement(createNewAdminBtn);
        fillNewAdminFormUp(role, name, email, pass1, pass2);
        clickOnElement(crtNewAdmSaveButton);
    }

    public void selectElementInTable(String elementName) //TODO total piece of s... find more elegant solution
    {
        selectElementCheckboxInTable(tableBody,elementName);
        // using only email for search
    }

    public void deactivateORactivateAdmin(String adminEmail)
    {
        selectElementInTable(adminEmail);
        clickOnElement(deactivateBtn);
        clickOnElement(deactivationConfirmBtn);
        waitForJSload();
    }

    public void showInactive()
    {
        clickOnElement(showInactiveBtn);
    }

    public void deleteAdmin(String name)
    {
        selectElementInTable(name);
        clickOnElement(deleteBtn);
    }

    public void deleteAll()
    {
        clickOnElement(selectAllCheckbox);
        clickOnElement(deleteBtn);
    }

    public void deactivateAll()
    {
        clickOnElement(selectAllCheckbox);
        clickOnElement(deactivateBtn);
    }






}
