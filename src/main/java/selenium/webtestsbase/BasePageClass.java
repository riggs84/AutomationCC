package selenium.webtestsbase;


import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;


/**
 * Created by MartinRiggs on 6/19/2017.
 */
public class BasePageClass {

    private String PAGE_URL;
    //private WebDriverWait wait;
    public void setPageUrl(String text)
    {
        this.PAGE_URL = text;
    }

    public BasePageClass() {
        PageFactory.initElements(new CustomFieldDecorator(DriverFactory.getInstance().getDriver()), this);
    }
    public boolean isTextPresent(String text) {
        try{
            boolean b = DriverFactory.getInstance().getDriver().getPageSource().contains(text);
            return b;
        }
        catch(Exception e){
            return false;
        }
    }

    public void openPage() {
        DriverFactory.getInstance().getDriver().get(getPageUrl());
    }

    public void logOut()
    {
        DriverFactory.getInstance().getDriver().get("https://control.goodsync.com/ui/user-logout");
    }

    public String getPageUrl()
    {
        return PAGE_URL;
    }

    public void waitForJSload() {
        DriverFactory.getInstance().getWaitHandler().until(ExpectedConditions
                .attributeToBe(By.xpath("html"), "class", " "));
                //.attributeContains(By.xpath("html"), "class", " "));

    }


}
