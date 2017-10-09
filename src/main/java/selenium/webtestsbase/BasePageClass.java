package selenium.webtestsbase;


import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

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

    public void makeScreenShot(String screenFileName){
        File sourceFile = ((TakesScreenshot)DriverFactory.getInstance().getDriver()).getScreenshotAs(OutputType.FILE);
        try {
            String filePath = new File("").getAbsolutePath();
            org.apache.commons.io.FileUtils.copyFile(sourceFile, new File(filePath + "\\ScreenShots\\"+ screenFileName +".jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
