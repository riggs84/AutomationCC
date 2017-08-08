package selenium.testng.tests;

import io.qameta.allure.Description;
import selenium.pages.LoginPage;
import selenium.webtestsbase.DriverFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Created by Victor on 28.06.2017.
 */
public class LoginTest {
    LoginPage loginPage;

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
                        {"viktor.iurkov@yandex.ru", "123456", "_qwerty"},
                };
    }
    @DataProvider (name = "nonRegisteredUsers")
    public Object [][] nonRegisteredUsers()
    {
        return new Object[][]
                {

                };
    }

    //@BeforeClass
    public LoginTest()
    {
        DriverFactory.setBrowser("CHROME");
        this.loginPage = new LoginPage();
    }

    @Description("The test checks that registered user can login into system")
    @Test (dataProvider = "registeredUsers")
    public void loginTestRegisteredUser(String email, String password, String userName)
    {
        //loginPage.openPage();
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

    @AfterClass
    public void afterClass()
    {
        DriverFactory.browserClose();
    }


}
