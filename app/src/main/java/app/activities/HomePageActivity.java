package app.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.TextView;

import com.project.homey.R;

import java.util.ArrayList;

import app.customcomponents.HomeyProgressDialog;
import app.customcomponents.ScrollHorizontalWithItems;
import app.customcomponents.ScrollVerticalWithItems;
import app.logic.appcomponents.Group;
import app.logic.appcomponents.Task;
import app.logic.managers.GroupManager;
import app.logic.managers.Services;
import app.logic.managers.SessionManager;
import app.logic.managers.TaskManager;
import callback.GotoGroupPageCallBack;
import callback.GroupsCallBack;
import callback.TasksCallBack;


public class HomePageActivity extends AppCompatActivity {

    //***** Class components: *****
    private BottomNavigationView bottomNavigationView;
    private ImageButton plusButton;
    private ScrollHorizontalWithItems scrollHorizontalWithItems;
    private ScrollVerticalWithItems scrollVerticalWithItems;
    private TextView txt;
    private HomeyProgressDialog pDialog;
    //*****************************

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        // Progress dialog
        pDialog = new HomeyProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);

        scrollHorizontalWithItems = (ScrollHorizontalWithItems) findViewById(R.id.GroupsHolder);
        scrollVerticalWithItems = (ScrollVerticalWithItems) findViewById(R.id.TasksHolder);
        plusButton = (ImageButton) findViewById(R.id.buttonPlus);
        txt = (TextView) findViewById(R.id.textView11);

        //TODO:: Remove the rest of the code from this function. (ben 17.6.17)
        plusButton.setOnClickListener(v -> {
            Intent intent = new Intent(HomePageActivity.this, PlusActivity.class);
            startActivity(intent);
        });

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent;
                switch (item.getItemId()) {
                    case R.id.today:
                        intent = new Intent(HomePageActivity.this, TestActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.assignment:
                        break;
                    case R.id.settings:
                        intent = new Intent(HomePageActivity.this, SettingsActivity.class);
                        startActivity(intent);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }

    private void loadTasks() {
        pDialog.showDialog();
        ((TaskManager) (Services.GetService(TaskManager.class))).GetUserTasks(new TasksCallBack() {
            @Override
            public void onSuccess(ArrayList<Task> tasks) {
                scrollVerticalWithItems.SetUserTasks(tasks);
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
        Context context=this;
        ((GroupManager) (Services.GetService(GroupManager.class))).GetUserGroups(new GroupsCallBack() {
            @Override
            public void onSuccess(ArrayList<Group> groups) {
                scrollHorizontalWithItems.SetUserGroups(groups, LinearLayoutCompat.HORIZONTAL, new GotoGroupPageCallBack() {
                    @Override
                    public void onSuccess(Group group) {
                        Intent i = new Intent(context, GroupPageActivity.class);
                        Bundle b = new Bundle();
                        b.putParcelable("group", group);
                        i.putExtras(b);
                        startActivity(i);
                    }

                    @Override
                    public void onFailure(String error) {

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

    @Override
    protected void onStart() {
        super.onStart();

        txt.setText("Welcome " + ((SessionManager) (Services.GetService(SessionManager.class))).getUser().getName());
        loadGroups();
        loadTasks();
    }
}


