package selenium.webtestsbase;


import com.sun.jna.platform.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.File;
import java.io.IOException;


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
        DriverFactory.getInstance().getDriver().get(getPageUrl() + "/ui/user-logout");
    }

    public String getPageUrl()
    {
        return PAGE_URL;
    }

    public void waitForJSload() {
        WebElement element = DriverFactory.getInstance().getDriver().findElement(By.id("nprogress"));
        /*DriverFactory.getInstance().getWaitHandler().until(ExpectedConditions
                .attributeToBe(By.xpath("html"), "class", " "));
                //.attributeContains(By.xpath("html"), "class", " "));*/
        DriverFactory.getInstance().getWaitHandler().until(ExpectedConditions
                //.invisibilityOfElementLocated(By.id("nprogress")));
                .stalenessOf(element)); //<-- this variant is faster than previous

    }

    public void makeScreenShot(String screenFileName){
        File sourceFile = ((TakesScreenshot)DriverFactory.getInstance().getDriver()).getScreenshotAs(OutputType.FILE);
        try {
            org.apache.commons.io.FileUtils.copyFile(sourceFile, new File("C:\\AutomationCC\\AutomationCC\\ScreenShots\\"+ screenFileName +".jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
