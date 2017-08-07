package selenium.BaseElementClass;

import org.openqa.selenium.WebElement;

public class Element {
     protected WebElement element;

     public Element(WebElement element){
         this.element = element;
     }

     public void click(){
         element.click();
     }



}
