package com.project.homey.activities.login;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.project.homey.R;
import com.project.homey.bases.ActivityTestBase;
import com.project.homey.utils.TimeUtils;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import app.activities.HomePageActivity;
import app.enums.TaskStatus;
import app.logic.appcomponents.Task;
import app.logic.managers.Services;
import app.logic.managers.TaskManager;
import callback.TasksCallBack;

import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static org.hamcrest.Matchers.is;

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
            public void onSuccess(ArrayList<Task> tasks) {
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
                .filter(task -> !task.getStatus().equals(TaskStatus.COMPLETED.value()))
                .collect(Collectors.toList());

        int i;
        for (i = 1; ; ) {
            if (getViewById(R.id.homePageActivityTasksHolder + i).isExists()) {
                i++;
            } else {
                break;
            }
        }
        assertThat("Not all tasks are shown", i, is(incompleteTasks.size()));
    }


}
