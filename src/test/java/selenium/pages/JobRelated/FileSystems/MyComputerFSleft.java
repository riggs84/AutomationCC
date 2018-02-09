package selenium.pages.JobRelated.FileSystems;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import selenium.Elements.CheckBox;
import selenium.Elements.InputField;
import selenium.webtestbase.BasePageClass;

public class MyComputerFSleft extends BasePageClass {

    @FindBy(xpath = ".//*[@id='div-folder-manual1']//input[@name='f1']")
    InputField fsPathInputField;

    @FindBy(xpath = ".//*[@id='mycomputer-left']/div/div[1]/div/label")
    CheckBox compressInNTFScheckBox;

    @FindBy(xpath = ".//*[@id='mycomputer-left']/div/div[2]/div/label")
    CheckBox uncompressInNTFScheckBox;

    @FindBy(xpath = ".//*[@id='mycomputer-left']/div/div[3]/div/label")
    CheckBox fatSystemThatNotRevealThemSelfCheckBox;

    @FindBy(xpath = ".//*[@id='panel-left-advanced-container']/div[@data-target='#panel-left-advanced']")
    WebElement advanced;

    public MyComputerFSleft(){
        super();
    }

    public void setConnectiodConfig(String path){
        fsPathInputField.inputText(path);
    }

    public CheckBox getCompressInNTFScheckBox() {
        return compressInNTFScheckBox;
    }

    public CheckBox getUncompressInNTFScheckBox() {
        return uncompressInNTFScheckBox;
    }

    public CheckBox getFatSystemThatNotRevealThemSelfCheckBox() {
        return fatSystemThatNotRevealThemSelfCheckBox;
    }
}
