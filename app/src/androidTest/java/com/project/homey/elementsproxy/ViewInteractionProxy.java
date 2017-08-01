package com.project.homey.elementsproxy;

import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.matcher.ViewMatchers;

import com.project.homey.utils.TimeUtils;

import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withHint;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by barakm on 19/07/2017
 */

public class ViewInteractionProxy {

    private ViewInteraction viewInteraction;

    public ViewInteractionProxy(ViewInteraction viewInteraction) {
        this.viewInteraction = viewInteraction;
    }

    public ViewInteraction getViewInteraction() {
        return viewInteraction;
    }

    public void click() {
        viewInteraction.perform(ViewActions.click());
        TimeUtils.Wait(500);
    }

    public boolean isDisplayed() {
        viewInteraction.check(matches(ViewMatchers.isDisplayed()));
        //Assume that we get here only if the view is displayed so it's ok to return true.
        return true;
    }

    public void hintEquals(String hintText) {
        viewInteraction.check(matches(withHint(hintText)));
    }

    public void isClickable() {
        viewInteraction.check(matches(ViewMatchers.isClickable()));
    }

    public void insertText(String textToInsert) {
        viewInteraction.perform(typeText(textToInsert));
    }

    public void textEquals(String viewText) {
        viewInteraction.check(matches(withText(viewText)));
    }

    public boolean isExists() {
        try {
            isDisplayed();
        } catch (NoMatchingViewException noMatchingException) {
            return false;
        }

        return true;
    }
}
