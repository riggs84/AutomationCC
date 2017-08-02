package com.selenium.test.Elements;

import com.selenium.test.BaseElementClass.Element;
import org.openqa.selenium.WebElement;

public class InputField extends Element {

    protected WebElement element;

    public InputField(WebElement element){
        super(element);
    }
}
