package selenium.pages;

import io.qameta.allure.Step;
import selenium.Elements.Button;
import selenium.Elements.InputField;
import selenium.Elements.ModalConfirmWindow;
import selenium.Elements.Table;
import selenium.webtestsbase.BasePageClass;
import org.openqa.selenium.support.FindBy;

/**
 * Created by Victor on 11.07.2017.
 */
public class UsersPage extends BasePageClass {

    @FindBy(xpath = ".//*[@id='tbl-users_wrapper']//div[@class='togglebutton']/label/span")
    Button showInactiveBtn;

    @FindBy(xpath = "//input[@type='search' and @class='form-control']")
    InputField filterField;

    @FindBy(id = "btn-create-new")
    Button createNewUserBtn;

    @FindBy(id = "btn-activate-checked")
    Button activateBtn;

    @FindBy(id = "btn-deactivate-checked")
    Button deactivateBtn;

    @FindBy(id = "btn-remove-checked")
    Button deleteBtn;

    /*@FindBy(xpath = ".//*[@id='tbl-users']//span/span")
    WebElement selectAllCheckbox;*/

    @FindBy(xpath = "//*[@id='user-edit']//fieldset//input[@name='user_os_name']")
    InputField crtNewUserOSnameField;

    @FindBy(xpath = "//*[@id='user-edit']//fieldset//input[@name='user_full_name']")
    InputField crtNewUserFullNameField;

    @FindBy(xpath = "//*[@id='user-edit']//fieldset//input[@name='user_email']")
    InputField crtNewUserEmailField;

    @FindBy(xpath = ".//*[@id='user-edit']/div/div/div[3]/button[2]")
    Button crtNewUserSaveBtn;

    @FindBy(xpath = ".//*[@id='user-edit']/div/div/div[3]/button[1]")
    Button crtNewUserCancelBtn;

    @FindBy (xpath = "//table")
    Table table;

    @FindBy(xpath = "//div[@class='bootbox modal fade in']//div[@class='modal-content']")
    ModalConfirmWindow modalConfirmWindow;

    public UsersPage()
    {
        super();
        //setPageUrl("http://192.168.1.214:8080/ui/users");
        setPageUrl("https://control.goodsync.com/ui/users");
    }

    private void fillNewUserCreationFormUp(String osName, String fullName, String email)
    {
        crtNewUserOSnameField.inputText(osName);
        crtNewUserFullNameField.inputText(fullName);
        crtNewUserEmailField.inputText(email);
    }

    @Step("Create new user")
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

    @Step("Apply filter")
    public void applyFilter(String searchRequest)
    {
        filterField.clear();
        filterField.inputText(searchRequest);
    }

    @Step("Delete selected user")
    public void deleteUser(String name){
        table.selectElementCheckboxInTable(name);
        deleteBtn.click();
        modalConfirmWindow.confirmAction();
    }

    @Step("Sort table entries by column name")
    public void sortTableBy(String columnName){
        table.sortBy(columnName);
        waitForJSload();
    }

    public boolean hasElementsInTable(String elementName)
    {
        return table.tableContainsElements(elementName);
    }

    //TODO not working yet
    /*public boolean hasOtherElementsInTableExcept(String elementName)
    {
        return table.tableContainsElementsExcept(elementName);
    }*/

    public boolean isSortedAscendant(String elementName)
    {
        return table.checkAscendantOrderInTable(elementName);
    }

    public boolean isSortedDescendant(String elementName)
    {
        return table.checkDescendantOrderInTable(elementName);
    }

    @Step("Select all users and delete them")
    public void deleteAllusers(){
        table.selectAllInTable();
        deleteBtn.click();
        modalConfirmWindow.confirmAction();
        waitForJSload();
    }

    public int countAllElementsInTable(){
        return table.countAllElementsInTable();
    }

    @Step("Click on 'show inactive' button")
    public void showInactive(){
        showInactiveBtn.click();
        waitForJSload();
    }

    @Step("Select user and click 'deactivate' button")
    public void deactivateUser(String userName){
        table.selectElementCheckboxInTable(userName);
        deactivateBtn.click();
        modalConfirmWindow.confirmAction();
        waitForJSload();
    }

    @Step("Select user and click 'activate' button")
    public void activateUser(String userName){
        table.selectElementCheckboxInTable(userName);
        activateBtn.click();
        modalConfirmWindow.confirmAction();
        waitForJSload();
    }

    @Step("Click cancel btn in user creation dlg")
    public void newUserCreationCancelling(){
        crtNewUserCancelBtn.click();
        waitForJSload();
    }
}
