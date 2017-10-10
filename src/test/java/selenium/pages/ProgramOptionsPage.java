package selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import selenium.Elements.Button;
import selenium.pages.ProgramOptionsRelated.EditProgramOptionsForm;
import selenium.Helpers.BasePageClass;

public class ProgramOptionsPage extends BasePageClass {

    @FindBy(id = "tbl-company-view-global-options")
    WebElement optionsTable;

    @FindBy(id = "btn-edit-global-job-options")
    Button editProgramOptionsBtn;

    public ProgramOptionsPage(){
        super();
    }

    public String getSettingValueByName(String setting){
        WebElement element = optionsTable.findElement(By.xpath("//tbody//tr[td//text()[contains(., '"+ setting +"')]]"));
        WebElement result = element.findElement(By.xpath("//td[2]"));
        return result.getText();
    }

    public EditProgramOptionsForm editProgramOptions(){
        editProgramOptionsBtn.click();
        return new EditProgramOptionsForm();
    }
}
