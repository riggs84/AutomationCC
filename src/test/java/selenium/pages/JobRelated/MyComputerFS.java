package selenium.pages.JobRelated;

import org.openqa.selenium.support.FindBy;
import selenium.Elements.CheckBox;
import selenium.Elements.InputField;
import selenium.webtestsbase.BasePageClass;

public class MyComputerFS extends BasePageClass {

    //TODO needs to improve cuz FS located on left and right sides with diff locators
    @FindBy(xpath = ".//*[@id='div-folder-manual1']//input[@name='f1']")
    InputField fsPathInputField;

    @FindBy(xpath = ".//div[@class='panel-body']//div[@class='checkbox']/label[contains(text(),'Compress in NTFS')]/span/span")
    CheckBox compressInNTFScheckBox;

    @FindBy(xpath = ".//div[@class='panel-body']//div[@class='checkbox']/label[contains(text(),'Uncompress in NTFS')]/span/span")
    CheckBox uncompressInNTFScheckBox;

    @FindBy(xpath = ".//div[@class='panel-body']//div[@class='checkbox']/label[contains(text(),'FAT file system that does not reveal itself')]/span/span")
    CheckBox fatSystemThatNotRevealThemSelfCheckBox;


    public MyComputerFS(){
        super();
    }
}
