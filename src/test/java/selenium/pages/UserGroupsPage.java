package selenium.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.support.FindBy;
import selenium.Elements.Button;
import selenium.Elements.InputField;
import selenium.Elements.ModalConfirmWindow;
import selenium.Elements.Table;
import selenium.Helpers.BasePageClass;

public class UserGroupsPage extends BasePageClass {

    @FindBy(xpath = ".//*[@id='tbl-groups_wrapper']//div[@class='togglebutton']/label/span")
    Button showInactiveBtn;

    @FindBy(xpath = "//input[@type='search' and @class='form-control']")
    InputField filterField;

    @FindBy(id = "btn-create-new")
    Button createNewUserGroupBtn;

    @FindBy(id = "btn-activate-checked")
    Button activateBtn;

    @FindBy(id = "btn-deactivate-checked")
    Button deactivateBtn;

    @FindBy(id = "btn-remove-checked")
    Button deleteBtn;

    @FindBy(xpath = "//table")
    Table table;

    @FindBy(xpath = ".//*[@id='group-edit']//fieldset//input[@name='ugroup_name']")
    InputField userGroupNameInputField;

    @FindBy(xpath = ".//*[@id='group-edit']//fieldset//input[@name='ugroup_os_name']")
    InputField userGroupOSnameInputField;

    @FindBy(xpath = ".//*[@id='group-edit']/div/div/div[3]/button[2]")
    Button crtNewUserGroupSaveBtn;

    @FindBy(xpath = ".//*[@id='group-edit']/div/div/div[3]/button[1]")
    Button crtNewUserGroupCancelBtn;

    @FindBy(xpath = "//div[@class='bootbox modal fade in']//div[@class='modal-content']")
    ModalConfirmWindow modalConfirmWindow;

    public UserGroupsPage(){
        super();
        setPageUrl("/ui/user-groups");
    }

    private void fillCreateNewUsersGroupFormUp(String userGroupName, String userGroupOSname){
        userGroupNameInputField.inputText(userGroupName);
        userGroupOSnameInputField.inputText(userGroupOSname);
    }

    @Step("Create new group with data input: {userGroupName}, {userGroupOSname}")
    public void createNewUserGroup(String userGroupName, String userGroupOSname){
        createNewUserGroupBtn.click();
        fillCreateNewUsersGroupFormUp(userGroupName, userGroupOSname);
        crtNewUserGroupSaveBtn.click();
        waitForPageLoad();
    }

    @Step("Apply filter for: {searchRequest}")
    public void applyFilter(String searchRequest) {
        filterField.clear();
        filterField.inputText(searchRequest);
        waitForPageLoad();
    }

    @Step("Delete {name} group")
    public void deleteGroup(String name){
        table.selectElementCheckboxInTable(name);
        deleteBtn.click();
        modalConfirmWindow.confirmAction();
        waitForPageLoad();
    }

    @Step("Sort table entries by column {columnName}")
    public void sortTableBy(String columnName){
        table.sortBy(columnName);
        waitForPageLoad();
    }

    @Step("Select all groups and delete them")
    public void deleteAllGroups(){
        table.selectAllInTable();
        deleteBtn.click();
        modalConfirmWindow.confirmAction();
        waitForPageLoad();
    }

    @Step("Click on 'show inactive' button")
    public void showInactive(){
        showInactiveBtn.click();
        waitForPageLoad();
    }

    @Step("Select {userName} group and click 'deactivate' button")
    public UserGroupsPage deactivateGroup(String userName){
        table.selectElementCheckboxInTable(userName);
        deactivateBtn.click();
        modalConfirmWindow.confirmAction();
        waitForPageLoad();
        return new UserGroupsPage();
    }

    @Step("Select {userName} group and click 'activate' button")
    public void activateGroup(String userName){
        table.selectElementCheckboxInTable(userName);
        activateBtn.click();
        modalConfirmWindow.confirmAction();
        waitForPageLoad();
    }

    @Step("Click cancel btn in user creation dlg")
    public void newUserGroupCreationCancelling(){
        crtNewUserGroupCancelBtn.click();
        waitForPageLoad();
    }

    public int countAllElementsInTable(){
        return table.countAllElementsInTable();
    }

    public boolean isSortedAscendant(String elementName)
    {
        return table.checkAscendantOrderInTable(elementName);
    }

    public boolean isSortedDescendant(String elementName)
    {
        return table.checkDescendantOrderInTable(elementName);
    }

    public boolean checkElementPresentInTable(String elementName) {
        return table.tableContainsElements(elementName);
    }

    public int countElementsInTableByName(String elementName){
        return table.countElementsInTable(elementName);
        //countElementsInTable(tableBody, elementName);
    }


}
