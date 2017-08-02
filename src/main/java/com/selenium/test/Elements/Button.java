package com.selenium.test.Elements;

import com.selenium.test.BaseElementClass.Element;
import org.openqa.selenium.WebElement;

public class Button extends Element {

    protected WebElement element;

    public Button(WebElement element){
        super(element);
    }

    public boolean isVisisble(){
        return element.isDisplayed();
    }

}
