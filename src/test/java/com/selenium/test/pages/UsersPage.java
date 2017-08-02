package com.selenium.test.pages;

import com.selenium.test.Elements.Table;
import com.selenium.test.webtestsbase.BasePageClass;
import com.selenium.test.webtestsbase.CustomFieldDecorator;
import com.selenium.test.webtestsbase.DriverFactory;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by Victor on 11.07.2017.
 */
public class UsersPage extends BasePageClass {

    @FindBy(css = ".togglebutton>label")
    WebElement showInactiveBtn;

    @FindBy(xpath = "//input[@type='search' and @class='form-control']")
    WebElement filterField;

    @FindBy(xpath = "//table/thead/tr/th[1]")
    WebElement userOSnameTableField;

    @FindBy(xpath = "//table/thead/tr/th[2]")
    WebElement userFullNameTableField;

    @FindBy(xpath = "//table/thead/tr/th[3]")
    WebElement emailTableField;

    @FindBy(xpath = "//table/thead/tr/th[4]")
    WebElement groupsTableField;

    @FindBy(xpath = "//table/thead/tr/th[5]")
    WebElement jobsCountTableField;

    @FindBy(xpath = "//table/thead/tr/th[6]")
    WebElement lastJobRunTableField;

    @FindBy(xpath = "//table/thead/tr/th[7]")
    WebElement lastJobRunErrorTableField;

    @FindBy(xpath = "//table/thead/tr/th[8]")
    WebElement activeTableField;

    @FindBy(id = "btn-create-new")
    WebElement createNewUserBtn;

    @FindBy(id = "btn-activate-checked")
    WebElement activateBtn;

    @FindBy(id = "btn-deactivate-checked")
    WebElement deactivateBtn;

    @FindBy(id = "btn-remove-checked")
    WebElement deleteBtn;

    @FindBy(xpath = ".//*[@id='tbl-users']//span/span")
    WebElement selectAllCheckbox;

    @FindBy(xpath = "//input[@class='form-control empty' and @name='user_os_name']")
    WebElement crtNewUserOSnameField;

    @FindBy(xpath = "//input[@class='form-control empty' and @name='user_full_name']")
    WebElement crtNewUserFullNameField;

    @FindBy(xpath = "//input[@class='form-control empty' and @name='user_email']")
    WebElement crtNewUserEmailField;

    @FindBy(xpath = ".//*[@id='user-edit']/div/div/div[3]/button[2]")
    WebElement crtNewUserSaveBtn;

    @FindBy(xpath = ".//*[@id='user-edit']/div/div/div[3]/button[1]")
    WebElement crtNewUserCancelBtn;

    @FindBy (xpath = "//table")
    public Table table;

    public UsersPage()
    {
        super();
        setPageUrl("https://control.goodsync.com/ui/users");
    }

    private void fillNewUserCreationFormUp(String osName, String fullName, String email)
    {
        setElementText(crtNewUserOSnameField, osName);
        setElementText(crtNewUserFullNameField, fullName);
        setElementText(crtNewUserEmailField, email);
    }

    public void createNewUser(String osName, String fullName, String email)
    {
        createNewUserBtn.click();
        fillNewUserCreationFormUp(osName, fullName, email);
        crtNewUserSaveBtn.click();
        waitForJSload();
    }

    public boolean checkElementPresentInTable(String elementName)
    {
        return table.tableContainsElements(elementName);
    }

    public int countElementsInTableByName(String elementName){
        return table.countElementsInTable(elementName);
                //countElementsInTable(tableBody, elementName);
    }

    public void applyFilter(String searchRequest)
    {
        filterField.clear();
        filterField.sendKeys(searchRequest);
    }

    /*public void selectElementInTable(String elementName) //TODO total piece of s... find more elegant solution
    {
        selectElementCheckboxInTable(tableBody,elementName);
        // using only email for search
    }*/

    public void deleteUser(String name){
        //selectElementInTable(name);
        deleteBtn.click();
    }
}
