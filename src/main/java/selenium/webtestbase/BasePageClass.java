package selenium.webtestbase;


import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by MartinRiggs on 6/19/2017.
 */
public class BasePageClass {

    private String PAGE_URL = PropertyReaderHelper.getValueFromFileByName("server.name");
    //private WebDriverWait wait;
    public void setPageUrl(String text) {
        this.PAGE_URL = PAGE_URL + text;
    }

    public BasePageClass() {
        PageFactory.initElements(new CustomFieldDecorator(DriverFactory.getInstance().getDriver()), this);
    }
    public boolean isTextPresent(String text) {
        try{
            /*String str = DriverFactory.getInstance().getDriver().findElement(By.tagName("body")).getText();
           */
            return DriverFactory.getInstance().getDriver().getPageSource().contains(text);
        }
        catch(Exception e){
            e.getMessage();
            return false;
        }
    }

    public void openPage() {
        DriverFactory.getInstance().getDriver().get(getPageUrl());
    }

    public void logOut() {
        DriverFactory.getInstance().getDriver()
                .get(PropertyReaderHelper.getValueFromFileByName("server.name") + "/ui/user-logout");
    }

    public String getPageUrl()
    {
        return PAGE_URL;
    }

    public void waitForModalWindowOpen(){
        try {
            DriverFactory.getInstance().getWaitHandler().until(ExpectedConditions
                    .visibilityOfElementLocated(By.id("modal-edit")));
        } catch (NoSuchElementException ex){

        }
    }

    public void waitForPageLoad() {
        /*DriverFactory.getInstance().getWaitHandler().until(ExpectedConditions
                .attributeToBe(By.xpath("html"), "class", " "));*/
                //.attributeContains(By.xpath("html"), "class", " "));*/
        try {
            WebElement element = DriverFactory.getInstance().getDriver().findElement(By.id("nprogress"));
            DriverFactory.getInstance().getWaitHandler().until(ExpectedConditions
                    //.invisibilityOfElementLocated(By.id("nprogress")));
                    .stalenessOf(element)); //<-- this variant is faster than previous
            /*DriverFactory.getInstance().getWaitHandler().until(ExpectedConditions
                    .attributeToBe(By.xpath("html"), "class", " "));*/
        } catch (NoSuchElementException ex) {
        }
    }
}
