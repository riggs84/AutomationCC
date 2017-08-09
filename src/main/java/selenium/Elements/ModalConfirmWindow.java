package selenium.Elements;

import selenium.BaseElementClass.Element;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ModalConfirmWindow extends Element {

    public ModalConfirmWindow(WebElement element){
        super(element);
    }

    /*@FindBy(xpath=".//button[@class='btn btn-default']")
    Button cancel;

    @FindBy(xpath=".//button[@class='btn btn-warning']")
    Button confirm;*/

    public void confirmAction(){
        //confirm.click();
        element.findElement(By.xpath(".//div[@class='modal-footer']/button[2]")).click();
    }

    public void cancelAction(){
        //cancel.click();
        element.findElement(By.xpath(".//div[@class='modal-footer']/button[1]")).click();
    }

    public String getWarningMessageText(){
        return element.findElement(By.className("alert alert-warning")).getText();
    }
}
