package com.selenium.test.pages;

import com.selenium.test.webtestsbase.BasePageClass;
import com.selenium.test.webtestsbase.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;

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


    /*@FindBys(
            {
                    @FindBy(xpath = "//table/thead/tr/th[1]"),

                    @FindBy(xpath = "//table/thead/tr/th[2]"),

                    @FindBy(xpath = "//table/thead/tr/th[3]"),

                    @FindBy(xpath = "//table/thead/tr/th[4]"),

                    @FindBy(xpath = "//table/thead/tr/th[5]"),

                    @FindBy(xpath = "//table/thead/tr/th[6]"),

                    @FindBy(xpath = "//table/thead/tr/th[7]"),

                    @FindBy(xpath = "//table/thead/tr/th[8]")

            }
    )
    private List<WebElement> tableHeaders;*/

    @FindBy(xpath = "//tbody")
    private WebElement tableBody;

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

    /*TODO i can add result rows count as check it with recieved value by function
    it might add additional checking that we recieved exact rows as we expected in functions below*/
    public boolean isElementsPresentInTable(String elementName)
    {
        //WebElement table = DriverFactory.getDriver().findElement(By.xpath("//tbody"));
        List <WebElement> rows = tableBody.findElements(By.xpath("./*[contains(td,"+ elementName + ")]"));
        if (rows.isEmpty())
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public boolean isElementsNotEqualPresentInTable(String elementName)
    {
        //WebElement table = DriverFactory.getDriver().findElement(By.xpath("//tbody"));
        List <WebElement> rows = tableBody.findElements(By.xpath("./*[not(contains(td,"+ elementName + "))]"));
        if (rows.isEmpty())
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean isSortedAscendant(String elementName)
    {
        //TODO if list is empty - no element found exception will be rised.
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
            case "USERGROUPS":
                xpathRequest = "//tbody/tr/td[4]";
                break;
            case "COMPUTERGROUPS":
                xpathRequest = "//tbody/tr/td[5]";
                break;
            case "EMAILCONFIRMED":
                xpathRequest = "//tbody/tr/td[6]/span";
                break;
            case "CREATIONDATE":
                xpathRequest = "//tbody/tr/td[7]";
                break;
            case "ACTIVE":
                xpathRequest = "//tbody/tr/td[8]/span";
                break;
            default:
                break;
        }
        ArrayList<String> rowList = new ArrayList<>();
        List<WebElement> elements = tableBody.findElements(By.xpath(xpathRequest));//dependency on user decision
        for (WebElement list: elements)
        {
            rowList.add(list.getText());
        }
        ArrayList<String> bufList = new ArrayList<>();
        for (String strArr: rowList)
        {
            bufList.add(strArr);
        }
        Collections.sort(bufList);
        if (bufList.equals(rowList))
            return true;
        else
            return false;
    }





}
