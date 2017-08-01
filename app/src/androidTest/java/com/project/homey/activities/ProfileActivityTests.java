package com.project.homey.activities;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import app.activities.ProfileActivity;

/**
 * Created by barakm on 23/12/2016
 */

@RunWith(AndroidJUnit4.class)
public class ProfileActivityTests {

    @Rule
    public ActivityTestRule<ProfileActivity> m_ProfileActivity =
            new ActivityTestRule<>(ProfileActivity.class);

    @Test
    public void hasUserNameTextView() {
    }
}
