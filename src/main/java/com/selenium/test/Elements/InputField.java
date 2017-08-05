package com.selenium.test.Elements;

import com.selenium.test.BaseElementClass.Element;
import org.openqa.selenium.WebElement;

public class InputField extends Element {

    public InputField(WebElement element){
        super(element);
    }

    public void clear(){
        element.clear();
    }

    public void inputText(String string){
        element.sendKeys(string);
    }
}
