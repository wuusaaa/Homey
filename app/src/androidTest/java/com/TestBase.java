package com;

import android.support.test.espresso.ViewInteraction;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;

import app.logic.appcomponents.User;
import app.logic.managers.DBManager;
import app.logic.managers.Services;
import app.logic.managers.SessionManager;
import callback.UserCallBack;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by barakm on 20/03/2017
 */

@RunWith(AndroidJUnit4.class)
public abstract class TestBase {

    protected final String userName = "test@gmail.com";
    protected final String password = "test";

    @Before
    public void BeforeTest() {
        ((DBManager) (Services.GetService(DBManager.class))).Login(userName, password, new UserCallBack() {
            @Override
            public void onSuccess(User user) {
                ((SessionManager) (Services.GetService(SessionManager.class))).setUser(user);

            }

            @Override
            public void onFailure(String error) {
                throw new RuntimeException("Error on user creation");
            }
        });

        TimeUtils.Wait();
    }

    protected ViewInteraction getViewById(final int viewID) {
        return onView(withId(viewID));
    }

    @After
    public void AfterTest() {

    }
}
