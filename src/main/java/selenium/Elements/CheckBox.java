package selenium.Elements;

import org.openqa.selenium.By;
import selenium.BaseElementClass.Element;
import org.openqa.selenium.WebElement;

public class CheckBox extends Element {

    public CheckBox(WebElement element){
        super(element);
    }

    public CheckBox selectCheckBox(){
        element.findElement(By.xpath("./..//span/span")).click();
        //element.click();
        return this;
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

    public boolean isSelected(){
        return element.isSelected();
    }

    public boolean isEnabled(){
        return element.isEnabled();
    }
}
