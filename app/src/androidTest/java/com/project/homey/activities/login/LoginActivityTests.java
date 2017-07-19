package com.project.homey.activities.login;

import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.TimeUtils;
import com.project.homey.R;
import com.project.homey.activities.ActivityTestBase;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import app.activities.LoginActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withHint;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

/**
 * Created by barakm on 10/07/2017
 */

@RunWith(AndroidJUnit4.class)
public class LoginActivityTests extends ActivityTestBase {

    private ViewInteraction emailField;
    private ViewInteraction passwordField;
    private ViewInteraction loginButton;

    @Rule
    public ActivityTestRule<LoginActivity> loginActivityActivityTestRule =
            new ActivityTestRule<>(LoginActivity.class);

    @Override
    public void BeforeTest() {
        emailField = getViewById(R.id.email);
        passwordField = getViewById(R.id.password);
        loginButton = getViewById(R.id.buttonLogin);
    }

    @Test
    public void HasUserNameTextView() {
        emailField.check(matches(isDisplayed()));
        emailField.check(matches(withHint("Email")));

        passwordField.check(matches(isDisplayed()));
        passwordField.check(matches(withHint("Password")));

        loginButton.check(matches(isDisplayed()));
        loginButton.check(matches(isClickable()));
        loginButton.check(matches(withText("Log in")));
    }

    @Test
    public void LoginWithExistingUser() {
        // Insert credentials.
        emailField.perform(typeText(userName));
        passwordField.perform(typeText(password));

        //Click on log in button.
        loginButton.perform(click());

        //Verify home page has loaded.
        TimeUtils.Wait(5000);
        getViewById(R.id.activity_home_page).check(matches(isDisplayed()));

        logout();
    }

    @Test
    public void LoginWithNotExistingUser() {
        // Insert wrong credentials.
        emailField.perform(typeText("notExistingUser@gmail.com"));
        passwordField.perform(typeText("notExistingUser"));

        //Click on log in button.
        loginButton.perform(click());

        //Verify an appropriate massage appears and user didn't log in.
        TimeUtils.Wait(2000);
        onView(withText(R.string.loginCredentialsAreWrongPleaseTryAgain)).inRoot(withDecorView(not(is(loginActivityActivityTestRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
        getViewById(R.id.loginActivity).check(matches(isDisplayed()));
    }

    private void logout() {
        getViewById(R.id.settings).perform(click());
        getViewById(R.id.buttonLogout).perform(click());
    }

    @Override
    public void AfterTest() {
    }
}
