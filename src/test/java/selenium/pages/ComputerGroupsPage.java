package selenium.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.support.FindBy;
import selenium.Elements.Button;
import selenium.Elements.InputField;
import selenium.Elements.ModalConfirmWindow;
import selenium.Elements.Table;
import selenium.webtestbase.BasePageClass;

public class ComputerGroupsPage extends BasePageClass {

    @FindBy(xpath = ".//*[@id='tbl-groups_wrapper']//div[@class='togglebutton']/label/span")
    Button showInactiveBtn;

    @FindBy(xpath = ".//input[@type='search' and @class='form-control']")
    InputField filterInputField;

    @FindBy(id = "btn-create-new")
    Button crtNewComputerGroupBtn;

    @FindBy(id = "btn-activate-checked")
    Button activateBtn;

    @FindBy(id = "btn-deactivate-checked")
    Button deactivateBtn;

    @FindBy(id = "btn-remove-checked")
    Button deleteBtn;

    @FindBy(xpath = ".//*[@id='group-edit']//fieldset//input[@name='cgroup_name']")
    InputField computerGroupNameInputField;

    @FindBy(xpath = "//table")
    Table table;

    @FindBy(id = "group-edit")
    ModalConfirmWindow confirmBtns;

    @FindBy(xpath = ".//div[@class='modal-dialog']")
    ModalConfirmWindow modalConfirmWindow;

    public ComputerGroupsPage(){
        super();
        setPageUrl("/ui/computer-groups");
    }

    @Step("Create new computer group form filling with data: {computerGroupName}")
    public void createNewComputerGroup(String computerGroupName){
        crtNewComputerGroupBtn.click();
        computerGroupNameInputField.inputText(computerGroupName);
        confirmBtns.confirmAction();
        waitForPageLoad();
    }

    @Step("Apply filter for: {searchRequest}")
    public void applyFilter(String searchRequest) {
        filterInputField.clear();
        filterInputField.inputText(searchRequest);
        waitForPageLoad();
    }

    @Step("Delete {name} group")
    public void deleteComputerGroup(String name){
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
    public void deleteAllComputerGroups(){
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

    @Step("Select {groupName} computerGroup and click 'deactivate' button")
    public void deactivateComputerGroup(String groupName){
        table.selectElementCheckboxInTable(groupName);
        deactivateBtn.click();
        modalConfirmWindow.confirmAction();
        waitForPageLoad();
    }

    @Step("Select {userName} group and click 'activate' button")
    public void activateComputerGroup(String userName){
        table.selectElementCheckboxInTable(userName);
        activateBtn.click();
        modalConfirmWindow.confirmAction();
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

    public int countElementsInTable(String elementName){
        return table.countElementsInTable(elementName);
    }

}
