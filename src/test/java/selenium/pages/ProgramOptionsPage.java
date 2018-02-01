package selenium.pages;

import org.openqa.selenium.support.FindBy;
import selenium.Elements.Button;
import selenium.webtestbase.BasePageClass;

public class ProgramOptionsPage extends BasePageClass {

    @FindBy(xpath = ".//*[@id='btn-edit-global-job-options']")
    Button editProgramOptionsBtn;



     public ProgramOptionsPage(){
         super();
     }
}
