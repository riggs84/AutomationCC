package selenium.testng;

import org.testng.annotations.*;
import selenium.pages.LoginPage;
import selenium.webtestsbase.DriverFactory;
import selenium.webtestsbase.SQLhelper;

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
