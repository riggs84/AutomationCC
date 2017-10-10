package selenium.pages.ProgramOptionsRelated;

import org.openqa.selenium.support.FindBy;
import selenium.Elements.Button;
import selenium.Elements.Link;
import selenium.pages.JobRelated.FiltersTab;
import selenium.pages.ProgramOptionsPage;
import selenium.Helpers.BasePageClass;

public class EditProgramOptionsForm extends BasePageClass {

    @FindBy(xpath = ".//*[@id='panel-options']//a[@href='#tab-general']")
    Link generalLink;

    @FindBy(xpath = ".//*[@id='panel-options']//a[@href='#tab-filters']")
    Link filtersLink;

    @FindBy(xpath = ".//*[@id='panel-options']//a[@href='#tab-connection']")
    Link connectionLink;

    @FindBy(xpath = ".//*[@id='global-options-edit']//button[@type='submit']")
    Button saveBtn;

    @FindBy(xpath = ".//*[@id='global-options-edit']//button[@type='button']")
    Button cancelBtn;

    public EditProgramOptionsForm(){
        super();
    }

    public ProgramOptionsPage saveChanges(){
        saveBtn.click();
        return new ProgramOptionsPage();
    }

    public ProgramOptionsPage cancelChanges(){
        cancelBtn.click();
        return new ProgramOptionsPage();
    }

    public GeneralTab clickGeneralLink(){
        generalLink.clickLink();
        return new GeneralTab();
    }

    public FiltersTab clickFiltersLink(){
        filtersLink.clickLink();
        return new FiltersTab();
    }

    public ConnectionTab clickConnectionLink(){
        connectionLink.clickLink();
        return new ConnectionTab();
    }
}
