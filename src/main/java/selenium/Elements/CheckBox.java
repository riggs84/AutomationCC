package selenium.Elements;

import org.openqa.selenium.By;
import selenium.BaseElementClass.Element;
import org.openqa.selenium.WebElement;
import selenium.webtestbase.DriverFactory;

public class CheckBox extends Element {

    public CheckBox(WebElement element){
        super(element);
    }

    public CheckBox selectCheckBox(){
        element.findElement(By.xpath("./..//span/span")).click();
        //element.click();
        return this;
    }

    public CheckBox setCheckbox(boolean bool){
        System.out.println(element.isSelected());
        if (bool){
            if(element.isSelected()){
                return this;
            } else {
                selectCheckBox();
            }
        } else {
            if(element.isSelected()){
                selectCheckBox();
            }
            return this;
        }
        return this;
    }

    public boolean isSelected(){
        return element.isSelected();
    }

    public boolean isEnabled(){
        return element.isEnabled();
    }
}
