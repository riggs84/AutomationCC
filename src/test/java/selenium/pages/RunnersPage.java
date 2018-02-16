package selenium.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.support.FindBy;
import selenium.Elements.InputField;
import selenium.Elements.Table;
import selenium.webtestbase.BasePageClass;

public class RunnersPage extends BasePageClass {

    @FindBy(xpath = "//input[@type='search' abd @class='form-control']")
    InputField filterInputField;

    @FindBy(xpath = "//table")
    Table table;

    public RunnersPage(){
        super();
        setPageUrl("/ui/job-runners");
    }

    @Step("Find {name} in runners table on Runners page")
    public boolean isPresentInTable(String name){
        return table.tableContainsElements(name);
    }

    @Step("Apply filter for {searchRequest}")
    public RunnersPage applyFilter(String searchRequest){
        filterInputField.clear();
        filterInputField.inputText(searchRequest);
        waitForPageLoad();
        return new RunnersPage();
    }
}
