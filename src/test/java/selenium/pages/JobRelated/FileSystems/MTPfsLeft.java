package selenium.pages.JobRelated.FileSystems;

import org.openqa.selenium.support.FindBy;
import selenium.Elements.InputField;
import selenium.webtestsbase.BasePageClass;

public class MTPfsLeft extends BasePageClass {

    @FindBy(xpath = ".//*[@id='div-folder-manual1']//input[@name='f1']")
    InputField fsPathInputField;

    public MTPfsLeft(){
        super();
    }

    public MTPfsLeft setConnectoidConfig(String path){
        fsPathInputField.inputText(path);
        return this;
    }
}
