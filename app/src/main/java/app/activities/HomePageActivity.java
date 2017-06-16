package app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.homey.R;

import java.util.ArrayList;

import app.customcomponents.ScrollHorizontalWithItems;
import app.customcomponents.ScrollVerticalWithItems;
import app.logic.appcomponents.Group;
import app.logic.appcomponents.Task;
import app.logic.managers.GroupManager;
import app.logic.managers.Services;
import app.logic.managers.SessionManager;
import app.logic.managers.TaskManager;
import callback.GroupsCallBack;
import callback.TasksCallBack;


public class HomePageActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    ImageButton plusButton;

    private TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        plusButton = (ImageButton) findViewById(R.id.buttonPlus);
        txt = (TextView) findViewById(R.id.textView11);
        txt.setText("Welcome " + ((SessionManager) (Services.GetService(SessionManager.class))).getUser().getName());

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

        loadGroups();
        loadTasks();
    }

    private void loadTasks() {
        final ScrollVerticalWithItems scrollVerticalWithItems = (ScrollVerticalWithItems) findViewById(R.id.TasksHolder);
        ((TaskManager) (Services.GetService(TaskManager.class))).GetUserTasks(new TasksCallBack() {
            @Override
            public void onSuccess(ArrayList<Task> tasks) {
                scrollVerticalWithItems.SetUserTasks(tasks);
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }

    private void loadGroups() {
        final ScrollHorizontalWithItems scrollHorizontalWithItems = (ScrollHorizontalWithItems) findViewById(R.id.GroupsHolder);
        ((GroupManager) (Services.GetService(GroupManager.class))).GetUserGroups(new GroupsCallBack() {
            @Override
            public void onSuccess(ArrayList<Group> groups) {
                scrollHorizontalWithItems.SetUserGroups(groups, LinearLayout.HORIZONTAL);
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }


}


