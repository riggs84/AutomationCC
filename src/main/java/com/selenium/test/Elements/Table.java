package com.selenium.test.Elements;

import com.selenium.test.BaseElementClass.Element;
import com.selenium.test.webtestsbase.DriverFactory;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

public class Table extends Element {

    public Table(WebElement element) {
        super(element);
    }

    public boolean tableContainsElements(String elementName)
    {
        String source = (String)((JavascriptExecutor) DriverFactory.getDriver())
                .executeScript("return arguments[0].innerHTML;", element);
        if (source.toUpperCase().contains(elementName.toUpperCase()))
            return true;
        else
            return false;
    }
}
