package com.project.homey.bases;

import android.support.test.runner.AndroidJUnit4;

import com.project.homey.utils.TimeUtils;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;

import app.logic.appcomponents.User;
import app.logic.managers.DBManager;
import app.logic.managers.Services;
import app.logic.managers.SessionManager;
import callback.UserCallBack;

import static android.support.test.InstrumentationRegistry.getContext;

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
                ((SessionManager) (Services.GetService(SessionManager.class))).setContext(getContext());
                ((SessionManager) (Services.GetService(SessionManager.class))).setUser(user);
                ((SessionManager) (Services.GetService(SessionManager.class))).setLogin(true);
            }

            @Override
            public void onFailure(String error) {
                throw new RuntimeException("Error on user creation");
            }
        });

        TimeUtils.Wait();
    }


    @After
    public void AfterTest() {

    }
}
