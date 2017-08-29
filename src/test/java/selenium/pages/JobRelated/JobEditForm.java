package selenium.pages.JobRelated;

import org.openqa.selenium.support.FindBy;
import selenium.Elements.Button;
import selenium.Elements.InputField;
import selenium.Elements.Link;
import selenium.Elements.TextField;
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
    Button crtNewAdmCancelButton;

    @FindBy(xpath = ".//*[@id='job-edit']/div/div/div[3]/button[2]")
    Button crtNewAdmSaveButton;

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

    GeneralTab generalTab;

    public JobEditForm(){
        super();
    }

}
