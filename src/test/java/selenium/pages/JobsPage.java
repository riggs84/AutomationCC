package selenium.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.support.FindBy;
import selenium.Elements.Button;
import selenium.Elements.InputField;
import selenium.Elements.ModalConfirmWindow;
import selenium.Elements.Table;
import selenium.pages.JobRelated.JobEditForm;
import selenium.webtestsbase.BasePageClass;

public class JobsPage extends BasePageClass {

    @FindBy (xpath = "//input[@type='search' and @class='form-control']")
    InputField filterField;

    @FindBy (xpath = ".//*[@id='tbl-jobs_wrapper']//div[@class='togglebutton']/label/span")
    Button showInactiveBtn;

    @FindBy (xpath = ".//*[@id='btn-create-new']")
    Button createNewJobBtn;

    @FindBy (xpath = ".//*[@id='btn-activate-checked']")
    Button activateBtn;

    @FindBy (xpath = ".//*[@id='btn-deactivate-checked']")
    Button deactivateBtn;

    @FindBy (xpath = ".//*[@id='btn-remove-checked']")
    Button deleteBtn;

    @FindBy(xpath = "//table")
    Table table;

    @FindBy(xpath = "//div[@class='bootbox modal fade in']//div[@class='modal-dialog']")
    ModalConfirmWindow modalConfirmWindow;

    public JobsPage(){
        super();
        setPageUrl("/ui/jobs");
    }

    @Step("Click cancel btn in create new admin form")
    public void cancelingJobCreation(){
        createNewJobBtn.click();
    }

    @Step("Apply filter")
    public JobsPage applyFilter(String searchRequest) {
        filterField.clear();
        filterField.inputText(searchRequest);
        waitForJSload();
        return new JobsPage();
    }

    public boolean isSortedAscendant(String elementName)
    {
        return table.checkAscendantOrderInTable(elementName);
    }

    public boolean isSortedDescendant(String elementName)
    {
        return table.checkDescendantOrderInTable(elementName);
    }

    @Step("Deactivate Job")
    public void deactivateJob(String JobName)
    {
        table.selectElementCheckboxInTable(JobName);
        deactivateBtn.click();
        modalConfirmWindow.confirmAction();
        waitForJSload();
    }

    @Step("Activate job")
    public void activateJob(String jobName){
        table.selectElementCheckboxInTable(jobName);
        activateBtn.click();
        modalConfirmWindow.confirmAction();
        waitForJSload();
    }

    @Step("Click on 'Show inactive' button")
    public void showInactive() {
        showInactiveBtn.click();
        waitForJSload();
    }

    @Step("Delete selected Job")
    public void deleteAdmin(String name) {
        table.selectElementCheckboxInTable(name);
        deleteBtn.click();
        modalConfirmWindow.confirmAction();
        waitForJSload();
    }

    @Step("Delete all jobs presented in table")
    public void deleteAll() {
        table.selectAllInTable();
        deleteBtn.click();
        modalConfirmWindow.confirmAction();
        waitForJSload();
    }

    @Step("Deactivate all jobs presented in table")
    public void deactivateAll() {
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

    @Step("Click on create new job button and set job")
    public JobEditForm createNewJob(){
        createNewJobBtn.click();
        return new JobEditForm();
    }

    @Step("Click on link in table and open related page")
    public void clickOnTheLink(String row, String link){
        table.clickOnTheLinkBy(row, link);
    }

    public String getLinkAddress(String row, String name){
        return table.getLinkAddressWithValidation(row, name);
    }


}
