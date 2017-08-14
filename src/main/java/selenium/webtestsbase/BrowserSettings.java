package selenium.webtestsbase;

import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * Created by Victor on 21.06.2017.
 */
public class BrowserSettings {
    public static DesiredCapabilities getSettings (String browserName)
    {
        DesiredCapabilities capability = null;
        switch(browserName.toUpperCase())
        {
            case "CHROME":
                capability = DesiredCapabilities.chrome();
                capability.setCapability("webdriver_accept_untrusted_certs", true);
                break;
            case "IE":
                capability = DesiredCapabilities.internetExplorer();
                break;
            case "FIREFOX":
                capability = DesiredCapabilities.firefox();
                capability.setCapability("webdriver_accept_untrusted_certs", true);
                //capability.setCapability("marionette", false);
                break;
            default:
                throw new IllegalStateException("The browser " +browserName+ " is not supported in tests");
        }

        return capability;

    }

}
