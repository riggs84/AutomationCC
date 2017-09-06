package selenium.pages.ProgramOptionsRelated;

import org.openqa.selenium.support.FindBy;
import selenium.Elements.CheckBox;
import selenium.Elements.InputField;
import selenium.webtestsbase.BasePageClass;

public class GeneralTab extends BasePageClass {

    @FindBy(xpath = ".//*[@id='tab-general']/div/div[1]/div/label")
    CheckBox warnAboutTimeOutOfRangeCheckBox;

    @FindBy(xpath = ".//*[@id='tab-general']/div/div[2]/div/label")
    CheckBox autoInstallNewVerIfFoundCheckBox;

    @FindBy(xpath = ".//*[@id='tab-general']//input[@name='parallel-auto-jobs']")
    InputField maxJobRunInParallelInputField;

    @FindBy(xpath = ".//*[@id='tab-general']//input[@name='auto-jobs-quant']")
    InputField autoJobQuantInputField;

    @FindBy(xpath = ".//*[@id='tab-general']//input[@name='months-old-gens']")
    InputField keepFileHistoryMonthsInputField;

    public GeneralTab(){
        super();
    }

    public GeneralTab setWarnAboutTimeOutOfRangeCheckBoxToValue(boolean value){
        warnAboutTimeOutOfRangeCheckBox.setCheckbox(value);
        return this;
    }

    public GeneralTab setAutoInstallVerIfFoundCheckBoxToValue(boolean value){
        autoInstallNewVerIfFoundCheckBox.setCheckbox(value);
        return this;
    }

    public GeneralTab setMaxJobRunInParallelFieldToValue(String jobsCount){
        maxJobRunInParallelInputField.inputText(jobsCount);
        return this;
    }

    public GeneralTab setAutoJobQuantFieldToValue(String seconds){
        autoJobQuantInputField.inputText(seconds);
        return this;
    }

    public GeneralTab setKeepFileHistoryMonthsFieldToValue(String months){
        keepFileHistoryMonthsInputField.inputText(months);
        return this;
    }
}
