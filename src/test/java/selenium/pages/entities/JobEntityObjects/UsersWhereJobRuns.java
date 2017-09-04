package selenium.pages.entities.JobEntityObjects;

import org.openqa.selenium.support.FindBy;
import selenium.Elements.Button;
import selenium.Elements.InputField;
import selenium.Elements.Table;
import selenium.webtestsbase.BasePageClass;

public class UsersWhereJobRuns extends BasePageClass {

    @FindBy(xpath = ".//*[@id='tbl-select-users_filter']//input[@class='form-control input-sm']")
    InputField filterInputField;

    @FindBy(id = "tbl-select-users")
    Table table;

    @FindBy(xpath = ".//*[@id='users-select-frm']//div[@class='modal-footer']//button[1]")
    Button cancelBtn;

    @FindBy(xpath = ".//*[@id='users-select-frm']//div[@class='modal-footer']//button[2]")
    Button saveBtn;

    public UsersWhereJobRuns(){
        super();
    }
}
