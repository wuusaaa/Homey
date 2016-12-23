package com.project.homey;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by barakm on 23/12/2016.
 */

@RunWith(AndroidJUnit4.class)
public class ProfileActivityTests {

    @Rule
    public ActivityTestRule<ProfileActivity> m_ProfileActivity =
            new ActivityTestRule<ProfileActivity>(ProfileActivity.class);

    @Test
    public void hasUserNameTextView() {
        onView(withId(R.id.textViewUserName)).check(matches(isDisplayed()));
    }

    @Test
    public void hasUserNameEditText() {
        onView(withId(R.id.editTextUserName)).check(matches(isDisplayed()));
    }

    @Test
    public void hasEmailTextView() {
        onView(withId(R.id.textViewEmail)).check(matches(isDisplayed()));
    }

    @Test
    public void hasEmaillEditText() {
        onView(withId(R.id.editTextEmail)).check(matches(isDisplayed()));
    }
}
