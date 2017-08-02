package app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.project.homey.R;

import app.logic.appcomponents.Group;
import app.logic.managers.GroupManager;
import app.logic.managers.Services;
import callback.GroupCallBack;

public class AddGroupActivity extends ActivityWithHeaderBase {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group);
    }

    public void buttonAddGroup_onClick(View view) {
        GroupManager groupManager = (GroupManager) Services.GetService(GroupManager.class);
        String name = ((EditText) findViewById(R.id.editTextGroupName)).getText().toString();
        byte[] arr = {0, 5, 3, 2, 4, 5};
        groupManager.AddNewGroup(name, arr, new GroupCallBack() {
            @Override
            public void onSuccess(Group group) {
                ((EditText) findViewById(R.id.editTextGroupName)).setText("blabla");
            }

            @Override
            public void onFailure(String error) {
                ((EditText) findViewById(R.id.editTextGroupName)).setText(error);
            }
        });
    }

    public void buttonChangePicture_OnClick(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(intent, "android.intent.action.SEND_MULTIPLE"), 1);
    }
}
