package app.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.project.homey.R;

import java.util.ArrayList;
import java.util.List;

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

        Bundle b = this.getIntent().getExtras();
        if (b != null)
            group = b.getParcelable("group");
        participantsHolder = (ScrollHorizontalWithItems) findViewById(R.id.groupPageActivityParticipantsHolder);

        pDialog = new HomeyProgressDialog(this);
        loadTasks();
        ImageView image = (ImageView) findViewById(R.id.groupActivityImage);
        // TODO: Set group icon
        if (group.GetImage() != null)
        {
            Bitmap bitMapImage = BitmapFactory.decodeByteArray(group.GetImage(), 0, group.GetImage().length);
            ((ImageView)findViewById(R.id.groupActivityImage)).setImageBitmap(bitMapImage);
        }
        else
        {
            ((ImageView)findViewById(R.id.groupActivityImage)).setImageResource(R.mipmap.ic_group_default);
        }

        // Arrange group buttons
        arrangeButtons();

        tasksOwnerFilter();
    }

    @Override
    protected void onStart() {
        super.onStart();

        screenName = (TextView) findViewById(R.id.textViewScreenName);
        screenName.setText(((EnvironmentManager) (Services.GetService(EnvironmentManager.class))).GetScreenName());
    }

    private void loadTasks() {
        pDialog.showDialog();

        ((TaskManager) (Services.GetService(TaskManager.class))).GetGroupTasks(new TasksCallBack() {
            @Override
            public void onSuccess(List<Task> tasks) {
                scrollVerticalWithItems.SetTasks(tasks, t -> goToTask(t), c -> onCheckBoxClicked(c));
                pDialog.hideDialog();
            }

            @Override
            public void onFailure(String error) {
                pDialog.hideDialog();
            }
        }, Integer.parseInt(group.GetId()));
    }

    private void goToTask(Task task) {
        Intent intent = new Intent(this, TaskActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("task", task);
        intent.putExtras(bundle);
        startActivity(intent);
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


    public void tasksOwnerFilter() {
        Spinner spinner = (Spinner) findViewById(R.id.spinnerTasksOwners);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                switch (selectedItem) {
                    case "All":
                        doAll();
                        break;
                    case "My Tasks":
                        doMyTasks();
                        break;

                    case "Others":
                        doOthers();
                        break;
                }
            } // to close the onItemSelected

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void doOthers() {
        scrollVerticalWithItems.filterOthers(this::goToTask, this::onCheckBoxClicked);
    }

    private void doMyTasks() {
        scrollVerticalWithItems.filterMyTasks(this::goToTask, this::onCheckBoxClicked);
    }

    private void doAll() {
        scrollVerticalWithItems.visibleAllItems(this::goToTask, this::onCheckBoxClicked);
    }
}
