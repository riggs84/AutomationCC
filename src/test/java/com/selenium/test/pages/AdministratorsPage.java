package com.selenium.test.pages;

import com.selenium.test.webtestsbase.BasePageClass;
import com.selenium.test.webtestsbase.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
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

    @FindBy(xpath = "//fieldset/div[1]/select")
    private WebElement crtNewAdmRoleField;

    @FindBy(xpath = "//fieldset/div[2]/input")
    private WebElement crtNewAdmNameField;

    @FindBy(xpath = "//fieldset/div[3]/input")
    private WebElement crtNewAdmEmailField;

    @FindBy(xpath = "//fieldset/div[4]/div[1]/input")
    private WebElement crtNewAdmTempPassField;

    @FindBy(xpath = "//fieldset/div[4]/div[2]/input")
    private WebElement crtNewAdmTempPassReEnterField;

    @FindBy(xpath = "//div[class='modal-footer']/button[1]")
    private WebElement crtNewAdmCancelButton;

    @FindBy(xpath = "//div[class='modal-footer']/button[2]")
    private WebElement crtNewAdmSaveButton;


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
        }
        return temp;
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

    public boolean hasElementsInTable(String elementName)
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

    public boolean hasOtherElementsInTableExcept(String elementName)
    {
        List <WebElement> rows = tableBody.findElements(By.xpath("/*[not(contains(td,"+ elementName + "))]"));
        //List <WebElement> rows = tableBody.findElements(By.xpath("./*[not(contains(text()," + elementName + "))]"));
        if (rows.isEmpty())
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public boolean isSortedAscendant(String elementName)
    {
        //TODO if list is empty - no element found exception will be rised.
        ArrayList<String> rowList = new ArrayList<>();
        List<WebElement> elements = tableBody
                .findElements(By.xpath(getXpathTableLocation("")));//dependency on user decision
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

    public boolean isSortedDescendant(String elementName)
    {
        //TODO if list is empty - no element found exception will be rised.
        ArrayList<String> rowList = new ArrayList<>();
        List<WebElement> elements = tableBody
                .findElements(By.xpath(getXpathTableLocation("")));//dependency on user decision
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
        Collections.reverse(bufList);
        if (bufList.equals(rowList))
            return true;
        else
            return false;
    }

    private String getXpathTableLocation(String elementName)
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
        return xpathRequest;
    }

    public void fillNewAdminFormUp(String adminRole, String name, String email, String tempPass, String reEnterTempPass)
    {
        Select selection = new Select(crtNewAdmRoleField);
        selection.deselectByVisibleText(adminRole);
        crtNewAdmNameField.sendKeys(name);
        crtNewAdmEmailField.sendKeys(email);
        crtNewAdmTempPassField.sendKeys(tempPass);
        crtNewAdmTempPassReEnterField.sendKeys(reEnterTempPass);
    }

    public void createNewAdministrator(String role, String name, String email, String pass1, String pass2)
    {
        clickOnElement(createNewAdminBtn);
        fillNewAdminFormUp(role, name, email, pass1, pass2);
        clickOnElement(crtNewAdmSaveButton);
    }






}
