package selenium.pages.JobRelated.FileSystems;

import org.openqa.selenium.support.FindBy;
import selenium.Elements.CheckBox;
import selenium.Elements.InputField;
import selenium.webtestsbase.BasePageClass;

public class MyComputerFSleft extends BasePageClass {

    @FindBy(xpath = ".//*[@id='div-folder-manual1']//input[@name='f1']")
    InputField fsPathInputField;

    @FindBy(xpath = ".//*[@id='mycomputer-left']/div/div[1]/div/label/span/span")
    CheckBox compressInNTFScheckBox;

    @FindBy(xpath = ".//*[@id='mycomputer-left']/div/div[2]/div/label/span/span")
    CheckBox uncompressInNTFScheckBox;

    @FindBy(xpath = ".//*[@id='mycomputer-left']/div/div[3]/div/label/span/span")
    CheckBox fatSystemThatNotRevealThemSelfCheckBox;

    public MyComputerFSleft(){
        super();
    }

    public void setConnectiodConfig(String path, boolean compressNTFS, boolean uncompressNTFS, boolean fatFS){
        fsPathInputField.inputText(path);
        compressInNTFScheckBox.setCheckbox(compressNTFS);
        uncompressInNTFScheckBox.setCheckbox(uncompressNTFS);
        fatSystemThatNotRevealThemSelfCheckBox.setCheckbox(fatFS);
    }
}
