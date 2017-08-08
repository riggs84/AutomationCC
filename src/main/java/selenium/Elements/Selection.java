package selenium.Elements;

import org.openqa.selenium.WebElement;
import selenium.BaseElementClass.Element;

public class Selection extends Element {

    public Selection(WebElement element){
        super(element);
    }

    private org.openqa.selenium.support.ui.Select getSelectElement() {
        return new org.openqa.selenium.support.ui.Select(element);
    }

    public void selectByVisibleText(String string){
        getSelectElement().selectByVisibleText(string);
    }
}
