package com.project.homey.activities.login;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.project.homey.R;
import com.project.homey.bases.ActivityTestBase;
import com.project.homey.elementsproxy.ViewInteractionProxy;
import com.project.homey.utils.TimeUtils;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import app.activities.LoginActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

/**
 * Created by barakm on 10/07/2017
 */

@RunWith(AndroidJUnit4.class)
public class LoginActivityTests extends ActivityTestBase {

    private ViewInteractionProxy emailField;
    private ViewInteractionProxy passwordField;
    private ViewInteractionProxy loginButton;

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
        emailField.isDisplayed();
        emailField.hintEquals("Email");

        passwordField.isDisplayed();
        passwordField.hintEquals("Password");

        loginButton.isDisplayed();
        loginButton.isClickable();
        loginButton.textEquals("Log in");
    }

    @Test
    public void LoginWithExistingUser() {
        // Insert credentials.
        emailField.insertText(userName);
        passwordField.insertText(password);

        //Click on log in button.
        loginButton.click();

        //Verify home page has loaded.
        TimeUtils.Wait(10000);
//        TimeUtils.busyWait(5000, is(true), () -> getViewById(R.id.activity_home_page).isDisplayed());
        getViewById(R.id.activity_home_page).isDisplayed();

        logout();
    }

    @Test
    public void LoginWithNotExistingUser() {
        // Insert wrong credentials.
        emailField.insertText("notExistingUser@gmail.com");
        passwordField.insertText("notExistingUser");

        //Click on log in button.
        loginButton.click();

        //Verify an appropriate massage appears and user didn't log in.
        TimeUtils.Wait(2000);
        onView(withText(R.string.loginCredentialsAreWrongPleaseTryAgain)).inRoot(withDecorView(not(is(loginActivityActivityTestRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
        getViewById(R.id.loginActivity).isDisplayed();
    }

    private void logout() {
        getViewById(R.id.settings).click();
        getViewById(R.id.buttonLogout).click();
    }

    @Override
    public void AfterTest() {
    }
}
