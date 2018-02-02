package selenium.testng;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.qameta.allure.Description;
import org.testng.annotations.*;
import selenium.Helpers.SQLhelper;
import selenium.Listeners.ScreenshotListener;
import selenium.pages.LoginPage;
import org.testng.Assert;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by Victor on 28.06.2017.
 */
@Listeners({ScreenshotListener.class})
public class LoginTest{
    LoginPage loginPage;

    @BeforeClass
    public void beforeClass(){
        SQLhelper.cleanAndRecreateDataBase();
    }

    @DataProvider (name = "nonValidEmailCredentials")
    public Object[][] nonValidEmailCredentials()
    {
        return new Object [][]{
                {"viktor.iurkov@ yandex.ru", "123456"},
        };
    }
    @DataProvider (name = "registeredUsers")
    public Object [][] registeredUsers()
    {
        // email, password, userName
        return new Object [][]
                {
                        {"viktor.iurkov@yandex.ru", "123456", "viktor iurkov"},
                };
    }
    @DataProvider (name = "nonRegisteredUsers")
    public Object [][] nonRegisteredUsers()
    {
        return new Object[][]
                {
                        {"viktor.iurkov1@yandex.ru", "1234567"},
                };
    }


    public LoginTest()
    {
        //DriverFactory.getInstance().setBrowser("CHROME");
        this.loginPage = new LoginPage();
    }

    @Description("The test checks that registered user can login into system")
    @Test (dataProvider = "registeredUsers")
    public void loginTestRegisteredUser(String email, String password, String userName) {
        loginPage.loginAs(email, password);
        Assert.assertTrue(loginPage.isTextPresent(userName));
        loginPage.logOut(); //for cookie clean up
    }

    @Description("The test checks that non registered user can not login into system")
    @Test (dataProvider = "nonRegisteredUsers")
    public void loginTestNonRegistredUser(String email, String password)
    {
        loginPage.loginAs(email, password);
        Assert.assertTrue(loginPage.isTextPresent("Incorrect UserId or Password"));
    }

    @Description("The test checks that password filed must be filled")
    @Test
    public void loginTestNoPasswordEntered()
    {
        //loginPage.openPage();
        loginPage.loginAs("viktor.iurkov@yandex.ru","");
        Assert.assertTrue(loginPage.isTextPresent("This field is required."));
    }

    @Description("The test checks that email field must be filled")
    @Test
    public void loginTestNoEmailEntered()
    {
        //loginPage.openPage();
        loginPage.loginAs("", "123456");
        Assert.assertTrue(loginPage.isTextPresent("This field is required."));
    }

    @Description("The test checks system reaction for different non valid emails")
    @Test (dataProvider = "nonValidEmailCredentials")
    public void loginTestInvalidEmailCredentials (String email, String password)
    {
        //loginPage.openPage();
        loginPage.loginAs(email, password);
        Assert.assertTrue(loginPage.isTextPresent("Please enter a valid email address."));
    }

    @Description("The test checks that if db has no settings in Admin.admmin_settings and no cookie exists on login event db should be filled with values by default for locale settings")
    @Test
    public void valuesAppliedByDefaultForLocaleSettingsOnFirstLoginTest(){
        String localOffSet = ZoneOffset.systemDefault().getRules().getOffset(LocalDateTime.now()).toString();
        SQLhelper.cleanAndRecreateDataBase();
        loginPage.loginAs("viktor.iurkov@yandex.ru", "123456");
        // getting json from db with settings
        String str = SQLhelper.readValueFromDB("Administrators", "admin_settings");
        JsonObject json = new JsonParser().parse(str).getAsJsonObject();
        Assert.assertEquals(json.get("locale").getAsString(), "en-US", "Locale setting is not equal to en-US as it should be by default");
        Assert.assertEquals(json.get("no_run_period_unit").getAsString(), "days", "no run period unit is not equal to default value 'days' ");
        Assert.assertEquals(json.get("no_run_period_value").getAsInt(), 7, "no run period value is not equal default value '7'");
        Assert.assertTrue(json.get("timezone").getAsString().contains(localOffSet), "timezone is not equal to current time zone");
    }

    /*@AfterSuite
    public void afterSuite()
    {
        DriverFactory.getInstance().browserClose();
    }*/

    /*@AfterClass
    public void afterClass(){
        loginPage.logOut();
    }*/


}
