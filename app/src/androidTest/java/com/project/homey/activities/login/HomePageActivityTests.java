package com.project.homey.activities.login;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.project.homey.bases.ActivityTestBase;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import app.activities.HomePageActivity;
import app.logic.managers.Services;
import app.logic.managers.SessionManager;

/**
 * Created by barakm on 19/07/2017
 */

@RunWith(AndroidJUnit4.class)
public class HomePageActivityTests extends ActivityTestBase {

    @Rule
    public ActivityTestRule<HomePageActivity> homePageActivityActivityTestRule;

    @Override
    public void BeforeTest() {
        super.BeforeTest();

        Intent homePageIntent = new Intent();
        homePageActivityActivityTestRule = new ActivityTestRule<>(HomePageActivity.class);
        homePageActivityActivityTestRule.launchActivity(homePageIntent);
    }

    @Test
    public void verifyAllUserTasksAreShown() {
        int userId = ((SessionManager) (Services.GetService(SessionManager.class))).getUser().GetUserId();
//        int numOfTasksInDB = ((DBManager) (Services.GetService(DBManager.class))).GetUserTasks(userId, );

    }
}
