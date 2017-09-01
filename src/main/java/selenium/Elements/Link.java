package selenium.Elements;

import org.openqa.selenium.WebElement;
import selenium.BaseElementClass.Element;

public class Link extends Element {

    public Link(WebElement element){
        super(element);
    }

    public void clickLink(){
        element.click();
    }
}
