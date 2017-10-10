package selenium.pages.JobRelated.FileSystems;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import selenium.Elements.CheckBox;
import selenium.Elements.InputField;
import selenium.webtestbase.BasePageClass;

public class MyComputerFSright extends BasePageClass{

    @FindBy(xpath = ".//*[@id='div-folder-manual2']//input[@name='f2']")
    InputField fsPathInputField;

    @FindBy(xpath = ".//*[@id='mycomputer-right']/div/div[1]/div/label/span/span")
    CheckBox compressInNTFScheckBox;

    @FindBy(xpath = ".//*[@id='mycomputer-right']/div/div[2]/div/label/span/span")
    CheckBox uncompressInNTFScheckBox;

    @FindBy(xpath = ".//*[@id='mycomputer-right']/div/div[3]/div/label/span/span")
    CheckBox fatSystemThatNotRevealThemSelfCheckBox;

    @FindBy(xpath = ".//*[@id='panel-right-advanced-container']/div[@data-target='#panel-right-advanced']")
    WebElement advanced;

    public MyComputerFSright(){
        super();
    }

    public void setConnectiodConfig(String path, boolean compressNTFS, boolean uncompressNTFS, boolean fatFS){
        fsPathInputField.inputText(path);
        advanced.click();
        compressInNTFScheckBox.setCheckbox(compressNTFS);
        uncompressInNTFScheckBox.setCheckbox(uncompressNTFS);
        fatSystemThatNotRevealThemSelfCheckBox.setCheckbox(fatFS);
    }
}
