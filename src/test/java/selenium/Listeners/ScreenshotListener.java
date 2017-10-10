package selenium.Listeners;

import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import selenium.webtestbase.DriverFactory;

public class ScreenshotListener extends TestListenerAdapter {
    @Attachment
    private byte[] makeScreenshot(){
        return ((TakesScreenshot) DriverFactory.getInstance().getDriver()).getScreenshotAs(OutputType.BYTES);
    }

    @Override
    public void onTestFailure(ITestResult var1){
        makeScreenshot();
    }
}
