package selenium.BaseElementClass;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import selenium.webtestsbase.DriverFactory;

public class Element {
     protected WebElement element;

     public Element(WebElement element){
         this.element = element;
     }

     public void click(){
         element.click();
     }

    public void waitUntilElementIsVisible(){
        DriverFactory.getInstance().getWaitHandler().until(ExpectedConditions.visibilityOf(element));
    }



}
