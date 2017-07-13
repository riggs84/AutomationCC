package com.selenium.test.testng.tests;

import com.selenium.test.pages.LoginPage;
import com.selenium.test.pages.UsersPage;
import com.selenium.test.webtestsbase.DriverFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

/**
 * Created by Victor on 13.07.2017.
 */
public class UsersTest {
    LoginPage lp;
    UsersPage usersPage;

    @BeforeClass
    public void beforeClass(){
        DriverFactory.setBrowser("FIREFOX");
        lp = new LoginPage()
            .loginAs("viktor.iurkov@yandex.ru", "123456");
        usersPage = new UsersPage();
    }

    @AfterClass
    public void afterClass(){
        DriverFactory.browserClose();
    }
}
