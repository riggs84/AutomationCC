package selenium.pages.entities;

import org.openqa.selenium.support.FindBy;
import selenium.Elements.Button;
import selenium.Elements.Link;
import selenium.webtestsbase.BasePageClass;

public class Job extends BasePageClass {

    @FindBy(xpath = ".//*[@id='btn-edit-job']")
    Button editJobBtn;

    @FindBy(xpath = ".//*[@id='btn-job-clone']")
    Button cloneJobBtn;

    @FindBy(xpath = ".//*[@id='btn-send-spop']")
    Button specialOpsBtn;

    @FindBy(xpath = ".//*[@id='btn-job-run']")
    Button runJobBtn;

    @FindBy(xpath = ".//*[@id='a-show-job-cl']")
    Link commandLine;
    

    public Job(){
        super();
    }
}
