package selenium.testng;

import io.qameta.allure.Description;
import io.qameta.allure.Issue;
import io.qameta.allure.Issues;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import selenium.Helpers.SQLhelper;
import selenium.Listeners.ScreenshotListener;
import selenium.pages.LoginPage;
import selenium.pages.SettingsPage;
import selenium.webtestbase.RunnerMock;

@Listeners({ScreenshotListener.class})
public class SettingsTest extends SetupClass {
    SettingsPage settingsPage;
    LoginPage loginPage;
    RunnerMock runner;

    public SettingsTest(){
        this.settingsPage = new SettingsPage();
        this.loginPage = new LoginPage();
        this.runner = new RunnerMock();
    }

    @BeforeClass
    public void beforeClass(){
        SQLhelper.cleanAndRecreateDataBase();
        loginPage.loginAs("viktor.iurkov@yandex.ru", "123456");
    }

    @AfterMethod
    public void afterMethod(){
        SQLhelper.cleanAndRecreateDataBase();
    }

    @Description("The test checks that PIN code can not be empty if enabled")
    @Issue("GS-7")
    @Test
    public void pinCodeCanNotBeEmptyIfEnabledTest(){
        settingsPage.openPage();
        settingsPage.clickEditCompanySettings()
                .clickRequirePinCodeOnRunnerRegistration()
                .clickSaveBtn();
        Assert.assertTrue(settingsPage.isTextPresent("This field is required."));
    }

    @Description("The test checks that if pin is set and new runner register to server -> runner will receive error")
    @Issue("GS-21")
    @Test
    public void runnerReceiveErrorIfNoPinProvidedTest() {
        settingsPage.openPage();
        settingsPage.clickEditCompanySettings()
                .clickRequirePinCodeOnRunnerRegistration()
                .setPinCode("12345")
                .clickSaveBtn();
        runner.sendNewUserQuery(SQLhelper.getCompanyId(), "viktor", "PC", "2",
                "Test", "0", "", "");
        Assert.assertEquals(runner.getResponseBody(), "ERROR: Wrong Company PIN!");
    }

    @Description("The test checks that Company Pin can be equal to 20 digits")
    @Issue("GS-1")
    @Test
    public void companyPinCanBeEqualTo20digitsTest(){
        String pin = "12345678901234567890";
        settingsPage.openPage();
        settingsPage.clickEditCompanySettings()
                .clickRequirePinCodeOnRunnerRegistration()
                .setPinCode(pin)
                .clickSaveBtn();
        String pinFromDB = SQLhelper.readValueFromDB("Companies", "pin");
        Assert.assertEquals(pin, pinFromDB);
    }

    @Description("The test checks that Company Pin can be equal to 4 digits")
    @Issue("GS-2")
    @Test
    public void companyPinCanBeEqualTo4digitsTest(){
        String pin = "1234";
        settingsPage.openPage();
        settingsPage.clickEditCompanySettings()
                .clickRequirePinCodeOnRunnerRegistration()
                .setPinCode(pin)
                .clickSaveBtn();
        String pinFromDB = SQLhelper.readValueFromDB("Companies", "pin");
        Assert.assertEquals(pin, pinFromDB);
    }

    @Description("The test checks that Company Pin can be equal to 19 digits")
    @Issue("GS-3")
    @Test
    public void companyPinCanBeEqualTo19digitsTest(){
        String pin = "1234567890123456789";
        settingsPage.openPage();
        settingsPage.clickEditCompanySettings()
                .clickRequirePinCodeOnRunnerRegistration()
                .setPinCode(pin)
                .clickSaveBtn();
        String pinFromDB = SQLhelper.readValueFromDB("Companies", "pin");
        Assert.assertEquals(pin, pinFromDB);
    }

    @Description("The test checks that Company Pin can be equal to 5 digits")
    @Issue("GS-4")
    @Test
    public void companyPinCanBeEqualTo5digitsTest(){
        String pin = "12345678901234567890";
        settingsPage.openPage();
        settingsPage.clickEditCompanySettings()
                .clickRequirePinCodeOnRunnerRegistration()
                .setPinCode(pin)
                .clickSaveBtn();
        String pinFromDB = SQLhelper.readValueFromDB("Companies", "pin");
        Assert.assertEquals(pin, pinFromDB);
    }

    @Description("The test checks that Company Pin can not be equal to 21 digits")
    @Issue("GS-5")
    @Test
    public void companyPinCanNotBeEqualTo21digitsTest(){
        String pin = "123456789012345678901";
        settingsPage.openPage();
        settingsPage.clickEditCompanySettings()
                .clickRequirePinCodeOnRunnerRegistration()
                .setPinCode(pin)
                .clickSaveBtn();
        Assert.assertTrue(settingsPage.isTextPresent("Please enter no more than 20 characters."));
    }

