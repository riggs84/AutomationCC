package selenium.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.support.FindBy;
import selenium.Elements.Button;
import selenium.Elements.InputField;
import selenium.Elements.ModalConfirmWindow;
import selenium.Elements.Table;
import selenium.webtestsbase.BasePageClass;

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
        setPageUrl("https://control.goodsync.com/ui/computers");
    }

    private void fillCreateNewComputerFormUp(String computerOSname){
        computerOSnameInputField.inputText(computerOSname);
    }

    @Step("Create new computer form filling")
    public void createNewComputer(String computerOSname){
        createNewComputerBtn.click();
        fillCreateNewComputerFormUp(computerOSname);
        crtNewComputerSaveBtn.click();
        waitForJSload();
    }

    @Step("Apply filter")
    public void applyFilter(String searchRequest) {
        filterField.clear();
        filterField.inputText(searchRequest);
        waitForJSload();
    }

    @Step("Delete selected group")
    public void deleteComputer(String name){
        table.selectElementCheckboxInTable(name);
        deleteBtn.click();
        modalConfirmWindow.confirmAction();
        waitForJSload();
    }

    @Step("Sort table entries by column name")
    public void sortTableBy(String columnName){
        table.sortBy(columnName);
        waitForJSload();
    }

    @Step("Select all groups and delete them")
    public void deleteAllComputers(){
        table.selectAllInTable();
        deleteBtn.click();
        modalConfirmWindow.confirmAction();
        waitForJSload();
    }

    @Step("Click on 'show inactive' button")
    public void showInactive(){
        showInactiveBtn.click();
        waitForJSload();
    }

    @Step("Select group and click 'deactivate' button")
    public void deactivateComputer(String userName){
        table.selectElementCheckboxInTable(userName);
        deactivateBtn.click();
        modalConfirmWindow.confirmAction();
        waitForJSload();
    }

    @Step("Select group and click 'activate' button")
    public void activateComputer(String userName){
        table.selectElementCheckboxInTable(userName);
        activateBtn.click();
        modalConfirmWindow.confirmAction();
        waitForJSload();
    }

    @Step("Click cancel btn in user creation dlg")
    public void newComputerCreationCancelling(){
        crtNewComputerCancelBtn.click();
        waitForJSload();
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
