package com.project.homey.activities;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import com.project.homey.R;
import com.project.homey.bases.ActivityTestBase;
import com.project.homey.utils.TimeUtils;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import app.activities.HomePageActivity;
import app.customcomponents.ScrollHorizontalWithItems;
import app.customcomponents.ScrollVerticalWithItems;
import app.enums.TaskStatus;
import app.logic.appcomponents.Group;
import app.logic.appcomponents.Task;
import app.logic.managers.GroupManager;
import app.logic.managers.Services;
import app.logic.managers.TaskManager;
import callback.GroupsCallBack;
import callback.TasksCallBack;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
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
    public void verifyAllUserTasksAreShown() throws InterruptedException {
        final List<Task> tasksFromDb = new ArrayList<>();
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        ((TaskManager) (Services.GetService(TaskManager.class))).GetUserTasks(new TasksCallBack() {
            @Override
            public void onSuccess(List<Task> tasks) {
                tasksFromDb.addAll(tasks);
                atomicBoolean.set(true);
            }

            @Override
            public void onFailure(String error) {
                atomicBoolean.set(true);
            }
        });

        TimeUtils.busyWait(5000, is(true), atomicBoolean::get);

        List<Task> incompleteTasks = tasksFromDb.stream()
                .filter(new Predicate<Task>() {
                    @Override
                    public boolean test(Task task) {
                        return !task.getStatus().equals(TaskStatus.COMPLETED.value());
                    }
                })
                .collect(Collectors.toList());

        ScrollVerticalWithItems scrollVerticalWithItems = (ScrollVerticalWithItems) (homePageActivityActivityTestRule.getActivity().findViewById(R.id.homePageActivityTasksHolder));
        LinearLayout linearLayout = ((LinearLayout) (scrollVerticalWithItems.getChildAt(0)));

        int i, taskLayoutCounter = 0;
        for (i = 0; i < linearLayout.getChildCount(); i++) {
            if (linearLayout.getChildAt(i).getClass().toString().contains("TaskLayout")) {
                taskLayoutCounter++;
            }
        }
        assertEquals("Not all tasks are shown", taskLayoutCounter, incompleteTasks.size());
    }

    @Test
    public void verifyAllUserGroupsAreShown() throws InterruptedException {
        final List<Group> groupsFromDb = new ArrayList<>();
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        ((GroupManager) (Services.GetService(GroupManager.class))).GetUserGroups(new GroupsCallBack() {
            @Override
            public void onSuccess(ArrayList<Group> groups) {
                groupsFromDb.addAll(groups);
                atomicBoolean.set(true);
            }

            @Override
            public void onFailure(String error) {
                atomicBoolean.set(true);
            }
        });

        TimeUtils.busyWait(5000, is(true), atomicBoolean::get);

        ScrollHorizontalWithItems scrollHorizontalWithItems = (ScrollHorizontalWithItems) (homePageActivityActivityTestRule.getActivity().findViewById(R.id.GroupsHolder));
        LinearLayout linearLayout = ((LinearLayout) (scrollHorizontalWithItems.getChildAt(0)));

        int i, linearLayoutCounter = 0;
        for (i = 0; i < linearLayout.getChildCount(); i++) {
            if (linearLayout.getChildAt(i).getClass().toString().contains("LinearLayout")) {
                linearLayoutCounter++;
            }
        }
        assertEquals("Not all tasks are shown", linearLayoutCounter, groupsFromDb.size());
    }

    @Test
    public void hasLogoutButton() {
        TimeUtils.Wait();
        getViewById(R.id.buttonLogout).isDisplayed();
        getViewById(R.id.buttonLogout).click();
        TimeUtils.Wait();
        getViewById(R.id.logoutActivity).isDisplayed();
    }

    @Test
    public void hasProfileImageButton() {
        TimeUtils.Wait();
        getViewById(R.id.profileImage).isDisplayed();
        getViewById(R.id.profileImage).click();
        TimeUtils.Wait();
        getViewById(R.id.activity_profile).isDisplayed();
    }

    @Test
    public void submitButtonWorksForCompleteTask() {
        onData(instanceOf(CheckBox.class)).atPosition(0).perform(click());
        onView(instanceOf(CheckBox.class)).perform(click());
        onView(withText("incmpleteTask")).perform();
        onView(withId(R.id.homePageActivityTasksHolder)).perform(click());
        getViewById(R.id.buttonSubmit);
    }


}
