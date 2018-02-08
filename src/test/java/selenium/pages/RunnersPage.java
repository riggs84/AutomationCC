package selenium.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.support.FindBy;
import selenium.Elements.Table;
import selenium.webtestbase.BasePageClass;

public class RunnersPage extends BasePageClass {

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
}
