package com.selenium.test.Elements;

import com.selenium.test.BaseElementClass.Element;
import org.openqa.selenium.WebElement;

public class CheckBox extends Element {
    protected WebElement element;

    public CheckBox(WebElement element){
        super(element);
    }

    public void selectCheckBox(){
        element.click();
    }
}
