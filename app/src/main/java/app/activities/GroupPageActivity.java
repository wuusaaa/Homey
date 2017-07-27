package app.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.homey.R;

import java.util.ArrayList;

import app.customcomponents.HomeyProgressDialog;
import app.customcomponents.ScrollHorizontalWithItems;
import app.customcomponents.ScrollVerticalWithItems;
import app.logic.appcomponents.Group;
import app.logic.appcomponents.Task;
import app.logic.managers.EnvironmentManager;
import app.logic.managers.Services;
import app.logic.managers.TaskManager;
import callback.GoToTaskPageCallBack;
import callback.TasksCallBack;

/**
 * Created by Raz on 7/6/2017
 */

public class GroupPageActivity extends ActivityWithHeaderBase {
    private Group group;
    private ScrollHorizontalWithItems participantsHolder;
    private HomeyProgressDialog pDialog;
    private ScrollVerticalWithItems scrollVerticalWithItems;
    private TextView screenName;

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

        ((TaskManager) (Services.GetService(TaskManager.class))).GetGroupTasks(new TasksCallBack() {
            @Override
            public void onSuccess(ArrayList<Task> tasks) {
                scrollVerticalWithItems.SetTasks(tasks, taskClickCallBack);
                pDialog.hideDialog();
            }

            @Override
            public void onFailure(String error) {
                pDialog.hideDialog();
            }
        }, Integer.parseInt(group.GetId()));
    }
}
