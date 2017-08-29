package selenium.pages;

import org.openqa.selenium.support.FindBy;
import selenium.Elements.Button;
import selenium.Elements.InputField;
import selenium.Elements.Table;
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

    public JobsPage(){
        super();
        setPageUrl("/ui/jobs");
    }
}
