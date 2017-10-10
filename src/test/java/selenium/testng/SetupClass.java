package selenium.testng;

import org.testng.annotations.*;
import selenium.webtestbase.DriverFactory;
import selenium.Helpers.SQLhelper;

public class SetupClass {
    @BeforeSuite
    public void beforeSuite(){
        SQLhelper.cleanAndRecreateDataBase();
    }

    /*@BeforeClass
    public void beforeClass(){
        new LoginPage().loginAs("viktor.iurkov@yandex.ru", "123456");
    }*/

    /*@AfterMethod
    public void afterMethod(){
        SQLhelper.cleanAndRecreateDataBase();
    }*/

    @AfterClass
    public void afterClass(){
        DriverFactory.getInstance().getDriver().manage().deleteAllCookies();
    }

    @AfterSuite
    public void afterSuite(){
        DriverFactory.getInstance().browserClose();
    }
}
