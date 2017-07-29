package app.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.homey.R;

import java.util.ArrayList;

import app.activities.interfaces.IonClicked;
import app.customcomponents.HomeyProgressDialog;
import app.customcomponents.ScrollHorizontalWithItems;
import app.customcomponents.ScrollVerticalWithItems;
import app.logic.appcomponents.Group;
import app.logic.appcomponents.Task;
import app.logic.appcomponents.User;
import app.logic.managers.DBManager;
import app.logic.managers.EnvironmentManager;
import app.logic.managers.Services;
import app.logic.managers.SessionManager;
import app.logic.managers.TaskManager;
import callback.GoToTaskPageCallBack;
import callback.TasksCallBack;
import callback.UsersCallBack;

/**
 * Created by Raz on 7/6/2017
 */

public class GroupPageActivity extends ActivityWithHeaderBase {
    private Group group;
    private ScrollHorizontalWithItems participantsHolder;
    private HomeyProgressDialog pDialog;
    private ScrollVerticalWithItems scrollVerticalWithItems;
    private TextView screenName;
    private boolean isAdmin; // Ugly but necessary. The other option is to implement empty methods around the project.

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_page);
        scrollVerticalWithItems = (ScrollVerticalWithItems) findViewById(R.id.homePageActivityTasksHolder);

        screenName = (TextView) findViewById(R.id.textViewScreenName);
        screenName.setText(((EnvironmentManager) (Services.GetService(EnvironmentManager.class))).GetScreenName());

        Bundle b = this.getIntent().getExtras();
        if (b != null)
            group = b.getParcelable("group");
        participantsHolder = (ScrollHorizontalWithItems) findViewById(R.id.groupPageActivityParticipantsHolder);

        pDialog = new HomeyProgressDialog(this);
        loadTasks();
        ImageView image = (ImageView) findViewById(R.id.groupActivityImage);
        // TODO: Set group icon

        // Arrange group buttons
        arrangeButtons();
    }

    private void loadTasks() {
        pDialog.showDialog();
        Context context = this;

        GoToTaskPageCallBack taskClickCallBack = new GoToTaskPageCallBack() {
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

        IonClicked taskLayoutCheckBoxCallBack = this::onCheckBoxClicked;

        ((TaskManager) (Services.GetService(TaskManager.class))).GetGroupTasks(new TasksCallBack() {
            @Override
            public void onSuccess(ArrayList<Task> tasks) {
                scrollVerticalWithItems.SetTasks(tasks, taskClickCallBack, taskLayoutCheckBoxCallBack);
                pDialog.hideDialog();
            }

            @Override
            public void onFailure(String error) {
                pDialog.hideDialog();
            }
        }, Integer.parseInt(group.GetId()));
    }

    //******************************************************
    // Handles Add Member and Delete/Leave group buttons
    //******************************************************
    private void arrangeButtons() {
        Button addMember = (Button) findViewById(R.id.button_group_add_member);
        Button deleteLeave = (Button) findViewById(R.id.button_group_delete_leave);

        if (!isGroupAdmin()) {
            addMember.setEnabled(false);
            deleteLeave.setText(R.string.leave_group);
        } else {
            deleteLeave.setText(R.string.delete_group);
        }
    }

    //******************************************************
    // Checks if the user is one of the group admins
    //******************************************************
    private boolean isGroupAdmin() {
        ((DBManager) Services.GetService(DBManager.class)).GetGroupAdmins(Integer.parseInt(group.GetId()), new UsersCallBack() {
            @Override
            public void onSuccess(ArrayList<User> users) {
                isAdmin = false;
                User theUser = ((SessionManager) Services.GetService(SessionManager.class)).getUser();
                for (User user : users) {
                    if (user.GetUserId() == theUser.GetUserId()) {
                        isAdmin = true;
                        break;
                    }
                }
            }

            @Override
            public void onFailure(String error) {
            }
        });
        return isAdmin;
    }

    //******************************************************
    // Adds new member to the group
    //******************************************************
    public void buttonGroupAddMemberOnClick(View view) {
        // IMPORTANT! This method is only for testing don't look for logic here !!!
        Button addMember = (Button) findViewById(R.id.button_group_add_member);
        if (addMember.getText().equals(R.string.add_member)) {
            addMember.setText(R.string.added);
        } else {
            addMember.setText(R.string.add_member);
        }
    }

    //******************************************************
    // Adds new member to the group
    //******************************************************
    public void buttonGroupDeleteLeaveOnClick(View view) {
        // TODO
    }

    public void onCheckBoxClicked(View view) {
        //TODO
    }
}
