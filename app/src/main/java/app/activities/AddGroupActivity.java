package app.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.project.homey.R;

import app.logic.lib.Group;
import app.logic.managers.GroupManager;
import app.logic.managers.Services;
import callback.GroupCallBack;


public class AddGroupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group);
    }

    public void buttonAddGroup_onClick(View view) {
        GroupManager groupManager = (GroupManager) Services.GetService(GroupManager.class);
        String name = ((EditText) findViewById(R.id.editTextName)).getText().toString();
        byte[] arr = {0, 5, 3, 2, 4, 5};
        groupManager.AddNewGroup(name, arr, new GroupCallBack() {
            @Override
            public void onSuccess(Group group) {
                ((EditText) findViewById(R.id.editTextName)).setText("blabla");
            }

            @Override
            public void onFailure(String error) {
                ((EditText) findViewById(R.id.editTextName)).setText(error);
            }
        });
    }
}
