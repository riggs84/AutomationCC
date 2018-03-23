package selenium.webtestbase;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

/**
 * Created by Victor on 21.06.2017.
 */
public class DriverFactory {

    private static volatile DriverFactory instance;
    private WebDriver driver = null;
    private WebDriverWait wait = null;
    private static long IMPLICIT_WAIT_TIMEOUT = 5;

    private DriverFactory(){
        setBrowser(PropertyReaderHelper.getValueFromFileByName("browser.name"));
    }

    // https://habrahabr.ru/post/129494/
    public static DriverFactory getInstance(){
        DriverFactory localFactory = instance;
        if (instance == null){
            synchronized (DriverFactory.class){
                localFactory = instance;
                if (localFactory == null){
                    instance = localFactory = new DriverFactory();
                }
            }
            //instance = new DriverFactory();
        }
        return localFactory;
    }

    public WebDriverWait getWaitHandler(){
        if (wait != null) {
            return wait;
        } else {
            throw new IllegalStateException("call getInstance() first");
        }
    }

    public WebDriver getDriver(){
        if (driver != null) {
            return driver;
        } else {
            throw new IllegalStateException("Driver has not been initialized. " +
                    "Please call setBrowser() before use this method");
        }
    }

    public void setBrowser(String browserName)
    {
        switch(browserName.toUpperCase())
        {
            // driver = new browserType(desiredcapabilities)
            case "CHROME":
                System.setProperty("webdriver.chrome.driver", "C:\\ChromeDriver\\chromedriver.exe");
                driver = new ChromeDriver(BrowserSettings.getSettings("chrome"));
                wait = new WebDriverWait(driver, IMPLICIT_WAIT_TIMEOUT);
                break;
            case "IE":
                break;
            case "FIREFOX":
                System.setProperty("webdriver.gecko.driver", "C:\\GeckoDriver\\geckodriver.exe");
                driver = new FirefoxDriver(BrowserSettings.getSettings("firefox"));
                break;
            default:
                break;
        }
        driver.manage().timeouts().implicitlyWait(IMPLICIT_WAIT_TIMEOUT, TimeUnit.SECONDS);
    }

    public void browserClose()
    {
        if (driver != null)
        {
            driver.quit();
            driver = null;
            instance = null;
        }
    }

}
