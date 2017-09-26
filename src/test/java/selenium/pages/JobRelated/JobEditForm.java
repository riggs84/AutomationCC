package selenium.pages.JobRelated;

import org.openqa.selenium.support.FindBy;
import selenium.Elements.Button;
import selenium.Elements.InputField;
import selenium.Elements.Link;
import selenium.Elements.TextField;
import selenium.pages.JobsPage;
import selenium.webtestsbase.BasePageClass;

import java.util.prefs.BackingStoreException;

public class JobEditForm extends BasePageClass {

    @FindBy(xpath = ".//*[@id='job-edit']//fieldset//input[@name='job_name']")
    InputField jobNameInputField;

    @FindBy(xpath = ".//*[@id='job-edit']//fieldset//input[@name='dir_l']")
    TextField dirLeftTextField;

    @FindBy(xpath = ".//*[@id='job-edit']//fieldset//input[@name='dir_r']")
    TextField dirRightTextField;

    @FindBy(xpath = ".//*[@id='job-edit']//fieldset//span[@class='input-group-btn']/div")
    TextField jobDirectionIcon;

    @FindBy(xpath = ".//*[@id='job-edit']//fieldset//input[@name='description']")
    InputField jobDescriptionInputField;

    @FindBy(xpath = ".//*[@id='job-edit']/div/div/div[3]/button[1]")
    Button crtNewJobCancelButton;

    @FindBy(xpath = ".//*[@id='job-edit']/div/div/div[3]/button[2]")
    Button crtNewJobSaveButton;

    @FindBy(xpath = ".//*[@id='panel-options']//a[contains(text(),'Left Folder')]")
    Link leftFolder;

    @FindBy(xpath = ".//*[@id='panel-options']//a[contains(text(),'Right Folder')]")
    Link rightFolder;

    @FindBy(xpath = ".//*[@id='panel-options']//a[contains(text(),'General')]")
    Link general;

    @FindBy(xpath = ".//*[@id='panel-options']//a[contains(text(),'Filters')]")
    Link filters;

    @FindBy(xpath = ".//*[@id='panel-options']//a[contains(text(),'Auto')]")
    Link auto;

    @FindBy(xpath = ".//*[@id='panel-options']//a[contains(text(),'Scripts')]")
    Link scripts;

    @FindBy(xpath = ".//*[@id='panel-options']//a[contains(text(),'Advanced')]")
    Link advanced;

    LeftFolderTab leftFolderTab;
    RightFolderTab rightFolderTab;


    public JobEditForm(){
        super();
        leftFolderTab = new LeftFolderTab();
        rightFolderTab = new RightFolderTab();
    }

    public JobEditForm setJobNameAndDescr(String jobName, String descr){
        jobNameInputField.inputText(jobName);
        jobDescriptionInputField.inputText(descr);
        return this;
    }

    public GeneralTab clickGeneralTabLink(){
        general.clickLink();
        return new GeneralTab();
    }

    public FiltersTab clickFiltersTabLink(){
        filters.clickLink();
        return new FiltersTab();
    }

    public ScriptsTab clickScriptsTabLink(){
        scripts.clickLink();
        return new ScriptsTab();
    }

    public JobsPage saveJob(){
        crtNewJobSaveButton.click();
        waitForJSload();
        return new JobsPage();
    }

    public AdvancedTab clickAdvancedTabLink(){
        advanced.clickLink();
        return new AdvancedTab();
    }

    public JobEditForm clickLeftFolderLink(){
        leftFolder.clickLink();
        return this;
    }

    public LeftFolderTab selectFSonLeftSideByName(String fsName){
        leftFolderTab.selectFileSystem(fsName);
        return new LeftFolderTab();
    }

    public JobsPage clickFormCancelButton(){
        crtNewJobCancelButton.click();
        return new JobsPage();
    }

    public RightFolderTab selectFSonRightSideByName(String fsName){
        rightFolderTab.selectFileSystem(fsName);
        return new RightFolderTab();
    }

    public JobEditForm clickRightFolderLink(){
        rightFolder.clickLink();
        return this;
    }

    public AutoTab clickAutoTabLink(){
        auto.clickLink();
        return new AutoTab();
    }



}
