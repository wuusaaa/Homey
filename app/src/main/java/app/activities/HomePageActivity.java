package app.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.homey.R;

import java.util.ArrayList;

import app.activities.interfaces.IonClicked;
import app.customcomponents.HomeyProgressDialog;
import app.customcomponents.ScrollHorizontalWithItems;
import app.customcomponents.ScrollVerticalWithItems;
import app.logic.appcomponents.Group;
import app.logic.appcomponents.Task;
import app.logic.managers.EnvironmentManager;
import app.logic.managers.GroupManager;
import app.logic.managers.Services;
import app.logic.managers.SessionManager;
import app.logic.managers.TaskManager;
import callback.GoToTaskPageCallBack;
import callback.GotoGroupPageCallBack;
import callback.GroupsCallBack;
import callback.TasksCallBack;

public class HomePageActivity extends ActivityWithHeaderBase {

    //***** Class components: *****
    private ScrollHorizontalWithItems scrollHorizontalWithItems;
    private ScrollVerticalWithItems scrollVerticalWithItems;
    private TextView screenName;
    private TextView textViewUserName;
    private ImageButton buttonProfile;
    private HomeyProgressDialog pDialog;
    private GoToTaskPageCallBack taskClickCallBack;
    private IonClicked taskCheckBoxClickCallBack;
    private Button buttonSubmit;
    private int checkedBoxesCounter;
    //*****************************

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        // Progress dialog
        pDialog = new HomeyProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);

        buttonSubmit = (Button) findViewById(R.id.buttonSubmit);

        scrollHorizontalWithItems = (ScrollHorizontalWithItems) findViewById(R.id.GroupsHolder);
        scrollVerticalWithItems = (ScrollVerticalWithItems) findViewById(R.id.homePageActivityTasksHolder);
        textViewUserName = (TextView) findViewById(R.id.userName);
        buttonProfile = (ImageButton) findViewById(R.id.profileImage);
        screenName = (TextView) findViewById(R.id.textViewScreenName);
        screenName.setText(((EnvironmentManager) (Services.GetService(EnvironmentManager.class))).GetScreenName());
    }

    @Override
    protected void onStart() {
        super.onStart();
        initPage();
    }

    //******* Init functions: ********************
    private void loadTasks() {
        pDialog.showDialog();
        Context context = this;

        taskClickCallBack = new GoToTaskPageCallBack() {
            @Override
            public void onSuccess(Task task) {
                Intent intent = new Intent(context, TaskActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("task", task);
                intent.putExtras(bundle);
                startActivity(intent);
            }

            @Override
            public void onFailure(String error) {

            }
        };

        taskCheckBoxClickCallBack = this::onCheckBoxClicked;

        ((TaskManager) (Services.GetService(TaskManager.class))).GetUserTasks(new TasksCallBack() {
            @Override
            public void onSuccess(ArrayList<Task> tasks) {
                scrollVerticalWithItems.SetTasks(tasks, taskClickCallBack, taskCheckBoxClickCallBack);
                pDialog.hideDialog();
            }

            @Override
            public void onFailure(String error) {
                pDialog.hideDialog();
            }
        });

    }

    private void loadGroups() {
        pDialog.showDialog();
        Context context = this;
        ((GroupManager) (Services.GetService(GroupManager.class))).GetUserGroups(new GroupsCallBack() {
            @Override
            public void onSuccess(ArrayList<Group> groups) {
                scrollHorizontalWithItems.SetUserGroups(groups, LinearLayoutCompat.HORIZONTAL, new GotoGroupPageCallBack() {
                    @Override
                    public void onSuccess(Group group) {
                        Intent i = new Intent(context, GroupPageActivity.class);
                        Bundle b = new Bundle();
                        ((EnvironmentManager) (Services.GetService(EnvironmentManager.class))).SetScreenName(group.GetName());
                        b.putParcelable("group", group);
                        i.putExtras(b);
                        startActivity(i);
                    }

                    @Override
                    public void onFailure(String error) {
                        //TODO handle error
                    }
                });
                pDialog.hideDialog();
            }

            @Override
            public void onFailure(String error) {
                pDialog.hideDialog();
            }
        });
    }

    private void initPage() {
        loadGroups();
        loadTasks();
        fetchUserName();
        setProfileClick();
        initSubmitButton();
    }

    private void initSubmitButton() {
        buttonSubmit.setLayoutParams(new LinearLayout.LayoutParams(0, 0));
        checkedBoxesCounter = 0;
    }

    private void setProfileClick() {
        buttonProfile.setOnClickListener(clickedButton ->
        {
            Intent intent = new Intent(this, ProfileActivity.class);
            startActivity(intent);
        });
    }

    private void fetchUserName() {
        String userName = ((SessionManager) Services.GetService(SessionManager.class)).getUser().getName();
        textViewUserName.setText("Welcome " + userName);
    }

    //*********************************************

    public void onLogoutClick(View view) {
        Intent intent = new Intent(this, LogoutActivity.class);
        startActivity(intent);
    }

    public void onSubmitClicked(View view) {

    }

    public void onCheckBoxClicked(View view) {
        CheckBox checkBox = (CheckBox) view;

        if (checkBox.isChecked()) {
            checkedBoxesCounter++;
        } else {
            checkedBoxesCounter--;
        }

        if (checkedBoxesCounter > 0) {
            buttonSubmit.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            buttonSubmit.setVisibility(View.VISIBLE);
        } else {
            buttonSubmit.setLayoutParams(new LinearLayout.LayoutParams(0, 0));
            buttonSubmit.setVisibility(View.INVISIBLE);
        }
    }
}


