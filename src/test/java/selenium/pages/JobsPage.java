package selenium.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.support.FindBy;
import selenium.Elements.Button;
import selenium.Elements.InputField;
import selenium.Elements.ModalConfirmWindow;
import selenium.Elements.Table;
import selenium.pages.JobRelated.JobEditForm;
import selenium.pages.entities.Job;
import selenium.webtestbase.BasePageClass;

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

    @Step("Apply filter for: {searchRequest}")
    public JobsPage applyFilter(String searchRequest) {
        filterField.clear();
        filterField.inputText(searchRequest);
        waitForPageLoad();
        return new JobsPage();
    }

    @Step("Select {jobsName} job by checking its checkbox in table")
    public JobsPage selectJobInTable(String jobsName){
        table.selectElementCheckboxInTable(jobsName);
        return this;
    }
    public boolean isSortedAscendant(String elementName)
    {
        return table.checkAscendantOrderInTable(elementName);
    }

    public boolean isSortedDescendant(String elementName)
    {
        return table.checkDescendantOrderInTable(elementName);
    }

    @Step("Find {jobName} job in table on Jobs page")
    public boolean isJobPresentInTable(String jobName){
        return table.tableContainsElements(jobName);
    }

    @Step("Deactivate {jobName} Job")
    public JobsPage deactivateJob(String jobName) {
        table.selectElementCheckboxInTable(jobName);
        deactivateBtn.click();
        modalConfirmWindow.confirmAction();
        waitForPageLoad();
        return new JobsPage();
    }

    @Step("Activate {jobName} job")
    public JobsPage activateJob(String jobName){
        table.selectElementCheckboxInTable(jobName);
        activateBtn.click();
        modalConfirmWindow.confirmAction();
        waitForPageLoad();
        return new JobsPage();
    }

    @Step("Click on 'Show inactive' button")
    public void showInactive() {
        showInactiveBtn.click();
        waitForPageLoad();
    }

    @Step("Delete {name} Job")
    public JobsPage deleteJob(String name) {
        table.selectElementCheckboxInTable(name);
        deleteBtn.click();
        modalConfirmWindow.confirmAction();
        waitForPageLoad();
        return new JobsPage();
    }

    @Step("Delete all jobs presented in table")
    public JobsPage deleteAllJobs() {
        table.selectAllInTable();
        deleteBtn.click();
        modalConfirmWindow.confirmAction();
        waitForPageLoad();
        return new JobsPage();
    }

    @Step("Deactivate all jobs presented in table")
    public void deactivateAllJobs() {
        table.selectAllInTable();
        deactivateBtn.click();
        modalConfirmWindow.confirmAction();
    }

    public int countElementsInTable(String elementName){
        return table.countElementsInTable(elementName);
    }

    @Step("Sort by column {elementName}")
    public void sortBy(String elementName){
        table.sortBy(elementName);
        waitForPageLoad();
    }

    @Step("Click on create new job button and set job")
    public JobEditForm createNewJob(){
        createNewJobBtn.click();
        waitForModalWindowOpen();
        return new JobEditForm();
    }

    @Step("Click on the link {linkName} in table and open related page")
    public Job clickOnTheJobNameInTable(String linkName){
        table.clickOnTheLinkBy(linkName, linkName);
        waitForPageLoad();
        return new Job();
    }

    public String getLinkAddress(String row, String name){
        return table.getLinkAddressWithValidation(row, name);
    }


}
