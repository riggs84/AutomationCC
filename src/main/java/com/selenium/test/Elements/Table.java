package com.selenium.test.Elements;

import com.selenium.test.BaseElementClass.Element;
import com.selenium.test.webtestsbase.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import java.util.List;

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

    public int countElementsInTable(String elementName){
        List<WebElement> rows = element.findElements(By.xpath("//*(contains(td,'" + elementName + "'))"));
        return rows.size();
    }


}
