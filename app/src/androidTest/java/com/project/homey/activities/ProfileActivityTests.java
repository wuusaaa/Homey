package com.project.homey.activities;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.project.homey.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import app.activities.ProfileActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

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
        onView(withId(R.id.textViewUserNameLabel)).check(matches(isDisplayed()));
        onView(withId(R.id.textViewUserNameLabel)).check(matches(withText("User Name:")));

    }

    @Test
    public void hasUsersUserName() {
        onView(withId(R.id.textViewUserNameValue)).check(matches(isDisplayed()));
        onView(withId(R.id.textViewUserNameValue)).check(matches(withText("")));
    }

    @Test
    public void hasEmailTextView() {
        onView(withId(R.id.textViewEmailLabel)).check(matches(isDisplayed()));
        onView(withId(R.id.textViewEmailLabel)).check(matches(withText("Email:")));
    }

    @Test
    public void hasUsersEmailTextView() {
        onView(withId(R.id.textViewEmailValue)).check(matches(isDisplayed()));
        onView(withId(R.id.textViewEmailValue)).check(matches(withText("")));
    }

    @Test
    public void hasUsersProfilePicture() {
        onView(withId(R.id.imageButtonUsersProfilePicture)).check(matches(isDisplayed()));
    }
}
