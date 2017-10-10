package selenium.pages.JobRelated;

import org.openqa.selenium.support.FindBy;
import selenium.Elements.InputField;
import selenium.Helpers.BasePageClass;

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

    public ScriptsTab setPreAnalyzeFieldToValue(String preAnalyzed){
        preAnalyzeInputField.inputText(preAnalyzed);
        return this;
    }

    public ScriptsTab setPostAnalyzeFieldToValue(String postAnalyze){
        postAnalyzeInputField.inputText(postAnalyze);
        return this;
    }

    public ScriptsTab setPostSyncFieldToValue(String postSync){
        postSyncInputField.inputText(postSync);
        return this;
    }
}
