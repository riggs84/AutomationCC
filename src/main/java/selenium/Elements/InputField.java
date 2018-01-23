package selenium.Elements;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import selenium.BaseElementClass.Element;
import org.openqa.selenium.WebElement;
import selenium.webtestbase.DriverFactory;

public class InputField extends Element {

    public InputField(WebElement element){
        super(element);
    }

    public void clear(){
        element.clear();
    }

    public void inputText(String string){
        //waitUntilElementIsVisible();
        waitUntilElementIsClickable();
        element.click();
        clear();
        waitUntilElementIsFocused();
        element.sendKeys(string);
    }

    public void setText(String str){
        element.click();
        element.sendKeys(str);
    }

    private void waitUntilElementIsFocused(){
        DriverFactory.getInstance().getWaitHandler().until(ExpectedConditions.elementSelectionStateToBe(element, element.isSelected()));
                //.until(ExpectedConditions.attributeContains(element.findElement(By.xpath("/ancestor::div[1]")), "class", "form-group label-floating is-empty is-focused"));
    }

    public String getValue(){
        return element.getAttribute("value");
    }

    public String getErrorMessage(){
        try {
            return element.findElement(By.xpath("following-sibling::span")).getText();
        } catch(Exception ex){
            return null;
        }
    }

}
