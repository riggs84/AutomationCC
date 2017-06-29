package com.selenium.test.pages;

import com.selenium.test.webtestsbase.BasePageClass;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

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

    @FindBy (xpath = "//table/thread/tr/th[1]")
    private WebElement roleTableField;

    @FindBy (xpath = "//table/thread/tr/th[2]")
    private WebElement nameTableField;

    @FindBy (xpath = "//table/thread/tr/th[3]")
    private WebElement emailTableField;

    @FindBy (xpath = "//table/thread/tr/th[4]")
    private WebElement userGroupsTableField;

    @FindBy (xpath = "//table/thread/tr/th[5]")
    private WebElement computerGroupsTableField;

    @FindBy (xpath = "//table/thread/tr/th[6]")
    private WebElement emailConfirmedTableField;

    @FindBy (xpath = "//table/thread/tr/th[7]")
    private WebElement creationDateTableField;

    @FindBy (xpath = "//table/thread/tr/th[8]")
    private WebElement activeTableField;

    public AdministratorsPage()
    {
        super();
        setPageUrl("https://control.goodsync.com/ui/administrators");
    }

    public void applyFilter(String searchRequest)
    {
        filterField.clear();
        filterField.sendKeys(searchRequest);
    }

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
            case "USERGROUPS":
                userGroupsTableField.click();
                break;
            case "COMPUTERGROUPS":
                computerGroupsTableField.click();
                break;
            case "EMAILCONFIRMED":
                emailConfirmedTableField.click();
                break;
            case "CREATIONDATE":
                creationDateTableField.click();
                break;
            case "ACTIVATE":
                activeTableField.click();
                break;
            default:
                break;

        }

    }




}
