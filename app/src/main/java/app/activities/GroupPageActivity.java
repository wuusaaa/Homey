package app.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.UserManager;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.project.homey.R;

import java.util.ArrayList;
import java.util.List;

import app.activities.interfaces.IHasImage;
import app.activities.interfaces.IHasText;
import app.customcomponents.HomeyProgressDialog;
import app.customcomponents.ScrollHorizontalWithItems;
import app.customcomponents.ScrollVerticalWithItems;
import app.customcomponents.TaskLayout;
import app.enums.TaskProperty;
import app.enums.TaskStatus;
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
import callback.TasksCallBack;
import callback.UpdateCallBack;
import callback.UpdateTask;
import callback.UsersCallBack;

/**
 * Created by Raz on 7/6/2017
 */

public class GroupPageActivity extends ActivityWithHeaderBase {
    private final String ALL = "All";
    private final String COMPLETED = "Completed";
    private final String INCOMPLETE = "Incomplete";
    private final String MY_TASKS = "My Tasks";
    private final String OTHERS = "Others";
    private Group group;
    private HomeyProgressDialog pDialog;
    private ScrollVerticalWithItems scrollVerticalWithItems;
    private ScrollHorizontalWithItems participantsHolder;
    private boolean isAdmin; // Ugly but necessary. The other option is to implement empty methods around the project.
    private Button buttonSubmit; // TODO: need to avoid duplicate code in home page activity
    private List<TaskLayout> taskLayoutsChecked = new ArrayList<>();// TODO: need to avoid duplicate code in home page activity
    private List<TaskLayout> taskLayoutsUnchecked = new ArrayList<>();// TODO: need to avoid duplicate code in home page activity

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_page);
        scrollVerticalWithItems = (ScrollVerticalWithItems) findViewById(R.id.groupPageActivityTasksHolder);
        participantsHolder = (ScrollHorizontalWithItems)findViewById (R.id.groupPageActivityParticipantsHolder);

        Bundle b = this.getIntent().getExtras();
        if (b != null)
            group = b.getParcelable("group");

        pDialog = new HomeyProgressDialog(this);
        loadTasks();
        loadUsers();
        ImageView image = (ImageView) findViewById(R.id.groupActivityImage);
        Bitmap bitMapImage = BitmapFactory.decodeByteArray(group.GetImage(), 0, group.GetImage().length);
        if (bitMapImage != null) {
            ((ImageView) findViewById(R.id.groupActivityImage)).setImageBitmap(bitMapImage);
        } else {
            ((ImageView) findViewById(R.id.groupActivityImage)).setImageResource(R.mipmap.ic_group_default);
        }

        buttonSubmit = (Button) findViewById(R.id.buttonSubmit);

        // Arrange group buttons
        arrangeButtons();

        tasksOwnerFilter();
        tasksCompleteFilter();
    }

    @Override
    protected void onStart() {
        super.onStart();

        screenName = (TextView) findViewById(R.id.textViewScreenName);
        screenName.setText(((EnvironmentManager) (Services.GetService(EnvironmentManager.class))).GetScreenName());
    }

    private void loadTasks() {
        pDialog.showDialog();
        Context context = this;

        ((TaskManager) (Services.GetService(TaskManager.class))).GetGroupTasks(new TasksCallBack() {
            @Override
            public void onSuccess(List<Task> tasks) {
                scrollVerticalWithItems.SetTasks(tasks, t ->
                                ((ActivityChangeManager) Services.GetService(ActivityChangeManager.class)).SetTaskActivity(context, t),
                        c -> onCheckBoxClicked(c));

                pDialog.hideDialog();
            }

            @Override
            public void onFailure(String error) {
                pDialog.hideDialog();
            }
        }, Integer.parseInt(group.GetId()));
    }

    private void loadUsers()
    {
        Context context = this;
        //TODO : Ruz : JSON ERROR
        ((DBManager) Services.GetService(DBManager.class)).GetGroupUsersByGroupId(Integer.parseInt(group.GetId()), new UsersCallBack()
        {
            @Override
            public void onSuccess(ArrayList<User> users)
            {
                participantsHolder.SetScrollerItems(users, LinearLayoutCompat.HORIZONTAL, new GotoGroupPageCallBack() {
                    @Override
                    public <T extends IHasImage & IHasText> void onSuccess(T user) {
                        ((ActivityChangeManager)Services.GetService(ActivityChangeManager.class)).SetProfileActivity(context, (User) user);
                    }

                    @Override
                    public void onFailure(String error)
                    {
                        //TODO handle error
                    }
                });
            }

            @Override
            public void onFailure(String error)
            {
                Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
            }
        });
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
                    if (user.GetUserId().equals(theUser.GetUserId())) {
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

    public void tasksOwnerFilter() {
        Spinner spinner = (Spinner) findViewById(R.id.spinnerTasksOwners);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                switch (selectedItem) {
                    case ALL:
                        showAllOwners();
                        break;
                    case MY_TASKS:
                        showMyTasks();
                        break;
                    case OTHERS:
                        showOthers();
                        break;
                }
            } // to close the onItemSelected

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void tasksCompleteFilter() {
        Spinner spinner = (Spinner) findViewById(R.id.spinnerCompleteOrIncomplete);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                switch (selectedItem) {
                    case ALL:
                        showAll();
                        break;
                    case COMPLETED:
                        showCompletedTasks();
                        break;
                    case INCOMPLETE:
                        showIncompleteTasks();
                        break;
                }
            } // to close the onItemSelected

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void showCompletedTasks() {
        scrollVerticalWithItems.showCompletedTasks();
    }

    private void showIncompleteTasks() {
        scrollVerticalWithItems.showIncompleteTasks();
    }

    private void showOthers() {
        scrollVerticalWithItems.filterOthers();
    }

    private void showMyTasks() {
        scrollVerticalWithItems.filterMyTasks();
    }

    private void showAllOwners() {
        scrollVerticalWithItems.showAllTasksOwners();
    }

    private void showAll() {
        scrollVerticalWithItems.showAllTasks();
    }

    private void initSubmitButton() {
        buttonSubmit.setLayoutParams(new LinearLayout.LayoutParams(0, 0));
        taskLayoutsChecked.clear();
        taskLayoutsUnchecked.clear();
    }

    public void onCheckBoxClicked(View view) {
        TaskLayout taskLayout = (TaskLayout) view;

        if (taskLayout.isChecked()) {
            if (taskLayoutsUnchecked.contains(taskLayout)) {
                taskLayoutsUnchecked.remove(taskLayout);
            } else {
                taskLayoutsChecked.add(taskLayout);
            }
        } else {
            if (taskLayoutsChecked.contains(taskLayout)) {
                taskLayoutsChecked.remove(taskLayout);

            } else {
                taskLayoutsUnchecked.add(taskLayout);
            }
        }

        if (taskLayoutsChecked.size() > 0 || taskLayoutsUnchecked.size() > 0) {
            buttonSubmit.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        } else {
            buttonSubmit.setLayoutParams(new LinearLayout.LayoutParams(0, 0));
        }
    }

    public void onSubmitClicked(View view) {
        UpdateCallBack updateCheckedTasks = new UpdateTask(this.getBaseContext(), taskLayoutsChecked.size(), this::refreshTasks);
        UpdateCallBack updateUncheckedTasks = new UpdateTask(this.getBaseContext(), taskLayoutsUnchecked.size(), this::refreshTasks);

        taskLayoutsChecked.forEach(taskLayout -> {
            taskLayout.getTask().setStatus(TaskStatus.COMPLETED);
            ((DBManager) (Services.GetService(DBManager.class))).UpdateTask(taskLayout.getTask().GetTaskId(),
                    TaskProperty.STATUS,
                    taskLayout.getTask().getStatus(),
                    updateCheckedTasks);
        });

        taskLayoutsUnchecked.forEach(taskLayout -> {
            taskLayout.getTask().setStatus(TaskStatus.INCOMPLETE);
            ((DBManager) (Services.GetService(DBManager.class))).UpdateTask(taskLayout.getTask().GetTaskId(),
                    TaskProperty.STATUS,
                    taskLayout.getTask().getStatus(),
                    updateUncheckedTasks);
        });
    }

    private void refreshTasks() {
        initSubmitButton();
        loadTasks();
    }
}
