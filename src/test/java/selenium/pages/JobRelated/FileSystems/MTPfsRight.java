package selenium.pages.JobRelated.FileSystems;

import org.openqa.selenium.support.FindBy;
import selenium.Elements.InputField;
import selenium.webtestbase.BasePageClass;

public class MTPfsRight extends BasePageClass {

    @FindBy(xpath = ".//*[@id='div-folder-manual2']//input[@name='f2']")
    InputField fsPathInputField;

    public MTPfsRight(){
        super();
    }

    public MTPfsRight setConnectoidConfig(String path){
        fsPathInputField.inputText(path);
        return this;
    }
}
