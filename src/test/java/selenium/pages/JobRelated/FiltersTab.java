package selenium.pages.JobRelated;

import org.openqa.selenium.support.FindBy;
import selenium.Elements.Button;
import selenium.Elements.CheckBox;
import selenium.Elements.InputField;
import selenium.webtestsbase.BasePageClass;

public class FiltersTab extends BasePageClass {

    @FindBy(xpath = ".//*[@id='txt_new_filter']")
    InputField enterFilterInputFiled;

    @FindBy(xpath = ".//*[@id='btn_exclude_add']")
    Button excludeAddBtn;

    @FindBy(xpath = ".//*[@id='btn_include_add']")
    Button includeAddBtn;

    @FindBy(xpath = ".//*[@id='tab-filters']//label[contains(text(),'Exclude empty folders')]")
    CheckBox excludeEmptyFoldersCheckBox;

    @FindBy(xpath = ".//*[@id='tab-filters']//label[contains(text(),'Exclude Hidden files and folders')]")
    CheckBox excludeHiddenCheckBox;

    @FindBy(xpath = ".//*[@id='tab-filters']//label[contains(text(),'Exclude System files and folders')]")
    CheckBox excludeSysFilesFoldersCheckBox;

    public FiltersTab(){
        super();
    }
}
