package selenium.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.support.FindBy;
import selenium.Elements.Button;
import selenium.Elements.InputField;
import selenium.Elements.ModalConfirmWindow;
import selenium.Elements.Table;
import selenium.webtestbase.BasePageClass;

public class ComputersPage extends BasePageClass {

    @FindBy(xpath = ".//*[@id='tbl-computers_wrapper']//div[@class='togglebutton']/label/span")
    Button showInactiveBtn;

    @FindBy(xpath = "//input[@type='search' and @class='form-control']")
    InputField filterField;

    @FindBy(id = "btn-create-new")
    Button createNewComputerBtn;

    @FindBy(id = "btn-activate-checked")
    Button activateBtn;

    @FindBy(id = "btn-deactivate-checked")
    Button deactivateBtn;

    @FindBy(id = "btn-remove-checked")
    Button deleteBtn;

    @FindBy(xpath = "//table")
    Table table;

    @FindBy(xpath = "//div[@class='bootbox modal fade in']//div[@class='modal-content']")
    ModalConfirmWindow modalConfirmWindow;

    @FindBy(xpath = ".//*[@id='computer-edit']/div/div/div[3]/button[2]")
    Button crtNewComputerSaveBtn;

    @FindBy(xpath = ".//*[@id='computer-edit']/div/div/div[3]/button[1]")
    Button crtNewComputerCancelBtn;

    @FindBy(xpath = ".//*[@id='computer-edit']//fieldset//input[@name='computer_os_name']")
    InputField computerOSnameInputField;

    public ComputersPage(){
        super();
        setPageUrl("/ui/computers");
    }

    private void fillCreateNewComputerFormUp(String computerOSname){
        computerOSnameInputField.inputText(computerOSname);
    }

    @Step("Create new computer form filling with data: {computerOSname}")
    public void createNewComputer(String computerOSname){
        createNewComputerBtn.click();
        fillCreateNewComputerFormUp(computerOSname);
        crtNewComputerSaveBtn.click();
        waitForPageLoad();
    }

    @Step("Apply filter for: {searchRequest}")
    public void applyFilter(String searchRequest) {
        filterField.clear();
        filterField.inputText(searchRequest);
        waitForPageLoad();
    }

    @Step("Delete {name} computer")
    public void deleteComputer(String name){
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

    @Step("Select all computers and delete them")
    public void deleteAllComputers(){
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

    @Step("Select {userName} computer and click 'deactivate' button")
    public void deactivateComputer(String userName){
        table.selectElementCheckboxInTable(userName);
        deactivateBtn.click();
        modalConfirmWindow.confirmAction();
        waitForPageLoad();
    }

    @Step("Select {userName} group and click 'activate' button")
    public void activateComputer(String userName){
        table.selectElementCheckboxInTable(userName);
        activateBtn.click();
        modalConfirmWindow.confirmAction();
        waitForPageLoad();
    }

    @Step("Click cancel btn in user creation dlg")
    public void newComputerCreationCancelling(){
        crtNewComputerCancelBtn.click();
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

    public String getValueInCell(String columnName, String elementName){
      return table.getCellValueBy(elementName, columnName);
    }

}
