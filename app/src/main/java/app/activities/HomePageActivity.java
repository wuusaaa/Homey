package app.activities;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.project.homey.R;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

import app.activities.interfaces.IHasImage;
import app.activities.interfaces.IHasText;
import app.customcomponents.CircleImageButton;
import app.customcomponents.HomeyProgressDialog;
import app.customcomponents.ScrollHorizontalWithItems;
import app.customcomponents.ScrollVerticalWithItems;
import app.customcomponents.TaskLayout;
import app.enums.TaskStatus;
import app.logic.Notification.FirebaseIDService;
import app.logic.Notification.MyFirebaseMessagingService;
import app.logic.appcomponents.Group;
import app.logic.appcomponents.Task;
import app.logic.appcomponents.User;
import app.logic.managers.ActivityChangeManager;
import app.logic.managers.DBManager;
import app.logic.managers.EnvironmentManager;
import app.logic.managers.GroupManager;
import app.logic.managers.Services;
import app.logic.managers.SessionManager;
import app.logic.managers.TaskManager;
import callback.GotoGroupPageCallBack;
import callback.GroupsCallBack;
import callback.TasksCallBack;
import callback.UpdateCallBack;
import callback.UpdateTask;
import callback.UserCallBack;

public class HomePageActivity extends ActivityWithHeaderBase {

    //***** Class components: *****
    private ScrollHorizontalWithItems scrollHorizontalWithItems;
    private ScrollVerticalWithItems scrollVerticalWithItems;
    private TextView textViewUserName;
    private TextView textViewActiveTaskMsg1;
    private TextView textViewActiveTasksNumber1;
    private TextView textViewPointsMsg1;
    private TextView textViewPointsNumber1;
    private CircleImageButton imageButtonProfile;
    private HomeyProgressDialog pDialog;
    private Button buttonSubmit;
    private List<TaskLayout> taskLayoutsChecked = new ArrayList<>();
    private ActivityChangeManager activityChangeManager;
    //*****************************

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        // Progress dialog
        pDialog = new HomeyProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);

        activityChangeManager = (ActivityChangeManager) Services.GetService(ActivityChangeManager.class);

        buttonSubmit = (Button) findViewById(R.id.buttonSubmit);

        scrollHorizontalWithItems = (ScrollHorizontalWithItems) findViewById(R.id.GroupsHolder);
        scrollVerticalWithItems = (ScrollVerticalWithItems) findViewById(R.id.homePageActivityTasksHolder);
        textViewUserName = (TextView) findViewById(R.id.userName);
        imageButtonProfile = (CircleImageButton) findViewById(R.id.profileImage);
        screenName = (TextView) findViewById(R.id.textViewScreenName);
        screenName.setText(((EnvironmentManager) (Services.GetService(EnvironmentManager.class))).GetScreenName());

        textViewActiveTaskMsg1 = (TextView) findViewById(R.id.textViewActiveTasksMsg);
        textViewActiveTasksNumber1 = (TextView) findViewById(R.id.textViewActiveTasksNumber);
        textViewPointsMsg1 = (TextView) findViewById(R.id.textViewPointsMsg);
        textViewPointsNumber1 = (TextView) findViewById(R.id.textViewPointsNumber);
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        setScreenName("Homey");
        initPage();
    }

    //******* Init functions: ********************
    private void loadTasks() {
        pDialog.showDialog();
        Context context = this;

        ((TaskManager) (Services.GetService(TaskManager.class))).GetUserTasks(new TasksCallBack()
        {
            @Override
            public void onSuccess(List<Task> tasks)
            {
                scrollVerticalWithItems.SetTasks(
                        tasks,
                        new Consumer<Task>() {
                            @Override
                            public void accept(Task t) {
                                activityChangeManager.SetTaskActivity(context, t, new Runnable() {
                                    @Override
                                    public void run() {
                                        initPage();
                                    }
                                });
                            }
                        },
                        new Consumer<TaskLayout>() {
                            @Override
                            public void accept(TaskLayout c) {
                                onCheckBoxClicked(c);
                            }
                        });

                scrollVerticalWithItems.showIncompleteTasks();

                // Active tasks number:
                long activeTasksNumber = tasks.stream().filter(new Predicate<Task>() {
                    @Override
                    public boolean test(Task task) {
                        return !task.isCompleted();
                    }
                }).count();
                textViewActiveTasksNumber1.setText(Long.toString(activeTasksNumber));
                pDialog.hideDialog();
            }

            @Override
            public void onFailure(String error) {
                pDialog.hideDialog();
            }
        });
    }

    private void loadGroups()
    {
        pDialog.showDialog();
        Context context = this;
        ((GroupManager) (Services.GetService(GroupManager.class))).GetUserGroups(new GroupsCallBack() {
            @Override
            public void onSuccess(ArrayList<Group> groups) {
                scrollHorizontalWithItems.SetScrollerItems(groups, LinearLayoutCompat.HORIZONTAL, new GotoGroupPageCallBack() {
                    @Override
                    public <T extends IHasImage & IHasText> void onSuccess(T item) {
                        ((EnvironmentManager) (Services.GetService(EnvironmentManager.class))).SetScreenName(item.GetName());
                        activityChangeManager.SetGroupActivity(context, (Group) item);
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

    private void initPage()
    {
        loadGroups();
        loadTasks();
        setProfileClick();
        initSubmitButton();
        initHeader();
    }

    private void initSubmitButton() {
        buttonSubmit.setLayoutParams(new LinearLayout.LayoutParams(0, 0));
        taskLayoutsChecked.clear();
    }

    private void setProfileClick() {
        imageButtonProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View clickedButton) {
                User self = ((SessionManager) Services.GetService(SessionManager.class)).getUser();

                activityChangeManager.SetProfileActivity(HomePageActivity.this, self);
            }
        });
    }

    private void initHeader()
    {
        // User Image:
        User user = ((SessionManager) Services.GetService(SessionManager.class)).getUser();
        imageButtonProfile.setImageBytes(user.GetImage(), R.mipmap.ic_profile_default);

        // User Welcome msg.
        textViewUserName.setText("Welcome " + user.GetName());

        // User Points:
        textViewPointsNumber1.setText(Integer.toString(user.GetScore()));

        // Active Tasks Number is set from loadTasks.
    }

    //*********************************************

    public void onSubmitClicked(View view)
    {
        UpdateCallBack updateCallBack = new UpdateTask(this.getBaseContext(), taskLayoutsChecked.size(), this::initPage);

        taskLayoutsChecked.forEach(new Consumer<TaskLayout>() {
            @Override
            public void accept(TaskLayout taskLayout) {
                ((TaskManager) Services.GetService(TaskManager.class)).CompleteTask(taskLayout.getTask(), updateCallBack);
            }
        });
    }

    public void onCheckBoxClicked(View view)
    {
        TaskLayout taskLayout = (TaskLayout) view;

        if (taskLayout.isChecked()) {
            taskLayoutsChecked.add(taskLayout);
        } else {
            taskLayoutsChecked.remove(taskLayout);
        }

        if (taskLayoutsChecked.size() > 0) {
            buttonSubmit.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            buttonSubmit.setVisibility(View.VISIBLE);
        } else {
            buttonSubmit.setLayoutParams(new LinearLayout.LayoutParams(0, 0));
            buttonSubmit.setVisibility(View.INVISIBLE);
        }
    }
}