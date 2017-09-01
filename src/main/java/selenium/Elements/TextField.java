package selenium.Elements;

import org.openqa.selenium.WebElement;
import selenium.BaseElementClass.Element;

public class TextField extends Element {

    public TextField(WebElement element){
        super(element);
    }

    public boolean containsText(String searchRequest){
        if(element.getText().toUpperCase().contains(searchRequest.toUpperCase())){
            return true;
        }
        else {
            return false;
        }
    }

}
