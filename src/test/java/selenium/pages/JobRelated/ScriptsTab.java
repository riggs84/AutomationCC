package selenium.pages.JobRelated;

import org.openqa.selenium.support.FindBy;
import selenium.Elements.InputField;
import selenium.webtestsbase.BasePageClass;

public class ScriptsTab extends BasePageClass {

    @FindBy(xpath = ".//*[@id='tab-scripts']//input[@name='pre-analyze-action']")
    InputField preAnalyzeInputField;

    @FindBy(xpath = ".//*[@id='tab-scripts']//input[@name='post-analyze-action']")
    InputField postAnalyzeInputField;

    @FindBy(xpath = ".//*[@id='tab-scripts']//input[@name='post-sync-action']")
    InputField postSyncInputField;

    public ScriptsTab(){
        super();
    }
}
