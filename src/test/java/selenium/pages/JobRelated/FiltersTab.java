package selenium.pages.JobRelated;

import org.openqa.selenium.support.FindBy;
import selenium.Elements.Button;
import selenium.Elements.CheckBox;
import selenium.Elements.InputField;
import selenium.webtestbase.BasePageClass;

public class FiltersTab extends BasePageClass {

    //TODO add methods to delete created filters
    @FindBy(xpath = ".//*[@id='txt_new_filter']")
    InputField enterFilterInputFiled;

    @FindBy(xpath = ".//*[@id='btn_exclude_add']")
    Button excludeAddBtn;

    @FindBy(xpath = ".//*[@id='btn_include_add']")
    Button includeAddBtn;

    @FindBy(xpath = ".//*[@id='tab-filters']/div/div[3]/div/label/span/span")
    CheckBox excludeEmptyFoldersCheckBox;

    @FindBy(xpath = ".//*[@id='tab-filters']/div/div[4]/div/label/span/span")
    CheckBox excludeHiddenCheckBox;

    @FindBy(xpath = ".//*[@id='tab-filters']/div/div[5]/div/label/span/span")
    CheckBox excludeSysFilesFoldersCheckBox;

    public FiltersTab(){
        super();
    }

    private void setFilterInputFieldToValue(String filterRequest){
        enterFilterInputFiled.inputText(filterRequest);
    }

    public FiltersTab setExcludeEmptyFoldersCheckBoxToValue(boolean value){
        excludeEmptyFoldersCheckBox.setCheckbox(value);
        return this;
    }

    public FiltersTab setExcludeHiddenCheckBoxToValue(boolean value){
        excludeHiddenCheckBox.setCheckbox(value);
        return this;
    }

    public FiltersTab setExcludeSysFilesAndFoldersCheckBoxToValue(boolean value){
        excludeSysFilesFoldersCheckBox.setCheckbox(value);
        return this;
    }

    public FiltersTab addExcludeFilter(String filter){
        setFilterInputFieldToValue(filter);
        excludeAddBtn.click();
        return this;
    }

    public FiltersTab addIncludeFilter(String filter){
        setFilterInputFieldToValue(filter);
        includeAddBtn.click();
        return this;
    }
}
