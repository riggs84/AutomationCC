package selenium.Elements;

import org.openqa.selenium.WebElement;
import selenium.BaseElementClass.Element;
import org.openqa.selenium.support.ui.Select;

public class Selection extends Element {

    public Selection(WebElement element){
        super(element);
    }

    private Select getSelectElement() {
        return new Select(element);
    }

    public void selectByVisibleText(String string){
        getSelectElement().selectByVisibleText(string);
    }
}
