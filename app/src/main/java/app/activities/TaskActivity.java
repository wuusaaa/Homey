package app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.homey.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import app.customcomponents.HomeyProgressDialog;
import app.customcomponents.ScrollHorizontalWithItems;
import app.customcomponents.ScrollVerticalWithItems;
import app.enums.TaskProperty;
import app.enums.TaskStatus;
import app.logic.appcomponents.Group;
import app.logic.appcomponents.Task;
import app.logic.appcomponents.User;
import app.logic.managers.DBManager;
import app.logic.managers.Services;
import app.logic.managers.SessionManager;
import callback.GroupCallBack;
import callback.ServerCallBack;
import callback.UpdateCallBack;
import callback.UpdateTask;
import callback.UpdateTaskUsersByTaskIdCallBack;
import callback.UserCallBack;
import callback.UsersCallBack;


public class TaskActivity extends ActivityWithHeaderBase {

    private HomeyProgressDialog pDialog;
    private LinearLayout tasksHolderLayout;
    private ScrollVerticalWithItems scrollVerticalWithItems;
    private Task myTask;
    private Group taskGroup;
    private User taskCreator;
    private ScrollHorizontalWithItems taskAssignees;
    private List<User> taskAssigneesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        pDialog = new HomeyProgressDialog(this);
        setActivityComponents();
        setTaskInfo();
    }

    private void setActivityComponents() {
        myTask = getIntent().getExtras().getParcelable("task");

        //Get task's creator user.
        ((DBManager) Services.GetService(DBManager.class)).GetUser(myTask.GetCreatorId(), new UserCallBack() {
            @Override
            public void onSuccess(User user) {
                taskCreator = user;
                ((TextView) findViewById(R.id.taskActivityCreatorLabel)).setText(taskCreator.getName());
                ImageButton creatorImage = (ImageButton) findViewById(R.id.taskActivityCreatorImage);
                creatorImage.setOnClickListener(view->
                {
                    Intent intent = new Intent(getBaseContext(), ProfileActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("user", taskCreator);
                    intent.putExtras(bundle);
                    startActivity(intent);
                });
                //TODO::SET HERE CREATOR IMAGE
            }

            @Override
            public void onFailure(String error) {

            }
        });

        //Get task's Group.
        ((DBManager) Services.GetService(DBManager.class)).GetGroup(myTask.GetGroupId(), new GroupCallBack() {
            @Override
            public void onSuccess(Group group) {
                taskGroup = group;
                ((TextView) findViewById(R.id.taskActivityGroupLabel)).setText(taskGroup.GetName());
                ImageButton groupImage = (ImageButton) findViewById(R.id.taskActivityGroupImage);

                groupImage.setOnClickListener(view ->
                {
                    Intent intent = new Intent(getBaseContext(), GroupPageActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("group", taskGroup);
                    intent.putExtras(bundle);
                    startActivity(intent);
                });

                //TODO:: SET HERE GROUP IMAGE
            }

            @Override
            public void onFailure(String error) {
                ((TextView) findViewById(R.id.taskActivityGroupLabel)).setText("failed");
            }
        });
    }

    private void setTaskInfo() {
        setScreenName(myTask.GetName());
        if (myTask.GetStartTime() != null) {
            ((TextView) findViewById(R.id.taskActivityStartDate)).setText(myTask.GetStartTime().toString());
        } else {
            ((TextView) findViewById(R.id.taskActivityStartDate)).setText("-");
        }

        if (myTask.GetEndTime() != null) {
            ((TextView) findViewById(R.id.taskActivityEndDate)).setText(myTask.GetEndTime().toString());
        } else {
            ((TextView) findViewById(R.id.taskActivityEndDate)).setText("-");
        }

        ((TextView) findViewById(R.id.taskActivityDescription)).setText(myTask.GetDescription());
        //TODO::
        //findViewById(R.id.taskActivityTaskImage); SET HERE TASK IMAGE


        //TODO:: Next code takes user's group, need to change to task's assignee, and group call back here is null..
        ((DBManager) Services.GetService(DBManager.class)).GetTaskUsersByTaskId(Integer.parseInt(myTask.GetTaskId()), new UsersCallBack() {
            @Override
            public void onSuccess(ArrayList<User> users) {
                taskAssignees = (ScrollHorizontalWithItems) findViewById(R.id.taskActivityTaskAssignee);
                taskAssignees.SetScrollerItems(users, LinearLayoutCompat.HORIZONTAL, null);
                taskAssigneesList = users;
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadTaskInformation();
    }

    private void loadTaskInformation() {
        //pDialog.showDialog();
    }

    public void buttonCompleteOnClicked(View view) {
        UpdateCallBack updateCallBack = new UpdateTask(this.getBaseContext(), 1);

        myTask.setStatus(TaskStatus.COMPLETED);
        ((DBManager) Services.GetService(DBManager.class)).UpdateTask(myTask.GetTaskId(), TaskProperty.STATUS, TaskStatus.COMPLETED, updateCallBack);

    }

    public void buttonTakeOnClicked(View view) {
        String userId = ((SessionManager) (Services.GetService(SessionManager.class))).getUser().GetUserId();
        Stream<User> taskAssigneesStream = taskAssigneesList.stream().filter(user -> user.GetUserId().equals(userId));

        if (taskAssigneesStream.count() > 0) {
            ((DBManager) Services.GetService(DBManager.class)).UpdateTaskUsersByTaskId(myTask.GetTaskId(), new UpdateTaskUsersByTaskIdCallBack() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onFailure(String errorMessage) {

                }
            });
        }
    }

    public void buttonDeleteTaskOnClicked(View view) {
        ((DBManager) (Services.GetService(DBManager.class))).RemoveTask(myTask.GetTaskId(), new ServerCallBack() {
            @Override
            public void onSuccess(JSONObject result) {

            }

            @Override
            public void onFailure(String result) {

            }
        });
    }
}
