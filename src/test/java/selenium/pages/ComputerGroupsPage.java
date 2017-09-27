package selenium.pages;

import org.openqa.selenium.support.FindBy;
import selenium.Elements.Button;
import selenium.Elements.InputField;
import selenium.Elements.Table;
import selenium.webtestsbase.BasePageClass;

public class ComputerGroupsPage extends BasePageClass {

    @FindBy(xpath = ".//*[@id='tbl-groups_wrapper']//div[@class='togglebutton']/label/span")
    Button showInactiveBtn;

    @FindBy(xpath = "//input[@type='search' and @class='form-control']")
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

    public ComputerGroupsPage(){
        super();
        setPageUrl("/ui/computer-groups");
    }
}
