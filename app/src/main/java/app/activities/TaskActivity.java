package app.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.project.homey.R;

import java.util.ArrayList;

import app.logic.appcomponents.Group;
import app.logic.managers.GroupManager;
import app.logic.managers.Services;
import callback.GroupsCallBack;


public class TaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        ScrollWithItems horizontalScrollView = (ScrollWithItems) findViewById(R.id.HSV);
        ((GroupManager) (Services.GetService(GroupManager.class))).GetUserGroups(new GroupsCallBack() {
            @Override
            public void onSuccess(ArrayList<Group> groups) {
                horizontalScrollView.SetUserGroups(groups);
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }
}
