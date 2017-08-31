package selenium.Elements;

import selenium.BaseElementClass.Element;
import org.openqa.selenium.WebElement;

public class CheckBox extends Element {

    public CheckBox(WebElement element){
        super(element);
    }

    public void selectCheckBox(){
        element.click();
    }

    public void setCheckbox(boolean bool){
        if (bool){
            if(element.isSelected()){
                return;
            }
            else {
                selectCheckBox();
            }
        }
        else {
            if(element.isSelected()){
                selectCheckBox();
            }
            return;
        }
    }
}
