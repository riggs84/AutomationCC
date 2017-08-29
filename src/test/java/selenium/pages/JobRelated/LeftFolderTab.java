package selenium.pages.JobRelated;

import jdk.internal.util.xml.impl.Input;
import org.openqa.selenium.support.FindBy;
import selenium.Elements.Button;
import selenium.Elements.InputField;
import selenium.Elements.Selection;
import selenium.webtestsbase.BasePageClass;

public class LeftFolderTab extends BasePageClass {

    @FindBy(xpath = ".//*[@id='tab-left-folder']//label/span[@class='toggle']")
    Button useGSaccountBtn;

    @FindBy(id = "cbFileSystem1")
    Selection fileSystemSelect;

    @FindBy(xpath = ".//*[@id='div-folder-manual1']//input[@name='f1']")
    InputField pathInputField;



    public LeftFolderTab(){
        super();
    }
}
