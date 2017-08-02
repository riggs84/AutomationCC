package com.selenium.test.pages;

import com.selenium.test.Elements.Button;
import com.selenium.test.Elements.InputField;
import com.selenium.test.Elements.Table;
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
    private Button showInactiveBtn;

    @FindBy (xpath = "//input[@type='search' and @class='form-control']")
    private InputField filterField;

    @FindBy (id = "btn-create-new")
    private Button createNewAdminBtn;

    @FindBy (id = "btn-activate-checked")
    private Button activateBtn;

    @FindBy (id = "btn-deactivate-checked")
    private Button deactivateBtn;

    @FindBy (id = "btn-remove-checked")
    private Button deleteBtn;

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

    @FindBy(xpath = "//*[@id='tbl-group-admins']")
    Table table;

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
    private Button crtNewAdmCancelButton;

    @FindBy(xpath = ".//*[@id='admin-edit']/div/div/div[3]/button[2]")
    private Button crtNewAdmSaveButton;

    @FindBy(xpath = "//div[4]/div/div/div[3]/button[2]")
    private Button deactivationConfirmBtn;

    @FindBy(css = ".btn.btn-default")
    private Button deactivationCancelBtn;

    //@FindBy(xpath = ".//*[@id='tbl-group-admins']//span/span")
    //private WebElement selectAllCheckbox;

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
        filterField.inputText(searchRequest);
    }

    public boolean hasElementsInTable(String elementName)
    {
        return table.tableContainsElements(elementName);
    }

    public boolean hasOtherElementsInTableExcept(String elementName)
    {
        return table.tableContainsElementsExcept(elementName);
    }

    public boolean isSortedAscendant(String elementName)
    {
        return table.checkAscendantOrderInTable(elementName);
    }

    public boolean isSortedDescendant(String elementName)
    {
        return table.checkDescendantOrderInTable(elementName);
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
        createNewAdminBtn.click();
        fillNewAdminFormUp(role, name, email, pass1, pass2);
        crtNewAdmSaveButton.click();
    }

    public void selectElementInTable(String elementName) //TODO total piece of s... find more elegant solution
    {
        table.selectElementCheckboxInTable(elementName);
        // using only email for search
    }

    public void deactivateORactivateAdmin(String adminEmail)
    {
        table.selectElementCheckboxInTable(adminEmail);
        deactivateBtn.click();
        deactivationConfirmBtn.click();
        waitForJSload();
    }

    public void showInactive()
    {
        showInactiveBtn.click();
    }

    public void deleteAdmin(String name)
    {
        table.selectElementCheckboxInTable(name);
        deleteBtn.click();
    }

    public void deleteAll()
    {
        table.selectAllInTable();
        deleteBtn.click();
    }

    public void deactivateAll()
    {
        table.selectAllInTable();
        deactivateBtn.click();
    }

    public int countElementsInTable(String elementName){
        return table.countElementsInTable(elementName);
    }

    public void sortBy(String elementName){
        table.sortBy(elementName);
    }






}
