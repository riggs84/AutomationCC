package selenium.pages;

import io.qameta.allure.Step;
import selenium.Elements.*;
import selenium.webtestsbase.BasePageClass;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

/**
 * Created by Victor on 29.06.2017.
 */
public class AdministratorsPage extends BasePageClass {

    @FindBy (xpath = "//input[@id='cb-show-inactive' and @type='checkbox']")
    Button showInactiveBtn;

    @FindBy (xpath = "//input[@type='search' and @class='form-control']")
    InputField filterField;

    @FindBy (xpath = ".//*[@id='btn-create-new']")
    Button createNewAdminBtn;

    @FindBy (xpath = ".//*[@id='btn-activate-checked']")
    Button activateBtn;

    @FindBy (xpath = ".//*[@id='btn-deactivate-checked']")
    Button deactivateBtn;

    @FindBy (xpath = ".//*[@id='btn-remove-checked']")
    Button deleteBtn;

    @FindBy(xpath = "//table")
    Table table;

    @FindBy(xpath = ".//*[@id='admin-edit']/div/div/div[2]/div[2]/div/fieldset/div/div[1]/select")
    Selection crtNewAdmRoleField;

    @FindBy(xpath = ".//*[@id='admin-edit']/div/div/div[2]/div[2]/div/fieldset/div/div[2]/input")
    InputField crtNewAdmNameField;

    @FindBy(xpath = ".//*[@id='admin-edit']/div/div/div[2]/div[2]/div/fieldset/div/div[3]/input")
    InputField crtNewAdmEmailField;

    @FindBy(xpath = ".//*[@id='txt-admin-password']")
    InputField crtNewAdmTempPassField;

    @FindBy(xpath = ".//*[@id='password-container']/div[2]/input")
    InputField crtNewAdmTempPassReEnterField;

    @FindBy(xpath = ".//*[@id='admin-edit']/div/div/div[3]/button[1]")
    Button crtNewAdmCancelButton;

    @FindBy(xpath = ".//*[@id='admin-edit']/div/div/div[3]/button[2]")
    Button crtNewAdmSaveButton;

    /*@FindBy(xpath = "//div[4]/div/div/div[3]/button[2]")
    private Button deactivationConfirmBtn;

    @FindBy(css = ".btn.btn-default")
    private Button deactivationCancelBtn;*/

    @FindBy(xpath = "//div[@class='bootbox modal fade in']//div[@class='modal-dialog']")
    ModalConfirmWindow modalConfirmWindow;

    //@FindBy(xpath = ".//*[@id='tbl-group-admins']//span/span")
    //private WebElement selectAllCheckbox;

    public AdministratorsPage()
    {
        super();
        setPageUrl("https://control.goodsync.com/ui/administrators");
    }

    @Step("Apply filter")
    public void applyFilter(String searchRequest)
    {
        filterField.clear();
        filterField.inputText(searchRequest);
        waitForJSload();
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
        crtNewAdmRoleField.selectByVisibleText(adminRole);
        crtNewAdmNameField.inputText(name);
        crtNewAdmEmailField.inputText(email);
        crtNewAdmTempPassField.inputText(tempPass);
        crtNewAdmTempPassReEnterField.inputText(reEnterTempPass);
    }

    @Step("create new administrator")
    public void createNewAdministrator(String role, String name, String email, String pass1, String pass2)
    {
        createNewAdminBtn.click();
        fillNewAdminFormUp(role, name, email, pass1, pass2);
        crtNewAdmSaveButton.click();
        waitForJSload();
    }

    private void selectElementInTable(String elementName) //TODO total piece of s... find more elegant solution
    {
        table.selectElementCheckboxInTable(elementName);
        // using only email for search
    }

    // TODO add activation func
    @Step("Deactivate administrator")
    public void deactivateAdmin(String adminEmail)
    {
        table.selectElementCheckboxInTable(adminEmail);
        deactivateBtn.click();
        modalConfirmWindow.confirmAction();
        waitForJSload();
    }

    @Step("Activate admin")
    public void activateAdmin(String adminEmail){
        showInactive();
        table.selectElementCheckboxInTable(adminEmail);
        activateBtn.click();
        modalConfirmWindow.confirmAction();
        waitForJSload();
    }

    @Step("Click on 'Show inactive' button")
    public void showInactive()
    {
        showInactiveBtn.click();
        waitForJSload();
    }

    @Step("Delete selected administrator")
    public void deleteAdmin(String name)
    {
        table.selectElementCheckboxInTable(name);
        deleteBtn.click();
        modalConfirmWindow.confirmAction();
        waitForJSload();
    }

    @Step("Delete all administrators presented in table")
    public void deleteAll()
    {
        table.selectAllInTable();
        deleteBtn.click();
        modalConfirmWindow.confirmAction();
        waitForJSload();
    }

    @Step("Deactivate all administrators presented in table")
    public void deactivateAll()
    {
        table.selectAllInTable();
        deactivateBtn.click();
        modalConfirmWindow.confirmAction();
    }

    public int countElementsInTable(String elementName){
        return table.countElementsInTable(elementName);
    }

    @Step("Sort by column name")
    public void sortBy(String elementName){
        table.sortBy(elementName);
        waitForJSload();
    }






}
