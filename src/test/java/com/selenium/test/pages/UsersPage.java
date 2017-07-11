package com.selenium.test.pages;

import com.selenium.test.webtestsbase.BasePageClass;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

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

    @FindBy (xpath = "//tbody")
    WebElement tableBody;

    public UsersPage()
    {
        super();
        setPageUrl("https://control.goodsync.com/ui/users");
    }

    @Override
    protected String getXpathTableLocation(String elementName) {
        String xpathRequest = null;
        switch(elementName.toUpperCase())
        {
            case "USER OS NAME":
                xpathRequest = userOSnameTableField.toString();
                break;
            case "USER FULL NAME":
                xpathRequest = userFullNameTableField.toString();
                break;
            case "EMAIL":
                xpathRequest = emailTableField.toString();
                break;
            case "GROUPS":
                xpathRequest = groupsTableField.toString();
                break;
            case "JOBS COUNT":
                xpathRequest = jobsCountTableField.toString();
                break;
            case "LAST JOB RUN":
                xpathRequest = lastJobRunTableField.toString();
                break;
            case "LAST JOB RUN ERROR":
                xpathRequest = lastJobRunErrorTableField.toString();
                break;
            case "ACTIVE":
                xpathRequest = activeTableField.toString();
                break;
            default:
                break;
        }
        return xpathRequest;
    }

    @Override
    public void sortBy(String tableName) {
        switch(tableName.toUpperCase())
        {
            case "USER OS NAME":
                userOSnameTableField.click();
                break;
            case "USER FULL NAME":
                userFullNameTableField.click();
                break;
            case "EMAIL":
                emailTableField.click();
                break;
            case "GROUPS":
                groupsTableField.click();
                break;
            case "JOBS COUNT":
                jobsCountTableField.click();
                break;
            case "LAST JOB RUN":
                lastJobRunTableField.click();
                break;
            case "LAST JOB RUN ERROR":
                lastJobRunErrorTableField.click();
                break;
            case "ACTIVATE":
                activeTableField.click();
                break;
            default:
                break;
        }
    }

    public void applyFilter(String searchRequest)
    {
        filterField.clear();
        filterField.sendKeys(searchRequest);
    }
}