    // TODO some tests can be re-written with DataProvider!!!!
    @Description("The test checks that Company Pin can not be equal to 3 digits")
    @Issue("GS-6")
    @Test
    public void companyPinCanNotBeEqualTo3digitsTest(){
        String pin = "123";
        settingsPage.openPage();
        settingsPage.clickEditCompanySettings()
                .clickRequirePinCodeOnRunnerRegistration()
                .setPinCode(pin)
                .clickSaveBtn();
        Assert.assertTrue(settingsPage.isTextPresent("Please enter at least 4 characters."));
    }

    @Description("The test checks that Company Pin can not contain letters")
    @Issues(value = {@Issue("GS-8"), @Issue("GS-9")})
    @Test
    public void companyPinCanNotBeLetterTest(){
        String pin = "123ab";
        settingsPage.openPage();
        settingsPage.clickEditCompanySettings()
                .clickRequirePinCodeOnRunnerRegistration()
                .setPinCode(pin)
                .clickSaveBtn();
        Assert.assertTrue(settingsPage.isTextPresent("Please enter a valid number."));
    }

    @Description("The test checks that Company Pin can not contain space bar between digits")
    @Issue("GS-10")
    @Test
    public void companyPinCanNotContainSpaceBarBetweenDigitsTest(){
        String pin = "123 56";
        settingsPage.openPage();
        settingsPage.clickEditCompanySettings()
                .clickRequirePinCodeOnRunnerRegistration()
                .setPinCode(pin)
                .clickSaveBtn();
        Assert.assertTrue(settingsPage.isTextPresent("Please enter a valid number."));
    }

    @Description("The test checks that Company Pin can not contain leading space")
    @Issue("GS-11")
    @Test
    public void companyPinCanNotContainLeadingSpaceTest(){
        String pin = " 12356";
        settingsPage.openPage();
        settingsPage.clickEditCompanySettings()
                .clickRequirePinCodeOnRunnerRegistration()
                .setPinCode(pin)
                .clickSaveBtn();
        Assert.assertTrue(settingsPage.isTextPresent("Invalid characters. Spaces at start and end position are not allowed!"));
    }

    @Description("The test checks that Company Pin can not contain trailing space")
    @Issue("GS-12")
    @Test
    public void companyPinCanNotContainTrailingSpaceTest(){
        String pin = "12356 ";
        settingsPage.openPage();
        settingsPage.clickEditCompanySettings()
                .clickRequirePinCodeOnRunnerRegistration()
                .setPinCode(pin)
                .clickSaveBtn();
        Assert.assertTrue(settingsPage.isTextPresent("Invalid characters. Spaces at start and end position are not allowed!"));
    }

    @Description("The test checks that Company Pin can not contain special symbols")
    @Issue("GS-13")
    @Test
    public void companyPinCanNotContainSpecialSymbolsTest(){
        String pin = "12356&%*()+";
        settingsPage.openPage();
        settingsPage.clickEditCompanySettings()
                .clickRequirePinCodeOnRunnerRegistration()
                .setPinCode(pin)
                .clickSaveBtn();
        Assert.assertTrue(settingsPage.isTextPresent("Please enter a valid number."));
    }

    @Description("The test checks that if Company Pin is not equal runner receive error")
    @Issue("GS-24")
    @Test
    public void runnerReceiveErrorOnPinInequalityTest(){
        settingsPage.openPage();
        settingsPage.clickEditCompanySettings()
                .clickRequirePinCodeOnRunnerRegistration()
                .setPinCode("123456")
                .clickSaveBtn();
        runner.sendNewUserQuery(SQLhelper.getCompanyId(), "viktor", "PC", "2",
                "Test", "0", "", "12345");
        Assert.assertEquals(runner.getResponseBody(), "ERROR: Wrong Company PIN!");
    }

    @Description("The test checks that runner can ve registerred on correct PIN code")
    @Issue("GS-23")
    @Test
    public void runnerRegisteredOnCorrectPinTest(){
        settingsPage.openPage();
        settingsPage.clickEditCompanySettings()
                .clickRequirePinCodeOnRunnerRegistration()
                .setPinCode("123456")
                .clickSaveBtn();
        runner.sendNewUserQuery(SQLhelper.getCompanyId(), "viktor", "PC", "2",
                "Test", "0", "", "123456");
        Assert.assertTrue(runner.isResponseContains("password"));
    }

    @Description("The test checks that runner can be registered if runner has PIN and server pin is OFF")
    @Issue("GS-22")
    @Test
    public void runnerRegisteredWithPinOFFonServerTest(){
        runner.sendNewUserQuery(SQLhelper.getCompanyId(), "viktor", "PC", "2",
                "Test", "0", "", "123456");
        Assert.assertTrue(runner.isResponseContains("password"));
    }
}
