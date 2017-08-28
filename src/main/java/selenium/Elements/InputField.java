package selenium.Elements;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import selenium.BaseElementClass.Element;
import org.openqa.selenium.WebElement;
import selenium.webtestsbase.DriverFactory;

public class InputField extends Element {

    public InputField(WebElement element){
        super(element);
    }

    public void clear(){
        element.clear();
    }

    public void inputText(String string){
        waitUntilElementIsVisible();
        element.click();
        waitUntilElementIsFocused();
        element.sendKeys(string);
    }

    private void waitUntilElementIsFocused(){
        DriverFactory.getInstance().getWaitHandler().until(ExpectedConditions.
                attributeContains(element
                        .findElement(By.xpath("./ancestor::div")), "class", "form-group label-floating is-empty is-focused"));
    }

}
