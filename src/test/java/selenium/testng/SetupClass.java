package selenium.testng;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import selenium.webtestsbase.DriverFactory;
import selenium.webtestsbase.SQLhelper;

public class SetupClass {
    @BeforeSuite
    public void beforeSuite(){
        SQLhelper.cleanAndRecreateDataBase();
    }

    @AfterMethod
    public void afterMethod(){
        //SQLhelper.cleanAndRecreateDataBase();
        SQLhelper.dropAdminTable();
    }

    @AfterClass
    public void afterClass(){
        DriverFactory.getInstance().getDriver().manage().deleteAllCookies();
    }

    @AfterSuite
    public void afterSuite(){
        DriverFactory.getInstance().browserClose();
    }
}
