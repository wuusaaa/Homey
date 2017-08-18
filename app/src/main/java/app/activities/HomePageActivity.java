package app.activities;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.homey.R;

import java.util.ArrayList;
import java.util.List;

import app.activities.interfaces.IHasImage;
import app.activities.interfaces.IHasText;
import app.customcomponents.CircleImageButton;
import app.customcomponents.HomeyProgressDialog;
import app.customcomponents.ScrollHorizontalWithItems;
import app.customcomponents.ScrollVerticalWithItems;
import app.customcomponents.TaskLayout;
import app.enums.TaskProperty;
import app.enums.TaskStatus;
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

public class HomePageActivity extends ActivityWithHeaderBase {

    //***** Class components: *****
    private ScrollHorizontalWithItems scrollHorizontalWithItems;
    private ScrollVerticalWithItems scrollVerticalWithItems;
    private TextView textViewUserName;
    private CircleImageButton imageButtonProfile;
    private HomeyProgressDialog pDialog;
    private Button buttonSubmit;
    private List<TaskLayout> taskLayoutsChecked = new ArrayList<>();
    ActivityChangeManager activityChangeManager;
    //*****************************

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
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
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        initPage();
    }

    //******* Init functions: ********************
    private void loadTasks()
    {
        pDialog.showDialog();
        Context context = this;

        ((TaskManager) (Services.GetService(TaskManager.class))).GetUserTasks(new TasksCallBack() {
            @Override
            public void onSuccess(List<Task> tasks) {
                scrollVerticalWithItems.SetTasks(
                        tasks,
                        t -> activityChangeManager.SetTaskActivity(context, t),
                        c -> onCheckBoxClicked(c));

                scrollVerticalWithItems.showIncompleteTasks();
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

    public void initPage() {
        sendNotification();
        loadGroups();
        loadTasks();
        fetchUserName();
        setProfileClick();
        initSubmitButton();
        setUserImage();
    }

    private void sendNotification(){
        Intent intent = new Intent(this, HomePageActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder b = new NotificationCompat.Builder(this);

        b.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_header_logo)
                .setTicker("Raz")
                .setContentTitle("Logged In!")
                .setContentText("Welcome to Homey!.")
                .setDefaults(Notification.DEFAULT_LIGHTS| Notification.DEFAULT_SOUND)
                .setContentIntent(contentIntent)
                .setContentInfo("Info");


        NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, b.build());
    }

    private void initSubmitButton() {
        buttonSubmit.setLayoutParams(new LinearLayout.LayoutParams(0, 0));
        taskLayoutsChecked.clear();
    }

    private void setProfileClick() {
        imageButtonProfile.setOnClickListener(clickedButton ->
        {
            User self = ((SessionManager) Services.GetService(SessionManager.class)).getUser();

            activityChangeManager.SetProfileActivity(this, self);
        });
    }

    private void fetchUserName()
    {
        String userName = ((SessionManager) Services.GetService(SessionManager.class)).getUser().GetName();
        textViewUserName.setText("Welcome " + userName);
    }

    private void setUserImage()
    {
        User user = ((SessionManager) Services.GetService(SessionManager.class)).getUser();
        imageButtonProfile.setImageBytes(user.GetImage(),R.mipmap.ic_profile_default);
    }

    //*********************************************

    public void onLogoutClick(View view)
    {
        activityChangeManager.SetLogOutActivity(this);
    }

    public void onSubmitClicked(View view) {
        UpdateCallBack updateCallBack = new UpdateTask(this.getBaseContext(), taskLayoutsChecked.size(), this::initPage);

        taskLayoutsChecked.forEach(taskLayout -> {
            taskLayout.getTask().setStatus(TaskStatus.COMPLETED);
            ((DBManager) (Services.GetService(DBManager.class))).UpdateTask(taskLayout.getTask().GetTaskId(),
                    TaskProperty.STATUS,
                    taskLayout.getTask().getStatus(),
                    updateCallBack);
        });
    }

    public void onCheckBoxClicked(View view) {
        TaskLayout taskLayout = (TaskLayout) view;

        if (taskLayout.isChecked()) {
            taskLayoutsChecked.add(taskLayout);
        } else {
            taskLayoutsChecked.remove(taskLayout);
        }

        if (taskLayoutsChecked.size() > 0) {
            buttonSubmit.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            buttonSubmit.setVisibility(View.VISIBLE);
        } else {
            buttonSubmit.setLayoutParams(new LinearLayout.LayoutParams(0, 0));
            buttonSubmit.setVisibility(View.INVISIBLE);
        }
    }
}


