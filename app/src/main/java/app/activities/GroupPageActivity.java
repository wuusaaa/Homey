package app.activities;

import android.os.Bundle;
import android.widget.TextView;

import com.project.homey.R;

import java.util.ArrayList;

import app.customcomponents.HomeyProgressDialog;
import app.customcomponents.ScrollVerticalWithItems;
import app.logic.appcomponents.Group;
import app.logic.appcomponents.Task;
import app.logic.managers.EnvironmentManager;
import app.logic.managers.Services;
import app.logic.managers.TaskManager;
import callback.TasksCallBack;

/**
 * Created by Raz on 7/6/2017.
 */

public class GroupPageActivity extends ActivityBase {
    private Group group;
    private TextView groupDescription;
    private HomeyProgressDialog pDialog;
    private ScrollVerticalWithItems scrollVerticalWithItems;
    private TextView screenName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_page);
        scrollVerticalWithItems = (ScrollVerticalWithItems) findViewById(R.id.TasksHolder);

        screenName = (TextView) findViewById(R.id.textViewScreenName);
        screenName.setText(((EnvironmentManager) (Services.GetService(EnvironmentManager.class))).GetScreenName());

        Bundle b = this.getIntent().getExtras();
        if (b != null)
            group = b.getParcelable("group");
        groupDescription = (TextView) findViewById(R.id.groupDescription);

        groupDescription.setText(group.GetDescription());
        pDialog = new HomeyProgressDialog(this);
        loadTasks();
    }

    private void loadTasks() {
        pDialog.showDialog();
        ((TaskManager) (Services.GetService(TaskManager.class))).GetGroupTasks(new TasksCallBack() {
            @Override
            public void onSuccess(ArrayList<Task> tasks) {
                scrollVerticalWithItems.SetGroupTasks(tasks);
                pDialog.hideDialog();
            }

            @Override
            public void onFailure(String error) {
                pDialog.hideDialog();
            }
        },Integer.parseInt(group.GetId()));
    }
}
