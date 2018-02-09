package selenium.Elements;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import selenium.BaseElementClass.Element;
import org.openqa.selenium.WebElement;
import selenium.webtestbase.DriverFactory;

public class CheckBox extends Element {

    public CheckBox(WebElement element){
        super(element);
    }

    public void selectCheckBox(){
        // Somehow ftp connectoid checkboxes which are selected are not found. In that case we catch exception and try to click using JQuery
        try {
            element.findElement(By.xpath("./..//span/span")).click();
        } catch(Exception ex){
            String name  = element.getAttribute("name");
            JavascriptExecutor jsExec = (JavascriptExecutor) DriverFactory.getInstance().getDriver();
            String script = "$(\"[name='" + name + "']\").click()";
            jsExec.executeScript(script);
        }
        //element.click();
    }

    public CheckBox setCheckbox(boolean bool){
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
