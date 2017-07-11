package com.selenium.test.pages;

import com.selenium.test.webtestsbase.BasePageClass;

/**
 * Created by Victor on 11.07.2017.
 */
public class UsersPage extends BasePageClass {

    public UsersPage()
    {
        super();
        setPageUrl("https://control.goodsync.com/ui/users");
    }

    @Override
    protected String getXpathTableLocation(String name) {
        return null;
    }

    @Override
    public void sortBy(String tableNmae) {

    }
}
