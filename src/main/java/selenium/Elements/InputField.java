package selenium.Elements;

import selenium.BaseElementClass.Element;
import org.openqa.selenium.WebElement;

public class InputField extends Element {

    public InputField(WebElement element){
        super(element);
    }

    public void clear(){
        element.clear();
    }

    public void inputText(String string){
        element.click();
        element.sendKeys(string);
    }
}
