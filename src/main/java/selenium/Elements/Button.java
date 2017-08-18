package selenium.Elements;

import selenium.BaseElementClass.Element;
import org.openqa.selenium.WebElement;

public class Button extends Element {

    public Button(WebElement element){
        super(element);
    }

    public boolean isVisisble(){
        return element.isDisplayed();
    }


}
